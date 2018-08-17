package org.ufla.dcc.naivejudge.service.storage;

import java.nio.file.Path;
import java.util.stream.Stream;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

  void store(MultipartFile file, String folder);

  Path load(String filepath);

  Resource loadAsResource(String filepath);

  Stream<Path> loadAll();

  void deleteAll();

  void init();

}
