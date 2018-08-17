package org.ufla.dcc.naivejudge.modelo.enums;

import java.io.Serializable;

public enum Categoria implements Serializable {

  INICIANTE(0, "Iniciante"), AD_HOC(1, "Ad-hoc"), STRINGS(2, "Strings"), ESTRUTURAS_E_BIBLIOTECAS(3,
      "Estruturas e bibliotecas"), MATEMATICA(4, "Matemática"), PARADIGMAS(5,
          "Paradigmas"), GRAFOS(6, "Grafos"), GEOMETRIA_COMPUTACIONAL(7, "Geometria computacional");

  private int id;
  private String nome;

  private Categoria(int id, String nome) {
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
