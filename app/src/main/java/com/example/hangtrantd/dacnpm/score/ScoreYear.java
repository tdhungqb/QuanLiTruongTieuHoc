package com.example.hangtrantd.dacnpm.score;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 02/11/2017.
 */

public class ScoreYear {
    private String subject;
    private String score;
    private String semester1;
    private String semester2;
    private String factor;

    public ScoreYear(String subject, String score, String semester1, String semester2, String factor) {
        this.subject = subject;
        this.score = score;
        this.semester1 = semester1;
        this.semester2 = semester2;
        this.factor = factor;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getFactor() {
        return factor;
    }

    public void setFactor(String factor) {
        this.factor = factor;
    }

    public String getSemester1() {
        return semester1;
    }

    public void setSemester1(String semester1) {
        this.semester1 = semester1;
    }

    public String getSemester2() {
        return semester2;
    }

    public void setSemester2(String semester2) {
        this.semester2 = semester2;
    }
}
