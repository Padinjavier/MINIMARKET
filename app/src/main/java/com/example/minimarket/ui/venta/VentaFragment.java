package com.example.minimarket.ui.venta;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minimarket.DB.DBPRODUCTOS;
import com.example.minimarket.R;
import com.example.minimarket.adaptadores.listaproducto_vencidoAdapter;
import com.example.minimarket.adaptadores.listaproducto_ventaAdapter;
import com.example.minimarket.adaptadores.listaproductos_generalAdapter;
import com.example.minimarket.databinding.FragmentVentaBinding;
import com.example.minimarket.entidades.PRODUCTOS;

import java.util.ArrayList;

public class VentaFragment extends Fragment {

    private FragmentVentaBinding binding;


    RecyclerView listaproducto_vendidos;
    ArrayList<PRODUCTOS> listaArrayPRODUCTOSVENDIDOS;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        VentaViewModel galleryViewModel =
                new ViewModelProvider(this).get(VentaViewModel.class);

        binding = FragmentVentaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listaproducto_vendidos=view.findViewById(R.id.viewPRODUCTOS_VENDIDOS);
        listaproducto_vendidos.setLayoutManager(new LinearLayoutManager(getContext()));
        DBPRODUCTOS dbproductos=new DBPRODUCTOS(getContext());
        listaArrayPRODUCTOSVENDIDOS = new ArrayList<>();
        listaproducto_ventaAdapter adapterVendidos = new listaproducto_ventaAdapter(dbproductos.mostrarPRODUTOS());
        listaproducto_vendidos.setAdapter(adapterVendidos);



    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}