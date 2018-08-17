package org.ufla.dcc.naivejudge.modelo.forum;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.ufla.dcc.naivejudge.modelo.problema.Problema;

@Entity
public class Forum implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Integer id;

  @OneToOne(mappedBy = "forum")
  private Problema problema;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "forum")
  private List<ForumMensagem> mensagens;

  public Forum() {

  }

  public Forum(Problema problema) {
    this.problema = problema;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Forum other = (Forum) obj;
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

  public List<ForumMensagem> getMensagens() {
    return mensagens;
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

  public void setId(Integer id) {
    this.id = id;
  }

  public void setMensagens(List<ForumMensagem> mensagens) {
    this.mensagens = mensagens;
  }

  public void setProblema(Problema problema) {
    this.problema = problema;
  }

  @Override
  public String toString() {
    return "Forum [id=" + id + ", problema=" + problema + "]";
  }

}
