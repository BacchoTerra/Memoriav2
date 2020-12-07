package com.bacchoterra.memoriav2.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "memoria_table")
public class Memoria implements Serializable {

    private String tituloMemoria;
    private String descricaoMemoria;
    private String category;
    @PrimaryKey(autoGenerate = true)
    private int roomId;
    private int importancia = 0;
    private String imageUri;


    @Ignore
    public Memoria() {
    }


    public Memoria(String tituloMemoria, String descricaoMemoria, int importancia, String category) {
        this.tituloMemoria = tituloMemoria;
        this.descricaoMemoria = descricaoMemoria;
        this.importancia = importancia;
        this.category = category;
    }


    public String getTituloMemoria() {
        return tituloMemoria;
    }

    public void setTituloMemoria(String tituloMemoria) {
        this.tituloMemoria = tituloMemoria;
    }

    public String getDescricaoMemoria() {
        return descricaoMemoria;
    }

    public void setDescricaoMemoria(String descricaoMemoria) {
        this.descricaoMemoria = descricaoMemoria;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getImportancia() {
        return importancia;
    }

    public void setImportancia(int importancia) {
        this.importancia = importancia;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
