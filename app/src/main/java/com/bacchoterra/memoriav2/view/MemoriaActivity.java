package com.bacchoterra.memoriav2.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.bacchoterra.memoriav2.R;
import com.bacchoterra.memoriav2.adapter.MemoriaAdapter;
import com.bacchoterra.memoriav2.model.Memoria;
import com.bacchoterra.memoriav2.viewmodel.MemoriaViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class MemoriaActivity extends AppCompatActivity implements View.OnClickListener {

    //Components de layout
    private Toolbar toolbar;
    private FloatingActionButton fabAddMemoria;
    private RecyclerView recyclerView;
    private ImageView imageBlank;

    //Componentes de database
    private MemoriaViewModel memoriaViewModel;

    //Recycler
    private MemoriaAdapter adapter;

    //Componente de bundle
    private Bundle bundle;
    private String stringCategoria;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memoria);
        init();
    }

    private void init() {

        initViews();
        iniToolbar();
        initViewModel();
        initRecyclerView();
    }

    private void initViews() {

        toolbar = findViewById(R.id.activity_memoria_toolbar);
        fabAddMemoria = findViewById(R.id.activity_memoria_fabAddMemoria);
        recyclerView = findViewById(R.id.activity_memoria_recyclerView);
        imageBlank = findViewById(R.id.activity_memoria_imageBlank);

        fabAddMemoria.setOnClickListener(this);

    }

    private void iniToolbar() {

        bundle = getIntent().getExtras();
        if (bundle != null) {
            stringCategoria = bundle.getString(MainActivity.CAT_KEY);
        }

        toolbar.setTitle(stringCategoria);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    private void initViewModel() {

        memoriaViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(MemoriaViewModel.class);
        memoriaViewModel.getAllMemoria(stringCategoria).observe(this, new Observer<List<Memoria>>() {
            @Override
            public void onChanged(List<Memoria> memorias) {
                if (!memorias.isEmpty()){
                    adapter.submitList(memorias);

                    if (imageBlank.getVisibility() == View.VISIBLE && recyclerView.getVisibility() == View.GONE){
                        imageBlank.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }

                }else {
                    imageBlank.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }
        });


    }

    private void initRecyclerView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new MemoriaAdapter(this,memoriaViewModel);
        recyclerView.setAdapter(adapter);

    }


    private void initAddDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_memoria, null);
        builder.setView(dialogView);

        final TextInputEditText editTitulo = dialogView.findViewById(R.id.dialog_add_memoria_editTitulo);
        final EditText editDesc = dialogView.findViewById(R.id.dialog_add_memoria_editDesc);
        final SeekBar seekImport = dialogView.findViewById(R.id.dialog_add_memoria_seekBar);


        builder.setCancelable(false);
        builder.setPositiveButton(R.string.salvar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String titulo = editTitulo.getText().toString();
                String desc = editDesc.getText().toString();
                int importancia = seekImport.getProgress();
                if (desc.trim().length() >= 5) {

                    Memoria memoria = new Memoria();

                    if (titulo.isEmpty()) {
                        memoria.setTituloMemoria(null);
                    } else {
                        memoria.setTituloMemoria(titulo);
                    }


                    memoria.setDescricaoMemoria(desc);
                    memoria.setImportancia(importancia);
                    memoria.setCategory(stringCategoria);
                    memoriaViewModel.insert(memoria);
                } else {
                    Toast.makeText(MemoriaActivity.this, R.string.min_5_caracteres_anotacao, Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Button pBtn = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button nBtn = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        nBtn.setTextColor(getResources().getColor(R.color.negativeBtn));
        pBtn.setTextColor(getResources().getColor(R.color.positiveBtn));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_memoria_fabAddMemoria:

                initAddDialog();


        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}