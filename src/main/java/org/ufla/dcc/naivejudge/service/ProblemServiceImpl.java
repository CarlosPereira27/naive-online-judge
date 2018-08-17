package org.ufla.dcc.naivejudge.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder.Redirect;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.ufla.dcc.naivejudge.domain.problem.ProblemJudge;
import org.ufla.dcc.naivejudge.domain.problem.State;
import org.ufla.dcc.naivejudge.domain.problem.ProblemInstance;
import org.ufla.dcc.naivejudge.domain.problem.Category;
import org.ufla.dcc.naivejudge.domain.problem.Problem;
import org.ufla.dcc.naivejudge.domain.problem.Submission;
import org.ufla.dcc.naivejudge.repository.ProblemRepository;
import org.ufla.dcc.naivejudge.repository.SubmissionRepository;
import org.ufla.dcc.naivejudge.repository.UserRepository;
import org.ufla.dcc.naivejudge.service.storage.FileStorageService;
import org.ufla.dcc.naivejudge.service.storage.StorageService;

@Service
public class ProblemServiceImpl implements ProblemService {

  class PairLong {
    public long first = 0;
    public long second = 0;
  }

  class TestInstance implements Comparable<TestInstance> {

    String name;
    boolean input;
    boolean solution;

    public TestInstance(String name) {
      this.name = name;
      input = false;
      solution = false;
    }

    @Override
    public int compareTo(TestInstance o) {
      return name.compareTo(o.name);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      TestInstance other = (TestInstance) obj;
      if (!getOuterType().equals(other.getOuterType()))
        return false;
      if (input != other.input)
        return false;
      return true;
    }

    private ProblemServiceImpl getOuterType() {
      return ProblemServiceImpl.this;
    }

    private String getUniqueExtension() {
      if (input) {
        return INPUT_EXTENSION;
      }
      return SOLUTION_EXTENSION;
    }

    public String getUniqueFile() {
      return name + "." + getUniqueExtension();
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + getOuterType().hashCode();
      result = prime * result + (input ? 1231 : 1237);
      return result;
    }

    public boolean hasOnlyOneFile() {
      return !(input && solution);
    }

  }

  private static final String SOLUTION_EXTENSION = "sol";

  private static final String INPUT_EXTENSION = "in";

  private static final String OUTPUT_EXTENSION = "out";
  // private static final String JAVA_EXTENSION = "java";
  // private static final String JAVA_DEFAULT_FILE = "Main.java";

  @Autowired
  private ProblemRepository problemRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private SubmissionRepository submissionRepository;

  @Autowired
  private StorageService storageService;

  private boolean presentationError;

  private SortedSet<String> convertTestInstance(Map<String, TestInstance> testInstances) {
    SortedSet<String> testNames = new TreeSet<>();
    for (TestInstance testInstance : testInstances.values()) {
      testNames.add(testInstance.name);
    }
    return testNames;
  }

  private SortedSet<String> extractTestNames(MultipartFile[] testFiles) {
    Map<String, TestInstance> testInstances = new TreeMap<>();
    Set<String> wrongFiles = new TreeSet<>();
    for (MultipartFile file : testFiles) {
      String[] arraySplit = file.getOriginalFilename().split("\\.");
      if (arraySplit.length != 2 || !isInputOrOutput(arraySplit[1])) {
        wrongFiles.add(file.getOriginalFilename());
        continue;
      }
      TestInstance testInstance =
          testInstances.getOrDefault(arraySplit[0], new TestInstance(arraySplit[0]));
      if ((testInstance.input && arraySplit[1].equals(INPUT_EXTENSION))
          || (testInstance.solution && arraySplit[1].equals(OUTPUT_EXTENSION))) {
        throw new RuntimeException(
            "Erro! Arquivo de teste duplicado " + file.getOriginalFilename() + "!");
      }
      if (arraySplit[1].equals(INPUT_EXTENSION)) {
        testInstance.input = true;
      } else {
        testInstance.solution = true;
      }
      testInstances.put(arraySplit[0], testInstance);
    }
    StringBuilder errorMessage = new StringBuilder();
    if (hasError(testInstances)) {
      errorMessage.append(getErrorMessage(testInstances));
    }
    if (!wrongFiles.isEmpty()) {
      errorMessage.append(getErrorMessage(wrongFiles));
    }
    if (errorMessage.length() > 0) {
      throw new RuntimeException("Erro nos arquivos de teste!<br>" + errorMessage.toString());
    }
    return convertTestInstance(testInstances);
  }

