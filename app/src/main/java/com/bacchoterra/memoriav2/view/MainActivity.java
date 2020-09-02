package com.bacchoterra.memoriav2.view;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bacchoterra.memoriav2.R;
import com.bacchoterra.memoriav2.adapter.CategoryAdapter;
import com.bacchoterra.memoriav2.model.Categoria;
import com.bacchoterra.memoriav2.repository.CategoriaRepository;
import com.bacchoterra.memoriav2.viewmodel.CategoriaViewmodel;
import com.google.android.material.snackbar.Snackbar;

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
    public CategoryAdapter categoryAdapter;

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
        categoryAdapter = new CategoryAdapter();
        recyclerCategorias.setAdapter(categoryAdapter);

    }

    private void initViews() {
        toolbar = findViewById(R.id.main_activity_toolbar);
        drawerLayout = findViewById(R.id.main_activity_drawerLayout);
        editCategoria = findViewById(R.id.main_activity_editCategoria);
        btnSalvar = findViewById(R.id.main_activity_btnSalvarCategoria);
        recyclerCategorias = findViewById(R.id.main_activity_catRecyclerView);

        btnSalvar.setOnClickListener(this);

    }

    private void initToolbarAndDrawer() {

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


    }

    private void initViewModel() {

        categoriaViewmodel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(CategoriaViewmodel.class);


        categoriaViewmodel.deleteAll();
        categoriaViewmodel.selectAllCategoria().observe(this, new Observer<List<Categoria>>() {
            @Override
            public void onChanged(List<Categoria> list) {
                categoryAdapter.submitList(list);
            }
        });


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.main_activity_btnSalvarCategoria:
                String cat = editCategoria.getText().toString();
                Categoria categoria = new Categoria();

                if (!cat.isEmpty() && cat.trim().length() >= 4) {
                    categoria.setTitulo(cat);
                    categoriaViewmodel.insert(categoria);
                    editCategoria.setText(null);
                    Snackbar.make(toolbar, R.string.feito, Snackbar.LENGTH_LONG).show();
                } else if (cat.isEmpty()) {
                    editCategoria.setError("*");
                } else {
                    editCategoria.setError(getString(R.string.minimo_de_4_letras));
                }

        }
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }
}








