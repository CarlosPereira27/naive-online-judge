package org.ufla.dcc.naivejudge.modelo.est;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Progresso implements Serializable {

  private static final long serialVersionUID = 1L;

  private Double progressoGeral;

  private List<ProgressoCategoria> progressosCat = new ArrayList<>();

  public void addProgressoCategoria(ProgressoCategoria progressoCategoria) {
    progressosCat.add(progressoCategoria);
  }

  public Double getProgressoGeral() {
    return progressoGeral;
  }

  public List<ProgressoCategoria> getProgressosCat() {
    return progressosCat;
  }

  public void setProgressoGeral(Double progressoGeral) {
    this.progressoGeral = progressoGeral;
  }

  public void setProgressosCat(List<ProgressoCategoria> progressosCat) {
    this.progressosCat = progressosCat;
  }

}
