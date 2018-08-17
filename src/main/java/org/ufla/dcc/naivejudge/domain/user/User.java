package org.ufla.dcc.naivejudge.domain.user;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class User implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String passwordHash;

  @Column
  @Enumerated(EnumType.ORDINAL)
  private Gender gender;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn
  private University university;

  @Column
  private String country;

  @Column(nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "qtySubmissions", column = @Column(name = "qtySubmissions")),
      @AttributeOverride(name = "qtyAcceptedProblems",
          column = @Column(name = "qtyAcceptedProblems")),
      @AttributeOverride(name = "qtyTryProblems", column = @Column(name = "qtyTryProblems"))})
  private UserStatistics statistics;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  @OrderBy("category ASC")
  private List<UserCategoryStatistics> categoryStatistics;

  public User() {
    init();
  }

  public User(String name, String email, String passwordHash) {
    this.name = name;
    this.email = email;
    this.passwordHash = passwordHash;
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
    User other = (User) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  public List<UserCategoryStatistics> getCategoryStatistics() {
    return categoryStatistics;
  }

  public String getCountry() {
    return country;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public String getEmail() {
    return email;
  }

  public Gender getGender() {
    return gender;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public UserStatistics getStatistics() {
    return statistics;
  }

  public University getUniversity() {
    return university;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  public void init() {
    this.createdAt = new Date();
  }

  public void setCategoryStatistics(List<UserCategoryStatistics> categoryStatistics) {
    this.categoryStatistics = categoryStatistics;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public void setStatistics(UserStatistics statistics) {
    this.statistics = statistics;
  }

  public void setUniversity(University university) {
    this.university = university;
  }

  @Override
  public String toString() {
    return "User [id=" + id + ", name=" + name + ", email=" + email + ", passwordHash="
        + passwordHash + ", gender=" + gender + ", university=" + university + ", country="
        + country + ", createdAt=" + createdAt + "]";
  }

}
