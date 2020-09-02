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
import com.bacchoterra.memoriav2.model.Memoria;

public class MemoriaAdapter extends ListAdapter<Memoria,MemoriaAdapter.MyViewHolder> {

    private static final DiffUtil.ItemCallback<Memoria> DIFF_UTIL = new DiffUtil.ItemCallback<Memoria>() {
        @Override
        public boolean areItemsTheSame(@NonNull Memoria oldItem, @NonNull Memoria newItem) {
            return oldItem.getRoomId() == newItem.getRoomId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Memoria oldItem, @NonNull Memoria newItem) {
            return oldItem.getTituloMemoria().equals(newItem.getTituloMemoria()) && oldItem.getDescricaoMemoria().equals(newItem.getDescricaoMemoria()) && oldItem.getCategory().equals(newItem.getCategory()) && oldItem.getImportancia() == newItem.getImportancia();
        }
    };


    public MemoriaAdapter() {
        super(DIFF_UTIL);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.memoria_item_row,parent,false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Memoria m = getItem(position);
        holder.txtTitulo.setText(m.getTituloMemoria());
        holder.txtDesc.setText(m.getDescricaoMemoria());

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitulo;
        TextView txtDesc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitulo = itemView.findViewById(R.id.memoria_item_row_txtTitulo);
            txtDesc = itemView.findViewById(R.id.memoria_item_row_txtDesc);

        }
    }

}
