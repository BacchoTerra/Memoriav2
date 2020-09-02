package com.bacchoterra.memoriav2.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bacchoterra.memoriav2.R;
import com.bacchoterra.memoriav2.model.Memoria;
import com.bacchoterra.memoriav2.view.MemoriaActivity;

public class MemoriaAdapter extends ListAdapter<Memoria,MemoriaAdapter.MyViewHolder> {

    private Activity activity;

    private static final DiffUtil.ItemCallback<Memoria> DIFF_UTIL = new DiffUtil.ItemCallback<Memoria>() {
        @Override
        public boolean areItemsTheSame(@NonNull Memoria oldItem, @NonNull Memoria newItem) {
            return oldItem.getRoomId() == newItem.getRoomId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Memoria oldItem, @NonNull Memoria newItem) {

            if (oldItem.getTituloMemoria() != null && newItem.getTituloMemoria() != null){
                return oldItem.getTituloMemoria().equals(newItem.getTituloMemoria()) && oldItem.getDescricaoMemoria().equals(newItem.getDescricaoMemoria()) && oldItem.getCategory().equals(newItem.getCategory()) && oldItem.getImportancia() == newItem.getImportancia();

            }else {
                return oldItem.getDescricaoMemoria().equals(newItem.getDescricaoMemoria()) && oldItem.getCategory().equals(newItem.getCategory()) && oldItem.getImportancia() == newItem.getImportancia();
            }
        }
    };


    public MemoriaAdapter(MemoriaActivity activity) {
        super(DIFF_UTIL);
        this.activity = activity;
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

        if (m.getTituloMemoria() != null){
            holder.txtTitulo.setText(m.getTituloMemoria());
        }else {
            holder.txtTitulo.setVisibility(View.GONE);
            holder.viewDivider.setVisibility(View.GONE);
        }

        holder.txtDesc.setText(m.getDescricaoMemoria());

        switch (m.getImportancia()){
            case 0:
                break;
            case 1:
                holder.cardView.setBackgroundColor(activity.getResources().getColor(R.color.imp2));
                break;
            case 2:
                holder.cardView.setBackgroundColor(activity.getResources().getColor(R.color.imp3));
                break;
        }

    }

    public Memoria getMemoria(int position){
        return getItem(position);

    }

    private void initDeleteDialog(MyViewHolder holder){



    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView txtTitulo;
        TextView txtDesc;
        View viewDivider;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            cardView = itemView.findViewById(R.id.memoria_item_row_cardView);
            txtTitulo = itemView.findViewById(R.id.memoria_item_row_txtTitulo);
            txtDesc = itemView.findViewById(R.id.memoria_item_row_txtDesc);
            viewDivider = itemView.findViewById(R.id.memoria_item_row_viewDivider);


        }
    }

}
