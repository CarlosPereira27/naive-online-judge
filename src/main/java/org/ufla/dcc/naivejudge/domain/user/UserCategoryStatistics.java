package org.ufla.dcc.naivejudge.domain.user;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.ufla.dcc.naivejudge.domain.problem.Category;
import org.ufla.dcc.naivejudge.domain.problem.State;

@Entity
public class UserCategoryStatistics implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column(nullable = false)
  private Integer qtySubmissions;

  @Column(nullable = false)
  private Integer qtyAcceptedProblems;

  @Column(nullable = false)
  private Integer qtyTryProblems;

  @Column(nullable = false)
  @Enumerated(EnumType.ORDINAL)
  private Category category;

  @ManyToOne
  @JoinColumn(nullable = false)
  private User user;

  public UserCategoryStatistics() {
    this.qtySubmissions = 0;
    this.qtyAcceptedProblems = 0;
    this.qtyTryProblems = 0;
  }

  public UserCategoryStatistics(Integer qtySubmissions, Integer qtyAcceptedProblems,
      Integer qtyTryProblems, Category category) {
    this.qtySubmissions = qtySubmissions;
    this.qtyAcceptedProblems = qtyAcceptedProblems;
    this.qtyTryProblems = qtyTryProblems;
    this.category = category;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    UserCategoryStatistics other = (UserCategoryStatistics) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  public Category getCategory() {
    return category;
  }

  public Long getId() {
    return id;
  }

  public Integer getQtyAcceptedProblems() {
    return qtyAcceptedProblems;
  }

  public Integer getQtySubmissions() {
    return qtySubmissions;
  }

  public Integer getQtyTryProblems() {
    return qtyTryProblems;
  }

  public User getUser() {
    return user;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setQtyAcceptedProblems(Integer qtyAcceptedProblems) {
    this.qtyAcceptedProblems = qtyAcceptedProblems;
  }

  public void setQtySubmissions(Integer qtySubmissions) {
    this.qtySubmissions = qtySubmissions;
  }

  public void setQtyTryProblems(Integer qtyTryProblems) {
    this.qtyTryProblems = qtyTryProblems;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return "UserCategoryStatistics [id=" + id + ", qtySubmissions=" + qtySubmissions
        + ", qtyAcceptedProblems=" + qtyAcceptedProblems + ", qtyTryProblems=" + qtyTryProblems
        + ", category=" + category + "]";
  }

  public void updateSubmission(boolean newProblem, boolean notAccepted, State state) {
    qtySubmissions++;
    if (newProblem) {
      qtyTryProblems++;
    }
    if (state.equals(State.ACCEPTED) && notAccepted) {
      qtyAcceptedProblems++;
    }
  }

}
