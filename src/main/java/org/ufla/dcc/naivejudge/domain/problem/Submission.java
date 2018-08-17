package org.ufla.dcc.naivejudge.modelo.problema;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.ufla.dcc.naivejudge.modelo.enums.Estado;
import org.ufla.dcc.naivejudge.modelo.enums.Linguagem;
import org.ufla.dcc.naivejudge.modelo.usuario.Usuario;

@Entity
public class Submissao implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Integer id;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Usuario autor;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Problema problema;

  @Column(nullable = false)
  @Enumerated(EnumType.ORDINAL)
  private Linguagem linguagem;

  @Column(columnDefinition = "text", nullable = false)
  private String implementacao;

  @Column
  private Integer tempo;

  @Column
  private Estado estado;

  @Column(columnDefinition = "text")
  private String mensagem;

  @Column(nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date dataEnvio;

  public Submissao() {
    inicializar();
  }

  public Submissao(Usuario autor, Linguagem linguagem, String implementacao) {
    this.autor = autor;
    this.linguagem = linguagem;
    this.implementacao = implementacao;
    inicializar();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Submissao other = (Submissao) obj;
    if (autor == null) {
      if (other.autor != null)
        return false;
    } else if (!autor.equals(other.autor))
      return false;
    if (dataEnvio == null) {
      if (other.dataEnvio != null)
        return false;
    } else if (!dataEnvio.equals(other.dataEnvio))
      return false;
    if (estado != other.estado)
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (implementacao == null) {
      if (other.implementacao != null)
        return false;
    } else if (!implementacao.equals(other.implementacao))
      return false;
    if (linguagem != other.linguagem)
      return false;
    if (tempo == null) {
      if (other.tempo != null)
        return false;
    } else if (!tempo.equals(other.tempo))
      return false;
    return true;
  }

  public Usuario getAutor() {
    return autor;
  }

  public Date getDataEnvio() {
    return dataEnvio;
  }

  public Estado getEstado() {
    return estado;
  }

  public Integer getId() {
    return id;
  }

  public String getImplementacao() {
    return implementacao;
  }

  public Linguagem getLinguagem() {
    return linguagem;
  }

  public String getMensagem() {
    return mensagem;
  }

  public Problema getProblema() {
    return problema;
  }

  public Integer getTempo() {
    return tempo;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((autor == null) ? 0 : autor.hashCode());
    result = prime * result + ((dataEnvio == null) ? 0 : dataEnvio.hashCode());
    result = prime * result + ((estado == null) ? 0 : estado.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((implementacao == null) ? 0 : implementacao.hashCode());
    result = prime * result + ((linguagem == null) ? 0 : linguagem.hashCode());
    result = prime * result + ((tempo == null) ? 0 : tempo.hashCode());
    return result;
  }

  public void setAutor(Usuario autor) {
    this.autor = autor;
  }

  public void setDataEnvio(Date dataEnvio) {
    this.dataEnvio = dataEnvio;
  }

  public void setEstado(Estado estado) {
    this.estado = estado;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setImplementacao(String implementacao) {
    this.implementacao = implementacao;
  }

  public void setLinguagem(Linguagem linguagem) {
    this.linguagem = linguagem;
  }

  public void setMensagem(String mensagem) {
    this.mensagem = mensagem;
  }

  public void setProblema(Problema problema) {
    this.problema = problema;
  }

  public void setTempo(Integer tempo) {
    this.tempo = tempo;
  }

  @Override
  public String toString() {
    return "Submissao [id=" + id + ", autor=" + autor + ", linguagem=" + linguagem
        + ", implementacao=" + implementacao + ", tempo=" + tempo + ", estado=" + estado
        + ", dataEnvio=" + dataEnvio + "]";
  }

  private void inicializar() {
    this.dataEnvio = new Date();
  }

}
