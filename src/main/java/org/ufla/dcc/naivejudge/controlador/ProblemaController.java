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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.ufla.dcc.naivejudge.modelo.enums.Categoria;
import org.ufla.dcc.naivejudge.modelo.enums.Linguagem;
import org.ufla.dcc.naivejudge.modelo.problema.AvaliacaoProblema;
import org.ufla.dcc.naivejudge.modelo.problema.Problema;
import org.ufla.dcc.naivejudge.modelo.problema.Submissao;
import org.ufla.dcc.naivejudge.modelo.usuario.Usuario;
import org.ufla.dcc.naivejudge.servico.ProblemaService;
import org.ufla.dcc.naivejudge.servico.UsuarioService;

@Controller
@RequestMapping("/problema")
public class ProblemaController {

  @Autowired
  private ProblemaService problemaService;

  @Autowired
  private UsuarioService usuarioService;

  @GetMapping("/cadastrar")
  public String cadastrarGet(Model theModel, @ModelAttribute("problema") Problema problema,
      HttpSession session) {
    if (session.getAttribute("usuario") == null) {
      return "redirect:/usuario/login";
    }
    if (problema.getAvaliacao() == null) {
      problema.setAvaliacao(new AvaliacaoProblema());
    }
    theModel.addAttribute("categorias", Categoria.values());
    theModel.addAttribute("linguagens", Linguagem.values());

    return "cadastrarProblema";
  }

  @PostMapping("/cadastrar")
  public String cadastrarPost(Model theModel, @ModelAttribute("problema") Problema problema,
      @RequestParam("arqTestes") MultipartFile[] arqTestes,
      @RequestParam("arqImpl") MultipartFile arqImpl, RedirectAttributes attributes,
      HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null) {
      return "redirect:/usuario/login";
    }
    problema.setAutor(usuario);
    try {
      problemaService.cadastrarProblema(problema, arqTestes, arqImpl);
    } catch (Exception e) {
      e.printStackTrace();
      attributes.addFlashAttribute("problema", problema);
      attributes.addFlashAttribute("mensagem", new Mensagem(e.getMessage(), TipoDeAlerta.DANGER));
      return "redirect:/problema/cadastrar";
    }
    attributes.addFlashAttribute("mensagem",
        new Mensagem("Problema cadastrado com sucesso!", TipoDeAlerta.SUCCESS));

    return "redirect:/usuario/home";
  }

  @GetMapping("/listar")
  public String listarProblemas(Model theModel, HttpSession session) {
    if (session.getAttribute("usuario") == null) {
      return "redirect:/usuario/login";
    }
    theModel.addAttribute("problemas", problemaService.getProblemas());
    theModel.addAttribute("usuarios", usuarioService.getUsuariosTop());

    return "problemas";
  }

  @GetMapping("/categoria")
  public String listarProblemasCategoria(Model theModel,
      @RequestParam("categoriaId") Integer categoriaId, HttpSession session) {
    if (session.getAttribute("usuario") == null) {
      return "redirect:/usuario/login";
    }
    Categoria categoria = null;
    try {
      categoria = Categoria.values()[categoriaId];
    } catch (Exception e) {
      theModel.addAttribute("mensagem", new Mensagem(e.getMessage(), TipoDeAlerta.DANGER));
      e.printStackTrace();
    }
    theModel.addAttribute("categoria", categoria);
    theModel.addAttribute("problemas", problemaService.getProblemas(categoria));
    theModel.addAttribute("usuarios", usuarioService.getUsuariosTop());

    return "problemasCategoria";
  }

  @GetMapping("/get")
  public String problema(Model theModel, @RequestParam("problemaId") Integer problemaId,
      HttpSession session) {
    if (session.getAttribute("usuario") == null) {
      return "redirect:/usuario/login";
    }
    theModel.addAttribute("problema", problemaService.getProblema(problemaId));

    return "problema";
  }

  @GetMapping("/submeter")
  public String submeterGet(Model theModel, @RequestParam("problemaId") Integer problemaId,
      @ModelAttribute("submissao") Submissao submissao, RedirectAttributes attributes,
      HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null) {
      return "redirect:/usuario/login";
    }
    Problema problema = problemaService.getProblema(problemaId);
    if (problema == null) {
      attributes.addFlashAttribute("mensagem",
          new Mensagem("Problema não encontrado!", TipoDeAlerta.DANGER));
      return "redirect:/usuario/home";
    }
    submissao.setAutor(usuario);
    submissao.setProblema(problema);

    theModel.addAttribute("submissao", submissao);

    return "submeter";
  }

  @PostMapping("/submeter")
  public String submeterPost(Model theModel, @ModelAttribute("submissao") Submissao submissao,
      RedirectAttributes attributes, HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null) {
      return "redirect:/usuario/login";
    }
    submissao.setAutor(usuario);
    new Thread(new Runnable() {

      @Override
      public void run() {
        problemaService.processarSubmissao(submissao);
      }
    }).start();
    attributes.addAttribute("problemaId", submissao.getProblema().getId());
    attributes.addFlashAttribute("submissao", submissao);
    attributes.addFlashAttribute("mensagem",
        new Mensagem("Sua submissão está sendo analisada!", TipoDeAlerta.INFO));

    return "redirect:/problema/submeter";
  }

}
