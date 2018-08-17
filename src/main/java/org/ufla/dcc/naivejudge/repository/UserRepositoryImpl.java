package org.ufla.dcc.naivejudge.repository;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.ufla.dcc.naivejudge.domain.problem.Category;
import org.ufla.dcc.naivejudge.domain.problem.CategoryStatistics;
import org.ufla.dcc.naivejudge.domain.problem.State;
import org.ufla.dcc.naivejudge.domain.problem.Submission;
import org.ufla.dcc.naivejudge.domain.user.University;
import org.ufla.dcc.naivejudge.domain.user.User;
import org.ufla.dcc.naivejudge.domain.user.UserCategoryStatistics;
import org.ufla.dcc.naivejudge.domain.user.UserCategoryStatistics_;
import org.ufla.dcc.naivejudge.domain.user.UserStatistics;
import org.ufla.dcc.naivejudge.domain.user.UserStatistics_;
import org.ufla.dcc.naivejudge.domain.user.User_;
import org.ufla.dcc.naivejudge.dto.CategoryProgress;
import org.ufla.dcc.naivejudge.dto.Login;
import org.ufla.dcc.naivejudge.dto.Progress;

@Repository
public class UserRepositoryImpl implements UserRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  public UserRepositoryImpl() {}

  private void createUserCategoryStatistics(User user) {
    Session session = entityManager.unwrap(Session.class);
    for (Category cat : Category.values()) {
      UserCategoryStatistics userCategoryStatistics = new UserCategoryStatistics();
      userCategoryStatistics.setCategory(cat);
      userCategoryStatistics.setUser(user);
      session.save(userCategoryStatistics);
    }
  }

  @Override
  public User getUser(Long id) {
    Session session = entityManager.unwrap(Session.class);
    CriteriaBuilder builder = session.getCriteriaBuilder();
    CriteriaQuery<User> query = builder.createQuery(User.class);
    Root<User> root = query.from(User.class);
    query.select(root).where(builder.equal(root.get(User_.id), id));
    try {
      return session.createQuery(query).getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public User getUser(String email) {
    Session session = entityManager.unwrap(Session.class);
    CriteriaBuilder builder = session.getCriteriaBuilder();
    CriteriaQuery<User> query = builder.createQuery(User.class);
    Root<User> root = query.from(User.class);
    query.select(root).where(builder.equal(root.get(User_.email), email));
    try {
      return session.createQuery(query).getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public Progress getUserProgess(User user, List<CategoryStatistics> categoryStatistics) {
    List<UserCategoryStatistics> usuarioCatEst = user.getCategoryStatistics();
    Progress progress = new Progress();
    int problemsSum = 0;
    int acceptedProblemsSum = 0;
    final int SIZE = categoryStatistics.size();
    for (int i = 0; i < SIZE; i++) {
      Category category = categoryStatistics.get(i).getCategory();
      int problems = categoryStatistics.get(i).getQtyProblems();
      int acceptedProblems = usuarioCatEst.get(i).getQtyAcceptedProblems();
      problemsSum += problems;
      acceptedProblemsSum += acceptedProblems;
      double percentage = percentage(acceptedProblems, problems);
      progress.addCategoryProgress(new CategoryProgress(category, percentage));
    }
    progress.setGeneralProgress(percentage(acceptedProblemsSum, problemsSum));
    return progress;
  }

  @Override
  public List<User> getUsers() {
    Session session = entityManager.unwrap(Session.class);
    CriteriaBuilder builder = session.getCriteriaBuilder();
    CriteriaQuery<User> query = builder.createQuery(User.class);
    Root<User> root = query.from(User.class);
    Path<UserStatistics> path = root.get(User_.statistics);
    List<Order> orderList = new ArrayList<>();
    System.out.println("GET_USUARIOS");
    orderList.add(builder.desc(path.get(UserStatistics_.qtyAcceptedProblems)));
    orderList.add(builder.asc(path.get(UserStatistics_.qtySubmissions)));
    query.select(root).orderBy(orderList);
    return session.createQuery(query).getResultList();
  }

  @Override
  public List<User> getUsers(int max) {
    Session session = entityManager.unwrap(Session.class);
    CriteriaBuilder builder = session.getCriteriaBuilder();
    CriteriaQuery<User> query = builder.createQuery(User.class);
    Root<User> root = query.from(User.class);
    Path<UserStatistics> path = root.get(User_.statistics);
    List<Order> orderList = new ArrayList<>();
    orderList.add(builder.desc(path.get(UserStatistics_.qtyAcceptedProblems)));
    orderList.add(builder.asc(path.get(UserStatistics_.qtySubmissions)));
    query.select(root).orderBy(orderList);
    return session.createQuery(query).setMaxResults(max).getResultList();
  }

  @Override
  public List<User> getUsers(University university) {
    Session session = entityManager.unwrap(Session.class);
    session.refresh(university);
    return university.getStudents();
  }

  @Override
  public UserCategoryStatistics geUserCategoryStatistics(User user, Category category) {
    Session session = entityManager.unwrap(Session.class);
    CriteriaBuilder builder = session.getCriteriaBuilder();
    CriteriaQuery<UserCategoryStatistics> query = builder.createQuery(UserCategoryStatistics.class);
    Root<UserCategoryStatistics> root = query.from(UserCategoryStatistics.class);
    query.select(root)
        .where(builder.and(builder.equal(root.get(UserCategoryStatistics_.user), user),
            builder.equal(root.get(UserCategoryStatistics_.category), category)));
    try {
      return session.createQuery(query).getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  private double percentage(int quantity, int total) {
    if (quantity == total) {
      return 100d;
    }
    return (quantity * 100) / (double) total;
  }

  @Override
  public boolean save(User user) {
    Session session = entityManager.unwrap(Session.class);
    User userEmail = getUser(user.getEmail());
    if (userEmail != null) {
      return false;
    }
    user.setStatistics(new UserStatistics());
    session.save(user);
    createUserCategoryStatistics(user);
    return true;
  }

  @Override
  public boolean update(User user) {
    Session session = entityManager.unwrap(Session.class);
    User userEmail = getUser(user.getEmail());
    University universityEmail = null;
    if (userEmail != null) {
      if (!user.getId().equals(userEmail.getId())) {
        return false;
      }
      user.setStatistics(userEmail.getStatistics());
      user.setCreatedAt(userEmail.getCreatedAt());
      universityEmail = userEmail.getUniversity();
      if (universityEmail != null && !universityEmail.equals(user.getUniversity())) {
        universityEmail.unlinkStudent(userEmail);
        session.saveOrUpdate(universityEmail);
      }
      session.detach(userEmail);
    }
    University university = user.getUniversity();
    if (university != null && !university.equals(universityEmail)) {
      university.linkStudent(user);
      session.saveOrUpdate(university);
    }
    session.saveOrUpdate(user);
    return true;
  }

  @Override
  public void updateStatistics(Submission submission, boolean newProblem, boolean notAccepted) {
    Session session = entityManager.unwrap(Session.class);
    session.refresh(submission);
    User user = submission.getAuthor();
    UserStatistics statistics = user.getStatistics();
    statistics.updateSubmission(newProblem, notAccepted, submission.getState());
    UserCategoryStatistics userCategoryStatistics =
        geUserCategoryStatistics(user, submission.getProblem().getCategory());
    userCategoryStatistics.updateSubmission(newProblem, notAccepted, submission.getState());
    session.saveOrUpdate(userCategoryStatistics);
    session.saveOrUpdate(user);
    if (submission.getState().equals(State.ACCEPTED) && notAccepted) {
      University university = user.getUniversity();
      if (university != null) {
        university.setQtyAcceptedProblems(university.getQtyAcceptedProblems() + 1);
        session.saveOrUpdate(university);
      }
    }
  }

  @Override
  public User validateUser(Login login) {
    Session session = entityManager.unwrap(Session.class);
    CriteriaBuilder builder = session.getCriteriaBuilder();
    CriteriaQuery<User> query = builder.createQuery(User.class);
    Root<User> root = query.from(User.class);
    query.select(root).where(builder.and(builder.equal(root.get(User_.email), login.getEmail()),
        builder.equal(root.get(User_.passwordHash), login.getPassword())));
    try {
      return session.createQuery(query).getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

}
