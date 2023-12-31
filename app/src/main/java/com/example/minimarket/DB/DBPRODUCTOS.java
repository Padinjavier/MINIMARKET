package com.example.minimarket.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.minimarket.entidades.PRODUCTOS;
import com.example.minimarket.entidades.VENTAS;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DBPRODUCTOS extends DBHelper {

    Context context;

    public DBPRODUCTOS(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarPRODUCTOS(String codigo, String nombre, String marca, double precio, double cantidad, String tipounidad, String fecha) {
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
            values.put("TIPOUNIDAD", tipounidad);
            values.put("FECHA", fecha);
            ID = db.insert(TABLA_PRODUCTOS, null, values);
        } catch (Exception ex) {
            ex.toString();
        }
        return ID;
    }

public boolean editarContacto(String codigo, String nombre, String marca, double precio, double cantidad, String tipounidad, String fecha) {
    boolean correcto = false;
    DBHelper dbHelper = new DBHelper(context);
    SQLiteDatabase db = dbHelper.getWritableDatabase();

    try {
        ContentValues values = new ContentValues();
        values.put("NOMBRE", nombre);
        values.put("MARCA", marca);
        values.put("PRECIO", precio);
        values.put("CANTIDAD", cantidad);
        values.put("TIPOUNIDAD", tipounidad);

        int rowsAffected = db.update(TABLA_PRODUCTOS, values, "CODIGO=?", new String[]{codigo});
        correcto = rowsAffected > 0;
    } catch (Exception ex) {
        ex.printStackTrace();
        correcto = false;
    } finally {
        db.close();
    }

    return correcto;
}
    public boolean retirarPRODUCTO(String codigo, double cantidad, String fecha) {
        boolean correcto = false;
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("CANTIDAD", cantidad);

            int rowsAffected = db.update(TABLA_PRODUCTOS, values, "CODIGO = ? AND FECHA = ?", new String[]{codigo, fecha});
            correcto = rowsAffected > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }
    public PRODUCTOS buscarPRODUCTO(String codigo, String fecha) {

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        PRODUCTOS productos = null;
        Cursor cursorPRODUCTOS;
        cursorPRODUCTOS = db.rawQuery("SELECT * FROM " + TABLA_PRODUCTOS + " WHERE CODIGO = '" + codigo + "' AND FECHA = '" + fecha + "' LIMIT 1", null);
        if (cursorPRODUCTOS.moveToFirst()) {
            productos = new PRODUCTOS();
            productos.setId(cursorPRODUCTOS.getInt(0));
            productos.setNombre(cursorPRODUCTOS.getString(1));
            productos.setMarca(cursorPRODUCTOS.getString(2));
            productos.setPrecio(cursorPRODUCTOS.getDouble(3));
            productos.setCantidad(cursorPRODUCTOS.getDouble(4));
            productos.setTipounidad(cursorPRODUCTOS.getString(5));
            productos.setFecha(cursorPRODUCTOS.getString(6));
        }
        cursorPRODUCTOS.close();
        return productos;
    }

    //traer datos para tabla vista general
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<PRODUCTOS> mostrarPRODUTOS() {

        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaFormateada = fechaActual.format(formatoFecha);

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<PRODUCTOS> listaPRODUCTOS = new ArrayList<>();
        PRODUCTOS producto = null;
        Cursor cursorPRODUCTOS = null;
        cursorPRODUCTOS = db.rawQuery("SELECT * FROM " + TABLA_PRODUCTOS + " WHERE FECHA >= '" + fechaFormateada + "'", null);
        if (cursorPRODUCTOS.moveToFirst()) {
            do {
                producto = new PRODUCTOS();
                producto.setId(cursorPRODUCTOS.getInt(0));
                producto.setCodigo(cursorPRODUCTOS.getString(1));
                producto.setNombre(cursorPRODUCTOS.getString(2));
                producto.setMarca(cursorPRODUCTOS.getString(3));
                producto.setPrecio(cursorPRODUCTOS.getFloat(4));
                producto.setCantidad(cursorPRODUCTOS.getFloat(5));
                producto.setTipounidad(cursorPRODUCTOS.getString(6));
                producto.setFecha(cursorPRODUCTOS.getString(7));
                listaPRODUCTOS.add(producto);
            } while (cursorPRODUCTOS.moveToNext());
        }
        cursorPRODUCTOS.close();
        return listaPRODUCTOS;
    }

    //traer datos para tabla vista vencidos
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<PRODUCTOS> mostrarPRODUTOSVENCIDOS() {

        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaFormateada = fechaActual.format(formatoFecha);

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<PRODUCTOS> listaPRODUCTOS = new ArrayList<>();
        PRODUCTOS producto = null;
        Cursor cursorPRODUCTOS = null;
        cursorPRODUCTOS = db.rawQuery("SELECT * FROM " + TABLA_PRODUCTOS + " WHERE FECHA < '" + fechaFormateada + "'", null);
        if (cursorPRODUCTOS.moveToFirst()) {
            do {
                producto = new PRODUCTOS();
                producto.setId(cursorPRODUCTOS.getInt(0));
                producto.setCodigo(cursorPRODUCTOS.getString(1));
                producto.setNombre(cursorPRODUCTOS.getString(2));
                producto.setMarca(cursorPRODUCTOS.getString(3));
                producto.setPrecio(cursorPRODUCTOS.getFloat(4));
                producto.setCantidad(cursorPRODUCTOS.getFloat(5));
                producto.setTipounidad(cursorPRODUCTOS.getString(6));
                producto.setFecha(cursorPRODUCTOS.getString(7));
                listaPRODUCTOS.add(producto);
            } while (cursorPRODUCTOS.moveToNext());
        }
        cursorPRODUCTOS.close();
        return listaPRODUCTOS;
    }

    public boolean eliminarPRODUCTO(int id) {

        boolean correcto = false;

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLA_PRODUCTOS + " WHERE id = '" + id + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }
        return correcto;
    }

}







