package org.ufla.dcc.naivejudge.modelo.est;

import java.io.Serializable;
import org.ufla.dcc.naivejudge.modelo.enums.Categoria;

public class ProgressoCategoria implements Serializable {

  private static final long serialVersionUID = 1L;

  private Categoria categoria;

  private Double progresso;

  public ProgressoCategoria() {}

  public ProgressoCategoria(Categoria categoria, Double progresso) {
    this.categoria = categoria;
    this.progresso = progresso;
  }

  public Categoria getCategoria() {
    return categoria;
  }

  public Double getProgresso() {
    return progresso;
  }

  public void setCategoria(Categoria categoria) {
    this.categoria = categoria;
  }

  public void setProgresso(Double progresso) {
    this.progresso = progresso;
  }

}
