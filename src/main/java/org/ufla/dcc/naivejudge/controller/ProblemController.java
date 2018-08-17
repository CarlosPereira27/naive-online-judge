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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.ufla.dcc.naivejudge.domain.problem.ProblemJudge;
import org.ufla.dcc.naivejudge.domain.problem.ProgrammingLanguage;
import org.ufla.dcc.naivejudge.domain.problem.Category;
import org.ufla.dcc.naivejudge.domain.problem.Problem;
import org.ufla.dcc.naivejudge.domain.user.User;
import org.ufla.dcc.naivejudge.dto.AlertType;
import org.ufla.dcc.naivejudge.dto.Message;
import org.ufla.dcc.naivejudge.service.ProblemService;
import org.ufla.dcc.naivejudge.service.UserService;

@Controller
@RequestMapping("/problem")
public class ProblemController {

  @Autowired
  private ProblemService problemService;

  @Autowired
  private UserService userService;

  @GetMapping("/{problemId}")
  public String getProblem(Model model, @PathVariable("problemId") Long problemId) {
    model.addAttribute("problem", problemService.getProblem(problemId));
    return "problem/show-problem";
  }

  @GetMapping("/problems")
  public String getProblems(Model model) {
    model.addAttribute("problems", problemService.getProblems());
    model.addAttribute("topUsers", userService.getTopUsers());
    return "problem/list-problems";
  }

  @GetMapping("/category/{categoryId}")
  public String getProblemsCategory(Model model, @PathVariable("categoryId") Integer categoryId) {
    Category category = null;
    try {
      category = Category.values()[categoryId];
    } catch (Exception e) {
      model.addAttribute("message", new Message(e.getMessage(), AlertType.DANGER));
      e.printStackTrace();
    }
    model.addAttribute("category", category);
    model.addAttribute("problems", problemService.getProblems(category));
    model.addAttribute("topUsers", userService.getTopUsers());
    return "problem/list-problems-category";
  }

  @GetMapping("/register")
  public String getRegister(Model model, @ModelAttribute("problem") Problem problem,
      RedirectAttributes attributes, HttpSession session) {
    if (AuthUtils.authenticateUser(attributes, session, "cadastrar um problema") == null) {
      return "redirect:/user/home";
    }
    if (problem.getJudge() == null) {
      problem.setJudge(new ProblemJudge());
    }
    model.addAttribute("categories", Category.values());
    model.addAttribute("languages", ProgrammingLanguage.values());
    return "problem/register-problem";
  }

  @PostMapping("/register")
  public String postRegister(Model model, @ModelAttribute("problem") Problem problem,
      @RequestParam("testFiles") MultipartFile[] testFiles,
      @RequestParam("implementationFile") MultipartFile implementationFile,
      RedirectAttributes attributes, HttpSession session) {
    User user = AuthUtils.authenticateUser(attributes, session, "cadastrar um problema");
    if (user == null) {
      return "redirect:/user/home";
    }
    problem.setAuthor(user);
    try {
      problemService.save(problem, testFiles, implementationFile);
    } catch (Exception e) {
      e.printStackTrace();
      attributes.addFlashAttribute("problem", problem);
      attributes.addFlashAttribute("message", new Message(e.getMessage(), AlertType.DANGER));
      return "redirect:/problem/register";
    }
    attributes.addFlashAttribute("message",
        new Message("Problema cadastrado com sucesso!", AlertType.SUCCESS));
    return "redirect:/user/home";
  }

}
