package org.ufla.dcc.naivejudge.repositorio;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.ufla.dcc.naivejudge.modelo.enums.Categoria;
import org.ufla.dcc.naivejudge.modelo.enums.Estado;
import org.ufla.dcc.naivejudge.modelo.est.Progresso;
import org.ufla.dcc.naivejudge.modelo.est.ProgressoCategoria;
import org.ufla.dcc.naivejudge.modelo.problema.CategoriaEstatisticas;
import org.ufla.dcc.naivejudge.modelo.problema.Submissao;
import org.ufla.dcc.naivejudge.modelo.usuario.Login;
import org.ufla.dcc.naivejudge.modelo.usuario.Universidade;
import org.ufla.dcc.naivejudge.modelo.usuario.Usuario;
import org.ufla.dcc.naivejudge.modelo.usuario.UsuarioCatEst;
import org.ufla.dcc.naivejudge.modelo.usuario.UsuarioCatEst_;
import org.ufla.dcc.naivejudge.modelo.usuario.UsuarioEstatistica;
import org.ufla.dcc.naivejudge.modelo.usuario.UsuarioEstatistica_;
import org.ufla.dcc.naivejudge.modelo.usuario.Usuario_;

@Repository
public class UsuarioDaoImpl implements UsuarioDao {

  private SessionFactory sessionFactory;

  @Autowired
  public UsuarioDaoImpl(EntityManagerFactory entityManagerFactory) {
    sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
  }

  @Override
  public boolean atualizar(Usuario usuario) {
    Session sessao = sessionFactory.getCurrentSession();
    Usuario usuarioEmail = getUsuario(usuario.getEmail());
    Universidade universidadeEmail = null;
    if (usuarioEmail != null) {
      if (!usuario.getId().equals(usuarioEmail.getId())) {
        return false;
      }
      usuario.setEstatistica(usuarioEmail.getEstatistica());
      usuario.setDataInsercao(usuarioEmail.getDataInsercao());
      universidadeEmail = usuarioEmail.getUniversidade();
      if (universidadeEmail != null && !universidadeEmail.equals(usuario.getUniversidade())) {
        universidadeEmail.desvincularEstudante(usuarioEmail);
        sessao.saveOrUpdate(universidadeEmail);
      }
      sessao.detach(usuarioEmail);
    }
    Universidade universidade = usuario.getUniversidade();
    if (universidade != null && !universidade.equals(universidadeEmail)) {
      universidade.vincularEstudante(usuario);
      sessao.saveOrUpdate(universidade);
    }
    sessao.saveOrUpdate(usuario);
    return true;
  }

  @Override
  public void atualizarEstatisticas(Submissao submissao, boolean novoProblema,
      boolean naoResolvido) {
    Session sessao = sessionFactory.getCurrentSession();
    sessao.refresh(submissao);
    Usuario usuario = submissao.getAutor();
    UsuarioEstatistica estatistica = usuario.getEstatistica();
    estatistica.atualizarSubmissao(novoProblema, naoResolvido, submissao.getEstado());
    UsuarioCatEst usuarioCatEst = geUsuarioCatEst(usuario, submissao.getProblema().getCategoria());
    usuarioCatEst.atualizarSubmissao(novoProblema, naoResolvido, submissao.getEstado());
    sessao.saveOrUpdate(usuarioCatEst);
    sessao.saveOrUpdate(usuario);
    if (submissao.getEstado().equals(Estado.CORRETO) && naoResolvido) {
      Universidade universidade = usuario.getUniversidade();
      if (universidade != null) {
        universidade.setQtdProblemasResolvidos(universidade.getQtdProblemasResolvidos() + 1);
        sessao.saveOrUpdate(universidade);
      }
    }
  }

