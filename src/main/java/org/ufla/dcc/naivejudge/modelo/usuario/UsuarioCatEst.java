package org.ufla.dcc.naivejudge.modelo.usuario;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.ufla.dcc.naivejudge.modelo.enums.Categoria;
import org.ufla.dcc.naivejudge.modelo.enums.Estado;

@Entity
public class UsuarioCatEst implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Integer id;

  @Column(nullable = false)
  private Integer qtdSubmissoes;

  @Column(nullable = false)
  private Integer qtdProblemasResolvidos;

  @Column(nullable = false)
  private Integer qtdProblemasTentados;

  @Column(nullable = false)
  @Enumerated(EnumType.ORDINAL)
  private Categoria categoria;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Usuario usuario;

  public UsuarioCatEst() {
    this.qtdSubmissoes = 0;
    this.qtdProblemasResolvidos = 0;
    this.qtdProblemasTentados = 0;
  }

  public UsuarioCatEst(Integer qtdSubmissoes, Integer qtdProblemasResolvidos,
      Integer qtdProblemasTentados, Categoria categoria) {
    this.qtdSubmissoes = qtdSubmissoes;
    this.qtdProblemasResolvidos = qtdProblemasResolvidos;
    this.qtdProblemasTentados = qtdProblemasTentados;
    this.categoria = categoria;
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

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getQtdSubmissoes() {
    return qtdSubmissoes;
  }

  public void setQtdSubmissoes(Integer qtdSubmissoes) {
    this.qtdSubmissoes = qtdSubmissoes;
  }

  public Integer getQtdProblemasResolvidos() {
    return qtdProblemasResolvidos;
  }

  public void setQtdProblemasResolvidos(Integer qtdProblemasResolvidos) {
    this.qtdProblemasResolvidos = qtdProblemasResolvidos;
  }

  public Integer getQtdProblemasTentados() {
    return qtdProblemasTentados;
  }

  public void setQtdProblemasTentados(Integer qtdProblemasTentados) {
    this.qtdProblemasTentados = qtdProblemasTentados;
  }

  public Categoria getCategoria() {
    return categoria;
  }

  public void setCategoria(Categoria categoria) {
    this.categoria = categoria;
  }

  public Usuario getUsuario() {
    return usuario;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    UsuarioCatEst other = (UsuarioCatEst) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "UsuarioCategoriaEstatistica [id=" + id + ", qtdSubmissoes=" + qtdSubmissoes
        + ", qtdProblemasResolvidos=" + qtdProblemasResolvidos + ", qtdProblemasTentados="
        + qtdProblemasTentados + ", categoria=" + categoria + "]";
  }

}
