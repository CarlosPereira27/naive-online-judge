package org.ufla.dcc.naivejudge.servico;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder.Redirect;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.ufla.dcc.naivejudge.modelo.enums.Categoria;
import org.ufla.dcc.naivejudge.modelo.enums.Estado;
import org.ufla.dcc.naivejudge.modelo.problema.AvaliacaoProblema;
import org.ufla.dcc.naivejudge.modelo.problema.InstanciaProblema;
import org.ufla.dcc.naivejudge.modelo.problema.Problema;
import org.ufla.dcc.naivejudge.modelo.problema.Submissao;
import org.ufla.dcc.naivejudge.repositorio.ProblemaDao;
import org.ufla.dcc.naivejudge.repositorio.SubmissaoDao;
import org.ufla.dcc.naivejudge.repositorio.UsuarioDao;
import org.ufla.dcc.naivejudge.servico.armazenamento.ArmazenamentoDeArquivoService;
import org.ufla.dcc.naivejudge.servico.armazenamento.ArmazenamentoService;

@Service
public class ProblemaServiceImpl implements ProblemaService {

  class InstanciaTeste implements Comparable<InstanciaTeste> {

    String nome;
    boolean entrada;
    boolean solucao;

    public InstanciaTeste(String nome) {
      this.nome = nome;
      entrada = false;
      solucao = false;
    }

    @Override
    public int compareTo(InstanciaTeste o) {
      return nome.compareTo(o.nome);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      InstanciaTeste other = (InstanciaTeste) obj;
      if (!getOuterType().equals(other.getOuterType()))
        return false;
      if (entrada != other.entrada)
        return false;
      return true;
    }

    public String getUnicoArquivo() {
      return nome + "." + getUnicaExtensao();
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + getOuterType().hashCode();
      result = prime * result + (entrada ? 1231 : 1237);
      return result;
    }

    public boolean soTemUmArquivo() {
      return !(entrada && solucao);
    }

    private ProblemaServiceImpl getOuterType() {
      return ProblemaServiceImpl.this;
    }

    private String getUnicaExtensao() {
      if (entrada) {
        return EXTENSAO_ENTRADA;
      }
      return EXTENSAO_SOLUCAO;
    }

  }

  class ParLong {
    public long primeiro = 0;
    public long segundo = 0;
  }

  private static final String EXTENSAO_SOLUCAO = "sol";

  private static final String EXTENSAO_ENTRADA = "in";

  private static final String EXTENSAO_SAIDA = "out";
  // private static final String EXTENSAO_JAVA = "java";
  // private static final String ARQUIVO_PADRAO_JAVA = "Main.java";

  @Autowired
  ProblemaDao problemaDao;

  @Autowired
  UsuarioDao usuarioDao;
  @Autowired
  SubmissaoDao submissaoDao;
  @Autowired
  ArmazenamentoService armazenamentoService;

  private boolean malFormatado;

