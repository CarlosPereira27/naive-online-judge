package org.ufla.dcc.naivejudge.servico.armazenamento;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ArmazenamentoDeArquivoService implements ArmazenamentoService {

  public static final String DIRETORIO_RAIZ = "/home/carlos/naive_judge_arquivos/";

  private final Path rootLocation =
      Paths.get(PropriedadesArmazenamento.getInstance().getDiretorio());

  @Autowired
  public ArmazenamentoDeArquivoService() {}

  @Override
  public void armazenar(MultipartFile arquivo, String diretorio) {
    try {
      if (arquivo.isEmpty()) {
        throw new RuntimeException("Failed to store empty file " + arquivo.getOriginalFilename());
      }

      File novoArquivo = new File(diretorio + "/" + arquivo.getOriginalFilename());
      Files.copy(arquivo.getInputStream(),
          this.rootLocation.resolve(novoArquivo.getAbsolutePath()));
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException("Failed to store file " + arquivo.getOriginalFilename(), e);
    }
  }

  @Override
  public Path carregar(String nomeDoArquivo) {
    return rootLocation.resolve(nomeDoArquivo);
  }

  @Override
  public Resource carregarComoResource(String nomeDoArquivo) {
    try {
      Path file = carregar(nomeDoArquivo);
      Resource resource = new UrlResource(file.toUri());
      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        return null;
      }
    } catch (MalformedURLException e) {
      throw new RuntimeException("Could not read file: " + nomeDoArquivo, e);
    }
  }

  @Override
  public Stream<Path> carregarTodos() {
    try {
      return Files.walk(this.rootLocation, 1).filter(path -> !path.equals(this.rootLocation))
          .map(path -> this.rootLocation.relativize(path));
    } catch (IOException e) {
      throw new RuntimeException("Failed to read stored files", e);
    }
  }

  @Override
  public void deletarTodos() {
    FileSystemUtils.deleteRecursively(rootLocation.toFile());
  }

  public void inicializar() {
    try {
      Files.createDirectory(rootLocation);
    } catch (IOException e) {

    }
  }

}
