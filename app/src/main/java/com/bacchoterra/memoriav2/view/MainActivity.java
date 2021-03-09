package com.bacchoterra.memoriav2.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bacchoterra.memoriav2.R;
import com.bacchoterra.memoriav2.adapter.CategoryAdapter;
import com.bacchoterra.memoriav2.helper.RecyclerItemClickListener;
import com.bacchoterra.memoriav2.model.Categoria;
import com.bacchoterra.memoriav2.viewmodel.CategoriaViewModel;
import com.bacchoterra.memoriav2.viewmodel.MemoriaViewModel;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //LayoutComponents
    private Toolbar toolbar;
    private SearchView searchView;
    private DrawerLayout drawerLayout;
    private EditText editCategoria;
    private Button btnSalvar;
    private RecyclerView recyclerCategorias;
    private TextView txtDarkMode;

    //Database
    private CategoriaViewModel categoriaViewmodel;
    private MemoriaViewModel memoriaViewModel;

    //Recyclerview
    public CategoryAdapter categoryAdapter;

    //Bundle components
    public static final String CAT_KEY = "cat_key";

    //Dark mode shared preferences

    public static final String DARK_PREFS = "dark_prefs";
    public static final String DARK_SKEY = "dark_skey";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init() {


        initViews();
        checkSharedPrefs();
        initToolbarAndDrawer();
        initRecyclerView();
        initViewModel();
        initSearchView();
    }

    private void initViews() {
        toolbar = findViewById(R.id.main_activity_toolbar);
        searchView = findViewById(R.id.main_activity_searchView);
        drawerLayout = findViewById(R.id.main_activity_drawerLayout);
        editCategoria = findViewById(R.id.main_activity_editCategoria);
        btnSalvar = findViewById(R.id.main_activity_btnSalvarCategoria);
        recyclerCategorias = findViewById(R.id.main_activity_catRecyclerView);
        txtDarkMode = findViewById(R.id.main_activity_txtDarkMode);

        btnSalvar.setOnClickListener(this);
        txtDarkMode.setOnClickListener(this);

    }

    private void checkSharedPrefs() {

        sharedPreferences = getSharedPreferences(DARK_PREFS, MODE_PRIVATE);

        if (sharedPreferences.contains(DARK_SKEY)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            txtDarkMode.setText(R.string.desativar_dark_mode);
        }


    }

    private void initRecyclerView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerCategorias.setLayoutManager(layoutManager);
        recyclerCategorias.setHasFixedSize(true);
        categoryAdapter = new CategoryAdapter();
        recyclerCategorias.setAdapter(categoryAdapter);


        ItemTouchHelper.Callback itemTouchHelper = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {

                int drag_flags = ItemTouchHelper.ACTION_STATE_IDLE;;
                int swipe_flags = ItemTouchHelper.END | ItemTouchHelper.START;

                return makeMovementFlags(drag_flags, swipe_flags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {


                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Categoria categoria = categoryAdapter.getCategoria(viewHolder.getAdapterPosition());

                if (direction == 32) {
                    initAlertDialogDelete(categoria, viewHolder.getAdapterPosition());
                } else {
                    initAlertDialogEdit(categoria, viewHolder.getAdapterPosition());
                }

            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {


                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeRightBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.swipeDelete))
                        .addSwipeRightActionIcon(R.drawable.ic_outline_delete_forever_24)
                        .addSwipeLeftActionIcon(R.drawable.ic_baseline_create_24)
                        .addSwipeLeftBackgroundColor(getResources().getColor(R.color.swipeEdit))
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

            }
        };

        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(recyclerCategorias);


        recyclerCategorias.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerCategorias, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                String category = categoryAdapter.getCategoria(position).getTitulo();
                Intent intent = new Intent(MainActivity.this, MemoriaActivity.class);
                intent.putExtra(CAT_KEY, category);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        }));


    }

    private void initToolbarAndDrawer() {

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Welcome back");
        }
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

                searchView.setIconified(true);
                searchView.setQuery("",false);
                searchView.clearFocus();


            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });


    }

    private void initViewModel() {

        categoriaViewmodel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(CategoriaViewModel.class);

        sendViewModelQuery(null);

        memoriaViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(MemoriaViewModel.class);


    }

    private void sendViewModelQuery(String name){

        Log.i("porsche", "sendViewModelQuery: " + name);

        categoriaViewmodel.selectAllCategoria(name).observe(this, new Observer<List<Categoria>>() {
            @Override
            public void onChanged(List<Categoria> list) {
                categoryAdapter.submitList(list);
            }
        });
    }

    private void initSearchView(){

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.length() >=1){
                    sendViewModelQuery("%" + newText + "%");
                }else {
                    sendViewModelQuery(null);
                }


                return true;
            }
        });


    }

    private void initAlertDialogDelete(final Categoria categoria, final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.deletar_item);
        builder.setMessage(R.string.deseja_deletar_a_categoria_e_seu_conteudo);
        builder.setPositiveButton(R.string.deletar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                categoriaViewmodel.delete(categoria);
                memoriaViewModel.deleteAllFromCat(categoria.getTitulo());
                Toast.makeText(MainActivity.this, R.string.item_deletado, Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                categoryAdapter.notifyItemChanged(position);
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                categoryAdapter.notifyItemChanged(position);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Button pBtn = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button nBtn = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        pBtn.setTextColor(getResources().getColor(R.color.swipeDelete));
        nBtn.setTextColor(getResources().getColor(R.color.textColor));


    }

    private void initAlertDialogEdit(final Categoria categoria, final int position) {

        final View editLayout = getLayoutInflater().inflate(R.layout.dialog_edit_categoria, null);
        final EditText editCategoria = editLayout.findViewById(R.id.dialog_edit_categoria_editCat);


        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogTheme);
        builder.setView(editLayout);

        editCategoria.setText(categoria.getTitulo());

        builder.setTitle(R.string.editar_categoria);

        builder.setPositiveButton(R.string.salvar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (editCategoria.getText().toString().trim().length() >= 4) {


                    String oldCat = categoria.getTitulo();
                    String newCat = editCategoria.getText().toString();

                    Categoria edited = new Categoria();
                    edited.setTitulo(editCategoria.getText().toString());
                    edited.setRoomId(categoria.getRoomId());

                    categoriaViewmodel.update(edited);
                    memoriaViewModel.updateAllCat(oldCat, newCat);
                } else {
                    Toast.makeText(MainActivity.this, R.string.minimo_de_4_letras, Toast.LENGTH_SHORT).show();
                    categoryAdapter.notifyItemChanged(position);
                }


            }
        }).setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                categoryAdapter.notifyItemChanged(position);
            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                categoryAdapter.notifyItemChanged(position);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Button pBtn = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button nBtn = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        pBtn.setTextColor(getResources().getColor(R.color.colorAccent));
        nBtn.setTextColor(getResources().getColor(R.color.textColor));

    }


    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

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
                    Toast.makeText(this, R.string.categoria_adicionada, Toast.LENGTH_SHORT).show();
                } else if (cat.isEmpty()) {
                    editCategoria.setError("*");
                } else {
                    editCategoria.setError(getString(R.string.minimo_de_4_letras));
                }

                break;

            case R.id.main_activity_txtDarkMode:

                editor = sharedPreferences.edit();

                if (!sharedPreferences.contains(DARK_SKEY)) {
                    editor.putBoolean(DARK_SKEY, true).apply();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    txtDarkMode.setText(R.string.desativar_dark_mode);
                } else {
                    editor.remove(DARK_SKEY).apply();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    txtDarkMode.setText(R.string.ativar_dark_mode_beta);
                }


        }
    }


}








