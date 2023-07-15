package com.example.minimarket.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minimarket.R;
import com.example.minimarket.entidades.VENTAS;

import java.util.ArrayList;

public class listaproducto_ventaAdapter extends RecyclerView.Adapter<listaproducto_ventaAdapter.PRODUCTOVENDIDOviewGolder> {

    ArrayList<VENTAS> listaPRODUCTOS_VENDIDOS;

    public listaproducto_ventaAdapter(ArrayList<VENTAS> listaPRODUCTOS_VENDIDOS) {
        this.listaPRODUCTOS_VENDIDOS = listaPRODUCTOS_VENDIDOS;
    }
    public void setDatos(ArrayList<VENTAS> listaPRODUCTOS_VENDIDOS) {
        this.listaPRODUCTOS_VENDIDOS = listaPRODUCTOS_VENDIDOS;
    }
    @NonNull
    @Override
    public listaproducto_ventaAdapter.PRODUCTOVENDIDOviewGolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_productosventa, null, false);
        return new listaproducto_ventaAdapter.PRODUCTOVENDIDOviewGolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull listaproducto_ventaAdapter.PRODUCTOVENDIDOviewGolder holder, int position) {
        holder.viewNombre_vendido.setText("NOMBRE: " + listaPRODUCTOS_VENDIDOS.get(position).getNombre_venta());
        holder.viewPrecio_vendido.setText("PRECIO: " + String.valueOf(listaPRODUCTOS_VENDIDOS.get(position).getPrecio_venta()));
        holder.viewCantidad_vendido.setText("CANTIDAD: " + String.valueOf(listaPRODUCTOS_VENDIDOS.get(position).getCantidad_venta()));
        holder.viewTipoUnidad_vendido.setText(" " + listaPRODUCTOS_VENDIDOS.get(position).getTipounidad_venta());
        holder.viewPagoTotal.setText("TOTAL: " + (listaPRODUCTOS_VENDIDOS.get(position).getCantidad_venta() * listaPRODUCTOS_VENDIDOS.get(position).getPrecio_venta()));
    }

    @Override
    public int getItemCount() {
        return listaPRODUCTOS_VENDIDOS.size();
    }

    public class PRODUCTOVENDIDOviewGolder extends RecyclerView.ViewHolder {

        TextView viewNombre_vendido, viewPrecio_vendido, viewCantidad_vendido, viewTipoUnidad_vendido, viewPagoTotal;

        public PRODUCTOVENDIDOviewGolder(@NonNull View itemView) {
            super(itemView);
            viewNombre_vendido = itemView.findViewById(R.id.viewnombreventa);
            viewPrecio_vendido = itemView.findViewById(R.id.viewpreciovendido);
            viewCantidad_vendido = itemView.findViewById(R.id.viewcantidadvendido);
            viewTipoUnidad_vendido = itemView.findViewById(R.id.viewtipovendido);
            viewPagoTotal = itemView.findViewById(R.id.viewpagototal);

        }
    }
}
