package com.sime.itanortegaortega.wordbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.io.File;

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
}
