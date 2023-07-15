package com.example.minimarket.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.minimarket.entidades.VENTAS;

import java.util.ArrayList;

public class DBVENTAS extends DBHelper{
    Context context;
    public DBVENTAS(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarPRODUCTOVENTAS(String Vcodigo,String Vnombre, String Vmarca, double Vprecio, double Vcantidad,String Vtipounidad, double Vtotalpago) {
        long ID = 0;
        try {

            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Toast.makeText(context, Vcodigo+"|" +Vnombre+"|"+Vmarca+"|"+Vprecio+"|"+Vcantidad+"|"+Vtipounidad+"|"+Vtotalpago , Toast.LENGTH_LONG).show();

            ContentValues valuesv = new ContentValues();
            valuesv.put("CODIGOV", Vcodigo);
            valuesv.put("NOMBREV", Vnombre);
            valuesv.put("MARCAV", Vmarca);
            valuesv.put("PRECIOV", Vprecio);
            valuesv.put("CANTIDADV", Vcantidad);
            valuesv.put("TIPOUNIDADV", Vtipounidad);
            valuesv.put("TOTALPAGOV", Vtotalpago);
            ID = db.insert(TABLA_VENTAS, null, valuesv);
        } catch (Exception ex) {
            ex.printStackTrace(); // Imprime la traza de la excepción en el registro de la consola
            Toast.makeText(context,  ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return ID;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<VENTAS> mostrarPRODUTOSVENDIDOS() {

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<VENTAS> listaPRODUCTOSVENDIDOS = new ArrayList<>();
        VENTAS ventas = null;
        Cursor cursorVENTAS = null;
        cursorVENTAS = db.rawQuery("SELECT * FROM " + TABLA_VENTAS  +"", null);
        if (cursorVENTAS.moveToFirst()) {
            do {
                ventas = new VENTAS();
                ventas.setId_venta(cursorVENTAS.getInt(0));
                ventas.setCodigo_venta(cursorVENTAS.getString(1));
                ventas.setNombre_venta(cursorVENTAS.getString(2));
                ventas.setMarca_venta(cursorVENTAS.getString(3));
                ventas.setPrecio_venta(cursorVENTAS.getDouble(4));
                ventas.setCantidad_venta(cursorVENTAS.getDouble(5));
                ventas.setTipounidad_venta(cursorVENTAS.getString(6));
                ventas.setTotalpago_venta(cursorVENTAS.getDouble(7));
                listaPRODUCTOSVENDIDOS.add(ventas);
            } while (cursorVENTAS.moveToNext());
        }
        cursorVENTAS.close();
        return listaPRODUCTOSVENDIDOS;
    }
}
