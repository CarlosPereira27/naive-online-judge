package org.ufla.dcc.naivejudge.domain.problem;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ProblemInstance implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column(nullable = false)
  private String inputFile;

  @Column(nullable = false)
  private String outputFile;

  @ManyToOne
  @JoinColumn(nullable = false)
  private ProblemJudge judge;

  public ProblemInstance() {

  }

  public ProblemInstance(String test, ProblemJudge judge) {
    this.inputFile = test + ".in";
    this.outputFile = test + ".sol";
    this.judge = judge;
  }

  public ProblemInstance(String inputFile, String outputFile, ProblemJudge judge) {
    this.inputFile = inputFile;
    this.outputFile = outputFile;
    this.judge = judge;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ProblemInstance other = (ProblemInstance) obj;
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

  public String getInputFile() {
    return inputFile;
  }

  public ProblemJudge getJudge() {
    return judge;
  }

  public String getOutputFile() {
    return outputFile;
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

  public void setInputFile(String inputFile) {
    this.inputFile = inputFile;
  }

  public void setJudge(ProblemJudge judge) {
    this.judge = judge;
  }

  public void setOutputFile(String outputFile) {
    this.outputFile = outputFile;
  }

  @Override
  public String toString() {
    return "ProblemInstance [id=" + id + ", inputFile=" + inputFile + ", outputFile=" + outputFile
        + "]";
  }

}
