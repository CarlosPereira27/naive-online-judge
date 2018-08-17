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
import org.ufla.dcc.naivejudge.domain.problem.Problem;
import org.ufla.dcc.naivejudge.domain.problem.Submission;
import org.ufla.dcc.naivejudge.domain.user.User;
import org.ufla.dcc.naivejudge.dto.AlertType;
import org.ufla.dcc.naivejudge.dto.Message;
import org.ufla.dcc.naivejudge.service.ProblemService;
import org.ufla.dcc.naivejudge.service.SubmissionService;

@Controller
@RequestMapping("/submission")
public class SubmissionController {

  @Autowired
  private ProblemService problemService;

  @Autowired
  private SubmissionService submissionService;

  @GetMapping("/{submissionId}")
  public String getSubmission(Model model, RedirectAttributes attributes,
      @PathVariable("submissionId") Long submissionId, HttpSession session) {
    User user = AuthUtils.authenticateUser(attributes, session, "acessar suas submissões");
    if (user == null) {
      return "redirect:/user/home";
    }
    Submission submission = submissionService.getSubmission(submissionId);
    if (!user.getId().equals(submission.getAuthor().getId())) {
      attributes.addFlashAttribute("message",
          new Message("Usuário não está autorizado a acessar esse conteúdo!", AlertType.DANGER));
      return "redirect:/user/home";
    }
    model.addAttribute("submission", submission);
    return "submission/show-submission";

  }

  @GetMapping("/submissions")
  public String getSubmissions(Model model, RedirectAttributes attributes, HttpSession session) {
    User user = AuthUtils.authenticateUser(attributes, session, "acessar suas submissões");
    if (user == null) {
      return "redirect:/user/home";
    }
    model.addAttribute("submissions", submissionService.getSubmissions(user));
    return "submission/list-submissions";
  }

  @GetMapping("/submit/{problemId}")
  public String getSubmit(Model model, @PathVariable("problemId") Long problemId,
      @ModelAttribute("submission") Submission submission, RedirectAttributes attributes,
      HttpSession session) {
    User user = AuthUtils.authenticateUser(attributes, session, "submeter uma solução");
    if (user == null) {
      return "redirect:/user/submit/" + problemId;
    }
    Problem problem = problemService.getProblem(problemId);
    if (problem == null) {
      attributes.addFlashAttribute("message",
          new Message("Problema não encontrado!", AlertType.DANGER));
      return "redirect:/user/home";
    }
    submission.setAuthor(user);
    submission.setProblem(problem);
    model.addAttribute("submission", submission);
    return "submission/register-submission";
  }

  @PostMapping("/submit/{problemId}")
  public String postSubmit(Model model, @ModelAttribute("submission") Submission submission,
      @PathVariable("problemId") Long problemId, RedirectAttributes attributes,
      HttpSession session) {
    User user = AuthUtils.authenticateUser(attributes, session, "submeter uma solução");
    if (user == null) {
      return "redirect:/user/submit/" + problemId;
    }
    submission.setAuthor(user);
    new Thread(new Runnable() {
      @Override
      public void run() {
        problemService.processSubmission(submission);
      }
    }).start();
    attributes.addAttribute("problemId", submission.getProblem().getId());
    attributes.addFlashAttribute("submission", submission);
    attributes.addFlashAttribute("message",
        new Message("Sua submissão está sendo analisada!", AlertType.INFO));
    return "redirect:/user/home";
  }

}
