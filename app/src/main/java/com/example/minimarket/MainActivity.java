package com.example.minimarket;


import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
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

    Button crear_db, eliminar_bd;

    private static final int REQUEST_CODE_CONFIRM_PATTERN = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
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
        crear_db = findViewById(R.id.CREARDB);
        crear_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(MainActivity.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                if (db != null) {
                    Toast.makeText(MainActivity.this, "BASE DE DATOS CREADA", Toast.LENGTH_SHORT).show();
                    validarDB();
                } else {
                    Toast.makeText(MainActivity.this, "ERROR AL CREAR BASE DE DATOS", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //pedir patron para eliminar
        eliminar_bd = findViewById(R.id.ELIMINARDB);
        eliminar_bd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticatePattern();
            }
        });

    }

    private void authenticatePattern() {
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        if (keyguardManager.isKeyguardSecure()) {
            Intent intent = keyguardManager.createConfirmDeviceCredentialIntent("Desbloquea para continuar", "Ingresa tu patrón");
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
                // Aquí puedes realizar las acciones que desees después de la autenticación exitosa
                // Por ejemplo, puedes habilitar el botón que deseas
                eliminar_bd.setEnabled(true);
                eliminarBaseDeDatos();

            } else {
                // El patrón de desbloqueo no fue ingresado correctamente
                // Aquí puedes manejar el caso cuando la autenticación falla
            }
        }

    }

    public void eliminarBaseDeDatos() {
        boolean eliminadaa = deleteDatabase(DBHelper.DATABASE_NOMBRE);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("¿DESEA ELIMINAR LA BASE DE DATOS?")
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //accion si
                        if (eliminadaa) {
                            Toast.makeText(MainActivity.this, "Base de datos eliminada", Toast.LENGTH_SHORT).show();
                            validarDB();
//                            vistavista();
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


    public void validarDB() {
        boolean databaseExists = checkDatabaseExists();
        if (databaseExists) {
            crear_db.setVisibility(View.GONE);
            eliminar_bd.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(MainActivity.this, "Cree la base de datos", Toast.LENGTH_SHORT).show();
            crear_db.setVisibility(View.VISIBLE);
            eliminar_bd.setVisibility(View.GONE);


        }
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