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

import com.example.minimarket.DB.DBVENTAS;
import com.example.minimarket.R;
import com.example.minimarket.adaptadores.listaproducto_ventaAdapter;
import com.example.minimarket.databinding.FragmentVentaBinding;
import com.example.minimarket.entidades.VENTAS;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class VentaFragment extends Fragment {

    private FragmentVentaBinding binding;


    RecyclerView listaproducto_vendidos;
    ArrayList<VENTAS> listaArrayPRODUCTOSVENDIDOS;


    Button SCANERVENTA, VENDERR;

    SearchView BUQUEDAVENTA;

    TextView producto_codigo, producto_nombre, producto_marca, producto_tipounidad, producto_totalpagar;
    EditText producto_precio, producto_cantidad;
    String cantidad;
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
        DBVENTAS dbventas = new DBVENTAS(getContext());
        listaArrayPRODUCTOSVENDIDOS = new ArrayList<>();
        listaproducto_ventaAdapter adapterVendidos = new listaproducto_ventaAdapter(dbventas.mostrarPRODUTOSVENDIDOS());
        listaproducto_vendidos.setAdapter(adapterVendidos);


        SCANERVENTA = view.findViewById(R.id.scanerventa);
        BUQUEDAVENTA = view.findViewById(R.id.busquedaventa);
        VENDERR = view.findViewById(R.id.vender);

        producto_codigo = view.findViewById(R.id.viewcodigo_venta);
        producto_nombre = view.findViewById(R.id.viewnombre_venta);
        producto_marca = view.findViewById(R.id.viewmarca_venta);
        producto_precio = view.findViewById(R.id.viewprecio_venta);
        producto_cantidad = view.findViewById(R.id.viewcantidad_venta);
        producto_tipounidad = view.findViewById(R.id.viewunidad_venta);
        producto_totalpagar = view.findViewById(R.id.viewtotalpagar_venta);
        SCANERVENTA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScanActivity();
            }
        });

        if (getArguments() != null) {
            String codigo = getArguments().getString("codigo");
            String nombre = getArguments().getString("nombre");
            String marca = getArguments().getString("marca");
            String precio = getArguments().getString("precio");
            cantidad = getArguments().getString("cantidad");
            String tipounidad = getArguments().getString("tipounidad");


            producto_codigo.setText(codigo);
            producto_nombre.setText(nombre);
            producto_marca.setText(marca);
            producto_precio.setText(precio);
            producto_cantidad.setText(cantidad);
            producto_tipounidad.setText(tipounidad);
            producto_totalpagar.setText(String.valueOf(Double.parseDouble(producto_precio.getText().toString()) * Double.parseDouble(producto_cantidad.getText().toString())));

        }

        VENDERR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (producto_nombre.getText().toString().equals("") || producto_marca.getText().toString().equals("") || producto_precio.getText().toString().equals("") || producto_cantidad.getText().toString().equals("") || producto_cantidad.getText().toString().equals("0") || producto_tipounidad.getText().toString().equals("")) {

                    Toast.makeText(getContext(), "RELLENE TODOS LOS CAMPOS", Toast.LENGTH_SHORT).show();

                } else {
                    double cantidad2 = Double.parseDouble(producto_cantidad.getText().toString());
                    double limite = Double.parseDouble(cantidad);
                    if (cantidad2 > limite) {
                        Toast.makeText(getContext(), "La cantidad excede el límite de Stock: "+limite, Toast.LENGTH_SHORT).show();
                    } else {

                        String Vcodigo = producto_codigo.getText().toString();
                        String Vnombre = producto_nombre.getText().toString();
                        String Vmarca = producto_marca.getText().toString();
                        double Vprecio = Double.parseDouble(producto_precio.getText().toString());
                        double Vcantidad = Double.parseDouble(producto_cantidad.getText().toString());
                        String Vtipounidad = producto_tipounidad.getText().toString();
                        double Vtotalpagar = Double.parseDouble(producto_totalpagar.getText().toString());


                        DBVENTAS dbventas = new DBVENTAS(getContext());
                        long ID = dbventas.insertarPRODUCTOVENTAS(Vcodigo, Vnombre, Vmarca, Vprecio, Vcantidad, Vtipounidad, Vtotalpagar);

                        if (ID > 0) {
                            Toast.makeText(getContext(), "REGISTRO GUARDADO", Toast.LENGTH_SHORT).show();
                            limpiaredittext();
                            adapterVendidos.setDatos(dbventas.mostrarPRODUTOSVENDIDOS());
                            adapterVendidos.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getContext(), "ERROR AL GUARDAR", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    //codigo barra
    private void startScanActivity() {
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt("ESCANEANDO CÓDIGO DE BARRAS");
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
                Toast.makeText(getContext(), "La lectura ha sido cancelada.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), result.getContents(), Toast.LENGTH_SHORT).show();
                BUQUEDAVENTA.setQuery(result.getContents(), false);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void limpiaredittext() {
        producto_precio.setText("");
        producto_cantidad.setText("");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}