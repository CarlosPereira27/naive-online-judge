package org.ufla.dcc.naivejudge.domain.problem;

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
import org.ufla.dcc.naivejudge.domain.user.User;

@Entity
public class Submission implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @ManyToOne
  @JoinColumn(nullable = false)
  private User author;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Problem problem;

  @Column(nullable = false)
  @Enumerated(EnumType.ORDINAL)
  private ProgrammingLanguage language;

  @Column(columnDefinition = "text", nullable = false)
  private String implementation;

  @Column
  private Integer time;

  @Column
  private State state;

  @Column(columnDefinition = "text")
  private String message;

  @Column(nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date submissionDate;

  public Submission() {
    init();
  }

  public Submission(User author, ProgrammingLanguage language, String implementation) {
    this.author = author;
    this.language = language;
    this.implementation = implementation;
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
    Submission other = (Submission) obj;
    if (author == null) {
      if (other.author != null)
        return false;
    } else if (!author.equals(other.author))
      return false;
    if (submissionDate == null) {
      if (other.submissionDate != null)
        return false;
    } else if (!submissionDate.equals(other.submissionDate))
      return false;
    if (state != other.state)
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (implementation == null) {
      if (other.implementation != null)
        return false;
    } else if (!implementation.equals(other.implementation))
      return false;
    if (language != other.language)
      return false;
    if (time == null) {
      if (other.time != null)
        return false;
    } else if (!time.equals(other.time))
      return false;
    return true;
  }

  public User getAuthor() {
    return author;
  }

  public Long getId() {
    return id;
  }

  public String getImplementation() {
    return implementation;
  }

  public ProgrammingLanguage getLanguage() {
    return language;
  }

  public String getMessage() {
    return message;
  }

  public Problem getProblem() {
    return problem;
  }

  public State getState() {
    return state;
  }

  public Date getSubmissionDate() {
    return submissionDate;
  }

  public Integer getTime() {
    return time;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((author == null) ? 0 : author.hashCode());
    result = prime * result + ((submissionDate == null) ? 0 : submissionDate.hashCode());
    result = prime * result + ((state == null) ? 0 : state.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((implementation == null) ? 0 : implementation.hashCode());
    result = prime * result + ((language == null) ? 0 : language.hashCode());
    result = prime * result + ((time == null) ? 0 : time.hashCode());
    return result;
  }

  private void init() {
    this.submissionDate = new Date();
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setImplementation(String implementation) {
    this.implementation = implementation;
  }

  public void setLanguage(ProgrammingLanguage language) {
    this.language = language;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public void setProblem(Problem problem) {
    this.problem = problem;
  }

  public void setState(State state) {
    this.state = state;
  }

  public void setSubmissionDate(Date submissionDate) {
    this.submissionDate = submissionDate;
  }

  public void setTime(Integer time) {
    this.time = time;
  }

  @Override
  public String toString() {
    return "Submission [id=" + id + ", author=" + author + ", language=" + language
        + ", implementation=" + implementation + ", time=" + time + ", state=" + state
        + ", submissionDate=" + submissionDate + "]";
  }

}
