package org.ufla.dcc.naivejudge.repository;

import java.util.List;
import org.ufla.dcc.naivejudge.domain.problem.Problem;
import org.ufla.dcc.naivejudge.domain.problem.Submission;
import org.ufla.dcc.naivejudge.domain.user.User;

public interface SubmissionRepository {

  Submission getSubmission(Long id);

  List<Submission> getSubmissions(User user);

  List<Submission> getSubmissions(User user, Problem problem);

  void saveSubmission(Submission submission);

}
