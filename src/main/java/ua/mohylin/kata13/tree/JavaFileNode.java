package ua.mohylin.kata13.tree;

import java.util.Collections;
import java.util.List;

public class JavaFileNode implements TreeNode {

  private final String nodeName;
  private final long codeLinesCount;

  public JavaFileNode(String nodeName, long codeLinesCount) {
    this.nodeName = nodeName;
    this.codeLinesCount = codeLinesCount;
  }

  @Override
  public String getNodeName() {
    return nodeName;
  }

  @Override
  public long getCodeLinesCount() {
    return codeLinesCount;
  }

  @Override
  public List<TreeNode> getChildren() {
    return Collections.EMPTY_LIST;
  }
}
