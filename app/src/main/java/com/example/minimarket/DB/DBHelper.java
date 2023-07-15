package com.example.minimarket.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.minimarket.MainActivity;

import java.io.File;

public class DBHelper extends SQLiteOpenHelper {
    private Context context;
    private static final int DATABASE_VERSION = 14;
    public static final String DATABASE_NOMBRE = "tienda.db";
    public static final String TABLA_PRODUCTOS = "t_productos";
    public static final String TABLA_VENTAS = "t_ventas";

    public DBHelper(@Nullable Context context) {

        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
        this.context = context;
    }
    public boolean validarDB() {
        boolean databaseExists = checkDatabaseExists();
        return databaseExists;
    }
    private boolean checkDatabaseExists() {
        File databaseFile = context.getDatabasePath(DBHelper.DATABASE_NOMBRE);
        return databaseFile.exists();
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
                "FECHAV DATE NOT NULL," +
                "TOTALPAGOV DOUBLE NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLA_PRODUCTOS);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLA_VENTAS);
        onCreate(sqLiteDatabase);
    }
}
