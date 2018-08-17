package org.ufla.dcc.naivejudge.modelo.problema;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OrderColumn;
import org.ufla.dcc.naivejudge.modelo.enums.Categoria;

@Entity
public class CategoriaEstatisticas implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column
  @Enumerated(EnumType.ORDINAL)
  @OrderColumn
  public Categoria categoria;

  @Column(nullable = false)
  private Integer qtdProblemas;

  public CategoriaEstatisticas() {

  }

  public CategoriaEstatisticas(Categoria categoria, Integer qtdProblemas) {
    this.categoria = categoria;
    this.qtdProblemas = qtdProblemas;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    CategoriaEstatisticas other = (CategoriaEstatisticas) obj;
    if (categoria != other.categoria)
      return false;
    return true;
  }

  public Categoria getCategoria() {
    return categoria;
  }

  public Integer getQtdProblemas() {
    return qtdProblemas;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
    return result;
  }

  public void setCategoria(Categoria categoria) {
    this.categoria = categoria;
  }

  public void setQtdProblemas(Integer qtdProblemas) {
    this.qtdProblemas = qtdProblemas;
  }

  @Override
  public String toString() {
    return "CategoriaEstatisticas [categoria=" + categoria + ", qtdProblemas=" + qtdProblemas + "]";
  }

}
