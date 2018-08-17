package org.ufla.dcc.naivejudge.servico;

import java.util.List;
import org.ufla.dcc.naivejudge.modelo.est.ProgressoUsuario;
import org.ufla.dcc.naivejudge.modelo.usuario.Login;
import org.ufla.dcc.naivejudge.modelo.usuario.Usuario;

public interface UsuarioService {

  boolean atualizar(Usuario usuario);

  Usuario getUsuario(Integer id);

  ProgressoUsuario getUsuarioEstatistica(Integer id);

  List<Usuario> getUsuarios();

  List<Usuario> getUsuariosTop();

  boolean registrar(Usuario usuario);

  Usuario validarUsario(Login login);

}
