package navegador.up.edu.br.cortex;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


public class TarefaActivity extends AppCompatActivity {

    Tarefa tarefa;
    DatePickerDialog datapicker;
    TimePickerDialog hourpicker;
    TextView inicio;
    TextView fim;
    TextView clique;
    int hora,min;
    TextView horario;


    ///INT PARA NOTi

    int diaNoti,mesNoti,anoNoti,horaNoti,minNoti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText titulo = (EditText)findViewById(R.id.txt_titulo);
        final EditText descricao =(EditText)findViewById(R.id.txt_descricao);
        inicio = (TextView)findViewById(R.id.txtInicio);
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
                diaNoti = dia;
                mesNoti = mes;
                anoNoti = ano;

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

        ///
        horario = (TextView) findViewById(R.id.horario); //Campo onde a hora vai ficar
        horario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Abri o Time Picker, para pegar o horario
                Calendar ph = Calendar.getInstance();
                hora = ph.get(Calendar.HOUR_OF_DAY);
                min = ph.get(Calendar.MINUTE);
                horaNoti = hora;
                minNoti = min;

                hourpicker = new TimePickerDialog(TarefaActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int h, int m) {
                                horario.setText(h + ":" + m);
                            }
                        }, hora, min, true);
                LayoutInflater layoutInflater = LayoutInflater.from(TarefaActivity.this);
                View promptView = layoutInflater.inflate(R.layout.dialog_alarm, null);
                TimePickerDialog.Builder alertTime = new TimePickerDialog.Builder(TarefaActivity.this);
                alertTime.setView(promptView);
                alertTime.setCancelable(false)
                        .setPositiveButton("Sim, criar.", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //verifica o tipo de tarefa
                                /*
                                * se tarefa for do tipo unica,mensal, anual, ira pegar o dia escrito no campo data inicio ira procurar o dia da semana correspondente e ira criar um evnto na agenda
                                * se tarefa for do tipo diario ira criar um alarme repetindo todos os dias.
                                * */
                                TextView di = (TextView)findViewById(R.id.txtInicio);
                                if(di.getText() != "00/00/0000"){
                                    if(sequencial.getSelectedItem().equals("Unico")){
                                        //pegar o dia
                                        Toast.makeText(getApplicationContext(),
                                                "Dia : " + diaNoti + "Mes : " + (mesNoti+1) + "Ano : "+ anoNoti,
                                                Toast.LENGTH_LONG).show();
                                    }
                                }else{
                                    Toast.makeText(getApplicationContext(),
                                            "Preencher dados no campo data de inicio",
                                            Toast.LENGTH_LONG).show();
                                }


                                //Abrir o alarme

                                Intent alarm = new Intent(AlarmClock.ACTION_SET_ALARM)
                                        .putExtra(AlarmClock.EXTRA_HOUR, hora)
                                        .putExtra(AlarmClock.EXTRA_MINUTES, min)
                                        .putExtra(AlarmClock.EXTRA_SKIP_UI,true);

                                if (alarm.resolveActivity(getPackageManager()) != null) {
                                    startActivity(alarm);
                                }
                            }
                        }).setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alert = alertTime.create();
                alert.show();
                hourpicker.show();

            }
        });
        //// PEGAR NOTIFICAÇÃO

        ////
        Intent it = getIntent();
        if(it!= null && it.hasExtra("tarefa")){
            //nova instancia
            tarefa =  (Tarefa)it.getSerializableExtra("tarefa");

            titulo.setText((tarefa.getTitulo()));
            descricao.setText((tarefa.getDescricao()));
            inicio.setText(tarefa.getDataInicio());
            //sequencial.setSelection(((ArrayAdapter)sequencial.getAdapter()).getPosition(tarefa.getSequencial()));
            if(sequencial.getSelectedItem() != "Unico"){
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
                    tarefa.setTitulo(titulo.getText().toString());
                    tarefa.setDescricao(descricao.getText().toString());
                    tarefa.setSequencial(sequencial.getSelectedItem().toString());
                    tarefa.setDataInicio(inicio.getText().toString());
                    tarefa.setDataFim(fim.getText().toString());
                    Switch completar = (Switch)findViewById(R.id.switch1);
                    Boolean swtstate = completar.isChecked();
                    if(swtstate == true) {
                        tarefa.setStatusTarefa(1);
                    }else{
                        tarefa.setStatusTarefa(0);
                    }
                    new TarefaDao().salvar(tarefa);

                    tarefa = null;

                    Toast.makeText(getApplicationContext(),
                            "Salvo com sucesso!",
                            Toast.LENGTH_LONG).show();
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(TarefaActivity.this);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.keep&referrer=utm_source%3Dkeep_mkt%26utm_campaign%3Dkeep_mkt")); //Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.keep&referrer=utm_source%3Dkeep_mkt%26utm_campaign%3Dkeep_mkt")
                    PendingIntent pendingIntent = PendingIntent.getActivity(TarefaActivity.this, 0, intent, 0);
                    mBuilder.setContentIntent(pendingIntent);

                    mBuilder.setSmallIcon(R.drawable.ic_noti_small);
                    mBuilder.setContentTitle("Cortex - Dica:");
                    mBuilder.setContentText("Crie uma anotação no Google Keep também :)");



                    NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                    mNotificationManager.notify(001, mBuilder.build());

                    Intent it = new Intent(TarefaActivity.this, MainActivity.class);
                    startActivity(it);
                }else{
                    tarefa.setTitulo(titulo.getText().toString());
                    tarefa.setDescricao(descricao.getText().toString());
                    tarefa.setSequencial(sequencial.getSelectedItem().toString());
                    tarefa.setDataInicio(inicio.getText().toString());
                    tarefa.setDataFim(fim.getText().toString());
                    Switch completar = (Switch)findViewById(R.id.switch1);
                    Boolean swtstate = completar.isChecked();
                    if(swtstate == true) {
                        tarefa.setStatusTarefa(1);
                    }else{
                        tarefa.setStatusTarefa(0);
                    }
                    new TarefaDao().salvar(tarefa);

                    tarefa = null;

                    Toast.makeText(getApplicationContext(),
                            "Editado com sucesso!",
                            Toast.LENGTH_LONG).show();

                    Intent it = new Intent(TarefaActivity.this, MainActivity.class);
                    startActivity(it);
                }
            }
        });

    }

}
