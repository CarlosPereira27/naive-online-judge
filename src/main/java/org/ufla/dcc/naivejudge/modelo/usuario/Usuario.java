package org.ufla.dcc.naivejudge.modelo.usuario;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.ufla.dcc.naivejudge.modelo.enums.Genero;

@Entity
public class Usuario implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Integer id;

  @Column(nullable = false)
  private String nome;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String senha;

  @Column
  @Enumerated(EnumType.ORDINAL)
  private Genero genero;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn
  private Universidade universidade;

  @Column
  private String pais;

  @Column(nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date dataInsercao;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "qtdSubmissoes", column = @Column(name = "qtdSubmissoes")),
      @AttributeOverride(name = "qtdProblemasResolvidos",
          column = @Column(name = "qtdProblemasResolvidos")),
      @AttributeOverride(name = "qtdProblemasTentados",
          column = @Column(name = "qtdProblemasTentados"))})
  private UsuarioEstatistica estatistica;

  @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
  @OrderBy("categoria ASC")
  private List<UsuarioCatEst> estatisticasCategoria;

  public Usuario() {
    init();
  }

  public Usuario(String nome, String email, String senha) {
    this.nome = nome;
    this.email = email;
    this.senha = senha;
    init();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Usuario other = (Usuario) obj;
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

  public String getEmail() {
    return email;
  }

  public UsuarioEstatistica getEstatistica() {
    return estatistica;
  }

  public List<UsuarioCatEst> getEstatisticasCategoria() {
    return estatisticasCategoria;
  }

  public Genero getGenero() {
    return genero;
  }

  public Integer getId() {
    return id;
  }

  public String getNome() {
    return nome;
  }

  public String getPais() {
    return pais;
  }

  public String getSenha() {
    return senha;
  }

  public Universidade getUniversidade() {
    return universidade;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  public void init() {
    this.dataInsercao = new Date();
  }

  public void setDataInsercao(Date dataInsercao) {
    this.dataInsercao = dataInsercao;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setEstatistica(UsuarioEstatistica estatistica) {
    this.estatistica = estatistica;
  }

  public void setEstatisticasCategoria(List<UsuarioCatEst> estatisticasCategoria) {
    this.estatisticasCategoria = estatisticasCategoria;
  }

  public void setGenero(Genero genero) {
    this.genero = genero;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public void setPais(String pais) {
    this.pais = pais;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public void setUniversidade(Universidade universidade) {
    this.universidade = universidade;
  }

  @Override
  public String toString() {
    return "Usuario [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha
        + ", genero=" + genero + ", universidade=" + universidade + ", pais=" + pais
        + ", dataInsercao=" + dataInsercao + "]";
  }

}
