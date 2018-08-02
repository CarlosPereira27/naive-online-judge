package org.ufla.dcc.naivejudge.modelo.usuario;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.OrderColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Universidade implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Integer id;

  @Column(nullable = false)
  private String nome;

  @Column(nullable = false)
  private String sigla;

  @Column(nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date dataInsercao;

  @Column(nullable = false)
  private Integer qtdEstudantes;

  @OrderColumn(nullable = false)
  private Integer qtdProblemasResolvidos;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "universidade")
  @OrderBy("qtdProblemasResolvidos DESC, qtdSubmissoes ASC")
  private List<Usuario> usuarios;

  public Universidade() {
    inicializar();
  }

  public Universidade(String nome, String sigla) {
    this.nome = nome;
    this.sigla = sigla;
    inicializar();
  }

  /**
   * Desvincular um estudante
   * 
   * @param usuario
   */
  public void desvincularEstudante(Usuario usuario) {
    this.qtdEstudantes--;
    this.qtdProblemasResolvidos -= usuario.getEstatistica().getQtdProblemasResolvidos();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Universidade other = (Universidade) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  public Date getDataInsercao() {
    return dataInsercao;
  }

  public Integer getId() {
    return id;
  }

  public String getNome() {
    return nome;
  }

  public Integer getQtdEstudantes() {
    return qtdEstudantes;
  }

  public Integer getQtdProblemasResolvidos() {
    return qtdProblemasResolvidos;
  }

  public String getSigla() {
    return sigla;
  }

  public List<Usuario> getUsuarios() {
    return usuarios;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  public void setDataInsercao(Date dataInsercao) {
    this.dataInsercao = dataInsercao;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public void setQtdEstudantes(Integer qtdEstudantes) {
    this.qtdEstudantes = qtdEstudantes;
  }

  public void setQtdProblemasResolvidos(Integer qtdProblemasResolvidos) {
    this.qtdProblemasResolvidos = qtdProblemasResolvidos;
  }

  public void setSigla(String sigla) {
    this.sigla = sigla;
  }

  public void setUsuarios(List<Usuario> usuarios) {
    this.usuarios = usuarios;
  }

  @Override
  public String toString() {
    return "Universidade [id=" + id + ", nome=" + nome + ", sigla=" + sigla + ", dataInsercao="
        + dataInsercao + ", qtdEstudantes=" + qtdEstudantes + ", qtdProblemasResolvidos="
        + qtdProblemasResolvidos + "]";
  }

  /**
   * Vincular um estudante
   * 
   * @param usuario
   */
  public void vincularEstudante(Usuario usuario) {
    this.qtdEstudantes++;
    this.qtdProblemasResolvidos += usuario.getEstatistica().getQtdProblemasResolvidos();
  }

  private void inicializar() {
    this.dataInsercao = new Date();
    this.qtdEstudantes = 0;
    this.qtdProblemasResolvidos = 0;
  }

}
