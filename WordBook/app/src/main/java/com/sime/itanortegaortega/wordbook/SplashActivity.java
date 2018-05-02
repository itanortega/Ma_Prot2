package com.sime.itanortegaortega.wordbook;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

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

        ExisteArchivoVersion existeArchivoVersion = new ExisteArchivoVersion();
        existeArchivoVersion.execute();
    }

    class ExisteArchivoVersion extends AsyncTask<Void, Integer, Boolean> {

        private boolean existe = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Txt_estado.setText("Verificando Versión.");
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String urlLocal = LOCAL + "version.json";
            URL urlL = null;
            boolean existe = false;

            CAFData data = CAFData.dataWithContentsOfFile(urlLocal);
            if(data != null){
                existe = true;
            }

            publishProgress(25);
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
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }else{
                DescargarArchivos descargarArchivos = new DescargarArchivos();
                descargarArchivos.execute();
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

            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
