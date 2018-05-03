package com.sime.itanortegaortega.wordbook;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class AboutActivity extends AppCompatActivity {
    private final static String DOMAIN = "http://181.62.161.249:41062/www/api/";
    private static String LOCAL = "";

    Toolbar toolbar;
    LinearLayout Pnl_Actualizacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        LOCAL = getApplicationContext().getFilesDir().getAbsolutePath() + "/";

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null){
            CompararVersion compararVersion = new CompararVersion();
            compararVersion.execute();
        }else {
            Handler handler =  new Handler(getBaseContext().getMainLooper());
            handler.post( new Runnable(){
                public void run(){
                    Toast.makeText(getBaseContext(), "No fué posible buscar actualizaciones porque no hay conexión a internet.",Toast.LENGTH_LONG).show();
                }
            });
        }

        toolbar = (Toolbar) findViewById(R.id.id_tb_toolbar);
        Pnl_Actualizacion = (LinearLayout) findViewById(R.id.Pnl_Actualizacion);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("About of ... - Acerca de ...");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void actualizar(View view) {
        File file = new File(LOCAL + "version.json");
        file.delete();

        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
        finish();
    }


    class CompararVersion extends AsyncTask<Void, Integer, Boolean> {

        final String urlWeb = DOMAIN + "version.json";
        final String urlLocal = LOCAL + "version.json";
        boolean iguales = false;

        @Override
        protected Boolean doInBackground(Void... voids) {

            URL urlW = null;

            try {
                urlW = new URL(urlWeb);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            CAFData web;
            CAFData local;

            if(urlW != null){
                web = CAFData.dataWithContentsOfURL(urlW);
                local = CAFData.dataWithContentsOfFile(urlLocal);

                try {
                    JSONObject webJson = new JSONObject(web.toText());
                    JSONObject localJson = new JSONObject(local.toText());


                    if(webJson.toString().equals(localJson.toString())){
                        iguales = true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return iguales;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if(!aBoolean){
                runOnUiThread(new Runnable(){

                    @Override
                    public void run(){
                        Pnl_Actualizacion.setVisibility(View.VISIBLE);
                    }
                });
            }
        }
    }
}
