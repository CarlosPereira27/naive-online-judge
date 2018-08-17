package org.ufla.dcc.naivejudge.domain.problem;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ProblemStatistics implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column(nullable = false)
  private Integer qtySubmissions;

  @Column(nullable = false)
  private Integer qtyAccepted;

  public ProblemStatistics() {
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
    ProblemStatistics other = (ProblemStatistics) obj;
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

  public Integer getQtyAccepted() {
    return qtyAccepted;
  }

  public Integer getQtySubmissions() {
    return qtySubmissions;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  private void init() {
    this.qtySubmissions = 0;
    this.qtyAccepted = 0;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setQtyAccepted(Integer qtyAccepted) {
    this.qtyAccepted = qtyAccepted;
  }

  public void setQtySubmissions(Integer qtySubmissions) {
    this.qtySubmissions = qtySubmissions;
  }

  @Override
  public String toString() {
    return "ProblemStatistics [id=" + id + ", qtySubmissions=" + qtySubmissions + ", qtyAccepteds="
        + qtyAccepted + "]";
  }

  public void update(boolean notAccepted, State state) {
    qtySubmissions++;
    if (notAccepted && State.ACCEPTED.equals(state)) {
      qtyAccepted++;
    }
  }

}
