package com.example.minimarket.ui.venta;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minimarket.DB.DBHelper;
import com.example.minimarket.DB.DBPRODUCTOS;
import com.example.minimarket.DB.DBVENTAS;
import com.example.minimarket.MainActivity;
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


    Button VENDERR;


    TextView producto_codigo, producto_nombre, producto_marca, producto_fecha, producto_tipounidad, producto_totalpagar;


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


        VENDERR = view.findViewById(R.id.vender);

        producto_codigo = view.findViewById(R.id.viewcodigo_venta);
        producto_nombre = view.findViewById(R.id.viewnombre_venta);
        producto_marca = view.findViewById(R.id.viewmarca_venta);
        producto_precio = view.findViewById(R.id.viewprecio_venta);
        producto_cantidad = view.findViewById(R.id.viewcantidad_venta);
        producto_tipounidad = view.findViewById(R.id.viewunidad_venta);
        producto_fecha = view.findViewById(R.id.viewfecha_venta);
        producto_totalpagar = view.findViewById(R.id.viewtotalpagar_venta);


        if (getArguments() != null) {
            String codigo = getArguments().getString("codigo");
            String nombre = getArguments().getString("nombre");
            String marca = getArguments().getString("marca");
            String precio = getArguments().getString("precio");
            cantidad = getArguments().getString("cantidad");
            String tipounidad = getArguments().getString("tipounidad");
            String fecha = getArguments().getString("fechab");


            producto_codigo.setText(codigo);
            producto_nombre.setText(nombre);
            producto_marca.setText(marca);
            producto_precio.setText(precio);
            producto_cantidad.setText(cantidad);
            producto_tipounidad.setText(tipounidad);
            producto_fecha.setText(fecha);
            double totalPagar = Double.parseDouble(producto_precio.getText().toString()) * Double.parseDouble(producto_cantidad.getText().toString());
            float totalPagarFloat = (float) totalPagar;
            producto_totalpagar.setText(String.valueOf(totalPagarFloat));

        }

        producto_precio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No se necesita implementar este método
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calcularResultado();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No se necesita implementar este método
            }
        });

        producto_cantidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No se necesita implementar este método
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calcularResultado();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No se necesita implementar este método
            }
        });

        VENDERR.setOnClickListener(view1 -> {
            DBHelper dbHelper = new DBHelper(getContext());
            if (!validarDBventa()) {
                Toast.makeText(getContext(), "Por favor, ingrese productos para vender", Toast.LENGTH_SHORT).show();
            } else {
                if (producto_nombre.getText().toString().equals("") || producto_marca.getText().toString().equals("") || producto_precio.getText().toString().equals("") || producto_precio.getText().toString().equals(".") || producto_cantidad.getText().toString().equals("") || producto_cantidad.getText().toString().equals(".") || producto_tipounidad.getText().toString().equals("")) {

                    Toast.makeText(getContext(), "RELLENE TODOS LOS CAMPOS", Toast.LENGTH_SHORT).show();

                } else if (producto_fecha.getText().toString().equals("")) {

                    Toast.makeText(getContext(), "SELECCIONE UN PRODUCTO DEL STOCK |HOME|", Toast.LENGTH_SHORT).show();

                } else if (Double.parseDouble(producto_precio.getText().toString()) <= 0) {

                    Toast.makeText(getContext(), "EL PRECIO DEBE SER MAYOR A 0", Toast.LENGTH_SHORT).show();

                } else if (Double.parseDouble(producto_cantidad.getText().toString()) <= 0) {

                    Toast.makeText(getContext(), "LA CANTIDAD DEBE SER MAYOR A 0", Toast.LENGTH_SHORT).show();

                } else {
                    double cantidad2 = Double.parseDouble(producto_cantidad.getText().toString());
                    double limite = Double.parseDouble(cantidad);
                    if (cantidad2 > limite) {
                        Toast.makeText(getContext(), "La cantidad excede el límite de Stock: " + limite, Toast.LENGTH_SHORT).show();
                    } else {
                        float cantidadresta = (float) (limite - cantidad2);
                        String Vcodigo = producto_codigo.getText().toString();
                        String Vnombre = producto_nombre.getText().toString();
                        String Vmarca = producto_marca.getText().toString();
                        double Vprecio = Double.parseDouble(producto_precio.getText().toString());
                        double Vcantidad = Double.parseDouble(producto_cantidad.getText().toString());
                        String Vtipounidad = producto_tipounidad.getText().toString();
                        String Vfecha = producto_fecha.getText().toString();
                        double Vtotalpagar = Double.parseDouble(producto_totalpagar.getText().toString());


                        DBVENTAS dbventas1 = new DBVENTAS(getContext());
                        long ID = dbventas1.insertarPRODUCTOVENTAS(Vcodigo, Vnombre, Vmarca, Vprecio, Vcantidad, Vtipounidad, Vfecha, Vtotalpagar);

                        if (ID > 0) {
                            DBPRODUCTOS dbproductos = new DBPRODUCTOS(getContext());
                            dbproductos.retirarPRODUCTO(Vcodigo, cantidadresta, Vfecha);
                            Toast.makeText(getContext(), "REGISTRO GUARDADO", Toast.LENGTH_SHORT).show();

                            limpiaredittext();
                            adapterVendidos.setDatos(dbventas1.mostrarPRODUTOSVENDIDOS());
                            adapterVendidos.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getContext(), "ERROR AL GUARDAR", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });


    }

    public boolean validarDBventa() {
        boolean hasData = false;
        boolean dbb;
        DBHelper dbHelper = new DBHelper(getContext());
        String tabla = (DBHelper.TABLA_PRODUCTOS);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + tabla, null);
        if (cursor != null && cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            hasData = count > 0;
            cursor.close();
        }
        if (hasData) {
            // La tabla tiene datos
            dbb = true;
        } else {
            // La tabla no tiene datos
            dbb = false;
        }
        return dbb;
    }

    private void calcularResultado() {
        String strValue1 = producto_precio.getText().toString();
        String strValue2 = producto_cantidad.getText().toString();
        if (strValue1.equals(".") || strValue2.equals(".")) {
            Toast.makeText(getContext(), "INGRESE DATOS VALIDOS", Toast.LENGTH_SHORT).show();

        } else {
            if (!strValue1.isEmpty() && !strValue2.isEmpty()) {
                double value1 = Double.parseDouble(strValue1);
                double value2 = Double.parseDouble(strValue2);

                double result = value1 * value2;
                producto_totalpagar.setText(String.valueOf((float) result));
            } else {
                producto_totalpagar.setText("");
            }
        }
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