  private String getMessage(InputStream input) {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
    StringBuilder errorMessage = new StringBuilder();
    String line;
    try {
      while ((line = bufferedReader.readLine()) != null) {
        errorMessage.append(line).append('\n');
      }
      bufferedReader.close();
    } catch (IOException e1) {
      e1.printStackTrace();
      return null;
    }
    return errorMessage.toString();
  }

  private String getErrorMessage(Map<String, TestInstance> testInstances) {
    StringBuilder sb = new StringBuilder();
    sb.append("Os seguintes arquivos não contêm seus respectivos pares (in/sol):<br>");
    for (TestInstance testInstance : testInstances.values()) {
      if (testInstance.hasOnlyOneFile()) {
        sb.append(testInstance.getUniqueFile()).append(", <br>");
      }
    }
    int n = sb.length();
    sb.delete(n - 6, n).append("<br>");
    return sb.toString();
  }

  private String getErrorMessage(Set<String> wrongFiles) {
    StringBuilder sb = new StringBuilder();
    sb.append("Os seguintes arquivos não respeitam o padrão de arquivos de teste (in/sol):<br>");
    for (String file : wrongFiles) {
      sb.append(file).append(", <br>");
    }
    int n = sb.length();
    sb.delete(n - 6, n).append("<br>");
    return sb.toString();
  }

  @Override
  @Transactional
  public Problem getProblem(Long id) {
    return problemRepository.getProblem(id);
  }

  @Override
  @Transactional
  public List<Problem> getProblems() {
    return problemRepository.getProblems();
  }

  @Override
  @Transactional
  public List<Problem> getProblems(Category category) {
    return problemRepository.getProblems(category);
  }

  private boolean isInputOrOutput(String extension) {
    return extension != null
        && (extension.equals(INPUT_EXTENSION) || extension.equals(SOLUTION_EXTENSION));
  }

