package org.ufla.dcc.naivejudge.domain.problem;

import java.io.Serializable;

public enum ProgrammingLanguage implements Serializable {

  JAVA(0, "Java");

  private int id;
  private String name;

  private ProgrammingLanguage(int id, String name) {
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
