package com.example.projeto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private String user;
    private FragmentManager fragmentManager;
    public static final String USER="Username";
    public static final int ADD=100, EDIT=200, DELETE=300;
    public static final String OP_CODE="operacao";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navView);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,
                toolbar, R.string.open, R.string.close);
        toggle.syncState();
        drawer.addDrawerListener(toggle);

        carregarCabecalho();
        fragmentManager = getSupportFragmentManager();
        carregarFragmentoInicial();

        navigationView.setNavigationItemSelectedListener(this);
    }

    private boolean carregarFragmentoInicial(){
        Menu menu= navigationView.getMenu();
        MenuItem item = menu.getItem(0);
        item.setChecked(true);
        return onNavigationItemSelected(item);
    }

    private void carregarCabecalho() {
        Intent intent=getIntent();
        user=intent.getStringExtra(LoginActivity.USER);
        SharedPreferences sharedPreUser=getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);

        if (user != null) {
            SharedPreferences.Editor editorUser=sharedPreUser.edit();
            editorUser.putString(USER, user);
            editorUser.apply();
        }
        else{
            user=sharedPreUser.getString(USER,"Sem username");
        }


        View hView=navigationView.getHeaderView(0);
        TextView tvEmail=hView.findViewById(R.id.tvUser);
        tvEmail.setText(user);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment=null;
        if(item.getItemId()==R.id.navArtigos){
            fragment = new ListaArtigoFragment();
            setTitle(item.getTitle());
        }

//        else if(item.getItemId()==R.id.navEmail) {
//            enviarEmail();
//        }

        if (fragment != null)
            fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void enviarEmail() {

//        Intent intent=new Intent(Intent.ACTION_SEND);
//        intent.setType("message/rcf882");
//        intent.putExtra(intent.EXTRA_EMAIL,email);
//
//        if (intent.resolveActivity(getPackageManager())!=null) {
//            startActivity(intent);
//        }
//        else {
            Toast.makeText(this, "Não tem email configurado", Toast.LENGTH_LONG).show();
//        }
    }
}