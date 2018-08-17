package org.ufla.dcc.naivejudge.servico;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ufla.dcc.naivejudge.modelo.usuario.Universidade;
import org.ufla.dcc.naivejudge.modelo.usuario.Usuario;
import org.ufla.dcc.naivejudge.repositorio.UniversidadeDao;
import org.ufla.dcc.naivejudge.repositorio.UsuarioDao;

@Service
public class UniversidadeServiceImpl implements UniversidadeService {

  @Autowired
  private UniversidadeDao universidadeDao;

  @Autowired
  private UsuarioDao usuarioDao;

  @Override
  @Transactional
  public boolean registrar(Universidade universidade) {
    return universidadeDao.registrar(universidade);
  }

  @Override
  @Transactional
  public List<Universidade> universidades() {
    return universidadeDao.getUniversidades();
  }

  @Override
  @Transactional
  public List<Usuario> usuariosRank(Universidade universidade) {
    return usuarioDao.getUsuarios(universidade);
  }

}
