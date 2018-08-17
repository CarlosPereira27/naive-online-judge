package org.ufla.dcc.naivejudge.service;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ufla.dcc.naivejudge.domain.user.University;
import org.ufla.dcc.naivejudge.domain.user.User;
import org.ufla.dcc.naivejudge.repository.UniversityRepository;
import org.ufla.dcc.naivejudge.repository.UserRepository;

@Service
public class UniversityServiceImpl implements UniversityService {

  @Autowired
  private UniversityRepository universityRepository;

  @Autowired
  private UserRepository userRepository;

  @Override
  @Transactional
  public List<University> getUniversities() {
    return universityRepository.getUniversities();
  }

  @Override
  @Transactional
  public boolean save(University university) {
    return universityRepository.saveUniversity(university);
  }

  @Override
  @Transactional
  public List<User> studentsRank(University university) {
    return userRepository.getUsers(university);
  }

}
