package com.bacchoterra.memoriav2.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.bacchoterra.memoriav2.model.Memoria;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface MemoriaDao {

    @Insert
    void insert(Memoria memoria);

    @Update
    void update(Memoria memoria);

    @Delete
    void delete(Memoria memoria);

    @Query("DELETE FROM memoria_table WHERE category = :category")
    void deleteAll(String category);

    @Query("SELECT * FROM memoria_table WHERE category = :category")
    ArrayList<Memoria> selectAllMemoriaFromCategory(String category);


}
