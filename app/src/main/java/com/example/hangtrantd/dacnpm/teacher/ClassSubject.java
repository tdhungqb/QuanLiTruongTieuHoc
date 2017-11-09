package com.example.hangtrantd.dacnpm.teacher;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 08/11/2017.
 */

public class ClassSubject {
    private String clazz;
    private String subject;

    public ClassSubject(String clazz, String subject) {
        this.clazz = clazz;
        this.subject = subject;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
