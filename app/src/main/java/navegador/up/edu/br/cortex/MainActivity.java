package navegador.up.edu.br.cortex;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new Conn(getApplicationContext(), "tarefas.db", null ,1);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                       Intent it = new Intent(MainActivity.this,TarefaActivity.class);
                       startActivity(it);
                }
            }
        );

        ListView listaTarefas = (ListView)findViewById(R.id.listaTarefas);

        TarefaAdapter ta = new TarefaAdapter(new TarefaDao().listar(),this);

        listaTarefas.setAdapter(ta);

        listaTarefas.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Tarefa t = (Tarefa) adapterView.getItemAtPosition(i);
                Intent it = new Intent(MainActivity.this,TarefaActivity.class);
                it.putExtra("tarefa",t);
                startActivity(it);
            }
        });
        listaTarefas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int i, long l) {

                AlertDialog.Builder alert =  new AlertDialog.Builder(MainActivity.this);
                alert.setMessage("Deseja completar a tarefa?");
                alert.setCancelable(false);
                alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Tarefa tarefa = (Tarefa)adapterView.getItemAtPosition(i);

                        new  TarefaDao().alterarTarefa(tarefa,true);
                    }
                });
                alert.setNegativeButton("Não, marcar como incompleta.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Tarefa tarefa = (Tarefa)adapterView.getItemAtPosition(i);

                        new TarefaDao().alterarTarefa(tarefa,false);
                    }
                });
                alert.show();

                return true;
            }
        });

    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
