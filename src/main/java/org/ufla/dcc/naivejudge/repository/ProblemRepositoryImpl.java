package org.ufla.dcc.naivejudge.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.ufla.dcc.naivejudge.domain.forum.Forum;
import org.ufla.dcc.naivejudge.domain.problem.Category;
import org.ufla.dcc.naivejudge.domain.problem.CategoryStatistics;
import org.ufla.dcc.naivejudge.domain.problem.CategoryStatistics_;
import org.ufla.dcc.naivejudge.domain.problem.Problem;
import org.ufla.dcc.naivejudge.domain.problem.ProblemInstance;
import org.ufla.dcc.naivejudge.domain.problem.ProblemJudge;
import org.ufla.dcc.naivejudge.domain.problem.ProblemStatistics;
import org.ufla.dcc.naivejudge.domain.problem.Problem_;
import org.ufla.dcc.naivejudge.domain.problem.State;

@Repository
public class ProblemRepositoryImpl implements ProblemRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  public ProblemRepositoryImpl() {}

  public Forum createAForum() {
    Session session = entityManager.unwrap(Session.class);
    Forum forum = new Forum();
    session.save(forum);
    return forum;
  }

  @Override
  public void createInstances(ProblemJudge judge, SortedSet<String> tests) {
    Session session = entityManager.unwrap(Session.class);
    session.refresh(judge);
    List<ProblemInstance> instances = new ArrayList<>();
    for (String test : tests) {
      ProblemInstance instance = new ProblemInstance(test, judge);
      session.save(instance);
      instances.add(instance);
    }
    judge.setInstances(instances);
    session.saveOrUpdate(judge);
  }

  @Override
  public void createProblem(Problem problem) {
    Session session = entityManager.unwrap(Session.class);
    session.save(problem);
    CategoryStatistics categoryStatistics = getCategoriaEstatistica(problem.getCategory());
    categoryStatistics.setQtyProblems(categoryStatistics.getQtyProblems() + 1);
    session.saveOrUpdate(problem);
  }

  public ProblemStatistics createProblemStatistics() {
    ProblemStatistics statistics = new ProblemStatistics();
    Session session = entityManager.unwrap(Session.class);
    session.save(statistics);
    return statistics;
  }

  public CategoryStatistics getCategoriaEstatistica(Category category) {
    Session session = entityManager.unwrap(Session.class);
    CriteriaBuilder builder = session.getCriteriaBuilder();
    CriteriaQuery<CategoryStatistics> query = builder.createQuery(CategoryStatistics.class);
    Root<CategoryStatistics> root = query.from(CategoryStatistics.class);
    query.select(root).where(builder.equal(root.get(CategoryStatistics_.category), category));
    try {
      return session.createQuery(query).getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public List<CategoryStatistics> getCategoryStatistics() {
    Session session = entityManager.unwrap(Session.class);
    CriteriaBuilder builder = session.getCriteriaBuilder();
    CriteriaQuery<CategoryStatistics> query = builder.createQuery(CategoryStatistics.class);
    Root<CategoryStatistics> root = query.from(CategoryStatistics.class);
    query.select(root);
    return session.createQuery(query).getResultList();
  }

  @Override
  public Problem getProblem(Long id) {
    Session session = entityManager.unwrap(Session.class);
    CriteriaBuilder builder = session.getCriteriaBuilder();
    CriteriaQuery<Problem> query = builder.createQuery(Problem.class);
    Root<Problem> root = query.from(Problem.class);
    query.select(root).where(builder.equal(root.get(Problem_.id), id));

    try {
      return session.createQuery(query).getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public List<Problem> getProblems() {
    Session session = entityManager.unwrap(Session.class);
    CriteriaBuilder builder = session.getCriteriaBuilder();
    CriteriaQuery<Problem> query = builder.createQuery(Problem.class);
    Root<Problem> root = query.from(Problem.class);
    query.select(root);
    return session.createQuery(query).getResultList();
  }

  @Override
  public List<Problem> getProblems(Category category) {
    Session session = entityManager.unwrap(Session.class);
    CriteriaBuilder builder = session.getCriteriaBuilder();
    CriteriaQuery<Problem> query = builder.createQuery(Problem.class);
    Root<Problem> root = query.from(Problem.class);
    query.select(root).where(builder.equal(root.get(Problem_.category), category));
    return session.createQuery(query).getResultList();
  }

  @Override
  public ProblemJudge loadJudge(Problem problem) {
    Session session = entityManager.unwrap(Session.class);
    session.refresh(problem);
    return problem.getJudge();
  }

  @Override
  public void saveJudge(ProblemJudge judge) {
    Session session = entityManager.unwrap(Session.class);
    session.save(judge);
  }

  @Override
  public void updateStatistics(Problem problem, boolean notAccepted, State state) {
    Session session = entityManager.unwrap(Session.class);
    session.refresh(problem);
    ProblemStatistics statistics = problem.getStatistics();
    statistics.update(notAccepted, state);
    session.saveOrUpdate(statistics);
  }

}
