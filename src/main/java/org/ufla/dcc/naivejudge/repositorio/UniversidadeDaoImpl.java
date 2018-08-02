package org.ufla.dcc.naivejudge.repositorio;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.ufla.dcc.naivejudge.modelo.usuario.Universidade;
import org.ufla.dcc.naivejudge.modelo.usuario.Universidade_;

@Repository
public class UniversidadeDaoImpl implements UniversidadeDao {

  private SessionFactory sessionFactory;

  @Autowired
  public UniversidadeDaoImpl(EntityManagerFactory entityManagerFactory) {
    sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
  }

  @Override
  public Universidade getUniversidade(Integer id) {
    Session sessao = sessionFactory.getCurrentSession();

    CriteriaBuilder builder = sessao.getCriteriaBuilder();
    CriteriaQuery<Universidade> query = builder.createQuery(Universidade.class);
    Root<Universidade> root = query.from(Universidade.class);
    query.select(root).where(builder.equal(root.get(Universidade_.id), id));

    try {
      return sessao.createQuery(query).getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public Universidade getUniversidade(String nome) {
    Session sessao = sessionFactory.getCurrentSession();

    CriteriaBuilder builder = sessao.getCriteriaBuilder();
    CriteriaQuery<Universidade> query = builder.createQuery(Universidade.class);
    Root<Universidade> root = query.from(Universidade.class);
    query.select(root).where(builder.equal(root.get(Universidade_.nome), nome));

    try {
      return sessao.createQuery(query).getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public List<Universidade> getUniversidades() {
    Session sessao = sessionFactory.getCurrentSession();

    // SELECT * FROM Universidade
    // ORDER BY qtdProblemasResolvidos DESC, qtdEstudantes DESC;
    CriteriaBuilder builder = sessao.getCriteriaBuilder();
    CriteriaQuery<Universidade> query = builder.createQuery(Universidade.class);
    Root<Universidade> root = query.from(Universidade.class);
    List<Order> orderList = new ArrayList<>();
    orderList.add(builder.desc(root.get(Universidade_.qtdProblemasResolvidos)));
    orderList.add(builder.desc(root.get(Universidade_.qtdEstudantes)));
    query.select(root).orderBy(orderList);

    return sessao.createQuery(query).getResultList();
  }

  @Override
  public boolean registrar(Universidade universidade) {
    Session sessao = sessionFactory.getCurrentSession();
    Universidade universidadeNome = getUniversidade(universidade.getNome());
    if (universidadeNome != null) {
      return false;
    }
    sessao.save(universidade);
    return true;
  }

}
