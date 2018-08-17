package org.ufla.dcc.naivejudge.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.ufla.dcc.naivejudge.domain.problem.Problem;
import org.ufla.dcc.naivejudge.domain.problem.Submission;
import org.ufla.dcc.naivejudge.domain.problem.Submission_;
import org.ufla.dcc.naivejudge.domain.user.User;

@Repository
public class SubmissionRepositoryImpl implements SubmissionRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  public SubmissionRepositoryImpl() {}

  @Override
  public Submission getSubmission(Long id) {
    Session session = entityManager.unwrap(Session.class);
    CriteriaBuilder builder = session.getCriteriaBuilder();
    CriteriaQuery<Submission> query = builder.createQuery(Submission.class);
    Root<Submission> root = query.from(Submission.class);
    query.select(root).where(builder.equal(root.get(Submission_.id), id));
    try {
      return session.createQuery(query).getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public List<Submission> getSubmissions(User user) {
    Session session = entityManager.unwrap(Session.class);
    CriteriaBuilder builder = session.getCriteriaBuilder();
    CriteriaQuery<Submission> query = builder.createQuery(Submission.class);
    Root<Submission> root = query.from(Submission.class);
    query.select(root).where(builder.equal(root.get(Submission_.author), user))
        .orderBy(builder.desc(root.get(Submission_.id)));
    return session.createQuery(query).getResultList();
  }

  @Override
  public List<Submission> getSubmissions(User user, Problem problem) {
    Session session = entityManager.unwrap(Session.class);
    // SELECT * FROM Submission
    // WHERE author_id = ? and problem_id = ?
    CriteriaBuilder builder = session.getCriteriaBuilder();
    CriteriaQuery<Submission> query = builder.createQuery(Submission.class);
    Root<Submission> root = query.from(Submission.class);
    query.select(root)
        .where(builder.and(builder.equal(root.get(Submission_.author), user),
            builder.equal(root.get(Submission_.problem), problem)))
        .orderBy(builder.desc(root.get(Submission_.id)));
    return session.createQuery(query).getResultList();
  }

  @Override
  public void saveSubmission(Submission submission) {
    Session session = entityManager.unwrap(Session.class);
    session.saveOrUpdate(submission);
  }

}
