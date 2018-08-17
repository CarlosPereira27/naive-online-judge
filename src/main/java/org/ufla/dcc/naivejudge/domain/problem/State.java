package org.ufla.dcc.naivejudge.modelo.enums;

import java.io.Serializable;

public enum Estado implements Serializable {

  CORRETO(0, "Correto"), ERRO_DE_COMPILACAO(1, "Erro de compilação"), ERRO_DE_EXECUCAO(2,
      "Erro de execução"), TEMPO_EXCEDIDO(3, "Tempo excedido"), MAL_FORMATADO(4,
          "Mal formatado"), RESPOSTA_INCORRETA(5, "Resposta incorreta");

  public int id;
  public String nome;

  private Estado(int id, String nome) {
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
