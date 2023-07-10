package com.example.minimarket.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minimarket.R;
import com.example.minimarket.entidades.PRODUCTOS;

import java.util.ArrayList;

public class listaproducto_vencidoAdapter extends RecyclerView.Adapter<listaproducto_vencidoAdapter.PRODUCTOvencidoviewHolder> {

    ArrayList<PRODUCTOS> listaPRODUCTOS_V;

    public listaproducto_vencidoAdapter(ArrayList<PRODUCTOS> listaPRODUCTOS_V) {
        this.listaPRODUCTOS_V = listaPRODUCTOS_V;
    }

    @NonNull
    @Override
    public listaproducto_vencidoAdapter.PRODUCTOvencidoviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_productosvencidos, null, false);

        return new listaproducto_vencidoAdapter.PRODUCTOvencidoviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull listaproducto_vencidoAdapter.PRODUCTOvencidoviewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class PRODUCTOvencidoviewHolder extends RecyclerView.ViewHolder {

        TextView viewNombre, viewCantidad;
        public PRODUCTOvencidoviewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
