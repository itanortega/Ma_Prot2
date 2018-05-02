package com.sime.itanortegaortega.wordbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CategoriesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static String LOCAL = "";
    private CategoriasAdapter adapter;
    private GridView Gv_Categorias;
    private ExecutorService queue = Executors.newSingleThreadExecutor();
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        toolbar = (Toolbar) findViewById(R.id.id_tb_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Categories - Categorías");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        Gv_Categorias =  (GridView)findViewById(R.id.Gv_Categorias);
        Gv_Categorias.setOnItemClickListener(this);

        LOCAL = getApplicationContext().getFilesDir().getAbsolutePath() + "/";

        cargarListaCategorias();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.iteAcercaDe:
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void cargarListaCategorias() {
        final String strUrl_Local = LOCAL + "words.json";

        final Runnable thread = new Runnable() {
            @Override
            public void run() {
                CAFData data = null;
                data = CAFData.dataWithContentsOfFile(strUrl_Local);
                try {
                    JSONObject root = new JSONObject(data.toText());
                    JSONArray catJson = root.getJSONArray("categorias");

                    final JSONArray categoriasJson = catJson;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayList<Categoria> categorias = new ArrayList<>();
                            Categoria c;
                            JSONObject categoriaJson;

                            for (int i = 0; i < categoriasJson.length(); i++) {
                                try {
                                    categoriaJson = categoriasJson.getJSONObject(i);
                                    c = new Categoria(i, categoriaJson.getString("nombre").toString(), "", "");
                                    categorias.add(c);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            final ArrayList<Categoria> categoriasV = categorias;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter = new CategoriasAdapter(CategoriesActivity.this, categoriasV);
                                    Gv_Categorias.setAdapter(adapter);
                                }
                            });
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        queue.execute(thread);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) { Log.d("debugapp", "clic");
        Categoria item = (Categoria) parent.getItemAtPosition(position);

        Intent intent = new Intent(this, WordsActivity.class);
        intent.putExtra("id_categoria", item.getId());
        startActivity(intent);
    }
}