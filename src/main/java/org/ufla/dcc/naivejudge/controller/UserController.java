package org.ufla.dcc.naivejudge.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.ufla.dcc.naivejudge.domain.problem.Category;
import org.ufla.dcc.naivejudge.domain.user.Gender;
import org.ufla.dcc.naivejudge.domain.user.University;
import org.ufla.dcc.naivejudge.domain.user.User;
import org.ufla.dcc.naivejudge.dto.AlertType;
import org.ufla.dcc.naivejudge.dto.Login;
import org.ufla.dcc.naivejudge.dto.Message;
import org.ufla.dcc.naivejudge.dto.UserProgress;
import org.ufla.dcc.naivejudge.service.UniversityService;
import org.ufla.dcc.naivejudge.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

  public static String[] getCountries() {
    return new String[] {"Alemanha", "Argentina", "Brasil", "Canadá", "China", "Estados Unidos",
        "França", "Inglaterra", "Japão", "Mexico"};
  }

  @Autowired
  private UserService userService;

  @Autowired
  private UniversityService universityService;

  @GetMapping("/configurations")
  public String getConfigurations(Model model, HttpSession session, RedirectAttributes attributes) {
    User user = AuthUtils.authenticateUser(attributes, session, "acessar suas configurações");
    if (user == null) {
      return "redirect:/user/home";
    }
    if (user.getUniversity() == null) {
      user.setUniversity(new University());
    }
    model.addAttribute("universities", universityService.getUniversities());
    model.addAttribute("user", user);
    model.addAttribute("countries", getCountries());
    model.addAttribute("genders", Gender.values());
    return "user/edit-configurations";
  }

  @GetMapping("/home")
  public String getHome(Model model, @ModelAttribute("login") Login login,
      @ModelAttribute("message") Message message, RedirectAttributes attributes,
      HttpSession session) {
    User user = AuthUtils.authenticateUser(attributes, session, "acessar o sistema");
    if (user == null) {
      model.addAttribute("topUsers", userService.getTopUsers());
      return "user/user-home";
    }
    attributes.addFlashAttribute("message", message);
    return "redirect:/user/statistics/" + user.getId();
  }

  @GetMapping("/login")
  public String getLogin(Model model, @ModelAttribute("login") Login login,
      @ModelAttribute("message") Message message) {
    if (message.isNull()) {
      model.addAttribute("message", null);
    }
    return "user/user-login";
  }

  @GetMapping("/logout")
  public String getLogout(HttpSession session) {
    session.removeAttribute("user");
    return "redirect:/user/login";
  }

  @GetMapping("/rank")
  public String getRank(Model model) {
    model.addAttribute("users", userService.getUsers());
    return "user/users-rank";
  }

  @GetMapping("/register")
  public String getRegister(Model model, @ModelAttribute("user") User user,
      @ModelAttribute("message") Message message) {
    if (message.isNull()) {
      model.addAttribute("message", null);
    }
    return "user/register-user";
  }

  @GetMapping("/statistics/{userId}")
  public String getStatistics(Model model, @PathVariable("userId") Long userId,
      @ModelAttribute("message") Message message) {
    if (message.isNull()) {
      model.addAttribute("message", null);
    }
    if (!populateStatistics(model, userId)) {
      return "redirect:/user/home";
    }
    return "user/user-statistics";

  }

  private boolean populateStatistics(Model model, Long userId) {
    UserProgress userProgress = userService.getUserProgress(userId);
    if (userProgress == null) {
      return false;
    }
    model.addAttribute("topUsers", userProgress.getTopUsers());
    model.addAttribute("generalProgress", userProgress.getProgress().getGeneralProgress());
    model.addAttribute("categoriesProgress", userProgress.getProgress().getCategoryProgressList());
    model.addAttribute("userStatistics", userProgress.getUser());
    return true;
  }

  @PostMapping("/configurations")
  public String postConfigurations(@ModelAttribute("user") User user, RedirectAttributes attributes,
      HttpSession session) {
    User userSession =
        AuthUtils.authenticateUser(attributes, session, "acessar suas configurações");
    if (userSession == null) {
      return "redirect:/user/home";
    }
    boolean update = userService.update(user);
    user.setStatistics(userSession.getStatistics());
    if (!update) {
      attributes.addFlashAttribute("message",
          new Message("Email já cadastrado!", AlertType.DANGER));
    } else {
      attributes.addFlashAttribute("message",
          new Message("Dados atualizados com sucesso!", AlertType.SUCCESS));
      session.setAttribute("user", user);
    }
    return "redirect:/user/configurations";
  }

  @PostMapping("/login")
  public String postLogin(@ModelAttribute("login") Login login, RedirectAttributes attributes,
      HttpSession session) {
    User user = userService.validateUser(login);
    if (user == null) {
      attributes.addFlashAttribute("login", login);
      attributes.addFlashAttribute("message",
          new Message("Email ou senha incorreta!", AlertType.DANGER));
      return "redirect:/user/login";
    }
    session.setAttribute("user", user);
    session.setAttribute("categories", Category.values());
    attributes.addFlashAttribute("message",
        new Message("Bem vindo " + user.getName() + "!", AlertType.SUCCESS));
    return "redirect:/user/home";
  }

  @PostMapping("/register")
  public String postRegister(@ModelAttribute("user") User user, RedirectAttributes attributes,
      HttpSession session) {
    if (userService.save(user)) {
      session.setAttribute("user", user);
      session.setAttribute("categories", Category.values());
      attributes.addFlashAttribute("message",
          new Message("Bem vindo " + user.getName() + "!", AlertType.SUCCESS));
      return "redirect:/user/home";
    }
    attributes.addFlashAttribute("message", new Message("Email já cadastrado!", AlertType.DANGER));
    attributes.addFlashAttribute("user", user);
    return "redirect:/user/register";
  }

}
