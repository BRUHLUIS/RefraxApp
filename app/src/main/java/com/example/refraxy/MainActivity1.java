package com.example.refraxy;

import static com.example.refraxy.R.id.toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.refraxy.Fragments.ConnectFragment;
import com.example.refraxy.Fragments.FixturesFragment;
import com.example.refraxy.Fragments.HerramientaFragment;
import com.example.refraxy.Fragments.MainFragment;
import com.example.refraxy.Fragments.PesoFragment;
import com.example.refraxy.Fragments.ProfileFragment;
import com.example.refraxy.Fragments.TableFragment;
import com.google.android.material.navigation.NavigationView;


public class MainActivity1 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle =  new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        if (savedInstanceState == null) {
            loadFragment(new MainFragment());
            navigationView.setCheckedItem(R.id.nav_home);
        }

    }



    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.nav_home:
                loadFragment(new MainFragment());
                break;
            case R.id.nav_carrito:
                loadFragment(new ConnectFragment());
                break;
            case R.id.nav_promocion:
                loadFragment(new FixturesFragment());
                break;
            case R.id.nav_inventario:
                loadFragment(new TableFragment());
                break;
            case R.id.nav_profile:
                loadFragment(new ProfileFragment());
                menuItem.setChecked(true);
                break;
            case R.id.nav_herramientas:
                loadFragment(new HerramientaFragment());
                menuItem.setChecked(true);
                break;
            case R.id.nav_peso:
                loadFragment(new PesoFragment());
                menuItem.setChecked(true);
                break;


                default:
                    break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void loadFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
    }


}
