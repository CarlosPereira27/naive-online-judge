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
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.ufla.dcc.naivejudge.domain.forum.Forum;
import org.ufla.dcc.naivejudge.domain.user.User;

@Entity
public class Problem implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(columnDefinition = "text", nullable = false)
  private String description;

  @Column(nullable = false)
  private Integer limitTime;

  @ManyToOne
  @JoinColumn(nullable = false)
  private User author;

  @Column(nullable = false)
  @Enumerated(EnumType.ORDINAL)
  private Category category;

  @Column(nullable = false)
  private Integer difficulty;

  @Column
  private String subjects;

  @Column
  private String originContest;

  @Column(nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

  @OneToOne
  @JoinColumn(nullable = false, unique = true)
  private ProblemStatistics statistics;

  @OneToOne
  @JoinColumn(nullable = false, unique = true)
  private Forum forum;

  @OneToOne
  @JoinColumn(nullable = false, unique = true)
  private ProblemJudge judge;

  public Problem() {
    init();
  }

  public Problem(String title, String description, Integer limitTime, User author,
      Category category, Integer difficulty, ProblemJudge judge) {
    this.title = title;
    this.description = description;
    this.limitTime = limitTime;
    this.author = author;
    this.category = category;
    this.difficulty = difficulty;
    this.judge = judge;
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
    Problem other = (Problem) obj;
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

  public Category getCategory() {
    return category;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public String getDescription() {
    return description;
  }

  public Integer getDifficulty() {
    return difficulty;
  }

  public Forum getForum() {
    return forum;
  }

  public Long getId() {
    return id;
  }

  public ProblemJudge getJudge() {
    return judge;
  }

  public Integer getLimitTime() {
    return limitTime;
  }

  public String getOriginContest() {
    return originContest;
  }

  public ProblemStatistics getStatistics() {
    return statistics;
  }

  public String getSubjects() {
    return subjects;
  }

  public String getTitle() {
    return title;
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
    this.forum = new Forum(this);
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setDifficulty(Integer difficulty) {
    this.difficulty = difficulty;
  }

  public void setForum(Forum forum) {
    this.forum = forum;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setJudge(ProblemJudge judge) {
    this.judge = judge;
  }

  public void setLimitTime(Integer limitTime) {
    this.limitTime = limitTime;
  }

  public void setOriginContest(String originContest) {
    this.originContest = originContest;
  }

  public void setStatistics(ProblemStatistics statistics) {
    this.statistics = statistics;
  }

  public void setSubjects(String subjects) {
    this.subjects = subjects;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public String toString() {
    return "Problem [id=" + id + ", title=" + title + ", description=" + description
        + ", limitTime=" + limitTime + ", author=" + author + ", category=" + category
        + ", difficulty=" + difficulty + ", subjects=" + subjects + ", originContest="
        + originContest + ", createdAt=" + createdAt + ", statistics=" + statistics + "]";
  }

}
