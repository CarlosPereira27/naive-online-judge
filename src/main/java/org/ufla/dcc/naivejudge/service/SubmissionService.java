package org.ufla.dcc.naivejudge.service;

import java.util.List;
import org.ufla.dcc.naivejudge.domain.problem.Submission;
import org.ufla.dcc.naivejudge.domain.user.User;

public interface SubmissionService {

  Submission getSubmission(Long id);

  List<Submission> getSubmissions(User user);

}
