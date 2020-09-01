package com.bacchoterra.memoriav2.view;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bacchoterra.memoriav2.R;
import com.bacchoterra.memoriav2.adapter.NavAdapter;
import com.bacchoterra.memoriav2.model.Categoria;
import com.bacchoterra.memoriav2.model.Memoria;
import com.bacchoterra.memoriav2.repository.CategoriaRepository;
import com.bacchoterra.memoriav2.repository.MemoriaRepository;
import com.bacchoterra.memoriav2.viewmodel.CategoriaViewmodel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //LayoutComponents
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private EditText editCategoria;
    private Button btnSalvar;
    private RecyclerView recyclerCategorias;

    //Database
    private CategoriaRepository categoriaRepository;
    private CategoriaViewmodel categoriaViewmodel;

    //Recyclerview
    public NavAdapter navAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init() {

        initViews();
        initToolbarAndDrawer();
        initRecyclerView();
        initViewModel();
    }

    private void initRecyclerView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerCategorias.setLayoutManager(layoutManager);
        recyclerCategorias.setHasFixedSize(true);
        navAdapter = new NavAdapter();
        recyclerCategorias.setAdapter(navAdapter);

    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar_principal);
        drawerLayout = findViewById(R.id.drawer_layout_id);
        editCategoria = findViewById(R.id.editCategoria);
        btnSalvar = findViewById(R.id.btnSalvarCategoria);
        recyclerCategorias = findViewById(R.id.recycler_nav_view);

        btnSalvar.setOnClickListener(this);

    }

    private void initToolbarAndDrawer() {

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


    }

    private void initViewModel(){

        categoriaViewmodel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(CategoriaViewmodel.class);


        categoriaViewmodel.deleteAll();
        categoriaViewmodel.selectAllCategoria().observe(this, new Observer<List<Categoria>>() {
            @Override
            public void onChanged(List<Categoria> list) {
                navAdapter.submitList(list);
            }
        });


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btnSalvarCategoria:

                Categoria categoria = new Categoria();
                categoria.setTitulo(editCategoria.getText().toString());
                categoriaViewmodel.insert(categoria);




        }
    }
}








