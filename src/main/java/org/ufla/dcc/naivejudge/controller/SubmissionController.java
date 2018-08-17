package org.ufla.dcc.naivejudge.controlador;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.ufla.dcc.naivejudge.modelo.usuario.Usuario;
import org.ufla.dcc.naivejudge.servico.SubmissaoService;

@Controller
@RequestMapping("/submissao")
public class SubmissaoController {

  @Autowired
  private SubmissaoService submissaoService;

  @GetMapping("/listar")
  public String listar(Model theModel, HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null) {
      return "redirect:/usuario/login";
    }
    theModel.addAttribute("submissoes", submissaoService.getSubmissoes(usuario));

    return "submissoes";
  }

  @GetMapping("/get")
  public String submissao(Model theModel, @RequestParam("submissaoId") Integer submissaoId,
      HttpSession session) {
    if (session.getAttribute("usuario") == null) {
      return "redirect:/usuario/login";
    }
    theModel.addAttribute("submissao", submissaoService.getSubmissao(submissaoId));

    return "submissao";
  }

}
