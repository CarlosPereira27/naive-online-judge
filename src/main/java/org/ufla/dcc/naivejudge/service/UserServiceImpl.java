package org.ufla.dcc.naivejudge.servico;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ufla.dcc.naivejudge.modelo.est.ProgressoUsuario;
import org.ufla.dcc.naivejudge.modelo.problema.CategoriaEstatisticas;
import org.ufla.dcc.naivejudge.modelo.usuario.Login;
import org.ufla.dcc.naivejudge.modelo.usuario.Universidade;
import org.ufla.dcc.naivejudge.modelo.usuario.Usuario;
import org.ufla.dcc.naivejudge.repositorio.ProblemaDao;
import org.ufla.dcc.naivejudge.repositorio.UniversidadeDao;
import org.ufla.dcc.naivejudge.repositorio.UsuarioDao;

@Service
public class UsuarioServiceImpl implements UsuarioService {

  @Autowired
  private UsuarioDao usuarioDao;

  @Autowired
  private UniversidadeDao universidadeDao;

  @Autowired
  private ProblemaDao problemaDao;

  @Override
  @Transactional
  public boolean atualizar(Usuario usuario) {
    Universidade universidade = universidadeDao.getUniversidade(usuario.getUniversidade().getId());
    usuario.setUniversidade(universidade);
    return usuarioDao.atualizar(usuario);
  }

  @Override
  @Transactional
  public Usuario getUsuario(Integer id) {
    return usuarioDao.getUsuario(id);
  }

  @Override
  @Transactional
  public ProgressoUsuario getUsuarioEstatistica(Integer id) {
    ProgressoUsuario progressoUsuario = new ProgressoUsuario();
    Usuario usuario = usuarioDao.getUsuario(id);
    if (usuario == null) {
      return null;
    }
    progressoUsuario.setUsuario(usuario);
    progressoUsuario.setUsuariosTop(usuarioDao.getUsuarios(20));
    List<CategoriaEstatisticas> categoriasEst = problemaDao.getCategoriasEst();
    progressoUsuario.setProgresso(usuarioDao.getUsuarioProgesso(usuario, categoriasEst));
    return progressoUsuario;
  }

  @Override
  @Transactional
  public List<Usuario> getUsuarios() {
    return usuarioDao.getUsuarios();
  }

  @Override
  @Transactional
  public List<Usuario> getUsuariosTop() {
    return usuarioDao.getUsuarios(20);
  }

  @Override
  @Transactional
  public boolean registrar(Usuario usuario) {
    return usuarioDao.registrar(usuario);
  }

  @Override
  @Transactional
  public Usuario validarUsario(Login login) {
    Usuario usuario = usuarioDao.validarUsario(login);
    return usuario;
  }

}
