package org.ufla.dcc.naivejudge.modelo.usuario;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.ufla.dcc.naivejudge.modelo.enums.Estado;

@Embeddable
public class UsuarioEstatistica implements Serializable {

  private static final long serialVersionUID = 1L;

  @Column(nullable = false)
  private Integer qtdSubmissoes;

  @Column(nullable = false)
  private Integer qtdProblemasResolvidos;

  @Column(nullable = false)
  private Integer qtdProblemasTentados;

  public UsuarioEstatistica() {
    this.qtdSubmissoes = 0;
    this.qtdProblemasResolvidos = 0;
    this.qtdProblemasTentados = 0;
  }

  public UsuarioEstatistica(Integer qtdSubmissoes, Integer qtdProblemasResolvidos,
      Integer qtdProblemasTentados) {
    this.qtdSubmissoes = qtdSubmissoes;
    this.qtdProblemasResolvidos = qtdProblemasResolvidos;
    this.qtdProblemasTentados = qtdProblemasTentados;
  }

  public void atualizarSubmissao(boolean novoProblema, boolean naoResolvido, Estado estado) {
    qtdSubmissoes++;
    if (novoProblema) {
      qtdProblemasTentados++;
    }
    if (estado.equals(Estado.CORRETO) && naoResolvido) {
      qtdProblemasResolvidos++;
    }
  }

  public Integer getQtdProblemasResolvidos() {
    return qtdProblemasResolvidos;
  }

  public Integer getQtdProblemasTentados() {
    return qtdProblemasTentados;
  }

  public Integer getQtdSubmissoes() {
    return qtdSubmissoes;
  }

  public void setQtdProblemasResolvidos(Integer qtdProblemasResolvidos) {
    this.qtdProblemasResolvidos = qtdProblemasResolvidos;
  }

  public void setQtdProblemasTentados(Integer qtdProblemasTentados) {
    this.qtdProblemasTentados = qtdProblemasTentados;
  }

  public void setQtdSubmissoes(Integer qtdSubmissoes) {
    this.qtdSubmissoes = qtdSubmissoes;
  }

  @Override
  public String toString() {
    return "UsuarioEstatistica [qtdSubmissoes=" + qtdSubmissoes + ", qtdProblemasResolvidos="
        + qtdProblemasResolvidos + ", qtdProblemasTentados=" + qtdProblemasTentados + "]";
  }

}
