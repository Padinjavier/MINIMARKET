package com.example.minimarket.ui.ingreso;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.minimarket.DB.DBHelper;
import com.example.minimarket.DB.DBPRODUCTOS;
import com.example.minimarket.R;
import com.example.minimarket.databinding.FragmentIngresoBinding;
import com.example.minimarket.entidades.PRODUCTOS;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class IngresoFragment extends Fragment {

    Button btnScan, guardar, BTNGUARDAR_F;
    EditText txt_codigo, txt_nombre, txt_marca, txt_precio, txt_cantidad, txt_fecha;
    SearchView bar_busqueda;
    private FragmentIngresoBinding binding;
    View VIEWCALENDARIO;
    CalendarView CALENDARIO;
    long fechaSeleccionada;

    Spinner tipo_unidad;
    PRODUCTOS productos;

    boolean correcto = false;
    public boolean isFloatingWindowShown = false;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        IngresoViewModel ingresoViewModel = new ViewModelProvider(this).get(IngresoViewModel.class);

        binding = FragmentIngresoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnScan = view.findViewById(R.id.btnScan);
        txt_codigo = view.findViewById(R.id.TXTCODIGO);

        BTNGUARDAR_F = view.findViewById(R.id.GUARDAR_F);
        VIEWCALENDARIO = view.findViewById(R.id.view_c);
        CALENDARIO = view.findViewById(R.id.calendarView);

        txt_codigo = view.findViewById(R.id.TXTCODIGO);
        txt_nombre = view.findViewById(R.id.TXTNOMBRE);
        txt_marca = view.findViewById(R.id.TXTMARCA);
        txt_precio = view.findViewById(R.id.TXTPRECIO);
        txt_cantidad = view.findViewById(R.id.TXTCANTIDAD);
        txt_fecha = view.findViewById(R.id.TXTFECHA);
        guardar = view.findViewById(R.id.GUARDAR);

        //este es el spinner
        tipo_unidad = view.findViewById(R.id.TIPOUNIDAD);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item);
        adapter.add("Und");
        adapter.add("Kg");
        adapter.add("L");
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipo_unidad.setAdapter(adapter);
        //finn

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(getContext());
                if (!dbHelper.validarDB()) {
                    Toast.makeText(getContext(), "Por favor, cree la base de datos", Toast.LENGTH_SHORT).show();
                } else {
                    if (txt_codigo.getText().toString().equals("") || txt_nombre.getText().toString().equals("") || txt_marca.getText().toString().equals("") || txt_precio.getText().toString().equals("") || txt_cantidad.getText().toString().equals("") || txt_fecha.getText().toString().equals("")) {

                        Toast.makeText(getContext(), "RELLENE TODOS LOS CAMPOS", Toast.LENGTH_SHORT).show();

                    } else if (Double.parseDouble(txt_precio.getText().toString()) <= 0) {

                        Toast.makeText(getContext(), "EL PRECIO DEBE SER MAYOR A 0", Toast.LENGTH_SHORT).show();

                    } else if (Double.parseDouble(txt_cantidad.getText().toString()) <= 0) {

                        Toast.makeText(getContext(), "LA CANTIDAD DEBE SER MAYOR A 0", Toast.LENGTH_SHORT).show();

                    } else if (txt_codigo.getText().toString().length() > 14 || txt_codigo.getText().toString().length() < 12) {

                        Toast.makeText(getContext(), "EL CODIGO DEBE CONTENER 13 DIGITOS", Toast.LENGTH_SHORT).show();

                    } else {

                        String tcodigo = txt_codigo.getText().toString();
                        String tnombre = txt_nombre.getText().toString();
                        String tmarca = txt_marca.getText().toString();
                        double tprecio = Double.parseDouble(txt_precio.getText().toString());
                        double tcantidad = Double.parseDouble(txt_cantidad.getText().toString());
                        String ttipounidad = tipo_unidad.getSelectedItem().toString();
                        String tfecha = txt_fecha.getText().toString();

                        DBPRODUCTOS dbproductos = new DBPRODUCTOS(getContext());
                        productos = dbproductos.buscarPRODUCTO(tcodigo, tfecha);
                        if (productos != null) {
                            correcto = dbproductos.editarContacto(tcodigo, tnombre, tmarca, tprecio, tcantidad, ttipounidad, tfecha);
                            if (correcto) {
                                Toast.makeText(getContext(), "EDICION DE REGISTRO GUARDADO", Toast.LENGTH_SHORT).show();
                                limpiaredittext();
                            } else {
                                Toast.makeText(getContext(), "ERROR AL GUARDAR", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            dbproductos.insertarPRODUCTOS(tcodigo, tnombre, tmarca, tprecio, tcantidad, ttipounidad, tfecha);
                            Toast.makeText(getContext(), "NUEVO REGISTRO GUARDADO", Toast.LENGTH_SHORT).show();
                            limpiaredittext();
                        }
                    }
                }

            }
        });

        //codigo barra
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScanActivity();
            }
        });
//finn
//calendario bloquear fechas anteriores
        // Obtén la fecha actual
        Calendar calendar = Calendar.getInstance();
        long currentTimeInMillis = calendar.getTimeInMillis();
        // Establece la fecha mínima como la fecha actual
        CALENDARIO.setMinDate(currentTimeInMillis);
        CALENDARIO.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                // Almacena la fecha seleccionada en la variable
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                fechaSeleccionada = calendar.getTimeInMillis();
            }
        });
        txt_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ABRIR_C(view);
            }
        });

        BTNGUARDAR_F.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GUARDAR_F(view);
            }
        });
        //finn
    }

    public void GUARDAR_F(View view) {
        if (fechaSeleccionada != 0) {
            // Convierte la fecha almacenada a un formato legible
            SimpleDateFormat sdfOutput = new SimpleDateFormat("dd/MM/yyyy");
            String fechaFormateada = sdfOutput.format(new Date(fechaSeleccionada));
            // Establece el texto en el EditText
            txt_fecha.setText(fechaFormateada);
            ABRIR_C(view);
        } else {
            Toast.makeText(getContext(), "REGISTRE FECHA", Toast.LENGTH_SHORT).show();
        }
    }

    //activa o desacctiva elemtos
    public void ABRIR_C(View view) {
        if (VIEWCALENDARIO.getVisibility() == View.GONE) {
            VIEWCALENDARIO.setVisibility(View.VISIBLE);
            isFloatingWindowShown = true;
            disableScreenElements(true); // Desactivar elementos de la pantalla de abajo
        } else {
            VIEWCALENDARIO.setVisibility(View.GONE);
            isFloatingWindowShown = false;
            disableScreenElements(false); // Activar elementos de la pantalla de abajo
        }
    }

    //activa o desacctiva elemtos
    private void disableScreenElements(boolean disable) {
        // Desactivar o activar elementos de la pantalla de abajo según el valor de "disable"
        btnScan.setEnabled(!disable);

        txt_codigo.setEnabled(!disable);
        txt_nombre.setEnabled(!disable);
        txt_marca.setEnabled(!disable);
        txt_precio.setEnabled(!disable);
        txt_cantidad.setEnabled(!disable);
        txt_fecha.setEnabled(!disable);
        guardar.setEnabled(!disable);
        // Otros elementos que deseas desactivar/activar...
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
                txt_codigo.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    //limpiar_todo
    public void limpiaredittext() {
        txt_codigo.setText("");
        txt_nombre.setText("");
        txt_marca.setText("");
        txt_precio.setText("");
        txt_cantidad.setText("");
        txt_fecha.setText("");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}