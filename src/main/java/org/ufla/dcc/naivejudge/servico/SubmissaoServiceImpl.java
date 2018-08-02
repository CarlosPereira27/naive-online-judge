package org.ufla.dcc.naivejudge.servico;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ufla.dcc.naivejudge.modelo.problema.Submissao;
import org.ufla.dcc.naivejudge.modelo.usuario.Usuario;
import org.ufla.dcc.naivejudge.repositorio.SubmissaoDao;

@Service
public class SubmissaoServiceImpl implements SubmissaoService {

  @Autowired
  SubmissaoDao submissaoDao;

  @Override
  @Transactional
  public Submissao getSubmissao(Integer submissaoId) {
    return submissaoDao.getSubmissao(submissaoId);
  }

  @Override
  @Transactional
  public List<Submissao> getSubmissoes(Usuario usuario) {
    return submissaoDao.getSubmissoes(usuario);
  }

}
