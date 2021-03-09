package com.bacchoterra.memoriav2.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.bacchoterra.memoriav2.model.Categoria;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface CategoriaDao {

    @Insert
    void insert(Categoria categoria);

    @Update
    void update(Categoria categoria);

    @Delete
    void delete(Categoria categoria);

    @Query("DELETE FROM categoria_table")
    void deleteAll();

    @Query("SELECT * FROM categoria_table")
    LiveData<List<Categoria>> selectAllCategory();

    @Query("SELECT * FROM categoria_table WHERE titulo LIKE :name")
    LiveData<List<Categoria>> selectByName(String name);


}