  @Override
  public Usuario getUsuario(Integer id) {
    Session sessao = sessionFactory.getCurrentSession();

    CriteriaBuilder builder = sessao.getCriteriaBuilder();
    CriteriaQuery<Usuario> query = builder.createQuery(Usuario.class);
    Root<Usuario> root = query.from(Usuario.class);
    query.select(root).where(builder.equal(root.get(Usuario_.id), id));

    try {
      return sessao.createQuery(query).getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public Usuario getUsuario(String email) {
    Session sessao = sessionFactory.getCurrentSession();

    CriteriaBuilder builder = sessao.getCriteriaBuilder();
    CriteriaQuery<Usuario> query = builder.createQuery(Usuario.class);
    Root<Usuario> root = query.from(Usuario.class);
    query.select(root).where(builder.equal(root.get(Usuario_.email), email));

    try {
      return sessao.createQuery(query).getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public Progresso getUsuarioProgesso(Usuario usuario, List<CategoriaEstatisticas> categoriasEst) {
    List<UsuarioCatEst> usuarioCatEst = usuario.getEstatisticasCategoria();
    Progresso progresso = new Progresso();
    int sumProblemas = 0;
    int sumProblemasResolvidos = 0;
    final int TAMANHO = categoriasEst.size();
    for (int i = 0; i < TAMANHO; i++) {
      Categoria categoria = categoriasEst.get(i).getCategoria();
      int problemas = categoriasEst.get(i).getQtdProblemas();
      int problemasResolvidos = usuarioCatEst.get(i).getQtdProblemasResolvidos();
      sumProblemas += problemas;
      sumProblemasResolvidos += problemasResolvidos;
      double porcentagem = porcentagem(problemasResolvidos, problemas);
      progresso.addProgressoCategoria(new ProgressoCategoria(categoria, porcentagem));
    }
    progresso.setProgressoGeral(porcentagem(sumProblemasResolvidos, sumProblemas));
    return progresso;
  }

  @Override
  public List<Usuario> getUsuarios() {
    Session sessao = sessionFactory.getCurrentSession();

    CriteriaBuilder builder = sessao.getCriteriaBuilder();
    CriteriaQuery<Usuario> query = builder.createQuery(Usuario.class);
    Root<Usuario> root = query.from(Usuario.class);
    Path<UsuarioEstatistica> path = root.get(Usuario_.estatistica);
    List<Order> orderList = new ArrayList<>();
    System.out.println("GET_USUARIOS");
    orderList.add(builder.desc(path.get(UsuarioEstatistica_.qtdProblemasResolvidos)));
    orderList.add(builder.asc(path.get(UsuarioEstatistica_.qtdSubmissoes)));
    query.select(root).orderBy(orderList);
    return sessao.createQuery(query).getResultList();
  }

  @Override
  public List<Usuario> getUsuarios(int max) {
    Session sessao = sessionFactory.getCurrentSession();

    CriteriaBuilder builder = sessao.getCriteriaBuilder();
    CriteriaQuery<Usuario> query = builder.createQuery(Usuario.class);
    Root<Usuario> root = query.from(Usuario.class);
    Path<UsuarioEstatistica> path = root.get(Usuario_.estatistica);
    List<Order> orderList = new ArrayList<>();
    orderList.add(builder.desc(path.get(UsuarioEstatistica_.qtdProblemasResolvidos)));
    orderList.add(builder.asc(path.get(UsuarioEstatistica_.qtdSubmissoes)));
    query.select(root).orderBy(orderList);

    return sessao.createQuery(query).setMaxResults(max).getResultList();
  }

  @Override
  public List<Usuario> getUsuarios(Universidade universidade) {
    Session sessao = sessionFactory.getCurrentSession();
    sessao.refresh(universidade);
    return universidade.getUsuarios();
  }

  @Override
  public UsuarioCatEst geUsuarioCatEst(Usuario usuario, Categoria categoria) {
    Session sessao = sessionFactory.getCurrentSession();
    CriteriaBuilder builder = sessao.getCriteriaBuilder();
    CriteriaQuery<UsuarioCatEst> query = builder.createQuery(UsuarioCatEst.class);
    Root<UsuarioCatEst> root = query.from(UsuarioCatEst.class);
    query.select(root).where(builder.and(builder.equal(root.get(UsuarioCatEst_.usuario), usuario),
        builder.equal(root.get(UsuarioCatEst_.categoria), categoria)));

    try {
      return sessao.createQuery(query).getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public boolean registrar(Usuario usuario) {
    Session sessao = sessionFactory.getCurrentSession();
    Usuario usuarioEmail = getUsuario(usuario.getEmail());
    if (usuarioEmail != null) {
      return false;
    }
    usuario.setEstatistica(new UsuarioEstatistica());
    sessao.save(usuario);
    criarUsuarioCategoriaEstatiscas(usuario);
    return true;
  }

  @Override
  public Usuario validarUsario(Login login) {
    Session sessao = sessionFactory.getCurrentSession();

    CriteriaBuilder builder = sessao.getCriteriaBuilder();
    CriteriaQuery<Usuario> query = builder.createQuery(Usuario.class);
    Root<Usuario> root = query.from(Usuario.class);
    query.select(root).where(builder.and(builder.equal(root.get(Usuario_.email), login.getEmail()),
        builder.equal(root.get(Usuario_.senha), login.getSenha())));
    try {
      return sessao.createQuery(query).getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  private void criarUsuarioCategoriaEstatiscas(Usuario usuario) {
    Session sessao = sessionFactory.getCurrentSession();
    for (Categoria cat : Categoria.values()) {
      UsuarioCatEst usuarioCatEst = new UsuarioCatEst();
      usuarioCatEst.setCategoria(cat);
      usuarioCatEst.setUsuario(usuario);
      sessao.save(usuarioCatEst);
    }
  }

  private double porcentagem(int quantidade, int total) {
    if (quantidade == total) {
      return 100d;
    }
    return (quantidade * 100) / (double) total;
  }

}
