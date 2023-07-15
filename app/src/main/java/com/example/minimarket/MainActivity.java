package com.example.minimarket;


import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minimarket.DB.DBHelper;
import com.example.minimarket.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;


import java.io.File;


public class MainActivity extends AppCompatActivity {

    //creacion de variables objeto
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch swDarkMode;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private static final String PREFS_NAME = "MyPrefs";
    private static final String THEME_KEY = "Theme";
    private AppBarConfiguration mAppBarConfiguration;
    ActivityMainBinding binding;

    Button eliminar_bd;
    TextView crear_db;
    private static final int REQUEST_CODE_CONFIRM_PATTERN = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_venta, R.id.nav_ingreso)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        // asignamos las variables de entorno
        MainActivity ma = MainActivity.this;
        swDarkMode = findViewById(R.id.switch4);
        sp = ma.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        editor = sp.edit();
        // Establecer el estado del interruptor y configurar el listener
        boolean isDarkMode = sp.getBoolean(THEME_KEY, false);
        swDarkMode.setChecked(isDarkMode);
        swDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean(THEME_KEY, isChecked);
                editor.apply();
                setDayNight(isChecked);
            }
        });

        // Establecer el tema según el estado almacenado en las preferencias compartidas
        setDayNight(isDarkMode);
        eliminar_bd = findViewById(R.id.ELIMINARDB);
        crear_db = findViewById(R.id.LLENARDATOS);

        //pedir patron para eliminar
        eliminar_bd = findViewById(R.id.ELIMINARDB);
        eliminar_bd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticatePattern();
            }
        });

        validarDBmain();
    }

    private void authenticatePattern() {
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        if (keyguardManager.isKeyguardSecure()) {
            Intent intent = keyguardManager.createConfirmDeviceCredentialIntent("Desbloquear para continuar", "Ingrese su patrón");
            if (intent != null) {
                startActivityForResult(intent, REQUEST_CODE_CONFIRM_PATTERN);
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CONFIRM_PATTERN) {
            if (resultCode == RESULT_OK) {
                // El patrón de desbloqueo fue ingresado correctamente
                eliminar_bd.setEnabled(true);
                eliminarBaseDeDatos();

            } else {
                // El patrón de desbloqueo no fue ingresado correctamente
            }
        }

    }

    public void eliminarBaseDeDatos() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("¿DESEA ELIMINAR LA BASE DE DATOS?")
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //accion si
                        boolean eliminadaa = deleteDatabase(DBHelper.DATABASE_NOMBRE);
                        if (eliminadaa) {
                            Toast.makeText(MainActivity.this, "Base de datos eliminada", Toast.LENGTH_SHORT).show();
                            validarDBmain();
                            restartApp();
                        } else {
                            Toast.makeText(MainActivity.this, "Error al eliminar la base de datos", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //accion no
                    }
                }).show();
    }

    public void validarDBmain() {
        boolean databaseExists = checkDatabaseExists();
        boolean hasData = false;
        DBHelper dbHelper = new DBHelper(MainActivity.this);
        String tabla = (DBHelper.TABLA_PRODUCTOS);
        if (databaseExists) {
            // Verificar si alguna tabla tiene datos
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + tabla, null);
            if (cursor != null && cursor.moveToFirst()) {
                int count = cursor.getInt(0);
                hasData = count > 0;
                cursor.close();
            }
            if (hasData) {
                // La tabla tiene datos
                crear_db.setVisibility(View.GONE);
                eliminar_bd.setVisibility(View.VISIBLE);
            } else {
                // La tabla no tiene datos
                crear_db.setVisibility(View.VISIBLE);
                eliminar_bd.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Por favor, ingrese productos", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void restartApp() {
        finish();

        // Crear un nuevo Intent para iniciar la actividad principal
        Intent intent = new Intent(this, MainActivity.class);

        // Establecer flags para borrar la pila de actividades y crear una nueva tarea
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Iniciar la actividad principal
        startActivity(intent);
    }

    private boolean checkDatabaseExists() {
        File databaseFile = getApplicationContext().getDatabasePath(DBHelper.DATABASE_NOMBRE);
        return databaseFile.exists();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setDayNight(boolean isDarkMode) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            swDarkMode.setThumbDrawable(getResources().getDrawable(R.drawable.icons8_luna, getTheme()));

        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            swDarkMode.setThumbDrawable(getResources().getDrawable(R.drawable.icons8_sol, getTheme()));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Restaurar el tema al reanudar la aplicación
        boolean isDarkMode = sp.getBoolean(THEME_KEY, false);
        setDayNight(isDarkMode);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}