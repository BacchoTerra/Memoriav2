package com.bacchoterra.memoriav2.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.bacchoterra.memoriav2.dao.MemoriaDao;
import com.bacchoterra.memoriav2.database.MyDatabase;
import com.bacchoterra.memoriav2.model.Memoria;

import java.util.ArrayList;

public class MemoriaRepository {

    private MemoriaDao memoriaDao;
    private ArrayList<Memoria> allMemoriaFromCat;

    public MemoriaRepository(Application application, String category) {

        MyDatabase myDatabase = MyDatabase.getInstance(application);
        memoriaDao = myDatabase.memoriaDao();
        allMemoriaFromCat = memoriaDao.selectAllMemoriaFromCategory(category);

    }

    public void insert(Memoria m){
        new InsertMemoriaAsync(memoriaDao).execute(m);
    }

    public void update(Memoria m){
        new UpdateMemoriaAsync(memoriaDao).execute(m);
    }

    public void delete(Memoria m){
        new DeleteMemoriaAsync(memoriaDao).execute(m);
    }

    public void deleteAll(Memoria m,String cat){
        new DeleteAllMemoriaAsync(memoriaDao,cat).execute(m);
    }

    public ArrayList<Memoria> getAllMemoriaFromCat(String string){
        return allMemoriaFromCat;
    }


    private static class InsertMemoriaAsync extends AsyncTask<Memoria, Void, Void> {

        private MemoriaDao mDao;

        private InsertMemoriaAsync(MemoriaDao dao) {
            this.mDao = dao;
        }

        @Override
        protected Void doInBackground(Memoria... memorias) {

            mDao.insert(memorias[0]);
            return null;
        }
    }

    private static class UpdateMemoriaAsync extends AsyncTask<Memoria, Void, Void> {

        private MemoriaDao mDao;

        private UpdateMemoriaAsync(MemoriaDao dao) {
            this.mDao = dao;
        }

        @Override
        protected Void doInBackground(Memoria... memorias) {

            mDao.update(memorias[0]);
            return null;
        }

    }

    private static class DeleteMemoriaAsync extends AsyncTask<Memoria,Void,Void> {

        private MemoriaDao mDao;

        private DeleteMemoriaAsync(MemoriaDao dao) {
            this.mDao = dao;
        }

        @Override
        protected Void doInBackground(Memoria... memorias) {

            mDao.delete(memorias[0]);
            return null;
        }
    }

    private static class DeleteAllMemoriaAsync extends AsyncTask<Memoria, Void, Void> {

        private MemoriaDao mDao;
        private String category;

        private DeleteAllMemoriaAsync(MemoriaDao dao,String category) {
            this.mDao = dao;
            this.category = category;
        }

        @Override
        protected Void doInBackground(Memoria... memorias) {

            mDao.deleteAll(category);
            return null;
        }
    }
}
