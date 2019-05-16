package com.waes.diff.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Diff {

    @Id
    private String id;

    private String left;

    private String right;

    private String diff;

    private Diff() {
    }

    public Diff(String id) {
        this.id = id;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    public String getDiff() {
        return diff;
    }

    public boolean isComplete() {
        return left != null && right != null;
    }

}
