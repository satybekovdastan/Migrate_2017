package com.migrate.admin.pagination.Serializables;

/**
 * Created by ulan on 10/3/16.
 */
public class Mig2 {
    String text;
    int type;
    int strong;
    int em;
    int del;

    public int getDel() {
        return del;
    }

    public int getEm() {
        return em;
    }

    public int getStrong() {
        return strong;
    }

    public void setDel(int del) {
        this.del = del;
    }

    public void setEm(int em) {
        this.em = em;
    }

    public void setStrong(int strong) {
        this.strong = strong;
    }

    public int getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setType(int type) {
        this.type = type;
    }
}
