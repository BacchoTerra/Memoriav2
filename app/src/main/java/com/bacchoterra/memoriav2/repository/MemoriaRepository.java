package com.bacchoterra.memoriav2.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.bacchoterra.memoriav2.dao.MemoriaDao;
import com.bacchoterra.memoriav2.database.MyDatabase;
import com.bacchoterra.memoriav2.model.Memoria;

import java.util.ArrayList;
import java.util.List;

public class MemoriaRepository {

    private MemoriaDao memoriaDao;
    private LiveData<List<Memoria>> allMemoriaFromCat;

    public MemoriaRepository(Application application) {

        MyDatabase myDatabase = MyDatabase.getInstance(application);
        memoriaDao = myDatabase.memoriaDao();

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

    public void deleteAll(String cat){
        new DeleteAllMemoriaAsync(memoriaDao,cat).execute();
    }

    public void updateAllCat(String oldCat,String newCat){
        new UpdateAllCatAsync(memoriaDao,oldCat,newCat).execute();
    }

    public LiveData<List<Memoria>> getAllMemoriaFromCat(String string){
        allMemoriaFromCat = memoriaDao.selectAllMemoriaFromCategory(string);
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

    private static class UpdateAllCatAsync extends AsyncTask<Memoria, Void, Void> {

        private MemoriaDao mDao;
        private String oldCat;
        private String newCat;

        private UpdateAllCatAsync(MemoriaDao dao,String oldCat,String newCat) {
            this.mDao = dao;
            this.oldCat = oldCat;
            this.newCat = newCat;
        }

        @Override
        protected Void doInBackground(Memoria... memorias) {

            mDao.updateAllCategories(oldCat,newCat);
            return null;
        }
    }

}