  @Override
  @Transactional
  public void processSubmission(Submission submission) {
    ProblemJudge judge = problemRepository.loadJudge(submission.getProblem());
    List<Submission> submissions =
        submissionRepository.getSubmissions(submission.getAuthor(), submission.getProblem());
    boolean newProblem = submissions.isEmpty();
    boolean notAccepted = !accepted(submissions);
    String folderpath = FileStorageService.ROOT_FOLDER + "submission-"
        + String.valueOf(submission.getAuthor().getId());
    File folder = new File(folderpath);
    if (!folder.exists()) {
      folder.mkdirs();
    }
    File file = new File(folderpath + "/Main.java");
    if (file.exists()) {
      file.delete();
    }
    Process process = null;
    try {
      file.createNewFile();
      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
      bufferedWriter.write(submission.getImplementation());
      bufferedWriter.close();
      process = Runtime.getRuntime().exec("javac " + file.getAbsolutePath());
      process.waitFor();
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    if (process.exitValue() != 0) {
      submission.setMessage(getMessage(process.getErrorStream()));
      submission.setState(State.COMPILATION_ERROR);
    } else {
      validateResults(submission, judge, folderpath);
    }
    submissionRepository.saveSubmission(submission);
    userRepository.updateStatistics(submission, newProblem, notAccepted);
    problemRepository.updateStatistics(submission.getProblem(), notAccepted, submission.getState());
  }

  @Override
  @Transactional
  public void save(Problem problem, MultipartFile[] testFiles,
      MultipartFile implementationFile) {
    problem.setStatistics(problemRepository.createProblemStatistics());
    problem.setForum(problemRepository.createAForum());
    ProblemJudge judge = problem.getJudge();
    judge.setImplementation(implementationFile.getOriginalFilename());
    problemRepository.saveJudge(judge);
    problemRepository.createInstances(judge, extractTestNames(testFiles));
    problemRepository.createProblem(problem);
    String folder = FileStorageService.ROOT_FOLDER + "problem-" + String.valueOf(problem.getId());
    File folderFile = new File(folder);
    if (!folderFile.exists()) {
      folderFile.mkdirs();
    }
    storageService.store(implementationFile, folder);
    for (MultipartFile file : testFiles) {
      storageService.store(file, folder);
    }
    try {
      Runtime.getRuntime()
          .exec("javac " + folder + File.separator + implementationFile.getOriginalFilename());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private boolean accepted(List<Submission> submissions) {
    for (Submission submission : submissions) {
      if (submission.getState().equals(State.ACCEPTED)) {
        return true;
      }
    }
    return false;
  }

  private boolean hasError(Map<String, TestInstance> testInstances) {
    for (TestInstance testInstance : testInstances.values()) {
      if (testInstance.hasOnlyOneFile()) {
        return true;
      }
    }
    return false;
  }

  private PairLong validateInstance(File output, File solution) {
    PairLong par = new PairLong();
    try {
      BufferedReader brOut = new BufferedReader(new FileReader(output));
      BufferedReader brSol = new BufferedReader(new FileReader(solution));
      int cSol;
      int cOut;
      while ((cSol = brSol.read()) != -1) {
        cOut = brOut.read();
        if (Character.isWhitespace(cSol)) {
          if (!Character.isWhitespace(cOut)) {
            this.presentationError = true;
            do {
              cSol = brSol.read();
            } while (Character.isWhitespace(cSol));
          } else if (cOut != cSol) {
            this.presentationError = true;
            continue;
          } else {
            continue;
          }
        }
        if (Character.isWhitespace(cOut)) {
          this.presentationError = true;
          do {
            cOut = brOut.read();
          } while (Character.isWhitespace(cOut));
        }
        if (cOut == cSol) {
          par.first++;
        } else {
          par.second++;
        }
      }
      while ((cOut = brOut.read()) != -1) {
        if (Character.isWhitespace(cOut)) {
          this.presentationError = true;
          do {
            cOut = brOut.read();
          } while (Character.isWhitespace(cOut));
          if (cOut != -1) {
            par.second++;
          }
        } else {
          par.second++;
        }
      }
      brOut.close();
      brSol.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    return par;
  }

  private void validateResults(Submission submission, ProblemJudge judge, String local) {
    long countAccepteds = 0;
    long countErrors = 0;
    final long MAX_TIME = submission.getProblem().getLimitTime() * 2;
    long time = 0;
    long timeSum = 0;
    boolean failed = false;
    this.presentationError = false;
    String folder = FileStorageService.ROOT_FOLDER + "problem-"
        + String.valueOf(submission.getProblem().getId()) + File.separator;
    File out = new File(local + File.separator + "inst.out");
    for (ProblemInstance instance : judge.getInstances()) {
      try {
        out.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
      File in = new File(folder + instance.getInputFile());
      File sol = new File(folder + instance.getOutputFile());
      time = System.currentTimeMillis();
      ProcessBuilder processBuilder = new ProcessBuilder("java", "-cp", local, "Main");
      processBuilder.redirectInput(Redirect.from(in));
      processBuilder.redirectOutput(Redirect.to(out));
      Process process = null;
      try {
        process = processBuilder.start();
        if (!process.waitFor(MAX_TIME, TimeUnit.MILLISECONDS)) {
          submission.setMessage(State.TIME_LIMIT_EXCEEDED.getName());
          submission.setState(State.TIME_LIMIT_EXCEEDED);
          failed = true;
        } else if (process.exitValue() != 0) {
          submission.setMessage(getMessage(process.getErrorStream()));
          submission.setState(State.RUNTIME_ERROR);
          failed = true;
        }
      } catch (IOException e) {
        e.printStackTrace();
      } catch (InterruptedException e) {
        if (process != null) {
          System.out.println(getMessage(process.getErrorStream()));
        }
        e.printStackTrace();
      } catch (IllegalThreadStateException e) {
        e.printStackTrace();
      }
      time = System.currentTimeMillis() - time;
      timeSum += time;
      PairLong par = validateInstance(out, sol);
      countAccepteds += par.first;
      countErrors += par.second;
      out.delete();
      if (failed) {
        return;
      }
    }
    submission.setTime((int) timeSum / judge.getInstances().size());
    if (countErrors == 0 && presentationError) {
      submission.setState(State.PRESENTATION_ERROR);
      submission.setMessage("Resposta mal formatada!");

    } else if (countErrors == 0) {
      submission.setState(State.ACCEPTED);
      submission.setMessage("Resposta correta!");
    } else {
      double porcentagemErro = (countErrors / (double) (countAccepteds + countErrors)) * 100;
      int porcentagemErroInt = (int) Math.round(porcentagemErro);
      submission.setMessage(String.format("Resposta incorreta (%d)%%.", porcentagemErroInt));
      submission.setState(State.WRONG_ANSWER);
    }
  }

}
