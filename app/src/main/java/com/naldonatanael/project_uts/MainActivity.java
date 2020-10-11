package com.naldonatanael.project_uts;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Layanan> ListLayanan;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get data mahasiswa
        ListLayanan = new DaftarLayanan().LAYANAN;

        //recycler view
        recyclerView = findViewById(R.id.recycler_view_layanan);
        adapter = new RecyclerViewAdapter(MainActivity.this, ListLayanan);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.Home){
            startActivity(new Intent(this, MainActivity.class));
        }/*else if(item.getItemId()==R.id.Location){
            startActivity(new Intent(this, Location.class));
        }else if(item.getItemId()==R.id.AboutUs){
            startActivity(new Intent(this, About.class));
        }else if(item.getItemId()==R.id.ProfilSaya){
            startActivity(new Intent(this, ProfilSaya.class));
        }*/

        return true;
    }
}