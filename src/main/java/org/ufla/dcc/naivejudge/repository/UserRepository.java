package org.ufla.dcc.naivejudge.repository;

import java.util.List;
import org.ufla.dcc.naivejudge.domain.problem.Category;
import org.ufla.dcc.naivejudge.domain.problem.CategoryStatistics;
import org.ufla.dcc.naivejudge.domain.problem.Submission;
import org.ufla.dcc.naivejudge.domain.user.University;
import org.ufla.dcc.naivejudge.domain.user.User;
import org.ufla.dcc.naivejudge.domain.user.UserCategoryStatistics;
import org.ufla.dcc.naivejudge.dto.Login;
import org.ufla.dcc.naivejudge.dto.Progress;

public interface UserRepository {

  User getUser(Long id);

  User getUser(String email);

  Progress getUserProgess(User user, List<CategoryStatistics> categoryStatistics);

  List<User> getUsers();

  List<User> getUsers(int max);

  List<User> getUsers(University university);

  UserCategoryStatistics geUserCategoryStatistics(User user, Category category);

  boolean save(User user);

  boolean update(User user);

  void updateStatistics(Submission submission, boolean newProblem, boolean notAccepted);

  User validateUser(Login login);

}
