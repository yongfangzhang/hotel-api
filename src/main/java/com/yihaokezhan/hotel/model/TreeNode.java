package com.yihaokezhan.hotel.model;

import java.io.Serializable;

public interface TreeNode<T> extends Serializable {

    String findUuid();

    String findParentUuid();

    void addChild(T child);
}
