package com.app.project3;

import javax.crypto.spec.SecretKeySpec;

public class Singleton {
    SecretKeySpec editValue;
    private static final Singleton ourInstance = new Singleton();
    public static Singleton getInstance() {
        return ourInstance;
    }
    private Singleton() { }
    public void setKey(SecretKeySpec editValue) {
        this.editValue = editValue;
    }
    public SecretKeySpec getKey() {
        return editValue;
    }
}
