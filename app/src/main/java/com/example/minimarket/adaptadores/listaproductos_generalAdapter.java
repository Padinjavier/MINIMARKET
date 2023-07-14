package com.example.minimarket.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minimarket.MainActivity;
import com.example.minimarket.R;
import com.example.minimarket.entidades.PRODUCTOS;
import com.example.minimarket.ui.home.HomeFragment;
import com.example.minimarket.ui.venta.VentaFragment;

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
        holder.viewNombre.setText("NOMBRE: " + listaPRODUCTOS_G.get(position).getNombre());
        holder.viewPrecio.setText("PRECIO: " + String.valueOf((float) listaPRODUCTOS_G.get(position).getPrecio()));
        holder.viewCantidad.setText("CANTIDAD: " + String.valueOf((float) listaPRODUCTOS_G.get(position).getCantidad()));
        holder.viewTipoUnidad.setText(" " + listaPRODUCTOS_G.get(position).getTipounidad());

    }

    @Override
    public int getItemCount() {
        return listaPRODUCTOS_G.size();
    }

    public class PRODUCTOviewHolder extends RecyclerView.ViewHolder {

        TextView viewNombre, viewPrecio, viewCantidad, viewTipoUnidad;

        NavController navController;

        public PRODUCTOviewHolder(@NonNull View itemView, NavController navController) {
            super(itemView);
            this.navController = navController;

            viewNombre = itemView.findViewById(R.id.viewnombre);
            viewPrecio = itemView.findViewById(R.id.viewprecio);
            viewCantidad = itemView.findViewById(R.id.viewcantidad);
            viewTipoUnidad = itemView.findViewById(R.id.viewtipounidad);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), listaPRODUCTOS_G.get(getAdapterPosition()).getNombre() + "|", Toast.LENGTH_SHORT).show();

                    navController.navigate(R.id.nav_venta); // Navegación a la pestaña de ventas



                }
            });



        }
    }
}
