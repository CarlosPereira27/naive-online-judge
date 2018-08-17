package org.ufla.dcc.naivejudge.repositorio;

import java.util.List;
import org.ufla.dcc.naivejudge.modelo.problema.Problema;
import org.ufla.dcc.naivejudge.modelo.problema.Submissao;
import org.ufla.dcc.naivejudge.modelo.usuario.Usuario;

public interface SubmissaoDao {

  Submissao getSubmissao(Integer id);

  List<Submissao> getSubmissoes(Usuario usuario);

  List<Submissao> getSubmissoes(Usuario usuario, Problema problema);

  void salvarOuAtualizarSubmissao(Submissao submissao);

}
