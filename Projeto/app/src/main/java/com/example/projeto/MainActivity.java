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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projeto.adaptadores.ListaLinhasCarrinhoAdaptador;
import com.example.projeto.listeners.LinhasCarrinhoListener;
import com.example.projeto.modelo.LinhaCarrinho;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private FragmentManager fragmentManager;
    private MenuItem selectedItem;
    public static final int ADD = 100, EDIT = 200, DELETE = 300;

    public static final String OP="OPERACAO";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);

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

    private boolean carregarFragmentoInicial() {
        sharedPreferences = getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("ROLE", "");
        if ("cliente".equals(role)) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.menu_main);
        } else {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.menu_admin);
        }

        Menu menu = navigationView.getMenu();
        MenuItem item = menu.getItem(0);
        item.setChecked(true);

        // Chamar onNavigationItemSelected para carregar o fragmento correspondente
        return onNavigationItemSelected(item);
    }

    private void carregarCabecalho() {
        sharedPreferences = getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("EMAIL", "email");

        View View = navigationView.getHeaderView(0);
        TextView tvEmail = View.findViewById(R.id.tvEmail);
        tvEmail.setText(email);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        sharedPreferences = getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("USER_ID", 0);
        Fragment fragment = null;
        if (item.getItemId() == R.id.navInicio) {
            fragment = new PaginaPrincipalFragment();
        } else if (item.getItemId() == R.id.navArtigos) {
            fragment = new ListaArtigoFragment();
        } else if (item.getItemId() == R.id.navCategorias) {
            fragment = new ListaCategoriaFragment();
        } else if (item.getItemId() == R.id.navIvas) {
            fragment = new ListaIvaFragment();
        } else if (item.getItemId() == R.id.navCarrinho) {
            fragment = new ListaLinhasCarrinhoFragment();
        } else if (item.getItemId() == R.id.navCompras) {
            fragment = new ListaCarrinhoFragment();
        } else if (item.getItemId() == R.id.navFaturas) {
            fragment = new ListaFaturaFragment();
        } else if (item.getItemId() == R.id.navLogout) {
            SharedPreferences sharedPreferences = getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("TOKEN");
            editor.remove("EMAIL");
            editor.remove("ROLE");
            editor.remove("USER_ID");
            editor.remove("CARRINHO_ID");
            editor.apply();
            Toast.makeText(this, "Sessão encerrada", Toast.LENGTH_SHORT).show();
            Intent Intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(Intent);
            finish();
        }

        if (fragment != null) {
            if (selectedItem != null) {
                selectedItem.setChecked(false);
            }

            fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();

            item.setChecked(true);
            selectedItem = item;

            setTitle(item.getTitle());
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
}