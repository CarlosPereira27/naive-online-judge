package org.ufla.dcc.naivejudge.modelo.enums;

import java.io.Serializable;

public enum Linguagem implements Serializable {

  JAVA(0, "Java");

  private int id;
  private String nome;

  private Linguagem(int id, String nome) {
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
