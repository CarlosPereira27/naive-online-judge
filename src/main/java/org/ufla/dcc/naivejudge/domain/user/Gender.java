package org.ufla.dcc.naivejudge.domain.user;

import java.io.Serializable;

public enum Gender implements Serializable {

  MALE(0, "Masculino"), FEMALE(1, "Feminino");

  private int id;
  private String name;

  private Gender(int id, String name) {
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
