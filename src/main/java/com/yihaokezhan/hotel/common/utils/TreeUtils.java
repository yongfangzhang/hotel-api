package com.yihaokezhan.hotel.common.utils;

import java.util.ArrayList;
import java.util.List;

import com.yihaokezhan.hotel.model.TreeNode;

public class TreeUtils {

    public static <T extends TreeNode<T>> List<T> bulid(List<T> treeNodes) {
        List<T> trees = new ArrayList<T>();
        for (T node1 : treeNodes) {
            boolean mark = false;
            String pid = node1.findParentUuid();
            if (pid == null) {
                trees.add(node1);
                continue;
            }
            for (T node2 : treeNodes) {
                if (pid.equals(node2.findUuid())) {
                    mark = true;
                    node2.addChild(node1);
                    break;
                }
            }
            if (!mark) {
                trees.add(node1);
            }
        }
        return trees;
    }
}
