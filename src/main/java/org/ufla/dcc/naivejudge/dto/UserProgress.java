package org.ufla.dcc.naivejudge.dto;

import java.io.Serializable;
import java.util.List;
import org.ufla.dcc.naivejudge.domain.user.User;

public class UserProgress implements Serializable {

  private static final long serialVersionUID = 1L;

  private User user;

  private List<User> topUsers;

  private Progress progress;

  public UserProgress() {

  }

  public Progress getProgress() {
    return progress;
  }

  public List<User> getTopUsers() {
    return topUsers;
  }

  public User getUser() {
    return user;
  }

  public void setProgress(Progress progress) {
    this.progress = progress;
  }

  public void setTopUsers(List<User> topUsers) {
    this.topUsers = topUsers;
  }

  public void setUser(User user) {
    this.user = user;
  }

}
