package com.example.t3practica4.entities;

import java.io.Serializable;

public class Cookie implements Serializable {
    private int count;
    private String nombre;

    public int getCount() {
        return count;
    }

    public void increment() {
        count++;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
