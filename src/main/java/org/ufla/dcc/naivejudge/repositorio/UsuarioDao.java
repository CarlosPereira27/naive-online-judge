package org.ufla.dcc.naivejudge.repositorio;

import java.util.List;
import org.ufla.dcc.naivejudge.modelo.enums.Categoria;
import org.ufla.dcc.naivejudge.modelo.est.Progresso;
import org.ufla.dcc.naivejudge.modelo.problema.CategoriaEstatisticas;
import org.ufla.dcc.naivejudge.modelo.problema.Submissao;
import org.ufla.dcc.naivejudge.modelo.usuario.Login;
import org.ufla.dcc.naivejudge.modelo.usuario.Universidade;
import org.ufla.dcc.naivejudge.modelo.usuario.Usuario;
import org.ufla.dcc.naivejudge.modelo.usuario.UsuarioCatEst;

public interface UsuarioDao {

  boolean atualizar(Usuario usuario);

  void atualizarEstatisticas(Submissao submissao, boolean novoProblema, boolean naoResolvido);

  Usuario getUsuario(Integer id);

  Usuario getUsuario(String email);

  Progresso getUsuarioProgesso(Usuario usuario, List<CategoriaEstatisticas> categoriasEst);

  List<Usuario> getUsuarios();

  List<Usuario> getUsuarios(int max);

  List<Usuario> getUsuarios(Universidade universidade);

  UsuarioCatEst geUsuarioCatEst(Usuario usuario, Categoria categoria);

  boolean registrar(Usuario usuario);

  Usuario validarUsario(Login login);

}
