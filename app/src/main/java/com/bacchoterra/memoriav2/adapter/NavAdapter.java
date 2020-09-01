package com.bacchoterra.memoriav2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bacchoterra.memoriav2.R;
import com.bacchoterra.memoriav2.model.Categoria;

import java.util.List;

public class NavAdapter extends ListAdapter<Categoria, NavAdapter.MyViewHolder> {


    static final DiffUtil.ItemCallback<Categoria> DIFF_UTIL = new DiffUtil.ItemCallback<Categoria>() {
        @Override
        public boolean areItemsTheSame(@NonNull Categoria oldItem, @NonNull Categoria newItem) {
            return oldItem.getRoomId() == newItem.getRoomId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Categoria oldItem, @NonNull Categoria newItem) {
            return oldItem.getTitulo().equals(newItem.getTitulo()) && oldItem.getRoomId() == newItem.getRoomId();
        }
    };

    public NavAdapter() {
        super(DIFF_UTIL);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_nav, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Categoria categoria = getItem(position);

        holder.txt.setText(categoria.getTitulo());

    }

    public Categoria getCategoria(int position) {

        return getItem(position);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt = itemView.findViewById(R.id.txtNavRecycler);

        }
    }
}
