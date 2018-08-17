package org.ufla.dcc.naivejudge.dto;

import java.io.Serializable;
import org.ufla.dcc.naivejudge.domain.problem.Category;

public class CategoryProgress implements Serializable {

  private static final long serialVersionUID = 1L;

  private Category category;

  private Double progress;

  public CategoryProgress() {}

  public CategoryProgress(Category category, Double progress) {
    this.category = category;
    this.progress = progress;
  }

  public Category getCategory() {
    return category;
  }

  public Double getProgress() {
    return progress;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public void setProgress(Double progress) {
    this.progress = progress;
  }

}
