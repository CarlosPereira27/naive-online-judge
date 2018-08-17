package org.ufla.dcc.naivejudge.domain.problem;

import java.io.Serializable;

public enum Category implements Serializable {

  BEGINNER(0, "Iniciante"), AD_HOC(1, "Ad-hoc"), STRINGS(2, "Strings"), STRUCTURES_AND_LIBRARIES(3,
      "Estruturas e bibliotecas"), MATH(4, "Matemática"), PARADIGMS(5,
          "Paradigmas"), GRAPHS(6, "Grafos"), COMPUTER_GEOMETRY(7, "Geometria computacional");

  private int id;
  
  private String name;

  private Category(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return name;
  }

}
