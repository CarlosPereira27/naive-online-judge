package org.ufla.dcc.naivejudge.modelo.problema;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class InstanciaProblema implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Integer id;

  @Column(nullable = false)
  private String arquivoEntrada;

  @Column(nullable = false)
  private String arquivoSaida;

  @ManyToOne
  @JoinColumn(nullable = false)
  private AvaliacaoProblema avalicao;

  public InstanciaProblema() {

  }

  public InstanciaProblema(String teste, AvaliacaoProblema avaliacaoProblema) {
    this.arquivoEntrada = teste + ".in";
    this.arquivoSaida = teste + ".sol";
    this.avalicao = avaliacaoProblema;
  }

  public InstanciaProblema(String arquivoEntrada, String arquivoSaida, AvaliacaoProblema avalicao) {
    this.arquivoEntrada = arquivoEntrada;
    this.arquivoSaida = arquivoSaida;
    this.avalicao = avalicao;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    InstanciaProblema other = (InstanciaProblema) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  public String getArquivoEntrada() {
    return arquivoEntrada;
  }

  public String getArquivoSaida() {
    return arquivoSaida;
  }

  public AvaliacaoProblema getAvalicao() {
    return avalicao;
  }

  public Integer getId() {
    return id;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  public void setArquivoEntrada(String arquivoEntrada) {
    this.arquivoEntrada = arquivoEntrada;
  }

  public void setArquivoSaida(String arquivoSaida) {
    this.arquivoSaida = arquivoSaida;
  }

  public void setAvalicao(AvaliacaoProblema avalicao) {
    this.avalicao = avalicao;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "InstanciaProblema [id=" + id + ", arquivoEntrada=" + arquivoEntrada + ", arquivoSaida="
        + arquivoSaida + "]";
  }

}
