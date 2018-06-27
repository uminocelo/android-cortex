package navegador.up.edu.br.cortex;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;
import java.util.ArrayList;
import java.util.Calendar;


public class TarefaActivity extends AppCompatActivity {

    Tarefa tarefa;
    DatePickerDialog datapicker;
    TextView inicio;
    TextView fim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText titulo = (EditText)findViewById(R.id.txt_titulo);
        final EditText descricao =(EditText)findViewById(R.id.txt_descricao);
        inicio = (TextView)findViewById(R.id.txtInico);
        fim = (TextView)findViewById(R.id.txtFim);

        final Spinner sequencial = (Spinner)findViewById(R.id.spinner);

        inicio.setInputType(InputType.TYPE_NULL);
        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                int mes = calendar.get(Calendar.MONTH);
                int ano = calendar.get(Calendar.YEAR);

                datapicker = new DatePickerDialog(TarefaActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                                inicio.setText(dia+"/"+(mes+1)+"/"+ano);
                            }
                        },ano,mes,dia);
                datapicker.show();
            }
        });
        //Pega data do fim
        fim.setInputType(InputType.TYPE_NULL);
        fim.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final Calendar fimCalendar = Calendar.getInstance();
                int diaFim = fimCalendar.get(Calendar.DAY_OF_MONTH);
                int mesFim = fimCalendar.get(Calendar.DAY_OF_MONTH);
                int anoFim = fimCalendar.get(Calendar.YEAR);

                datapicker = new DatePickerDialog(TarefaActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                                fim.setText(dia+"/"+(mes+1)+"/"+ano);
                            }
                        },anoFim,mesFim,diaFim);
                datapicker.show();
            }
        });

        Intent it = getIntent();
        if(it!= null && it.hasExtra("tarefa")){
            //nova instancia
            tarefa =  (Tarefa)it.getSerializableExtra("tarefa");

            titulo.setText((tarefa.getTitulo()));
            descricao.setText((tarefa.getDescricao()));
            inicio.setText(tarefa.getDataInicio());
            sequencial.setSelection(((ArrayAdapter)sequencial.getAdapter()).getPosition(tarefa.getSequencial()));
            if(sequencial.getSelectedItem() == "Unico"){
                fim.setText(tarefa.getDataFim());
            }else{
                fim.setText("");
            }
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tarefa==null){
                    tarefa = new Tarefa();
                }
                tarefa.setTitulo(titulo.getText().toString());
                tarefa.setDescricao(descricao.getText().toString());
                tarefa.setSequencial(sequencial.getSelectedItem().toString());
                tarefa.setDataInicio(inicio.getText().toString());
                tarefa.setDataFim(fim.getText().toString());
                new TarefaDao().salvar(tarefa,true);

                tarefa = null;

                Toast.makeText(getApplicationContext(),
                        "Salvo com sucesso!",
                        Toast.LENGTH_LONG).show();
                Intent it = new Intent(TarefaActivity.this, MainActivity.class);
                startActivity(it);
            }
        });

    }

}
