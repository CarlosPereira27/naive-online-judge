package org.ufla.dcc.naivejudge.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder.Redirect;
import java.util.concurrent.TimeUnit;
import org.ufla.dcc.naivejudge.domain.user.Gender;
import org.ufla.dcc.naivejudge.service.storage.FileStorageService;

public class TesteProcess {

  static class ParLong {
    public long primeiro = 0;
    public long segundo = 0;
  }

  private final static String IMPLEMENTACAO =
      "import java.io.BufferedReader;\n" + "import java.io.BufferedWriter;\n"
          + "import java.io.IOException;\n" + "import java.io.InputStreamReader;\n"
          + "import java.io.OutputStreamWriter;\n" + "\n" + "public class Main {\n" + "\n"
          + "	public static void main(String[] args) throws IOException {\n"
          + "		BufferedReader in = new BufferedReader(\n"
          + "				new InputStreamReader(System.in));\n"
          + "		String[] strs = in.readLine().split(\" \");\n" + "		in.close();\n"
          + "		double h = Double.parseDouble(strs[0]);\n"
          + "		int p = Integer.parseInt(strs[1]);\n"
          + "		BufferedWriter out = new BufferedWriter(\n"
          + "				new OutputStreamWriter(System.out));\n"
          + "		out.write(String.format(\"%.2f\", h / p));\n" + "		out.close();\n" + "	}\n"
          + "\n" + "}\n";

  private static boolean malFormatado;

  public static void main(String[] args) {
    System.out.println(Gender.MALE);
    System.out.println("HELLO");
    System.exit(0);

    String diretorio = FileStorageService.ROOT_FOLDER + "problema_13/";

    String local = FileStorageService.ROOT_FOLDER + "submissao_1";

    File diretorioLocal = new File(local);
    if (!diretorioLocal.exists()) {
      diretorioLocal.mkdirs();
    }

    File arquivo = new File(diretorioLocal + "/Main.java");
    if (arquivo.exists()) {
      System.out.println("Delete arquivo:" + arquivo.delete());
    }
    Process process = null;
    try {
      System.out.println("Create arquivo:" + arquivo.createNewFile());
      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(arquivo));
      bufferedWriter.write(IMPLEMENTACAO);
      bufferedWriter.close();
      System.out.println("Exec -> javac " + arquivo.getAbsolutePath());
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
      System.out.println("ERRO");
      System.out.println(getMensagem(process.getErrorStream()));
    } else {
      long contAcertos = 0;
      long contErros = 0;
      @SuppressWarnings("unused")
      long tempoSoma = 0;
      boolean falhou = false;
      malFormatado = false;
      final long TEMPO_MAX = 2000;
      File out = new File(local + "/inst.out");
      for (int i = 1; i < 31; i++) {
        try {
          out.createNewFile();
        } catch (IOException e) {
          e.printStackTrace();
        }
        File in = new File(diretorio + "C_" + i + ".in");
        File sol = new File(diretorio + "C_" + i + ".sol");
        long tempo = System.currentTimeMillis();
        ProcessBuilder processBuilder = new ProcessBuilder("java", "-cp", local, "Main");

        processBuilder.redirectInput(Redirect.from(in));
        processBuilder.redirectOutput(Redirect.to(out));
        System.out.println(in.getAbsolutePath());
        System.out.println(out.getAbsolutePath());
        process = null;
        try {
          process = processBuilder.start();
          if (!process.waitFor(TEMPO_MAX, TimeUnit.MILLISECONDS)) {
            System.out.println("Estado.TEMPO_EXCEDIDO");
            falhou = true;
          } else if (process.exitValue() != 0) {
            System.out.println("Estado.ERRO_DE_EXECUCAO");
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
        ParLong par = validarSaida(out, sol);
        contAcertos += par.primeiro;
        contErros += par.segundo;
        out.delete();
        if (falhou) {
          return;
        }
      }
      if (contErros == 0 && malFormatado) {
        System.out.println("Resposta mal formatada!");
      } else if (contErros == 0) {
        System.out.println("Resposta correta!");
      } else {
        System.out.println("Erros -> " + (contErros / (double) (contAcertos + contErros) * 100));
      }
    }

  }

  private static String getMensagem(InputStream input) {
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

  private static ParLong validarSaida(File out, File sol) {
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
            malFormatado = true;
            do {
              cSol = brSol.read();
            } while (Character.isWhitespace(cSol));
          } else if (cOut != cSol) {
            malFormatado = true;
            continue;
          } else {
            continue;
          }

        }
        if (Character.isWhitespace(cOut)) {
          malFormatado = true;
          do {
            cOut = brOut.read();
          } while (Character.isWhitespace(cOut));

        }
        if (cOut == cSol) {
          par.primeiro++;
        } else {
          System.out.println("CARLOS_DIFERENTE -> '" + (char) cSol + "' - '" + (char) cOut + "'");
          System.out.println("CARLOS_DIFERENTE -> '" + cSol + "' - '" + cOut + "'");
          par.segundo++;
        }
      }
      while ((cOut = brOut.read()) != -1) {
        if (Character.isWhitespace(cOut)) {
          malFormatado = true;
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
}
