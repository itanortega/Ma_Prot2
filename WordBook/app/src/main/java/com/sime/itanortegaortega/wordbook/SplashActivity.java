package com.sime.itanortegaortega.wordbook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static android.telecom.DisconnectCause.LOCAL;

public class SplashActivity extends AppCompatActivity {
    private final static String DOMAIN = "http://181.62.161.249:41062/www/api/";
    private static String LOCAL = "";

    ProgressBar Pb_Estado;
    TextView Txt_estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Pb_Estado = (ProgressBar) findViewById(R.id.Pb_Estado);
        Txt_estado = (TextView) findViewById(R.id.Txt_estado);
        Pb_Estado.setMax(100);

        LOCAL = getApplicationContext().getFilesDir().getAbsolutePath() + "/";

         File file = new File(LOCAL + "version.json"); file.delete();

        ExisteArchivoVersion existeArchivoVersion = new ExisteArchivoVersion();
        existeArchivoVersion.execute();
    }

    class ExisteArchivoVersion extends AsyncTask<Void, Integer, Boolean> {

        private boolean existe = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            String urlLocal = LOCAL + "version.json";
            URL urlL = null;
            boolean existe = false;

            CAFData data = CAFData.dataWithContentsOfFile(urlLocal);
            if(data != null){
                existe = true;
                for(int i=1; i<=100; i++){
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    publishProgress(i);
                }
            }

            return existe;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            Pb_Estado.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Boolean existe) {
            super.onPostExecute(existe);

            if(existe){
                Intent intent = new Intent(getBaseContext(), CategoriesActivity.class);
                startActivity(intent);
                finish();
            }else{

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if (networkInfo != null){
                    DescargarArchivos descargarArchivos = new DescargarArchivos();
                    descargarArchivos.execute();
                }else {
                    Handler handler =  new Handler(getBaseContext().getMainLooper());
                    handler.post( new Runnable(){
                        public void run(){
                            Toast.makeText(getBaseContext(), "Es necesario conectarse a internet cuando se abre la aplicación por primera vez.",Toast.LENGTH_LONG).show();
                        }
                    });

                    Intent salida=new Intent( Intent.ACTION_MAIN);
                    finish();
                }
            }
        }
    }

    class DescargarArchivos extends AsyncTask<Void, Integer, Boolean>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Txt_estado.setText("Descargando Archivos.");
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            ArrayList<String> imagenes = new ArrayList<String>();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            URL urlV = null;
            URL urlP = null;
            try {
                urlV = new URL(DOMAIN + "version.json");
                urlP = new URL(DOMAIN + "words.json");

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            CAFData versionData;
            CAFData wordsData;

            if(urlV != null && urlP !=null) {
                versionData = CAFData.dataWithContentsOfURL(urlV);
                wordsData = CAFData.dataWithContentsOfURL(urlP);

                try {
                    JSONObject jsonVersion = new JSONObject(versionData.toText());
                    JSONObject jsonWordsData = new JSONObject(wordsData.toText());

                    versionData.writeToFile(LOCAL + "version.json", true);
                    wordsData.writeToFile(LOCAL + "words.json", true);

                    JSONArray categoriasData = jsonWordsData.getJSONArray("categorias");
                    JSONObject categoriaData;
                    JSONArray palabrasData;
                    JSONObject palabraData;
                    for (int i = 0; i < categoriasData.length(); i++) {
                        try {
                            categoriaData = categoriasData.getJSONObject(i);
                            imagenes.add(categoriaData.getString("español").toString());
                            palabrasData = categoriaData.getJSONArray("words");
                            for (int j = 0; j< palabrasData.length(); j++) {
                                palabraData = palabrasData.getJSONObject(j);
                                imagenes.add(palabraData.getString("español").toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    for(int k = 0; k < imagenes.size(); k++){
                        CAFData data = null;
                        try {
                            data = CAFData.dataWithContentsOfURL(new URL(DOMAIN + "img/" + imagenes.get(k).toString() + ".png"));
                            data.writeToFile(LOCAL + "img/" + imagenes.toString() + ".png", true);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        publishProgress(Integer.parseInt(String.valueOf(99*k/imagenes.size()))+1);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            publishProgress(100);
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Pb_Estado.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            Txt_estado.setText("Proceso finalizado");
            Pb_Estado.setProgress(100);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(getBaseContext(), CategoriesActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
