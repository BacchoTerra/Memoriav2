package com.bacchoterra.memoriav2.model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categoria_table")
public class Categoria {

    private String titulo;
    @PrimaryKey(autoGenerate = true)
    private String roomId;

    public Categoria(String titulo, String roomId) {
        this.titulo = titulo;
        this.roomId = roomId;
    }

    public Categoria() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
