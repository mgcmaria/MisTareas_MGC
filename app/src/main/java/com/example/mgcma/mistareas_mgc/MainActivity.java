package com.example.mgcma.mistareas_mgc;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mgcma.mistareas_mgc.bbdd.ControladorBBDD;

public class MainActivity extends AppCompatActivity {

    //Instanciamos la clase de la BBDD, variable global
    ControladorBBDD controladorDB;
    //Creamos un array que almenacenará las cadenas de texto que se vayan creando y/o eliminado
    private ArrayAdapter<String> miAdapter;
    //Referenciamos al ListView de tareas que hemos creado
    ListView listViewTareas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controladorDB = new ControladorBBDD(this);
        listViewTareas = (ListView) findViewById(R.id.listTareas);
        actualizarInterfaz();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        //AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final EditText cajaTexto = new EditText(this);

        switch (item.getItemId()){

            case R.id.nueva_tarea:

                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Nueva Tarea")
                        .setMessage("Indica el nombre de la nueva Tarea")
                        .setView(cajaTexto)
                        .setCancelable(false)
                        .setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //Almacenos en una variable la cadena de texto del EditText
                                String tarea = cajaTexto.getText().toString();
                                //Accedemos al metodo de la Clase ControladorBBDD que hemos creado
                                //de añadir tarea y le pasamos como texto el contenido de la caja de texto
                                controladorDB.anadirTarea(tarea);

                                LayoutInflater inflater = getLayoutInflater();
                                View layout = inflater.inflate(R.layout.toast,(ViewGroup) findViewById(R.id.toast_layout_root));

                                ImageView image = (ImageView) layout.findViewById(R.id.image);
                                image.setImageResource(R.mipmap.hecho);
                                TextView text = (TextView) layout.findViewById(R.id.text);
                                text.setText("Nueva tarea pendiente");

                                Toast toast = new Toast(getApplicationContext());
                                //toast.setGravity(Gravity.BOTTOM, 0, 0);
                                toast.setDuration(Toast.LENGTH_LONG);
                                toast.setView(layout);
                                toast.show();

                                actualizarInterfaz();
                            }
                        })
                        .setNegativeButton("Cancelar",null)
                        .create();
                dialog.show();

                return true;

            case R.id.historial:

                //Accedemos al historial de tareas hechas
                Intent intent1 = new Intent(this, HistActivity.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;

            case R.id.cerrar_sesion:

                //Volvemos a la pantalla de Login
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;

            case R.id.salir:

                AlertDialog dialog1 = new AlertDialog.Builder(this)
                        .setTitle("Salir")
                        .setMessage("¿Seguro que desea salir de la aplicación?")
                        .setCancelable(false)
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finishAffinity();
                                System.exit(0);
                            }
                        })
                        .setNegativeButton("NO",null)
                        .create();
                dialog1.show();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void actualizarInterfaz (){
        //Lo primero que tenemos que hacer es comprobar los registros que tiene la tabla
        if(controladorDB.numeroRegistros()==0){
            listViewTareas.setAdapter(null);
        } else {
            miAdapter = new ArrayAdapter<>(this,R.layout.item_tarea,R.id.tareaView,controladorDB.obtenerTareas());
            listViewTareas.setAdapter(miAdapter);
        }
    }

    public void borrarTarea (View view){

        //Obetenemos el padre del Botón (El view que llama a este método)
        View parent = (View) view.getParent();
        //dentro del padre buscaremos un identificador cuyo componente sea el texto de la tarea
        TextView tareaTextView = (TextView) parent.findViewById(R.id.tareaView);
        //Guardamos el contenido en otra variable
        String tarea = tareaTextView.getText().toString();

        //Borramos la tarea
        controladorDB.borrarTarea(tarea);

        //Mostramos un Toast personalizado para que el usuario sepa que la tarea ha sido eliminada

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast,(ViewGroup) findViewById(R.id.toast_layout_root));

        ImageView image = (ImageView) layout.findViewById(R.id.image);
        image.setImageResource(R.mipmap.checklist);
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText("Tarea realizada");

        Toast toast = new Toast(getApplicationContext());
        //toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

        //Actualizamos la interfaz
        actualizarInterfaz();
    }

}
