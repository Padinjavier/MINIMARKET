package com.example.minimarket.ui.home;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minimarket.DB.DBPRODUCTOS;
import com.example.minimarket.R;
import com.example.minimarket.adaptadores.listaproducto_vencidoAdapter;
import com.example.minimarket.adaptadores.listaproductos_generalAdapter;
import com.example.minimarket.databinding.FragmentHomeBinding;
import com.example.minimarket.entidades.PRODUCTOS;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    RecyclerView listaPRODUCTOS, listaproducto_vencido;
    ArrayList<PRODUCTOS> listaArrayPRODUCTOS;
    ArrayList<PRODUCTOS> listaArrayPRODUCTO_VENCIDO;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listaPRODUCTOS = view.findViewById(R.id.viewPRODUCTO_G);
        listaPRODUCTOS.setLayoutManager(new LinearLayoutManager(getContext()));
        DBPRODUCTOS dbproductos = new DBPRODUCTOS(getContext());
        listaArrayPRODUCTOS = new ArrayList<>();
        NavController navController = Navigation.findNavController(requireView());
        listaproductos_generalAdapter adapterG = new listaproductos_generalAdapter(dbproductos.mostrarPRODUTOS(), navController);
        listaPRODUCTOS.setAdapter(adapterG);

        listaproducto_vencido = view.findViewById(R.id.viewPRODUCTOVENCIDO);
        listaproducto_vencido.setLayoutManager(new LinearLayoutManager(getContext()));
        listaArrayPRODUCTO_VENCIDO = new ArrayList<>();
        listaproducto_vencidoAdapter adapterV = new listaproducto_vencidoAdapter(dbproductos.mostrarPRODUTOSVENCIDOS());
        listaproducto_vencido.setAdapter(adapterV);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}