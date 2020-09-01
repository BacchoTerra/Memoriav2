package com.bacchoterra.memoriav2.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bacchoterra.memoriav2.model.Categoria;
import com.bacchoterra.memoriav2.repository.CategoriaRepository;

import java.util.List;

public class CategoriaViewmodel extends AndroidViewModel {

    private CategoriaRepository cRepo;
    private LiveData<List<Categoria>> allCategoria;



    public CategoriaViewmodel(@NonNull Application application) {
        super(application);
        cRepo = new CategoriaRepository(application);
        allCategoria = cRepo.selectAllCategoria();
    }

    public void insert(Categoria categoria){
        cRepo.insert(categoria);
    }

    public void update (Categoria categoria) {
        cRepo.update(categoria);
    }

    public void delete(Categoria categoria){
        cRepo.delete(categoria);
    }

    public void deleteAll(){
        cRepo.deleteAll();
    }

    public LiveData<List<Categoria>> selectAllCategoria(){
        return allCategoria;
    }


}
