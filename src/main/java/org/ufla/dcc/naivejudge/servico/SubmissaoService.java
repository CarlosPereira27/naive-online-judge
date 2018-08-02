package org.ufla.dcc.naivejudge.servico;

import java.util.List;
import org.ufla.dcc.naivejudge.modelo.problema.Submissao;
import org.ufla.dcc.naivejudge.modelo.usuario.Usuario;

public interface SubmissaoService {

  Submissao getSubmissao(Integer submissaoId);

  List<Submissao> getSubmissoes(Usuario usuario);

}
