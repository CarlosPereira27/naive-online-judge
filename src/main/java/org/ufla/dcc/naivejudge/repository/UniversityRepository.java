package org.ufla.dcc.naivejudge.repository;

import java.util.List;
import org.ufla.dcc.naivejudge.domain.user.University;

public interface UniversityRepository {

  List<University> getUniversities();

  University getUniversity(Long id);

  University getUniversity(String name);

  boolean saveUniversity(University university);

}
