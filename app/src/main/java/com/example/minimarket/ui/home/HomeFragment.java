package com.example.minimarket.ui.home;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minimarket.DB.DBPRODUCTOS;
import com.example.minimarket.MainActivity;
import com.example.minimarket.R;
import com.example.minimarket.adaptadores.listaproductos_generalAdapter;
import com.example.minimarket.databinding.FragmentHomeBinding;
import com.example.minimarket.entidades.PRODUCTOS;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;


    RecyclerView listaPRODUCTOS;
    ArrayList<PRODUCTOS> listaArrayPRODUCTOS;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        listaPRODUCTOS=view.findViewById(R.id.viewPRODUCTO_G);
        listaPRODUCTOS.setLayoutManager(new LinearLayoutManager(getContext()));

        DBPRODUCTOS dbproductos=new DBPRODUCTOS(getContext());

        listaArrayPRODUCTOS = new ArrayList<>();
        listaproductos_generalAdapter adapter = new listaproductos_generalAdapter(dbproductos.mostrarPRODUTOS());


        listaPRODUCTOS.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}