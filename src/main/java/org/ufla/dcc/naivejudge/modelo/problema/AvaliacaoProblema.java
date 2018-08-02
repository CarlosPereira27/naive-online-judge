package org.ufla.dcc.naivejudge.modelo.problema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.ufla.dcc.naivejudge.modelo.enums.Linguagem;

@Entity
public class AvaliacaoProblema implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Integer id;

  @OneToOne(mappedBy = "avaliacao")
  private Problema problema;

  @Column(nullable = false)
  @Enumerated(EnumType.ORDINAL)
  private Linguagem linguagemImplementacao;

  @Column(nullable = false)
  private String implementacao;

  @Column
  @Enumerated(EnumType.ORDINAL)
  private Linguagem linguagemGeradorDeEntradas;

  @Column()
  private String geradorDeEntradas;

  @OneToMany(mappedBy = "avalicao")
  private List<InstanciaProblema> instancias;

  public AvaliacaoProblema() {
    instancias = new ArrayList<>();
  }

  public AvaliacaoProblema(Problema problema, Linguagem linguagemImplementacao,
      String implementacao) {
    this.problema = problema;
    this.linguagemImplementacao = linguagemImplementacao;
    this.implementacao = implementacao;
    instancias = new ArrayList<>();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AvaliacaoProblema other = (AvaliacaoProblema) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  public String getGeradorDeEntradas() {
    return geradorDeEntradas;
  }

  public Integer getId() {
    return id;
  }

  public String getImplementacao() {
    return implementacao;
  }

  public List<InstanciaProblema> getInstancias() {
    return instancias;
  }

  public Linguagem getLinguagemGeradorDeEntradas() {
    return linguagemGeradorDeEntradas;
  }

  public Linguagem getLinguagemImplementacao() {
    return linguagemImplementacao;
  }

  public Problema getProblema() {
    return problema;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  public void setGeradorDeEntradas(String geradorDeEntradas) {
    this.geradorDeEntradas = geradorDeEntradas;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setImplementacao(String implementacao) {
    this.implementacao = implementacao;
  }

  public void setInstancias(List<InstanciaProblema> instancias) {
    this.instancias = instancias;
  }

  public void setLinguagemGeradorDeEntradas(Linguagem linguagemGeradorDeEntradas) {
    this.linguagemGeradorDeEntradas = linguagemGeradorDeEntradas;
  }

  public void setLinguagemImplementacao(Linguagem linguagemImplementacao) {
    this.linguagemImplementacao = linguagemImplementacao;
  }

  public void setProblema(Problema problema) {
    this.problema = problema;
  }

  @Override
  public String toString() {
    return "AvaliacaoProblema [id=" + id + ", linguagemImplementacao=" + linguagemImplementacao
        + ", implementacao=" + implementacao + ", linguagemGeradorDeEntradas="
        + linguagemGeradorDeEntradas + ", geradorDeEntradas=" + geradorDeEntradas + ", instancias="
        + instancias + "]";
  }

}
