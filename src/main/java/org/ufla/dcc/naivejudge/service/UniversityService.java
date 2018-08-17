package org.ufla.dcc.naivejudge.service;

import java.util.List;
import org.ufla.dcc.naivejudge.domain.user.University;
import org.ufla.dcc.naivejudge.domain.user.User;

public interface UniversityService {

  List<University> getUniversities();

  boolean save(University university);

  List<User> studentsRank(University university);

}
