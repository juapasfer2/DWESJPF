package com.example.t3practica4.entities;

import java.io.Serializable;

public class Counter implements Serializable {
    private int count;
    public int getCount() {
        return count;
    }
    public void increment() {
        count++;
    }
}