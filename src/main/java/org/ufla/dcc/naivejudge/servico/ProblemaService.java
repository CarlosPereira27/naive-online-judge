package org.ufla.dcc.naivejudge.servico;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import org.ufla.dcc.naivejudge.modelo.enums.Categoria;
import org.ufla.dcc.naivejudge.modelo.problema.Problema;
import org.ufla.dcc.naivejudge.modelo.problema.Submissao;

public interface ProblemaService {

  void cadastrarProblema(Problema problema, MultipartFile[] arqTestes, MultipartFile arqImpl);

  Problema getProblema(Integer problemaId);

  List<Problema> getProblemas();

  List<Problema> getProblemas(Categoria categoria);

  void processarSubmissao(Submissao submissao);

}
