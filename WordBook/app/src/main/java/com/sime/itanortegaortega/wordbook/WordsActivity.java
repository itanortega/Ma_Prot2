package com.sime.itanortegaortega.wordbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WordsActivity extends AppCompatActivity {

    private static String LOCAL = "";

    int id_categoria;
    RecyclerView Rv_Palabras;
    LinearLayoutManager linearLayoutManager;
    private PalabrasAdapter palabrasAdapter;
    private ExecutorService queue = Executors.newSingleThreadExecutor();
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);

        toolbar = (Toolbar) findViewById(R.id.id_tb_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Words - Palabras");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        id_categoria = (int) getIntent().getIntExtra("id_categoria", 0);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);

        Rv_Palabras = (RecyclerView) findViewById(R.id.Rv_Palabras);
        Rv_Palabras.setLayoutManager(linearLayoutManager);

        LOCAL = getApplicationContext().getFilesDir().getAbsolutePath() + "/";

        cargarPalabras();
    }

    public void cargarPalabras(){
        Runnable thread = new Runnable() {
            @Override
            public void run() {
                String strUrl = LOCAL + "words.json";
                CAFData data = null;

                data = CAFData.dataWithContentsOfFile(strUrl);

                try {
                    JSONObject root = new JSONObject(data.toText());
                    JSONArray catJson = root.getJSONArray("categorias");
                    JSONObject categoriaJson = catJson.getJSONObject(id_categoria);
                    JSONArray palabras = categoriaJson.getJSONArray("words");

                    final JSONArray palabrasJson = palabras;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayList<Palabra> palabras = new ArrayList<>();
                            Palabra p;
                            JSONObject palabraJson;

                            for(int i=0; i<palabrasJson.length(); i++){
                                try {
                                    palabraJson = palabrasJson.getJSONObject(i);
                                    p = new Palabra(i, palabraJson.getString("inglés").toString(), palabraJson.getString("español").toString(), LOCAL + "img/" + palabraJson.getString("español").toString() + ".png");
                                    palabras.add(p);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            palabrasAdapter = new PalabrasAdapter(palabras, getApplicationContext());
                            Rv_Palabras.setAdapter(palabrasAdapter);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        queue.execute(thread);
    }
}