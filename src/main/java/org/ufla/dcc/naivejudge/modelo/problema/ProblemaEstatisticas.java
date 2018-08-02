package org.ufla.dcc.naivejudge.modelo.problema;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.ufla.dcc.naivejudge.modelo.enums.Estado;

@Entity
public class ProblemaEstatisticas implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Integer id;

  @Column(nullable = false)
  private Integer qtdSubmissoes;

  @Column(nullable = false)
  private Integer qtdResolucoes;

  public ProblemaEstatisticas() {
    inicializar();
  }

  public void atualizar(boolean naoResolvido, Estado estado) {
    qtdSubmissoes++;
    if (naoResolvido && Estado.CORRETO.equals(estado)) {
      qtdResolucoes++;
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ProblemaEstatisticas other = (ProblemaEstatisticas) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  public Integer getId() {
    return id;
  }

  public Integer getQtdResolucoes() {
    return qtdResolucoes;
  }

  public Integer getQtdSubmissoes() {
    return qtdSubmissoes;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setQtdResolucoes(Integer qtdResolucoes) {
    this.qtdResolucoes = qtdResolucoes;
  }

  public void setQtdSubmissoes(Integer qtdSubmissoes) {
    this.qtdSubmissoes = qtdSubmissoes;
  }

  @Override
  public String toString() {
    return "ProblemaEstatiscas [id=" + id + ", qtdSubmissoes=" + qtdSubmissoes + ", qtdResolucoes="
        + qtdResolucoes + "]";
  }

  private void inicializar() {
    this.qtdSubmissoes = 0;
    this.qtdResolucoes = 0;
  }

}
