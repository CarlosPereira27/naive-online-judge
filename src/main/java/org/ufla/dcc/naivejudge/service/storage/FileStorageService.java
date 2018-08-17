package org.ufla.dcc.naivejudge.service.storage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService implements StorageService {

  public static final String ROOT_FOLDER =
      System.getProperty("user.home") + File.separator + ".naive-judge-files" + File.separator;

  private final Path rootLocation = Paths.get(StorageProperties.getInstance().getFolder());

  @Autowired
  public FileStorageService() {}

  @Override
  public void store(MultipartFile file, String folder) {
    try {
      if (file.isEmpty()) {
        throw new RuntimeException("Failed to store empty file " + file.getOriginalFilename());
      }

      File newFile = new File(folder + File.separator + file.getOriginalFilename());
      Files.copy(file.getInputStream(), this.rootLocation.resolve(newFile.getAbsolutePath()));
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
    }
  }

  @Override
  public Path load(String filepath) {
    return rootLocation.resolve(filepath);
  }

  @Override
  public Resource loadAsResource(String filepath) {
    try {
      Path file = load(filepath);
      Resource resource = new UrlResource(file.toUri());
      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        return null;
      }
    } catch (MalformedURLException e) {
      throw new RuntimeException("Could not read file: " + filepath, e);
    }
  }

  @Override
  public Stream<Path> loadAll() {
    try {
      return Files.walk(this.rootLocation, 1).filter(path -> !path.equals(this.rootLocation))
          .map(path -> this.rootLocation.relativize(path));
    } catch (IOException e) {
      throw new RuntimeException("Failed to read stored files", e);
    }
  }

  @Override
  public void deleteAll() {
    FileSystemUtils.deleteRecursively(rootLocation.toFile());
  }

  public void init() {
    try {
      Files.createDirectory(rootLocation);
    } catch (IOException e) {

    }
  }

}
