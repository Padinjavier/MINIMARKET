package com.example.minimarket;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.minimarket.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    //creacion de variables objeto

    private Switch swDarkMode;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private static final String PREFS_NAME = "MyPrefs";
    private static final String THEME_KEY = "Theme";
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;



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
        swDarkMode = (Switch) findViewById(R.id.switch4);
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
    }
    private void setDayNight(boolean isDarkMode) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            swDarkMode.setText("MODO");
            swDarkMode.setThumbDrawable(getResources().getDrawable(R.drawable.icons8_luna));

        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            swDarkMode.setText("MODO");
            swDarkMode.setThumbDrawable(getResources().getDrawable(R.drawable.icons8_sol));
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