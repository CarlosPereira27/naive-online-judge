package org.ufla.dcc.naivejudge.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.ufla.dcc.naivejudge.domain.user.University;
import org.ufla.dcc.naivejudge.domain.user.User;
import org.ufla.dcc.naivejudge.dto.AlertType;
import org.ufla.dcc.naivejudge.dto.Message;
import org.ufla.dcc.naivejudge.service.UniversityService;

@Controller
@RequestMapping("/university")
public class UniversityController {

  @Autowired
  private UniversityService universityService;

  @GetMapping("/rank")
  public String getRank(Model model) {
    model.addAttribute("universities", universityService.getUniversities());
    return "university/rank-universities";
  }

  @GetMapping("/register")
  public String getRegister(Model model, @ModelAttribute("university") University university,
      @ModelAttribute("message") Message message, RedirectAttributes attributes,
      HttpSession session) {
    if (AuthUtils.authenticateUser(attributes, session, "cadastrar uma universidade") == null) {
      return "redirect:/user/home";
    }
    if (message.isNull()) {
      model.addAttribute("message", null);
    }
    return "university/register-university";
  }

  @PostMapping("/register")
  public String postRegister(Model model, @ModelAttribute("university") University university,
      RedirectAttributes attributes, HttpSession session) {
    User user = AuthUtils.authenticateUser(attributes, session, "cadastrar uma universidade");
    if (user == null) {
      return "redirect:/user/home";
    }
    if (universityService.save(university)) {
      attributes.addFlashAttribute("message",
          new Message("Universidade cadastrada com sucesso!", AlertType.SUCCESS));
      return "redirect:/user/configurations";
    }
    attributes.addFlashAttribute("message",
        new Message("Universidade com mesmo nome já cadastrada!", AlertType.DANGER));
    attributes.addFlashAttribute("university", university);
    return "redirect:/university/register";
  }

}
