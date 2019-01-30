package com.example.mgcma.mistareas_mgc;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity implements Animation.AnimationListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //Ocultamos la barra
        getSupportActionBar().hide();

        //Damos estilo al texto
        Typeface miFuente = Typeface.createFromAsset(getAssets(),"Vampire.ttf");
        TextView titulo_splash = (TextView) findViewById(R.id.texto_tarea);
        titulo_splash.setTypeface(miFuente);

        TextView titulo_mis = (TextView) findViewById(R.id.texto_mis);
        titulo_mis.setTypeface(miFuente);

        //Animaciones
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.animacion);
        Animation anim1 = AnimationUtils.loadAnimation(this,R.anim.animacion1);
        titulo_splash.startAnimation(anim);
        titulo_mis.startAnimation(anim1);
        anim1.setAnimationListener(this);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
        finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
