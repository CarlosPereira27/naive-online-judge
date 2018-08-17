package org.ufla.dcc.naivejudge.domain.problem;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OrderColumn;

@Entity
public class CategoryStatistics implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column
  @Enumerated(EnumType.ORDINAL)
  @OrderColumn
  public Category category;

  @Column(nullable = false)
  private Integer qtyProblems;

  public CategoryStatistics() {

  }

  public CategoryStatistics(Category category, Integer qtyProblems) {
    this.category = category;
    this.qtyProblems = qtyProblems;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    CategoryStatistics other = (CategoryStatistics) obj;
    if (category != other.category)
      return false;
    return true;
  }

  public Category getCategory() {
    return category;
  }

  public Integer getQtyProblems() {
    return qtyProblems;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((category == null) ? 0 : category.hashCode());
    return result;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public void setQtyProblems(Integer qtyProblems) {
    this.qtyProblems = qtyProblems;
  }

  @Override
  public String toString() {
    return "CategoryStatistics [category=" + category + ", qtyProblems=" + qtyProblems + "]";
  }

}
