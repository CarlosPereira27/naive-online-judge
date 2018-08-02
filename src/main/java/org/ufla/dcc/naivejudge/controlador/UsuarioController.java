package org.ufla.dcc.naivejudge.controlador;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.ufla.dcc.naivejudge.modelo.enums.Categoria;
import org.ufla.dcc.naivejudge.modelo.est.ProgressoUsuario;
import org.ufla.dcc.naivejudge.modelo.usuario.Login;
import org.ufla.dcc.naivejudge.modelo.usuario.Universidade;
import org.ufla.dcc.naivejudge.modelo.usuario.Usuario;
import org.ufla.dcc.naivejudge.servico.UniversidadeService;
import org.ufla.dcc.naivejudge.servico.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

  public static String[] getPaises() {
    return new String[] {"Alemanha", "Argentina", "Brasil", "Canadá", "China", "Estados Unidos",
        "França", "Inglaterra", "Japão", "Mexico"};
  }

  // need to inject the customer service
  @Autowired
  private UsuarioService usuarioService;

  @Autowired
  private UniversidadeService universidadeService;

  @GetMapping("/configuracoes")
  public String configuracoesGet(Model theModel, HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null) {
      return "redirect:/usuario/login";
    }
    if (usuario.getUniversidade() == null) {
      usuario.setUniversidade(new Universidade());
    }
    theModel.addAttribute("universidades", universidadeService.universidades());
    theModel.addAttribute("usuario", usuario);
    theModel.addAttribute("paises", getPaises());

    return "configuracoes";
  }

  @PostMapping("/configuracoes")
  public String configuracoesPost(RedirectAttributes attributes,
      @ModelAttribute("usuario") Usuario usuario, HttpSession session) {
    if (session.getAttribute("usuario") == null) {
      return "redirect:/usuario/login";
    }
    boolean atualizar = usuarioService.atualizar(usuario);
    usuario.setEstatistica(((Usuario) session.getAttribute("usuario")).getEstatistica());
    if (!atualizar) {
      attributes.addFlashAttribute("mensagem",
          new Mensagem("Email já cadastrado!", TipoDeAlerta.DANGER));
    } else {
      attributes.addFlashAttribute("mensagem",
          new Mensagem("Dados atualizados com sucesso!", TipoDeAlerta.SUCCESS));
      session.setAttribute("usuario", usuario);
    }

    return "redirect:/usuario/configuracoes";
  }

  @GetMapping("/estatistica")
  public String estatistica(Model theModel, @RequestParam("usuarioId") Integer usuarioId,
      @ModelAttribute("mensagem") Mensagem mensagem, HttpSession session) {
    if (mensagem.isNull()) {
      theModel.addAttribute("mensagem", null);
    }
    if (session.getAttribute("usuario") == null) {
      return "redirect:/usuario/login";
    }
    if (!popularEstatisticas(theModel, usuarioId)) {
      return "redirect:/usuario/home";
    }
    return "estatisticas";

  }

  @GetMapping("/home")
  public String home(@ModelAttribute("mensagem") Mensagem mensagem, RedirectAttributes attributes,
      HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null) {
      return "redirect:/usuario/login";
    }
    attributes.addAttribute("usuarioId", usuario.getId());
    attributes.addFlashAttribute("mensagem", mensagem);

    return "redirect:/usuario/estatistica";
  }

  @PostMapping("/login")
  public String logarPost(@ModelAttribute("login") Login login, RedirectAttributes attributes,
      HttpSession session) {
    Usuario usuario = usuarioService.validarUsario(login);
    if (usuario == null) {
      attributes.addFlashAttribute("login", login);
      attributes.addFlashAttribute("mensagem",
          new Mensagem("Email ou senha incorreta!", TipoDeAlerta.DANGER));
      return "redirect:/usuario/login";
    }
    session.setAttribute("usuario", usuario);
    session.setAttribute("categoriasProblemas", Categoria.values());
    attributes.addAttribute("usuarioId", usuario.getId());
    attributes.addFlashAttribute("mensagem",
        new Mensagem("Bem vindo " + usuario.getNome() + "!", TipoDeAlerta.SUCCESS));

    return "redirect:/usuario/home";
  }

  @GetMapping("/login")
  public String loginGet(Model theModel, @ModelAttribute("login") Login login,
      @ModelAttribute("mensagem") Mensagem mensagem) {
    System.out.println("LOGIN");
    if (mensagem.isNull()) {
      theModel.addAttribute("mensagem", null);
    }
    return "login";
  }

  @GetMapping("/logout")
  public String logout(HttpSession session) {
    session.removeAttribute("usuario");

    return "redirect:/usuario/login";
  }

  @GetMapping("/rank")
  public String rank(Model theModel, HttpSession session) {
    if (session.getAttribute("usuario") == null) {
      return "redirect:/usuario/login";
    }
    theModel.addAttribute("usuarios", usuarioService.getUsuarios());

    return "rank";
  }

  @GetMapping("/registrar")
  public String registrarGet(Model theModel, @ModelAttribute("usuario") Usuario usuario,
      @ModelAttribute("mensagem") Mensagem mensagem) {
    if (mensagem.isNull()) {
      theModel.addAttribute("mensagem", null);
    }
    return "registrar";
  }

  @PostMapping("/registrar")
  public String registrarPost(@ModelAttribute("usuario") Usuario usuario,
      RedirectAttributes attributes, HttpSession session) {
    if (usuarioService.registrar(usuario)) {
      session.setAttribute("usuario", usuario);
      session.setAttribute("categoriasProblemas", Categoria.values());
      attributes.addFlashAttribute("mensagem",
          new Mensagem("Bem vindo " + usuario.getNome() + "!", TipoDeAlerta.SUCCESS));
      return "redirect:/usuario/home";
    }
    attributes.addFlashAttribute("mensagem",
        new Mensagem("Email já cadastrado!", TipoDeAlerta.DANGER));
    attributes.addFlashAttribute("usuario", usuario);

    return "redirect:/usuario/registrar";
  }

  private boolean popularEstatisticas(Model theModel, Integer usuarioId) {
    ProgressoUsuario progressoUsuario = usuarioService.getUsuarioEstatistica(usuarioId);
    if (progressoUsuario == null) {
      return false;
    }
    theModel.addAttribute("usuarios", progressoUsuario.getUsuariosTop());
    theModel.addAttribute("porcentagemGeral", progressoUsuario.getProgresso().getProgressoGeral());
    theModel.addAttribute("progressoCats", progressoUsuario.getProgresso().getProgressosCat());
    theModel.addAttribute("usuarioEst", progressoUsuario.getUsuario());
    return true;
  }

}
