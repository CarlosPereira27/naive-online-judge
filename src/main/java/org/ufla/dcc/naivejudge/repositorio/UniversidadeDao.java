package org.ufla.dcc.naivejudge.repositorio;

import java.util.List;
import org.ufla.dcc.naivejudge.modelo.usuario.Universidade;

public interface UniversidadeDao {

  Universidade getUniversidade(Integer id);

  Universidade getUniversidade(String nome);

  List<Universidade> getUniversidades();

  boolean registrar(Universidade universidade);

}
