package org.ufla.dcc.naivejudge.domain.user;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.OrderColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class University implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String acronym;

  @Column(nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

  @Column(nullable = false)
  private Integer qtyStudents;

  @OrderColumn(nullable = false)
  private Integer qtyAcceptedProblems;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "university")
  @OrderBy("qtyAcceptedProblems DESC, qtySubmissions ASC")
  private List<User> students;

  public University() {
    init();
  }

  public University(String name, String acronym) {
    this.name = name;
    this.acronym = acronym;
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
    University other = (University) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  public String getAcronym() {
    return acronym;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Integer getQtyAcceptedProblems() {
    return qtyAcceptedProblems;
  }

  public Integer getQtyStudents() {
    return qtyStudents;
  }

  public List<User> getStudents() {
    return students;
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
    this.qtyStudents = 0;
    this.qtyAcceptedProblems = 0;
  }

  /**
   * Vincular um estudante
   * 
   * @param student
   */
  public void linkStudent(User student) {
    this.qtyStudents++;
    this.qtyAcceptedProblems += student.getStatistics().getQtyAcceptedProblems();
  }

  public void setAcronym(String acronym) {
    this.acronym = acronym;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setQtyAcceptedProblems(Integer qtyAcceptedProblems) {
    this.qtyAcceptedProblems = qtyAcceptedProblems;
  }

  public void setQtyStudents(Integer qtyStudents) {
    this.qtyStudents = qtyStudents;
  }

  public void setStudents(List<User> students) {
    this.students = students;
  }

  @Override
  public String toString() {
    return "University [id=" + id + ", name=" + name + ", acronym=" + acronym + ", createdAt="
        + createdAt + ", qtyStudents=" + qtyStudents + ", qtyAcceptedProblems="
        + qtyAcceptedProblems + "]";
  }

  /**
   * Desvincular um estudante
   * 
   * @param student
   */
  public void unlinkStudent(User student) {
    this.qtyStudents--;
    this.qtyAcceptedProblems -= student.getStatistics().getQtyAcceptedProblems();
  }

}
