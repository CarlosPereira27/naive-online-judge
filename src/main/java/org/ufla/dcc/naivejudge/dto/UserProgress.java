package org.ufla.dcc.naivejudge.modelo.est;

import java.io.Serializable;
import java.util.List;
import org.ufla.dcc.naivejudge.modelo.usuario.Usuario;

public class ProgressoUsuario implements Serializable {

  private static final long serialVersionUID = 1L;

  private Usuario usuario;

  private List<Usuario> usuariosTop;

  private Progresso progresso;

  public ProgressoUsuario() {

  }

  public Progresso getProgresso() {
    return progresso;
  }

  public Usuario getUsuario() {
    return usuario;
  }

  public List<Usuario> getUsuariosTop() {
    return usuariosTop;
  }

  public void setProgresso(Progresso progresso) {
    this.progresso = progresso;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }

  public void setUsuariosTop(List<Usuario> usuariosTop) {
    this.usuariosTop = usuariosTop;
  }


}