  @Override
  @Transactional
  public void cadastrarProblema(Problema problema, MultipartFile[] arqTestes,
      MultipartFile arqImpl) {
    problema.setEstatisticas(problemaDao.createProblemaEstatistica());
    problema.setForum(problemaDao.createAForum());
    AvaliacaoProblema avaliacaoProblema = problema.getAvaliacao();
    avaliacaoProblema.setImplementacao(arqImpl.getOriginalFilename());
    problemaDao.saveAvaliacaoProblema(avaliacaoProblema);
    problemaDao.criarInstancias(avaliacaoProblema, extrairNomesDeTestes(arqTestes));
    problemaDao.createProblema(problema);
    String diretorio = ArmazenamentoDeArquivoService.DIRETORIO_RAIZ + "problema_"
        + String.valueOf(problema.getId());
    File diretorioFile = new File(diretorio);
    if (!diretorioFile.exists()) {
      diretorioFile.mkdirs();
    }
    armazenamentoService.armazenar(arqImpl, diretorio);
    for (MultipartFile arqTeste : arqTestes) {
      armazenamentoService.armazenar(arqTeste, diretorio);
    }
    try {
      Runtime.getRuntime().exec("javac " + diretorio + "/" + arqImpl.getOriginalFilename());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  @Transactional
  public Problema getProblema(Integer problemaId) {
    return problemaDao.getProblema(problemaId);
  }

  @Override
  @Transactional
  public List<Problema> getProblemas() {
    return problemaDao.getProblemas();
  }

  @Override
  @Transactional
  public List<Problema> getProblemas(Categoria categoria) {
    return problemaDao.getProblemas(categoria);
  }

  @Override
  @Transactional
  public void processarSubmissao(Submissao submissao) {
    AvaliacaoProblema avaliacao = problemaDao.carregarAvaliacao(submissao.getProblema());
    List<Submissao> submissoesProblema =
        submissaoDao.getSubmissoes(submissao.getAutor(), submissao.getProblema());
    boolean novoProblema = submissoesProblema.isEmpty();
    boolean naoResolvido = !resolvido(submissoesProblema);
    String local = ArmazenamentoDeArquivoService.DIRETORIO_RAIZ + "submissao_"
        + String.valueOf(submissao.getAutor().getId());
    File diretorio = new File(local);
    System.out.println("diretó");
    if (!diretorio.exists()) {
      diretorio.mkdirs();
    }
    File arquivo = new File(local + "/Main.java");
    if (arquivo.exists()) {
      arquivo.delete();
    }
    Process process = null;
    try {
      arquivo.createNewFile();
      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(arquivo));
      bufferedWriter.write(submissao.getImplementacao());
      bufferedWriter.close();
      process = Runtime.getRuntime().exec("javac " + arquivo.getAbsolutePath());
      process.waitFor();
    } catch (IOException e) {
      System.out.println("---CARLOS_EXCEPTION---");
      e.printStackTrace();
      throw new RuntimeException(e);
    } catch (InterruptedException e) {
      System.out.println("---CARLOS_EXCEPTION---");
      e.printStackTrace();
    }
    if (process.exitValue() != 0) {
      submissao.setMensagem(getMensagem(process.getErrorStream()));
      submissao.setEstado(Estado.ERRO_DE_COMPILACAO);
    } else {
      validarResultados(submissao, avaliacao, local);
    }
    submissaoDao.salvarOuAtualizarSubmissao(submissao);
    usuarioDao.atualizarEstatisticas(submissao, novoProblema, naoResolvido);
    problemaDao.atualizarEstatisticas(submissao.getProblema(), naoResolvido, submissao.getEstado());
  }

  private SortedSet<String> converteInstanciaTeste(Map<String, InstanciaTeste> instanciasTeste) {
    SortedSet<String> nomesTestes = new TreeSet<>();
    for (InstanciaTeste instanciaTeste : instanciasTeste.values()) {
      nomesTestes.add(instanciaTeste.nome);
    }
    return nomesTestes;
  }

  private SortedSet<String> extrairNomesDeTestes(MultipartFile[] arqTestes) {
    Map<String, InstanciaTeste> instanciasTeste = new TreeMap<>();
    Set<String> arquivosErrados = new TreeSet<>();
    for (MultipartFile arq : arqTestes) {
      String[] arraySplit = arq.getOriginalFilename().split("\\.");
      if (arraySplit.length != 2 || !isEntradaOuSaida(arraySplit[1])) {
        arquivosErrados.add(arq.getOriginalFilename());
        continue;
      }
      InstanciaTeste instanciaTeste =
          instanciasTeste.getOrDefault(arraySplit[0], new InstanciaTeste(arraySplit[0]));
      if ((instanciaTeste.entrada && arraySplit[1].equals(EXTENSAO_ENTRADA))
          || (instanciaTeste.solucao && arraySplit[1].equals(EXTENSAO_SAIDA))) {
        throw new RuntimeException(
            "Erro! Arquivo de teste duplicado " + arq.getOriginalFilename() + "!");
      }
      if (arraySplit[1].equals(EXTENSAO_ENTRADA)) {
        instanciaTeste.entrada = true;
      } else {
        instanciaTeste.solucao = true;
      }
      instanciasTeste.put(arraySplit[0], instanciaTeste);
    }
    StringBuilder mensagemErro = new StringBuilder();
    if (temErro(instanciasTeste)) {
      mensagemErro.append(getMensagemErro(instanciasTeste));
    }
    if (!arquivosErrados.isEmpty()) {
      mensagemErro.append(getMensagemErroArquivosErrados(arquivosErrados));
    }
    if (mensagemErro.length() > 0) {
      throw new RuntimeException("Erro nos arquivos de teste!<br>" + mensagemErro.toString());
    }
    return converteInstanciaTeste(instanciasTeste);
  }

  private String getMensagem(InputStream input) {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
    StringBuilder mensagemDeErro = new StringBuilder();
    String linha;
    try {
      while ((linha = bufferedReader.readLine()) != null) {
        mensagemDeErro.append(linha).append('\n');
      }
      bufferedReader.close();
    } catch (IOException e1) {
      e1.printStackTrace();
      return null;
    }
    return mensagemDeErro.toString();
  }

  private String getMensagemErro(Map<String, InstanciaTeste> instanciasTeste) {
    StringBuilder sb = new StringBuilder();
    sb.append("Os seguintes arquivos não contêm seus respectivos pares (in/sol):<br>");
    for (InstanciaTeste instanciaTeste : instanciasTeste.values()) {
      if (instanciaTeste.soTemUmArquivo()) {
        sb.append(instanciaTeste.getUnicoArquivo()).append(", <br>");
      }
    }
    int n = sb.length();
    sb.delete(n - 6, n).append("<br>");
    return sb.toString();
  }

  private String getMensagemErroArquivosErrados(Set<String> arquivosErrados) {
    StringBuilder sb = new StringBuilder();
    sb.append("Os seguintes arquivos não respeitam o padrão de arquivos de teste (in/sol):<br>");
    for (String arq : arquivosErrados) {
      sb.append(arq).append(", <br>");
    }
    int n = sb.length();
    sb.delete(n - 6, n).append("<br>");
    return sb.toString();
  }

  private boolean isEntradaOuSaida(String ext) {
    return ext != null && (ext.equals(EXTENSAO_ENTRADA) || ext.equals(EXTENSAO_SOLUCAO));
  }

  private boolean resolvido(List<Submissao> submissoesProblema) {
    for (Submissao submissao : submissoesProblema) {
      if (submissao.getEstado().equals(Estado.CORRETO)) {
        return true;
      }
    }
    return false;
  }

  private boolean temErro(Map<String, InstanciaTeste> instanciasTeste) {
    for (InstanciaTeste instanciaTeste : instanciasTeste.values()) {
      if (instanciaTeste.soTemUmArquivo()) {
        return true;
      }
    }
    return false;
  }

  private ParLong validarInstancia(File out, File sol) {
    ParLong par = new ParLong();
    try {
      BufferedReader brOut = new BufferedReader(new FileReader(out));
      BufferedReader brSol = new BufferedReader(new FileReader(sol));
      int cSol;
      int cOut;
      while ((cSol = brSol.read()) != -1) {
        cOut = brOut.read();
        if (Character.isWhitespace(cSol)) {
          if (!Character.isWhitespace(cOut)) {
            this.malFormatado = true;
            do {
              cSol = brSol.read();
            } while (Character.isWhitespace(cSol));
          } else if (cOut != cSol) {
            this.malFormatado = true;
            continue;
          } else {
            continue;
          }
        }
        if (Character.isWhitespace(cOut)) {
          this.malFormatado = true;
          do {
            cOut = brOut.read();
          } while (Character.isWhitespace(cOut));
        }
        if (cOut == cSol) {
          par.primeiro++;
        } else {
          par.segundo++;
        }
      }
      while ((cOut = brOut.read()) != -1) {
        if (Character.isWhitespace(cOut)) {
          this.malFormatado = true;
          do {
            cOut = brOut.read();
          } while (Character.isWhitespace(cOut));
          if (cOut != -1) {
            par.segundo++;
          }
        } else {
          par.segundo++;
        }
      }
      brOut.close();
      brSol.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    return par;
  }

  private void validarResultados(Submissao submissao, AvaliacaoProblema avaliacao, String local) {
    long contAcertos = 0;
    long contErros = 0;
    final long TEMPO_MAX = submissao.getProblema().getTempoLimite() * 2;
    long tempo = 0;
    long tempoSoma = 0;
    boolean falhou = false;
    this.malFormatado = false;
    String diretorio = ArmazenamentoDeArquivoService.DIRETORIO_RAIZ + "problema_"
        + String.valueOf(submissao.getProblema().getId()) + "/";
    File out = new File(local + "/inst.out");
    for (InstanciaProblema instancia : avaliacao.getInstancias()) {
      try {
        out.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
      File in = new File(diretorio + instancia.getArquivoEntrada());
      File sol = new File(diretorio + instancia.getArquivoSaida());
      tempo = System.currentTimeMillis();
      ProcessBuilder processBuilder = new ProcessBuilder("java", "-cp", local, "Main");
      processBuilder.redirectInput(Redirect.from(in));
      processBuilder.redirectOutput(Redirect.to(out));
      Process process = null;
      try {
        process = processBuilder.start();
        if (!process.waitFor(TEMPO_MAX, TimeUnit.MILLISECONDS)) {
          submissao.setMensagem(Estado.TEMPO_EXCEDIDO.getNome());
          submissao.setEstado(Estado.TEMPO_EXCEDIDO);
          falhou = true;
        } else if (process.exitValue() != 0) {
          submissao.setMensagem(getMensagem(process.getErrorStream()));
          submissao.setEstado(Estado.ERRO_DE_EXECUCAO);
          falhou = true;
        }
      } catch (IOException e) {
        e.printStackTrace();
      } catch (InterruptedException e) {
        if (process != null) {
          System.out.println(getMensagem(process.getErrorStream()));
        }
        e.printStackTrace();
      } catch (IllegalThreadStateException e) {
        e.printStackTrace();
      }
      tempo = System.currentTimeMillis() - tempo;
      tempoSoma += tempo;
      ParLong par = validarInstancia(out, sol);
      contAcertos += par.primeiro;
      contErros += par.segundo;
      out.delete();
      if (falhou) {
        return;
      }
    }
    submissao.setTempo((int) tempoSoma / avaliacao.getInstancias().size());
    if (contErros == 0 && malFormatado) {
      submissao.setEstado(Estado.MAL_FORMATADO);
      submissao.setMensagem("Resposta mal formatada!");

    } else if (contErros == 0) {
      submissao.setEstado(Estado.CORRETO);
      submissao.setMensagem("Resposta correta!");
    } else {
      double porcentagemErro = (contErros / (double) (contAcertos + contErros)) * 100;
      int porcentagemErroInt = (int) Math.round(porcentagemErro);
      submissao.setMensagem(String.format("Resposta incorreta (%d)%%.", porcentagemErroInt));
      submissao.setEstado(Estado.RESPOSTA_INCORRETA);
    }
  }

}
