package org.ufla.dcc.naivejudge.service;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ufla.dcc.naivejudge.domain.problem.Submission;
import org.ufla.dcc.naivejudge.domain.user.User;
import org.ufla.dcc.naivejudge.repository.SubmissionRepository;

@Service
public class SubmissionServiceImpl implements SubmissionService {

  @Autowired
  private SubmissionRepository submissionRepository;

  @Override
  @Transactional
  public Submission getSubmission(Long id) {
    return submissionRepository.getSubmission(id);
  }

  @Override
  @Transactional
  public List<Submission> getSubmissions(User user) {
    return submissionRepository.getSubmissions(user);
  }

}
