package org.ufla.dcc.naivejudge.service;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ufla.dcc.naivejudge.domain.problem.CategoryStatistics;
import org.ufla.dcc.naivejudge.domain.user.University;
import org.ufla.dcc.naivejudge.domain.user.User;
import org.ufla.dcc.naivejudge.dto.Login;
import org.ufla.dcc.naivejudge.dto.UserProgress;
import org.ufla.dcc.naivejudge.repository.ProblemRepository;
import org.ufla.dcc.naivejudge.repository.UniversityRepository;
import org.ufla.dcc.naivejudge.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UniversityRepository universityRepository;

  @Autowired
  private ProblemRepository problemRepository;

  @Override
  @Transactional
  public List<User> getTopUsers() {
    return userRepository.getUsers(20);
  }

  @Override
  @Transactional
  public User getUser(Long id) {
    return userRepository.getUser(id);
  }

  @Override
  @Transactional
  public UserProgress getUserProgress(Long id) {
    UserProgress userProgress = new UserProgress();
    User user = userRepository.getUser(id);
    if (user == null) {
      return null;
    }
    userProgress.setUser(user);
    userProgress.setTopUsers(userRepository.getUsers(20));
    List<CategoryStatistics> categoryStatistics = problemRepository.getCategoryStatistics();
    userProgress.setProgress(userRepository.getUserProgess(user, categoryStatistics));
    return userProgress;
  }

  @Override
  @Transactional
  public List<User> getUsers() {
    return userRepository.getUsers();
  }

  @Override
  @Transactional
  public boolean save(User user) {
    return userRepository.save(user);
  }

  @Override
  @Transactional
  public boolean update(User user) {
    University university = universityRepository.getUniversity(user.getUniversity().getId());
    user.setUniversity(university);
    return userRepository.update(user);
  }

  @Override
  @Transactional
  public User validateUser(Login login) {
    User user = userRepository.validateUser(login);
    return user;
  }

}
