package com.example.clienteschat.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Mensaje {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "contenido")
    private String contenido;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    @Override
    public String toString() {
        return "Mensaje{" +
                "id=" + id +
                ", contenido='" + contenido + '\'' +
                '}';
    }
}
