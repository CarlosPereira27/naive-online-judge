package org.ufla.dcc.naivejudge.modelo.forum;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.ufla.dcc.naivejudge.modelo.usuario.Usuario;

@Entity
public class ForumMensagem implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Integer id;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Usuario autor;

  @Column(columnDefinition = "text", nullable = false)
  private String texto;

  @Column(nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date dataEnvio;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Forum forum;

  public ForumMensagem() {
    inicializar();
  }

  public ForumMensagem(Usuario autor, String texto, Forum forum) {
    this.autor = autor;
    this.texto = texto;
    this.forum = forum;
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
    ForumMensagem other = (ForumMensagem) obj;
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

  public Date getDataEnvio() {
    return dataEnvio;
  }

  public Forum getForum() {
    return forum;
  }

  public Integer getId() {
    return id;
  }

  public String getTexto() {
    return texto;
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

  public void setDataEnvio(Date dataEnvio) {
    this.dataEnvio = dataEnvio;
  }

  public void setForum(Forum forum) {
    this.forum = forum;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setTexto(String texto) {
    this.texto = texto;
  }

  @Override
  public String toString() {
    return "ForumMensagem [id=" + id + ", autor=" + autor + ", texto=" + texto + ", dataEnvio="
        + dataEnvio + ", forum=" + forum + "]";
  }

  private void inicializar() {
    this.dataEnvio = new Date();
  }

}
