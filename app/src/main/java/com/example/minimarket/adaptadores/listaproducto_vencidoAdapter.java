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

        return new PRODUCTOvencidoviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull listaproducto_vencidoAdapter.PRODUCTOvencidoviewHolder holder, int position) {
        holder.viewNombre_V.setText("NOMBRE: "+listaPRODUCTOS_V.get(position).getNombre());
        holder.viewFecha_V.setText("FECHA: "+listaPRODUCTOS_V.get(position).getFecha());
        holder.viewCantidad_V.setText("CANTIDAD: "+String.valueOf((float) listaPRODUCTOS_V.get(position).getCantidad()));
    }

    @Override
    public int getItemCount() {
        return listaPRODUCTOS_V.size();
    }

    public class PRODUCTOvencidoviewHolder extends RecyclerView.ViewHolder {

        TextView viewNombre_V, viewCantidad_V,viewFecha_V;
        public PRODUCTOvencidoviewHolder(@NonNull View itemView) {
            super(itemView);
            viewNombre_V = itemView.findViewById(R.id.viewnombre_v);
            viewFecha_V = itemView.findViewById(R.id.viewfecha_v);
            viewCantidad_V = itemView.findViewById(R.id.viewcantidad_v);

        }
    }
}
