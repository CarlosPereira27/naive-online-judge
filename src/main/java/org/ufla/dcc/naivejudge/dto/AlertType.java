package org.ufla.dcc.naivejudge.controlador;

import java.io.Serializable;

public enum TipoDeAlerta implements Serializable {

  DEFAULT(0, ""), SUCCESS(1, "alert-success"), INFO(2, "alert-info"), WARNING(3,
      "alert-warning"), DANGER(4, "alert-danger");

  private Integer id;
  private String nome;

  private TipoDeAlerta(Integer id, String nome) {
    this.id = id;
    this.nome = nome;
  }

  public Integer getId() {
    return id;
  }

  public String getNome() {
    return nome;
  }

  public String toString() {
    return nome;
  }

}
