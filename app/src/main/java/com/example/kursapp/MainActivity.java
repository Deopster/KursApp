package com.example.kursapp;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.kursapp.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    AuthDatabase logDB;
    static DatabaseAdapter databaseAdapter;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private static Context context;
    String table;
    String k = "0";
    TextView Nick,work,date,salary,g;
    TextView log,pas;
    LinearLayout block;
    Button add1,logout,logN,Button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.context = getApplicationContext();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                g=findViewById(R.id.text);
                System.out.println(g.getText());
                System.out.println(k);
                if(k.equals("android")) {
                    showDialog(g.getText().toString());
                }else{
                    Toast.makeText(context, "Недостаточно прав", Toast.LENGTH_SHORT).show();
                }
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }
    public void reg1(View view){
        EditText nik, pas1,pas2,key;
        Button addr;
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        dialog.setContentView(R.layout.reg);
        params.copyFrom(dialog.getWindow().getAttributes());
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        nik = dialog.findViewById(R.id.log);
        pas1 = dialog.findViewById(R.id.Pas1);
        pas2 = dialog.findViewById(R.id.Pass2);
        key = dialog.findViewById(R.id.allow);
        addr = dialog.findViewById(R.id.add12);
        addr.setOnClickListener(new View.OnClickListener() {;
            @Override
            public void onClick(View v) {
                if (nik.getText().toString().isEmpty()) {
                    nik.setError("Введите ФИО");
                }else if(pas1.getText().toString().isEmpty()) {
                    pas1.setError("Введите Пароль");
                }else if(!pas1.getText().toString().equals(pas2.getText().toString())) {
                    pas2.setError("Пароли не совпадают");
                } else {
                    logDB=new AuthDatabase(context);
                    logDB.addUser(nik.getText().toString(),pas1.getText().toString(),key.getText().toString());
                    dialog.cancel();
                }
            }
        });



    }
    public void showDialog(String raz) {
        final EditText title, des;
        Button close;
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        dialog.setContentView(R.layout.dialog);
        params.copyFrom(dialog.getWindow().getAttributes());
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        if (raz.equals("Список сотрудников Подбора персонала")){
            table="Data";
        }else if (raz.equals("Список сотрудников Учёта персонала")){
            table="Data1";
        }else if (raz.equals("Список сотрудников обучения персонала")) {
            table="Data2";
        }
        Nick = dialog.findViewById(R.id.log);
        work = dialog.findViewById(R.id.work);
        date = dialog.findViewById(R.id.Pas1);
        salary = dialog.findViewById(R.id.Pass2);
        add1 = dialog.findViewById(R.id.add12);
        add1.setOnClickListener(new View.OnClickListener() {;
            @Override
            public void onClick(View v) {
                if (Nick.getText().toString().isEmpty()) {
                    Nick.setError("Введите ФИО");
                }else if(work.getText().toString().isEmpty()) {
                    work.setError("Введите должность");
                }else if(date.getText().toString().isEmpty()) {
                    date.setError("Введите дату");
                }else if(salary.getText().toString().isEmpty()) {
                    salary.setError("Введите зарплату");
                }else {
                    databaseAdapter = new DatabaseAdapter(context);
                    if(k=="android") {
                        databaseAdapter.Add(Nick.getText().toString(), work.getText().toString(), date.getText().toString(), salary.getText().toString(), table);
                    }else{
                        Toast.makeText(context, "Недостаточно прав", Toast.LENGTH_SHORT).show();
                    }
                    dialog.cancel();
                }
            }
        });
        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public static Context getAppContext() {
        return MainActivity.context;
    }
    public void Log(View view) {
        logout=findViewById(R.id.logOut);
        log = findViewById(R.id.Getlogin);
        pas = findViewById(R.id.GetPassword);
        logN = findViewById(R.id.logIN);
        block= findViewById(R.id.block);
        Button2 = findViewById(R.id.button2);
        if (log.getText().toString().isEmpty()) {
            log.setError("Введите логин");
        } else if (pas.getText().toString().isEmpty()) {
            pas.setError("Введите пароль");
        } else {
            logDB=new AuthDatabase(context);
            int temp = logDB.auth(log.getText().toString(),pas.getText().toString());
            if (temp==0) {
                k=logDB.getk();
                System.out.println(k);
                block.setVisibility(View.GONE);
                logout.setVisibility(View.VISIBLE);
                pas.setVisibility(View.GONE);
            }
            }
        }
    public void out(View view) {
        block.setVisibility(View.VISIBLE);
        pas.setVisibility(View.VISIBLE);
        k="0";
        logDB.setData();
    }
}