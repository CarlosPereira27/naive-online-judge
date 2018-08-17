package org.ufla.dcc.naivejudge.servico;

import java.util.List;
import org.ufla.dcc.naivejudge.modelo.usuario.Universidade;
import org.ufla.dcc.naivejudge.modelo.usuario.Usuario;

public interface UniversidadeService {

  boolean registrar(Universidade universidade);

  List<Universidade> universidades();

  List<Usuario> usuariosRank(Universidade universidade);

}
