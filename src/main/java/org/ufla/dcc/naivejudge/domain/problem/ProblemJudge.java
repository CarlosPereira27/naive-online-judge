package org.ufla.dcc.naivejudge.domain.problem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class ProblemJudge implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @OneToOne(mappedBy = "judge")
  private Problem problem;

  @Column(nullable = false)
  @Enumerated(EnumType.ORDINAL)
  private ProgrammingLanguage implementationLanguage;

  @Column(nullable = false)
  private String implementation;

  @Column
  @Enumerated(EnumType.ORDINAL)
  private ProgrammingLanguage inputGeneratorLanguage;

  @Column
  private String inputGenerator;

  @OneToMany(mappedBy = "judge")
  private List<ProblemInstance> instances;

  public ProblemJudge() {
    instances = new ArrayList<>();
  }

  public ProblemJudge(Problem problem, ProgrammingLanguage implementationLanguage,
      String implementation) {
    this.problem = problem;
    this.implementationLanguage = implementationLanguage;
    this.implementation = implementation;
    instances = new ArrayList<>();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ProblemJudge other = (ProblemJudge) obj;
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

  public String getImplementation() {
    return implementation;
  }

  public ProgrammingLanguage getImplementationLanguage() {
    return implementationLanguage;
  }

  public String getInputGenerator() {
    return inputGenerator;
  }

  public ProgrammingLanguage getInputGeneratorLanguage() {
    return inputGeneratorLanguage;
  }

  public List<ProblemInstance> getInstances() {
    return instances;
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

  public void setImplementation(String implementation) {
    this.implementation = implementation;
  }

  public void setImplementationLanguage(ProgrammingLanguage implementationLanguage) {
    this.implementationLanguage = implementationLanguage;
  }

  public void setInputGeneratorDeEntradas(String inputGenerator) {
    this.inputGenerator = inputGenerator;
  }

  public void setInputGeneratorLanguage(ProgrammingLanguage inputGeneratorLanguage) {
    this.inputGeneratorLanguage = inputGeneratorLanguage;
  }

  public void setInstances(List<ProblemInstance> instances) {
    this.instances = instances;
  }

  public void setProblem(Problem problem) {
    this.problem = problem;
  }

  @Override
  public String toString() {
    return "ProblemJudge [id=" + id + ", implementationLanguage=" + implementationLanguage
        + ", implementation=" + implementation + ", inputGeneratorLanguage="
        + inputGeneratorLanguage + ", inputGenerator=" + inputGenerator + ", instances=" + instances
        + "]";
  }

}
