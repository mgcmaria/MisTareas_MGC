package com.example.mgcma.mistareas_mgc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
    }

    public void crearCuenta(View view){

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_crearcuenta,(ViewGroup) findViewById(R.id.toast_layout_crear));

        ImageView image = (ImageView) layout.findViewById(R.id.image);
        image.setImageResource(R.mipmap.herramientas);
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText("Funcionalidad no disponible");

        Toast toast = new Toast(getApplicationContext());
        //toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

    }

    public void login (View view){
        EditText usuario = (EditText) findViewById(R.id.editText);
        EditText pass = (EditText) findViewById(R.id.editText2);

        if(usuario.getText().toString().equalsIgnoreCase("admin") && pass.getText().toString().equalsIgnoreCase("admin")){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
        } else {
            Toast toast = Toast.makeText(this,"Usuario y/o contraseña incorrectos",Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
