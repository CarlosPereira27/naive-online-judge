package org.ufla.dcc.naivejudge.repositorio;

import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.ufla.dcc.naivejudge.modelo.problema.Problema;
import org.ufla.dcc.naivejudge.modelo.problema.Submissao;
import org.ufla.dcc.naivejudge.modelo.problema.Submissao_;
import org.ufla.dcc.naivejudge.modelo.usuario.Usuario;

@Repository
public class SubmissaoDaoImpl implements SubmissaoDao {

  private SessionFactory sessionFactory;

  @Autowired
  public SubmissaoDaoImpl(EntityManagerFactory entityManagerFactory) {
    sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
  }

  @Override
  public Submissao getSubmissao(Integer id) {
    Session sessao = sessionFactory.getCurrentSession();

    CriteriaBuilder builder = sessao.getCriteriaBuilder();
    CriteriaQuery<Submissao> query = builder.createQuery(Submissao.class);
    Root<Submissao> root = query.from(Submissao.class);
    query.select(root).where(builder.equal(root.get(Submissao_.id), id));

    try {
      return sessao.createQuery(query).getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public List<Submissao> getSubmissoes(Usuario usuario) {
    Session sessao = sessionFactory.getCurrentSession();

    CriteriaBuilder builder = sessao.getCriteriaBuilder();
    CriteriaQuery<Submissao> query = builder.createQuery(Submissao.class);
    Root<Submissao> root = query.from(Submissao.class);
    query.select(root).where(builder.equal(root.get(Submissao_.autor), usuario))
        .orderBy(builder.desc(root.get(Submissao_.id)));

    return sessao.createQuery(query).getResultList();
  }

  @Override
  public List<Submissao> getSubmissoes(Usuario usuario, Problema problema) {
    Session sessao = sessionFactory.getCurrentSession();

    // SELECT * FROM Submissao
    // WHERE autor_id = ? and problema_id = ?
    CriteriaBuilder builder = sessao.getCriteriaBuilder();
    CriteriaQuery<Submissao> query = builder.createQuery(Submissao.class);
    Root<Submissao> root = query.from(Submissao.class);
    query.select(root)
        .where(builder.and(builder.equal(root.get(Submissao_.autor), usuario),
            builder.equal(root.get(Submissao_.problema), problema)))
        .orderBy(builder.desc(root.get(Submissao_.id)));

    return sessao.createQuery(query).getResultList();
  }

  @Override
  public void salvarOuAtualizarSubmissao(Submissao submissao) {
    Session sessao = sessionFactory.getCurrentSession();
    sessao.saveOrUpdate(submissao);
  }

}
