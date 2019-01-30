package com.example.mgcma.mistareas_mgc;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mgcma.mistareas_mgc.bbdd.ControladorBBDD;

public class HistActivity extends AppCompatActivity {

    //Instanciamos la clase de la BBDD, variable global
    ControladorBBDD controladorDB;
    //Creamos un array que almenacenará las cadenas de texto que se vayan creando y/o eliminado
    private ArrayAdapter<String> miAdapter;
    //Referenciamos al ListView de tareas que hemos creado
    ListView listViewTareas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hist);
        getSupportActionBar().hide();

        controladorDB = new ControladorBBDD(this);
        listViewTareas = (ListView) findViewById(R.id.listTareas_old);
        actualizarInterfaz();
    }

    private void actualizarInterfaz (){
        //Lo primero que tenemos que hacer es comprobar los registros que tiene la tabla
        if(controladorDB.numeroRegistros_old()==0){
            listViewTareas.setAdapter(null);
        } else {
            miAdapter = new ArrayAdapter<>(this,R.layout.item_tarea_old,R.id.tareaView_old,controladorDB.obtenerTareas_old());
            listViewTareas.setAdapter(miAdapter);
        }
    }

    public void eliminar_tarea (View view) {

        //Obetenemos el padre del Botón (El view que llama a este método)
        View parent = (View) view.getParent();
        //dentro del padre buscaremos un identificador cuyo componente sea el texto de la tarea
        TextView tareaTextView = (TextView) parent.findViewById(R.id.tareaView_old);
        //Guardamos el contenido en otra variable
        final String tarea = tareaTextView.getText().toString();

        AlertDialog dialog1 = new AlertDialog.Builder(this)
                .setTitle("Eliminar tarea")
                .setMessage("¿Seguro que desea eliminar definitivamente la tarea?")
                .setCancelable(false)
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Borramos la tarea
                        controladorDB.eliminar_tarea(tarea);

                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.toast,(ViewGroup) findViewById(R.id.toast_layout_root));

                        ImageView image = (ImageView) layout.findViewById(R.id.image);
                        image.setImageResource(R.mipmap.menos);
                        TextView text = (TextView) layout.findViewById(R.id.text);
                        text.setText("Tarea eliminada");

                        Toast toast = new Toast(getApplicationContext());
                        //toast.setGravity(Gravity.BOTTOM, 0, 0);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout);
                        toast.show();

                        actualizarInterfaz();
                    }
                })
                .setNegativeButton("NO",null)
                .create();
        dialog1.show();
    }

    public void volver(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    }
