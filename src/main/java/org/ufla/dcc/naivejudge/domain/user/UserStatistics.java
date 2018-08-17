package org.ufla.dcc.naivejudge.domain.user;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.ufla.dcc.naivejudge.domain.problem.State;

@Embeddable
public class UserStatistics implements Serializable {

  private static final long serialVersionUID = 1L;

  @Column(nullable = false)
  private Integer qtySubmissions;

  @Column(nullable = false)
  private Integer qtyAcceptedProblems;

  @Column(nullable = false)
  private Integer qtyTryProblems;

  public UserStatistics() {
    this.qtySubmissions = 0;
    this.qtyAcceptedProblems = 0;
    this.qtyTryProblems = 0;
  }

  public UserStatistics(Integer qtySubmissions, Integer qtyAcceptedProblems,
      Integer qtyTryProblems) {
    this.qtySubmissions = qtySubmissions;
    this.qtyAcceptedProblems = qtyAcceptedProblems;
    this.qtyTryProblems = qtyTryProblems;
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

  public void setQtyAcceptedProblems(Integer qtyAcceptedProblems) {
    this.qtyAcceptedProblems = qtyAcceptedProblems;
  }

  public void setQtySubmissions(Integer qtySubmissions) {
    this.qtySubmissions = qtySubmissions;
  }

  public void setQtyTryProblems(Integer qtyTryProblems) {
    this.qtyTryProblems = qtyTryProblems;
  }

  @Override
  public String toString() {
    return "UserStatistics [qtySubmissions=" + qtySubmissions + ", qtyAcceptedProblems="
        + qtyAcceptedProblems + ", qtyTryProblems=" + qtyTryProblems + "]";
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
