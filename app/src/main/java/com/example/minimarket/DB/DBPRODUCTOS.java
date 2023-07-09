package com.example.minimarket.DB;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.minimarket.ui.ingreso.IngresoFragment;

public class DBPRODUCTOS extends DBHelper {

Context context;
    public DBPRODUCTOS(@Nullable Context context) {
        super(context);
        this.context=context;
    }
    public long insertarPRODUCTOS(String codigo, String nombre, String marca, double precio, double cantidad, String fecha) {
        long ID = 0;
        try {

            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("CODIGO", codigo);
            values.put("NOMBRE", nombre);
            values.put("MARCA", marca);
            values.put("PRECIO", precio);
            values.put("CANTIDAD", cantidad);
            values.put("FECHA", fecha);
            ID = db.insert(TABLA_PRODUCTOS, null, values);
        } catch (Exception ex) {
            ex.toString();
        }
        return ID;
    }
}
