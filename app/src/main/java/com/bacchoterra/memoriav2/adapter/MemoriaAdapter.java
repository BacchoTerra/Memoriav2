package com.bacchoterra.memoriav2.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bacchoterra.memoriav2.R;
import com.bacchoterra.memoriav2.model.Categoria;
import com.bacchoterra.memoriav2.model.Memoria;
import com.bacchoterra.memoriav2.view.MemoriaActivity;
import com.bacchoterra.memoriav2.viewmodel.MemoriaViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class MemoriaAdapter extends ListAdapter<Memoria, MemoriaAdapter.MyViewHolder> {

    private Activity activity;
    private MemoriaViewModel mViewModel;

    private static final DiffUtil.ItemCallback<Memoria> DIFF_UTIL = new DiffUtil.ItemCallback<Memoria>() {
        @Override
        public boolean areItemsTheSame(@NonNull Memoria oldItem, @NonNull Memoria newItem) {
            return oldItem.getRoomId() == newItem.getRoomId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Memoria oldItem, @NonNull Memoria newItem) {

            if (oldItem.getTituloMemoria() != null && newItem.getTituloMemoria() != null) {
                return oldItem.getTituloMemoria().equals(newItem.getTituloMemoria()) && oldItem.getDescricaoMemoria().equals(newItem.getDescricaoMemoria()) && oldItem.getCategory().equals(newItem.getCategory()) && oldItem.getImportancia() == newItem.getImportancia();

            } else if (oldItem.getTituloMemoria() != null && newItem.getTituloMemoria() == null || oldItem.getTituloMemoria() == null && newItem.getTituloMemoria() != null) {
                return false;

            } else {
                return oldItem.getDescricaoMemoria().equals(newItem.getDescricaoMemoria()) && oldItem.getCategory().equals(newItem.getCategory()) && oldItem.getImportancia() == newItem.getImportancia();

            }
        }
    };


    public MemoriaAdapter(MemoriaActivity activity, MemoriaViewModel viewModel) {
        super(DIFF_UTIL);
        this.activity = activity;
        this.mViewModel = viewModel;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.memoria_item_row, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        final Memoria memoria = getItem(position);

        if (memoria.getTituloMemoria() != null) {
            holder.txtTitulo.setText(memoria.getTituloMemoria());
            holder.txtTitulo.setVisibility(View.VISIBLE);
            holder.viewDivider.setVisibility(View.VISIBLE);
        } else {
            holder.txtTitulo.setVisibility(View.GONE);
            holder.viewDivider.setVisibility(View.GONE);
        }

        holder.txtDesc.setText(memoria.getDescricaoMemoria());

        switch (memoria.getImportancia()) {
            case 0:
                holder.cardView.setBackgroundColor(activity.getResources().getColor(android.R.color.white));
                break;
            case 1:
                holder.cardView.setBackgroundColor(activity.getResources().getColor(R.color.imp2));
                break;
            case 2:
                holder.cardView.setBackgroundColor(activity.getResources().getColor(R.color.imp3));
                break;
        }

        holder.imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDeleteDialog(memoria);
            }
        });

        holder.imageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initEditDialog(memoria, position);
            }
        });


    }

    public Memoria getMemoria(int position) {
        return getItem(position);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView txtTitulo;
        TextView txtDesc;
        View viewDivider;
        ImageView imageDelete;
        ImageView imageEdit;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            cardView = itemView.findViewById(R.id.memoria_item_row_cardView);
            txtTitulo = itemView.findViewById(R.id.memoria_item_row_txtTitulo);
            txtDesc = itemView.findViewById(R.id.memoria_item_row_txtDesc);
            viewDivider = itemView.findViewById(R.id.memoria_item_row_viewDivider);
            imageDelete = itemView.findViewById(R.id.memoria_item_row_imageDelete);
            imageEdit = itemView.findViewById(R.id.memoria_item_row_imageEdit);


        }
    }

    @SuppressLint("ResourceAsColor")
    private void initDeleteDialog(final Memoria memoria) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.deletar_item);
        builder.setMessage(R.string.deseja_deletar_a_memoria);
        builder.setPositiveButton(R.string.deletar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mViewModel.delete(memoria);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Button pButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        pButton.setTextColor(activity.getResources().getColor(android.R.color.holo_red_dark));
        pButton.setBackgroundColor(android.R.color.white);


    }

    private void initEditDialog(final Memoria memoria, final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final View dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_add_memoria, null);
        builder.setView(dialogView);

        final TextInputEditText editTitulo = dialogView.findViewById(R.id.dialog_add_memoria_editTitulo);
        final EditText editDesc = dialogView.findViewById(R.id.dialog_add_memoria_editDesc);
        final SeekBar seekImportancia = dialogView.findViewById(R.id.dialog_add_memoria_seekBar);

        if (memoria.getTituloMemoria() != null) {
            editTitulo.setText(memoria.getTituloMemoria());
        }
        editDesc.setText(memoria.getDescricaoMemoria());
        seekImportancia.setProgress(memoria.getImportancia());


        builder.setPositiveButton(R.string.salvar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (editTitulo.getText().toString().isEmpty()) {
                    memoria.setTituloMemoria(null);
                } else {
                    memoria.setTituloMemoria(editTitulo.getText().toString());
                }
                memoria.setDescricaoMemoria(editDesc.getText().toString());
                memoria.setImportancia(seekImportancia.getProgress());
                memoria.setRoomId(memoria.getRoomId());
                mViewModel.update(memoria);
                notifyItemChanged(position);

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }

}
