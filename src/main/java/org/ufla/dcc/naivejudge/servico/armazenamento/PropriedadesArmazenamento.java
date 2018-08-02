package org.ufla.dcc.naivejudge.servico.armazenamento;

import java.io.Serializable;

public class PropriedadesArmazenamento implements Serializable {

  private static final long serialVersionUID = 1L;

  private static PropriedadesArmazenamento propriedadesArmazenamento;

  public static PropriedadesArmazenamento getInstance() {
    if (propriedadesArmazenamento == null) {
      propriedadesArmazenamento = new PropriedadesArmazenamento();
    }
    return propriedadesArmazenamento;
  }

  private String diretorio = "upload-dir";

  public String getDiretorio() {
    return diretorio;
  }

  public void setDiretorio(String diretorio) {
    this.diretorio = diretorio;
  }

}
