package com.bacchoterra.memoriav2.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.bacchoterra.memoriav2.dao.CategoriaDao;
import com.bacchoterra.memoriav2.database.MyDatabase;
import com.bacchoterra.memoriav2.model.Categoria;

import java.util.List;

public class CategoriaRepository {

    private CategoriaDao categoriaDao;
    private LiveData<List<Categoria>> allCategorias;

    public CategoriaRepository(Application application) {

        MyDatabase myDatabase = MyDatabase.getInstance(application);
        categoriaDao = myDatabase.categoriaDao();
        allCategorias = categoriaDao.selectAllCategory();

    }

    public void insert(Categoria c){
        new InsertCatAsync(categoriaDao).execute(c);
    }

    public void update(Categoria c){
        new UpdateCatAsync(categoriaDao).execute(c);
    }

    public void delete(Categoria c){
        new DeleteCatAsync(categoriaDao).execute(c);
    }

    public void deleteAll(){
        new DeleteAllCatAsync(categoriaDao).execute();
    }

    public LiveData<List<Categoria>> selectAllCategoria(){
        return allCategorias;
    }

    private static class InsertCatAsync extends AsyncTask<Categoria,Void,Void>{

        private CategoriaDao cDao;

        public InsertCatAsync(CategoriaDao categoriaDao) {
            this.cDao = categoriaDao;

        }

        @Override
        protected Void doInBackground(Categoria... categorias) {

            cDao.insert(categorias[0]);

            return null;
        }
    }

    private static class UpdateCatAsync extends AsyncTask<Categoria,Void,Void>{

        private CategoriaDao cDao;

        public UpdateCatAsync(CategoriaDao categoriaDao) {
            this.cDao = categoriaDao;

        }

        @Override
        protected Void doInBackground(Categoria... categorias) {

            cDao.update(categorias[0]);

            return null;
        }
    }

    private static class DeleteCatAsync extends AsyncTask<Categoria,Void,Void>{

        private CategoriaDao cDao;

        public DeleteCatAsync(CategoriaDao categoriaDao) {
            this.cDao = categoriaDao;

        }

        @Override
        protected Void doInBackground(Categoria... categorias) {

            cDao.delete(categorias[0]);

            return null;
        }
    }

    private static class DeleteAllCatAsync extends AsyncTask<Categoria,Void,Void>{

        private CategoriaDao cDao;

        public DeleteAllCatAsync(CategoriaDao categoriaDao) {
            this.cDao = categoriaDao;

        }

        @Override
        protected Void doInBackground(Categoria... categorias) {

            cDao.deleteAll();

            return null;
        }
    }


}
