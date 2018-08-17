package org.ufla.dcc.naivejudge.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Progress implements Serializable {

  private static final long serialVersionUID = 1L;

  private Double generalProgress;

  private List<CategoryProgress> categoryProgressList = new ArrayList<>();

  public void addCategoryProgress(CategoryProgress categoryProgress) {
    categoryProgressList.add(categoryProgress);
  }

  public List<CategoryProgress> getCategoryProgressList() {
    return categoryProgressList;
  }

  public Double getGeneralProgress() {
    return generalProgress;
  }

  public void setCategoryProgressList(List<CategoryProgress> categoryProgressList) {
    this.categoryProgressList = categoryProgressList;
  }

  public void setGeneralProgress(Double generalProgress) {
    this.generalProgress = generalProgress;
  }

}
