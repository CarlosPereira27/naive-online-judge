package org.ufla.dcc.naivejudge.servico.armazenamento;

import java.nio.file.Path;
import java.util.stream.Stream;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ArmazenamentoService {

  void armazenar(MultipartFile arquivo, String diretorio);

  Path carregar(String nomeDoArquivo);

  Resource carregarComoResource(String nomeDoArquivo);

  Stream<Path> carregarTodos();

  void deletarTodos();

  void inicializar();

}
