package com.example.kursapp;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kursapp.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    ListView list;
    EditText add_name;
    EditText Stud;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    Database db;
    public ArrayList<String> listitem;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_gallery);
        list = findViewById(R.id.Students);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = new Database(this);
        listitem = new ArrayList<>();
        storeDataInArrays();
        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        setContentView(R.layout.fragment_gallery);
        Stud = findViewById(R.id.FIO);
        setContentView(binding.getRoot());
    }
    private void storeDataInArrays(){
        Cursor cursor = db.readAllData();
        if(cursor.getCount() != 0){
            while (cursor.moveToNext()){
                listitem.add(cursor.getString(0));
            }
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listitem);
            list.setAdapter(adapter);
        }

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
    public void addStudent(View v) {
        Database db = new Database(MainActivity.this);
        db.addStudent(Stud.getText().toString());
        Toast.makeText(MainActivity.this,"Student added",Toast.LENGTH_SHORT).show();
        Stud.setText("");
    }
}