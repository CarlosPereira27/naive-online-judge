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
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.ufla.dcc.naivejudge.modelo.enums.Categoria;
import org.ufla.dcc.naivejudge.modelo.forum.Forum;
import org.ufla.dcc.naivejudge.modelo.usuario.Usuario;

@Entity
public class Problema implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Integer id;

  @Column(nullable = false)
  private String titulo;

  @Column(columnDefinition = "text", nullable = false)
  private String descricao;

  @Column(nullable = false)
  private Integer tempoLimite;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Usuario autor;

  @Column(nullable = false)
  @Enumerated(EnumType.ORDINAL)
  private Categoria categoria;

  @Column(nullable = false)
  private Integer dificuldade;

  @Column
  private String topicos;

  @Column
  private String contestOrigem;

  @Column(nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date dataInsercao;

  @OneToOne
  @JoinColumn(nullable = false, unique = true)
  private ProblemaEstatisticas estatisticas;

  @OneToOne
  @JoinColumn(nullable = false, unique = true)
  private Forum forum;

  @OneToOne
  @JoinColumn(nullable = false, unique = true)
  private AvaliacaoProblema avaliacao;

  public Problema() {
    inicializar();
  }

  public Problema(String titulo, String descricao, Integer tempoLimite, Usuario autor,
      Categoria categoria, Integer dificuldade, AvaliacaoProblema avaliacao) {
    this.titulo = titulo;
    this.descricao = descricao;
    this.tempoLimite = tempoLimite;
    this.autor = autor;
    this.categoria = categoria;
    this.dificuldade = dificuldade;
    this.avaliacao = avaliacao;
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
    Problema other = (Problema) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  public Usuario getAutor() {
    return autor;
  }

  public AvaliacaoProblema getAvaliacao() {
    return avaliacao;
  }

  public Categoria getCategoria() {
    return categoria;
  }

  public String getContestOrigem() {
    return contestOrigem;
  }

  public Date getDataInsercao() {
    return dataInsercao;
  }

  public String getDescricao() {
    return descricao;
  }

  public Integer getDificuldade() {
    return dificuldade;
  }

  public ProblemaEstatisticas getEstatisticas() {
    return estatisticas;
  }

  public Forum getForum() {
    return forum;
  }

  public Integer getId() {
    return id;
  }

  public Integer getTempoLimite() {
    return tempoLimite;
  }

  public String getTitulo() {
    return titulo;
  }

  public String getTopicos() {
    return topicos;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  public void setAutor(Usuario autor) {
    this.autor = autor;
  }

  public void setAvaliacao(AvaliacaoProblema avaliacao) {
    this.avaliacao = avaliacao;
  }

  public void setCategoria(Categoria categoria) {
    this.categoria = categoria;
  }

  public void setContestOrigem(String contestOrigem) {
    this.contestOrigem = contestOrigem;
  }

  public void setDataInsercao(Date dataInsercao) {
    this.dataInsercao = dataInsercao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public void setDificuldade(Integer dificuldade) {
    this.dificuldade = dificuldade;
  }

  public void setEstatisticas(ProblemaEstatisticas estatisticas) {
    this.estatisticas = estatisticas;
  }

  public void setForum(Forum forum) {
    this.forum = forum;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setTempoLimite(Integer tempoLimite) {
    this.tempoLimite = tempoLimite;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public void setTopicos(String topicos) {
    this.topicos = topicos;
  }

  @Override
  public String toString() {
    return "Problema [id=" + id + ", titulo=" + titulo + ", descricao=" + descricao
        + ", tempoLimite=" + tempoLimite + ", autor=" + autor + ", categoria=" + categoria
        + ", dificuldade=" + dificuldade + ", topicos=" + topicos + ", contestOrigem="
        + contestOrigem + ", dataInsercao=" + dataInsercao + ", estatiscas=" + estatisticas + "]";
  }

  private void inicializar() {
    this.dataInsercao = new Date();
    this.forum = new Forum(this);
  }

}
