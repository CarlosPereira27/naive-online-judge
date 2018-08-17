package org.ufla.dcc.naivejudge.domain.forum;

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
import org.ufla.dcc.naivejudge.domain.problem.Problem;

@Entity
public class Forum implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @OneToOne(mappedBy = "forum")
  private Problem problem;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "forum")
  private List<Post> posts;

  public Forum() {

  }

  public Forum(Problem problem) {
    this.problem = problem;
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

  public Long getId() {
    return id;
  }

  public List<Post> getPosts() {
    return posts;
  }

  public Problem getProblem() {
    return problem;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setPosts(List<Post> posts) {
    this.posts = posts;
  }

  public void setProblema(Problem problem) {
    this.problem = problem;
  }

  @Override
  public String toString() {
    return "Forum [id=" + id + ", problem=" + problem + "]";
  }

}
