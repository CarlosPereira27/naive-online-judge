package org.ufla.dcc.naivejudge.domain.forum;

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
import org.ufla.dcc.naivejudge.domain.user.User;

@Entity
public class Post implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @ManyToOne
  @JoinColumn(nullable = false)
  private User author;

  @Column(columnDefinition = "text", nullable = false)
  private String content;

  @Column(nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Forum forum;

  public Post() {
    init();
  }

  public Post(User author, String content, Forum forum) {
    this.author = author;
    this.content = content;
    this.forum = forum;
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
    Post other = (Post) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  public User getAuthor() {
    return author;
  }

  public String getContent() {
    return content;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public Forum getForum() {
    return forum;
  }

  public Long getId() {
    return id;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  private void init() {
    this.createdAt = new Date();
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public void setForum(Forum forum) {
    this.forum = forum;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "Post [id=" + id + ", author=" + author + ", content=" + content + ", createdAt="
        + createdAt + ", forum=" + forum + "]";
  }

}
