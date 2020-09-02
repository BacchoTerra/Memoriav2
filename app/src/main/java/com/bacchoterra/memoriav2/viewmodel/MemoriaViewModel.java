package com.bacchoterra.memoriav2.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bacchoterra.memoriav2.model.Memoria;
import com.bacchoterra.memoriav2.repository.MemoriaRepository;

import java.util.List;

public class MemoriaViewModel extends AndroidViewModel {

    private MemoriaRepository memoriaRepository;
    private LiveData<List<Memoria>> list;

    public MemoriaViewModel(@NonNull Application application) {
        super(application);
        memoriaRepository = new MemoriaRepository(application);
    }

    public void insert(Memoria memoria){
        memoriaRepository.insert(memoria);
    }

    public void update(Memoria memoria){
        memoriaRepository.update(memoria);
    }

    public void delete(Memoria memoria){
        memoriaRepository.delete(memoria);
    }
    public void deleteAllFromCat(String categoria){
        memoriaRepository.deleteAll(categoria);
    }

    public LiveData<List<Memoria>> getAllMemoria (String categoria) {
        list = memoriaRepository.getAllMemoriaFromCat(categoria);
        return list;
    }
}
