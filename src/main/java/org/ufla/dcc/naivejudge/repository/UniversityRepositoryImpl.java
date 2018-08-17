package org.ufla.dcc.naivejudge.repository;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.ufla.dcc.naivejudge.domain.user.University;
import org.ufla.dcc.naivejudge.domain.user.University_;

@Repository
public class UniversityRepositoryImpl implements UniversityRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  public UniversityRepositoryImpl() {}

  @Override
  public List<University> getUniversities() {
    Session session = entityManager.unwrap(Session.class);
    // SELECT * FROM University
    // ORDER BY qtyAcceptedProblems DESC, qtyStudents DESC;
    CriteriaBuilder builder = session.getCriteriaBuilder();
    CriteriaQuery<University> query = builder.createQuery(University.class);
    Root<University> root = query.from(University.class);
    List<Order> orderList = new ArrayList<>();
    orderList.add(builder.desc(root.get(University_.qtyAcceptedProblems)));
    orderList.add(builder.desc(root.get(University_.qtyStudents)));
    query.select(root).orderBy(orderList);
    return session.createQuery(query).getResultList();
  }

  @Override
  public University getUniversity(Long id) {
    Session session = entityManager.unwrap(Session.class);
    CriteriaBuilder builder = session.getCriteriaBuilder();
    CriteriaQuery<University> query = builder.createQuery(University.class);
    Root<University> root = query.from(University.class);
    query.select(root).where(builder.equal(root.get(University_.id), id));
    try {
      return session.createQuery(query).getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public University getUniversity(String name) {
    Session session = entityManager.unwrap(Session.class);
    CriteriaBuilder builder = session.getCriteriaBuilder();
    CriteriaQuery<University> query = builder.createQuery(University.class);
    Root<University> root = query.from(University.class);
    query.select(root).where(builder.equal(root.get(University_.name), name));
    try {
      return session.createQuery(query).getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public boolean saveUniversity(University university) {
    Session session = entityManager.unwrap(Session.class);
    University universidadeNome = getUniversity(university.getName());
    if (universidadeNome != null) {
      return false;
    }
    session.save(university);
    return true;
  }

}
