package com.bacchoterra.memoriav2.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "memoria_table")
public class Memoria {

    private String tituloMemoria;
    private String descricaoMemoria;
    private String category;
    @PrimaryKey(autoGenerate = true)
    private String roomId;
    private int importancia = 0;


    public Memoria() {
    }


    public Memoria(String tituloMemoria, String descricaoMemoria, String roomId, int importancia, String category) {
        this.tituloMemoria = tituloMemoria;
        this.descricaoMemoria = descricaoMemoria;
        this.roomId = roomId;
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

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
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
}
