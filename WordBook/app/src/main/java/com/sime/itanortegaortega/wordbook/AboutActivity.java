package com.sime.itanortegaortega.wordbook;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class AboutActivity extends AppCompatActivity {
    private final static String DOMAIN = "http://181.62.161.249:41062/www/api/";
    private static String LOCAL = "";

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        toolbar = (Toolbar) findViewById(R.id.id_tb_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("About of ... - Acerca de ...");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void actualizar(View view) {
        LOCAL = getApplicationContext().getFilesDir().getAbsolutePath() + "/";

        File file = new File(LOCAL + "version.json");
        file.delete();

        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
        finish();
    }


    class CompararVersion extends AsyncTask<Void, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {

            final String urlWeb = DOMAIN + "version.json";
            final String urlLocal = LOCAL + "version.json";
            boolean iguales = false;

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

            if(aBoolean){
                /*Txt_estado.setText("Proceso finalizado");
                Pb_Estado.setProgress(100);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                finish();*/
            }
        }
    }
}
