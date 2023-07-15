package com.example.minimarket.adaptadores;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minimarket.R;
import com.example.minimarket.entidades.PRODUCTOS;

import java.util.ArrayList;

public class listaproductos_generalAdapter extends RecyclerView.Adapter<listaproductos_generalAdapter.PRODUCTOviewHolder> {

    ArrayList<PRODUCTOS> listaPRODUCTOS_G;
    NavController navController;

    public listaproductos_generalAdapter(ArrayList<PRODUCTOS> listaPRODUCTOS_G, NavController navController) {
        this.listaPRODUCTOS_G = listaPRODUCTOS_G;
        this.navController = navController;
    }

    @NonNull
    @Override
    public listaproductos_generalAdapter.PRODUCTOviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_productos, null, false);
        return new PRODUCTOviewHolder(view, navController);
    }

    @Override
    public void onBindViewHolder(@NonNull listaproductos_generalAdapter.PRODUCTOviewHolder holder, int position) {
        holder.viewcodigoo.setText("CODIGO: " + listaPRODUCTOS_G.get(position).getCodigo());
        holder.viewNombre.setText("NOMBRE: " + listaPRODUCTOS_G.get(position).getNombre());
        holder.viewfechafecha.setText("FECHA: "+listaPRODUCTOS_G.get(position).getFecha());
        holder.viewPrecio.setText("PRECIO: " + String.valueOf((float) listaPRODUCTOS_G.get(position).getPrecio()));
        holder.viewCantidad.setText("CANTIDAD: " + String.valueOf((float) listaPRODUCTOS_G.get(position).getCantidad()));
        holder.viewTipoUnidad.setText(" " + listaPRODUCTOS_G.get(position).getTipounidad());

    }

    @Override
    public int getItemCount() {
        return listaPRODUCTOS_G.size();
    }

    public class PRODUCTOviewHolder extends RecyclerView.ViewHolder {

        TextView viewcodigoo,viewNombre, viewPrecio, viewCantidad, viewTipoUnidad, viewfechafecha;

        NavController navController;

        public PRODUCTOviewHolder(@NonNull View itemView, NavController navController) {
            super(itemView);
            this.navController = navController;

            viewcodigoo=itemView.findViewById(R.id.codigoo);
            viewNombre = itemView.findViewById(R.id.viewnombre);
            viewPrecio = itemView.findViewById(R.id.viewprecio);
            viewCantidad = itemView.findViewById(R.id.viewcantidad);
            viewTipoUnidad = itemView.findViewById(R.id.viewtipounidad);
            viewfechafecha = itemView.findViewById(R.id.fechafecha);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavController navController = Navigation.findNavController(view);

                    Bundle bundle = new Bundle();
                    bundle.putString("codigo", listaPRODUCTOS_G.get(getAdapterPosition()).getCodigo());
                    bundle.putString("nombre", listaPRODUCTOS_G.get(getAdapterPosition()).getNombre());
                    bundle.putString("marca", listaPRODUCTOS_G.get(getAdapterPosition()).getMarca());
                    bundle.putString("precio", String.valueOf(listaPRODUCTOS_G.get(getAdapterPosition()).getPrecio()));
                    bundle.putString("cantidad", String.valueOf(listaPRODUCTOS_G.get(getAdapterPosition()).getCantidad()));
                    bundle.putString("tipounidad", listaPRODUCTOS_G.get(getAdapterPosition()).getTipounidad());

                    navController.navigate(R.id.nav_venta, bundle);
                }
            });
        }
    }
}
