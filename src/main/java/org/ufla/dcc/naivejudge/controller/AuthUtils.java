package org.ufla.dcc.naivejudge.controller;

import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.ufla.dcc.naivejudge.domain.user.User;
import org.ufla.dcc.naivejudge.dto.AlertType;
import org.ufla.dcc.naivejudge.dto.Message;

public class AuthUtils {

  public static User authenticateUser(RedirectAttributes attributes, HttpSession session,
      String operationMessage) {
    User user = (User) session.getAttribute("user");
    if (user == null) {
      attributes.addFlashAttribute("message",
          new Message("Faça login para " + operationMessage + "!", AlertType.DANGER));
    }
    return user;
  }

}
