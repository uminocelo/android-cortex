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

    private String getDateTime(Date time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(time);
    }

    public void salvar(Tarefa tarefa){
        SQLiteDatabase conn = Conn.getInstance().getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("titulo",tarefa.getTitulo());
        values.put("descricao",tarefa.getDescricao());
        values.put("dataInicio",getDateTime(tarefa.getDataInicio()));
        //values.put("dataFim",getDateTime(tarefa.getDataFim()));
        values.put("sequencial",tarefa.getSequencial());
        values.put("statusTarefas",tarefa.isStatusTarefa());

        if(tarefa.getId() == null){
            conn.insert("tarefa", null,values);
        } else {
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
    public void validarTarefa(Tarefa tarefa){
        ContentValues valores = pegarTarefa(tarefa);
        int conn = Conn.getInstance().getReadableDatabase().update("tarefa",valores,"id = ?",new String[]{tarefa.getId().toString()});

    }
    /*
        public List<Tarefa> listarCompletos(){
            SQLiteDatabase conn = Conn.getInstance().getReadableDatabase();
            String query = "SELECT id, titulo, descricao FROM tarefa WHERE statusTarefa = 1 ";
            //Cursor c = conn.query(query,null);
            //Cursor c = conn.query("tarefa",new String[]{"id","titulo","descricao"},null,null,null,null,null,"id");

        }
    */

    public ContentValues pegarTarefa(Tarefa tarefa){
        ContentValues dados = new ContentValues();
        dados.put("tarefa.id",tarefa.getId());
        dados.put("tarefa.titulo",tarefa.getTitulo());
        dados.put("tarefa.descricao",tarefa.getDescricao());
        dados.put("tarefa.dataFim",getDateTime(tarefa.getDataFim()));
        dados.put("tarefa.statusTarefa",tarefa.isStatusTarefa());
        return  dados;
    }

    public void excluir(Tarefa tarefa){
        SQLiteDatabase coon = Conn.getInstance().getWritableDatabase();

        coon.delete("tarefa","id = ?", new String[]{tarefa.getId().toString()});
    }


}
