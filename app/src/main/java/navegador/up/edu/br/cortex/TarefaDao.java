package navegador.up.edu.br.cortex;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TarefaDao {
    static ArrayList<Tarefa> tarefas = new ArrayList<Tarefa>();
    static  Integer id = 0;


    public void salvar(Tarefa tarefa,boolean valor){
        SQLiteDatabase conn = Conn.getInstance().getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("titulo",tarefa.getTitulo());
        values.put("descricao",tarefa.getDescricao());
        values.put("dataInicio",tarefa.getDataInicio());
        if (tarefa.getDataFim() == null){
            values.put("dataFim","");
        } else {
            values.put("dataFim",tarefa.getDataFim());
        }
        values.put("sequencial",tarefa.getSequencial());

        if(tarefa.getId() == null){
            values.put("statusTarefas",valor);
            conn.insert("tarefa", null,values);
        } else {
            values.put("statusTarefas",valor);
            conn.update("tarefa", values,"id = ?", new String [] {tarefa.getId().toString()});
        }


    }

    public List<Tarefa> listar() {
        SQLiteDatabase conn = Conn.getInstance().getReadableDatabase();
        Cursor c = conn.query("tarefa",new String[]{"id","titulo","descricao","sequencial","statusTarefa"},null,null,null,null,"dataInicio");

        ArrayList<Tarefa> tarefas = new ArrayList<Tarefa>();
        if(c.moveToFirst()){
            do{
                Tarefa tarefa = new Tarefa();
                tarefa.setId(c.getInt(0));
                tarefa.setTitulo(c.getString(1));
                tarefa.setDescricao(c.getString(2));
                tarefa.setSequencial(c.getString(3));
                tarefa.setStatusTarefa(Boolean.parseBoolean(c.getString(4)));

                tarefas.add(tarefa);

            }while(c.moveToNext());

        }
        return  tarefas;
    }

    //
    public void alterarTarefa(Tarefa tarefa,Boolean ativar){
        ContentValues valores = pegarTarefa(tarefa,ativar);
        SQLiteDatabase conn = Conn.getInstance().getReadableDatabase();
        conn.update("tarefa",valores,"id = ?",new String[]{tarefa.getId().toString()});

    }

    public ContentValues pegarTarefa(Tarefa tarefa, Boolean ativar){
        ContentValues dados = new ContentValues();
        dados.put("tarefa.id",tarefa.getId());
        dados.put("tarefa.titulo",tarefa.getTitulo());
        dados.put("tarefa.descricao",tarefa.getDescricao());
        dados.put("tarefa.dataFim",tarefa.getDataFim());
        dados.put("tarefa.statusTarefa",ativar);
        return  dados;
    }

    public void excluir(Tarefa tarefa){
        SQLiteDatabase coon = Conn.getInstance().getWritableDatabase();

        coon.delete("tarefa","id = ?", new String[]{tarefa.getId().toString()});
    }


}
