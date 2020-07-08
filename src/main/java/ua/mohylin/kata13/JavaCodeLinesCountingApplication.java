package ua.mohylin.kata13;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import ua.mohylin.kata13.tree.TreeNode;

public class JavaCodeLinesCountingApplication {

  public static void main(String[] args) throws IOException {
    File fileOrDir = getFileParameter(args);
    if (fileOrDir == null) {
      return;
    }

    JavaLinesCountingTask task = new JavaLinesCountingTask(fileOrDir);

    ForkJoinPool commonPool = ForkJoinPool.commonPool();
    TreeNode result = commonPool.invoke(task);

    printCount(result, 1);

    commonPool.shutdown();

    // Wait for the finalization of the tasks
    try {
      commonPool.awaitTermination(1, TimeUnit.DAYS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  static File getFileParameter(String[] args) {
    if (args == null || args.length == 0) {
      System.out.println("ERROR: File/directory path is expected as parameter\nExiting...");
      return null;
    }
    String path = args[0];
    File sourceCodeFile = new File(path);
    if (!sourceCodeFile.exists()) {
      System.out.println(
          String.format("ERROR: Specified path does not exist - %s\nExiting...", path));
      return null;
    }
    return sourceCodeFile;
  }

  static void printCount(final TreeNode node, final int depth) {
    if (node.getCodeLinesCount() == 0) {
      return;
    }
    String prefix = StringUtils.repeat("  ", depth - 1);
    System.out.println(
        String.format("%s%s : %d", prefix, node.getNodeName(), node.getCodeLinesCount()));
    List<TreeNode> childrenOrdered =
        node.getChildren().stream()
            .sorted(Comparator.comparing(TreeNode::getNodeName))
            .collect(Collectors.toList());
    for (TreeNode child : childrenOrdered) {
      printCount(child, depth + 1);
    }
  }
}
