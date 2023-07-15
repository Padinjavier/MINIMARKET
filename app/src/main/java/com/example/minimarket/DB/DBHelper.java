package com.example.minimarket.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 12;
    public static final String DATABASE_NOMBRE = "tienda.db";
    public static final String TABLA_PRODUCTOS = "t_productos";
    public static final String TABLA_VENTAS = "t_ventas";

    public DBHelper(@Nullable Context context) {

        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLA_PRODUCTOS + "(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "CODIGO TEXT NOT NULL," +
                "NOMBRE TEXT NOT NULL," +
                "MARCA TEXT NOT NULL," +
                "PRECIO DOUBLE NOT NULL," +
                "CANTIDAD DOUBLE NOT NULL," +
                "TIPOUNIDAD TEXT NOT NULL," +
                "FECHA DATE NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLA_VENTAS + "(" +
                "IDV INTEGER PRIMARY KEY AUTOINCREMENT," +
                "CODIGOV TEXT NOT NULL," +
                "NOMBREV TEXT NOT NULL," +
                "MARCAV TEXT NOT NULL," +
                "PRECIOV DOUBLE NOT NULL," +
                "CANTIDADV DOUBLE NOT NULL," +
                "TIPOUNIDADV TEXT NOT NULL," +
                "TOTALPAGOV DOUBLE NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLA_PRODUCTOS);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLA_VENTAS);
        onCreate(sqLiteDatabase);
    }
}
