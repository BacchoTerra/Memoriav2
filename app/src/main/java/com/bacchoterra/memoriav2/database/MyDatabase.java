package com.bacchoterra.memoriav2.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.bacchoterra.memoriav2.dao.CategoriaDao;
import com.bacchoterra.memoriav2.dao.MemoriaDao;
import com.bacchoterra.memoriav2.model.Categoria;
import com.bacchoterra.memoriav2.model.Memoria;


@Database(entities = {Memoria.class, Categoria.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {

    private static MyDatabase instance;

    public abstract MemoriaDao memoriaDao();

    public abstract CategoriaDao categoriaDao();

    public static synchronized MyDatabase getInstance(Context c) {

        if (instance == null) {

            instance = Room.databaseBuilder(c.getApplicationContext(), MyDatabase.class, "mydatabase").fallbackToDestructiveMigration().build();
        }

        return instance;

    }


}
