package com.bacchoterra.memoriav2.model;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "categoria_table")
public class Categoria {

    private String titulo;
    @PrimaryKey(autoGenerate = true)
    private int roomId;

    public Categoria(String titulo) {
        this.titulo = titulo;
    }

    @Ignore
    public Categoria() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
