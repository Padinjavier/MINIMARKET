package com.example.minimarket.ui.venta;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class VentaFragment extends Fragment {

    private FragmentVentaBinding binding;


    RecyclerView listaproducto_vendidos;
    ArrayList<PRODUCTOS> listaArrayPRODUCTOSVENDIDOS;


    Button SCANERVENTA;

    SearchView BUQUEDAVENTA;
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

        listaproducto_vendidos = view.findViewById(R.id.viewPRODUCTOS_VENDIDOS);
        listaproducto_vendidos.setLayoutManager(new LinearLayoutManager(getContext()));
        DBPRODUCTOS dbproductos = new DBPRODUCTOS(getContext());
        listaArrayPRODUCTOSVENDIDOS = new ArrayList<>();
        listaproducto_ventaAdapter adapterVendidos = new listaproducto_ventaAdapter(dbproductos.mostrarPRODUTOS());
        listaproducto_vendidos.setAdapter(adapterVendidos);


        SCANERVENTA = view.findViewById(R.id.scanerventa);
       BUQUEDAVENTA=view.findViewById(R.id.busquedaventa);


        SCANERVENTA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScanActivity();
            }
        });


    }
    //codigo barra
    private void startScanActivity() {
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt("ESCANEANDO CODIGO DE BARRAS");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.setOrientationLocked(true);
        integrator.initiateScan();
    }
    //codigo barra
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(getContext(), "Lectura cancelada", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), result.getContents(), Toast.LENGTH_SHORT).show();
                BUQUEDAVENTA.setQuery(result.getContents(), false);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}