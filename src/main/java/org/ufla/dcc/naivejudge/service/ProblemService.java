package org.ufla.dcc.naivejudge.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import org.ufla.dcc.naivejudge.domain.problem.Category;
import org.ufla.dcc.naivejudge.domain.problem.Problem;
import org.ufla.dcc.naivejudge.domain.problem.Submission;

public interface ProblemService {

  Problem getProblem(Long id);

  List<Problem> getProblems();

  List<Problem> getProblems(Category category);

  void processSubmission(Submission submission);

  void save(Problem problem, MultipartFile[] testFiles, MultipartFile implementationFile);

}
