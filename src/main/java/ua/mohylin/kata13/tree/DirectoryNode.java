package ua.mohylin.kata13.tree;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class DirectoryNode implements TreeNode {

  private final String nodeName;
  private boolean codeLinesCountInitialized = false;
  private long codeLinesCount;
  private final List<TreeNode> children = new LinkedList<>();

  public DirectoryNode(String nodeName) {
    this.nodeName = nodeName;
  }

  @Override
  public String getNodeName() {
    return nodeName;
  }

  @Override
  public long getCodeLinesCount() {
    if (!codeLinesCountInitialized) {
      codeLinesCount = getChildren().stream().mapToLong(TreeNode::getCodeLinesCount).sum();
      codeLinesCountInitialized = true;
    }
    return codeLinesCount;
  }

  @Override
  public List<TreeNode> getChildren() {
    return children;
  }

  public void addChildNode(TreeNode node) {
    children.add(node);
  }

  public void addChildNodes(Collection<TreeNode> childNodes) {
    this.children.addAll(childNodes);
  }
}
