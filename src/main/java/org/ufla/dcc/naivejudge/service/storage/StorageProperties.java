package org.ufla.dcc.naivejudge.service.storage;

import java.io.Serializable;

public class StorageProperties implements Serializable {

  private static final long serialVersionUID = 1L;

  private static StorageProperties storageProperties;

  public static StorageProperties getInstance() {
    if (storageProperties == null) {
      storageProperties = new StorageProperties();
    }
    return storageProperties;
  }

  private String folder = ".upload-folder";

  public String getFolder() {
    return folder;
  }

  public void setFolder(String folder) {
    this.folder = folder;
  }

}
