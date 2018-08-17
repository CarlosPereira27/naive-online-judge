package org.ufla.dcc.naivejudge.modelo.enums;

import java.io.Serializable;

public enum Genero implements Serializable {

  MASCULINO(0, "Masculino"), FEMININO(1, "Feminino");

  private int id;
  private String nome;

  private Genero(int id, String nome) {
    this.id = id;
    this.nome = nome;
  }

  public int getId() {
    return id;
  }

  public String getNome() {
    return nome;
  }

  @Override
  public String toString() {
    return nome;
  }

}
