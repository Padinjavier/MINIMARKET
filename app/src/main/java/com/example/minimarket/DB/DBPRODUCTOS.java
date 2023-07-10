package com.example.minimarket.DB;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.minimarket.entidades.PRODUCTOS;

import java.util.ArrayList;

public class DBPRODUCTOS extends DBHelper {

    Context context;

    public DBPRODUCTOS(@Nullable Context context) {
        super(context);
        this.context = context;
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

    //traer datos para tabla vista general
    public ArrayList<PRODUCTOS> mostrarPRODUTOS() {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<PRODUCTOS> listaPRODUCTOS = new ArrayList<>();
        PRODUCTOS producto = null;
        Cursor cursorPRODUCTOS = null;

        cursorPRODUCTOS = db.rawQuery("SELECT * FROM " + TABLA_PRODUCTOS, null);

        if (cursorPRODUCTOS.moveToFirst()) {
            do {
                producto = new PRODUCTOS();
                producto.setId(cursorPRODUCTOS.getInt(0));
                producto.setCodigo(cursorPRODUCTOS.getString(1));
                producto.setNombre(cursorPRODUCTOS.getString(2));
                producto.setPrecio(cursorPRODUCTOS.getFloat(3));
                producto.setCantidad(cursorPRODUCTOS.getFloat(4));

                listaPRODUCTOS.add(producto);
            } while (cursorPRODUCTOS.moveToNext());
        }
        cursorPRODUCTOS.close();

        return listaPRODUCTOS;
    }
}







