package org.ufla.dcc.naivejudge.controlador;

import java.io.Serializable;

public class Mensagem implements Serializable {

  private static final long serialVersionUID = 1L;

  private String conteudo;
  private TipoDeAlerta tipoDeAlerta;

  public Mensagem() {

  }

  public Mensagem(String conteudo, TipoDeAlerta tipoDeAlerta) {
    this.conteudo = conteudo;
    this.tipoDeAlerta = tipoDeAlerta;
  }

  public String getConteudo() {
    return conteudo;
  }

  public TipoDeAlerta getTipoDeAlerta() {
    return tipoDeAlerta;
  }

  public boolean isNull() {
    return conteudo == null && tipoDeAlerta == null;
  }

  public void setConteudo(String conteudo) {
    this.conteudo = conteudo;
  }

  public void setTipoDeAlerta(TipoDeAlerta tipoDeAlerta) {
    this.tipoDeAlerta = tipoDeAlerta;
  }

  @Override
  public String toString() {
    return "Mensagem [conteudo=" + conteudo + ", tipoDeAlerta=" + tipoDeAlerta + "]";
  }

}
