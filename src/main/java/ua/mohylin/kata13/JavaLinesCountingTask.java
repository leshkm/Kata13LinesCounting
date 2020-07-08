package ua.mohylin.kata13;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;
import ua.mohylin.kata13.tree.DirectoryNode;
import ua.mohylin.kata13.tree.JavaFileNode;
import ua.mohylin.kata13.tree.TreeNode;

public class JavaLinesCountingTask extends RecursiveTask<TreeNode> implements DebugLogger {

  private final File file;

  public JavaLinesCountingTask(File file) {
    this.file = file;
  }

  @Override
  public TreeNode compute() {

    if (file.isDirectory()) {
      DirectoryNode node = new DirectoryNode(file.getName());
      List<JavaLinesCountingTask> subtasks =
          Arrays.stream(file.listFiles())
              .filter((file) -> file.isDirectory() || file.getName().endsWith(".java"))
              .map(JavaLinesCountingTask::new)
              .collect(Collectors.toList());
      List<TreeNode> children =
          ForkJoinTask.invokeAll(subtasks).stream()
              .map(JavaLinesCountingTask::join)
              .collect(Collectors.toList());
      node.addChildNodes(children);
      return node;
    } else {
      try {
        return new JavaFileNode(file.getName(), run());
      } catch (IOException e) {
        e.printStackTrace();
        return new JavaFileNode(file.getName(), 0);
      }
    }
  }

  public long run() throws IOException {
    long count = countLinesWithCode(new FileInputStream(file));
    // debug(String.format("%s : %d\n", file.getName(), count));
    return count;
  }

  public long countLinesWithCode(InputStream inputStream) throws IOException {
    Objects.requireNonNull(inputStream);

    long countOfLinesWithCode = 0;
    long lineNo = 1;
    // we need to carry over knowledge about unclosed multiline comment
    boolean inMultilineComment = false;

    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    while (reader.ready()) {
      //      debug(String.format("%d: of - ", lineNo++));
      JavaSourceLine line = new JavaSourceLine(reader.readLine(), inMultilineComment);
      if (line.isHasCode()) {
        ++countOfLinesWithCode;
      }
      inMultilineComment = line.isOpenMultiLineComment();
      //      debug(
      //          String.format(
      //              ", code - %s, mlc - %s\n", line.isHasCode(), line.isOpenMultiLineComment()));
    }

    return countOfLinesWithCode;
  }
}
