package org.ufla.dcc.naivejudge.service;

import java.util.List;
import org.ufla.dcc.naivejudge.domain.user.User;
import org.ufla.dcc.naivejudge.dto.Login;
import org.ufla.dcc.naivejudge.dto.UserProgress;

public interface UserService {

  List<User> getTopUsers();

  User getUser(Long id);

  UserProgress getUserProgress(Long id);

  List<User> getUsers();

  boolean save(User user);

  boolean update(User user);

  User validateUser(Login login);

}
