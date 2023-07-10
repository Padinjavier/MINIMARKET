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

public class listaproductos_generalAdapter extends RecyclerView.Adapter<listaproductos_generalAdapter.PRODUCTOviewHolder> {

    ArrayList<PRODUCTOS> listaPRODUCTOS_G;

    public listaproductos_generalAdapter(ArrayList<PRODUCTOS> listaPRODUCTOS_G) {
        this.listaPRODUCTOS_G = listaPRODUCTOS_G;
    }


    @NonNull
    @Override
    public listaproductos_generalAdapter.PRODUCTOviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_productos, null, false);

        return new PRODUCTOviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull listaproductos_generalAdapter.PRODUCTOviewHolder holder, int position) {
        holder.viewNombre.setText("NOMBRE: "+listaPRODUCTOS_G.get(position).getNombre());
        holder.viewPrecio.setText("PRECIO: "+String.valueOf((float) listaPRODUCTOS_G.get(position).getPrecio()));
        holder.viewCantidad.setText("CANTIDAD: "+String.valueOf((float) listaPRODUCTOS_G.get(position).getCantidad()));


    }

    @Override
    public int getItemCount() {
        return listaPRODUCTOS_G.size();
    }

    public class PRODUCTOviewHolder extends RecyclerView.ViewHolder {

        TextView viewNombre, viewPrecio, viewCantidad;

        public PRODUCTOviewHolder(@NonNull View itemView) {
            super(itemView);

            viewNombre = itemView.findViewById(R.id.viewnombre);
            viewPrecio = itemView.findViewById(R.id.viewprecio);
            viewCantidad = itemView.findViewById(R.id.viewcantidad);

        }
    }
}
