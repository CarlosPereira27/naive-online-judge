package org.ufla.dcc.naivejudge.controlador;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.ufla.dcc.naivejudge.modelo.usuario.Universidade;
import org.ufla.dcc.naivejudge.modelo.usuario.Usuario;
import org.ufla.dcc.naivejudge.servico.UniversidadeService;

@Controller
@RequestMapping("/universidade")
public class UniversidadeController {

  // need to inject the customer service
  @Autowired
  private UniversidadeService universidadeService;

  @GetMapping("/rank")
  public String rank(Model theModel, HttpSession session) {
    if (session.getAttribute("usuario") == null) {
      return "redirect:/usuario/login";
    }
    theModel.addAttribute("universidades", universidadeService.universidades());

    return "universidades";
  }

  @GetMapping("/registrar")
  public String registrarGet(Model theModel,
      @ModelAttribute("universidade") Universidade universidade,
      @ModelAttribute("mensagem") Mensagem mensagem, HttpSession session) {
    if (session.getAttribute("usuario") == null) {
      return "redirect:/usuario/login";
    }
    if (mensagem.isNull()) {
      theModel.addAttribute("mensagem", null);
    }

    return "registrarUniversidade";
  }

  @PostMapping("/registrar")
  public String registrarPost(Model theModel,
      @ModelAttribute("universidade") Universidade universidade, RedirectAttributes attributes,
      HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null) {
      return "redirect:/usuario/login";
    }
    if (universidadeService.registrar(universidade)) {
      attributes.addFlashAttribute("mensagem",
          new Mensagem("Universidade cadastrada com sucesso!", TipoDeAlerta.SUCCESS));
      return "redirect:/usuario/configuracoes";
    }
    attributes.addFlashAttribute("mensagem",
        new Mensagem("Universidade com mesmo nome já cadastrada!", TipoDeAlerta.DANGER));
    attributes.addFlashAttribute("universidade", universidade);
    return "redirect:/universidade/registrar";
  }

}
