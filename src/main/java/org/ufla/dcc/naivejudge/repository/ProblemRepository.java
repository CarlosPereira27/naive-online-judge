package org.ufla.dcc.naivejudge.repository;

import java.util.List;
import java.util.SortedSet;
import org.ufla.dcc.naivejudge.domain.forum.Forum;
import org.ufla.dcc.naivejudge.domain.problem.Category;
import org.ufla.dcc.naivejudge.domain.problem.CategoryStatistics;
import org.ufla.dcc.naivejudge.domain.problem.Problem;
import org.ufla.dcc.naivejudge.domain.problem.ProblemJudge;
import org.ufla.dcc.naivejudge.domain.problem.ProblemStatistics;
import org.ufla.dcc.naivejudge.domain.problem.State;

public interface ProblemRepository {

  Forum createAForum();

  void createInstances(ProblemJudge judge, SortedSet<String> tests);

  void createProblem(Problem problem);

  ProblemStatistics createProblemStatistics();

  List<CategoryStatistics> getCategoryStatistics();

  Problem getProblem(Long id);

  List<Problem> getProblems();

  List<Problem> getProblems(Category category);

  ProblemJudge loadJudge(Problem problem);

  void saveJudge(ProblemJudge judge);

  void updateStatistics(Problem problem, boolean notAccepted, State state);

}
