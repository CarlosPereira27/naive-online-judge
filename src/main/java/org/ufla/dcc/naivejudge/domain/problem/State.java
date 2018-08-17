package org.ufla.dcc.naivejudge.domain.problem;

import java.io.Serializable;

public enum State implements Serializable {

  ACCEPTED(0, "Correto"), COMPILATION_ERROR(1, "Erro de compilação"), RUNTIME_ERROR(2,
      "Erro de execução"), TIME_LIMIT_EXCEEDED(3, "Tempo excedido"), PRESENTATION_ERROR(4,
          "Mal formatado"), WRONG_ANSWER(5, "Resposta incorreta");

  public int id;
  public String name;

  private State(int id, String name) {
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
