package ua.mohylin.kata13.tree;

import java.util.List;

public interface TreeNode {

  String getNodeName();

  long getCodeLinesCount();

  List<TreeNode> getChildren();
}
