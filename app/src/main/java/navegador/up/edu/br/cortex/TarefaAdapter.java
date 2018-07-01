package navegador.up.edu.br.cortex;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class TarefaAdapter extends BaseAdapter {

    private List<Tarefa> tarefas;
    Activity act;

    public TarefaAdapter(List<Tarefa> tarefas, Activity act){
        this.tarefas = tarefas;
        this.act = act;

    }


    @Override
    public int getCount() {
        return this.tarefas.size();
    }

    @Override
    public Object getItem(int i) {
        return this.tarefas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View v = act.getLayoutInflater().inflate(R.layout.tarefa_adapter, parent,false);
        TextView titulo = v.findViewById(R.id.titulo_lbl);
        ImageView tipo = v.findViewById(R.id.icon_tarefa);
        TextView stsTarefa = v.findViewById(R.id.status_tarefa);
        Tarefa t = tarefas.get(i);
        titulo.setText(t.getTitulo());
        if(t.getStatusTarefa() == 1){
            stsTarefa.setText("Completa");
        }
        if(t.getStatusTarefa()==0){
            stsTarefa.setText("Em andamento...");
        }
        if(t.getSequencial().equals("Unico")){
            tipo.setImageResource(R.drawable.ic_if_calendar_alt_285665);
        }else if(t.getSequencial().equals("Mensal")){
            tipo.setImageResource(R.drawable.ic_if_calendar_285670);
        }else if(t.getSequencial().equals("Semanal")){
            tipo.setImageResource(R.drawable.ic_if_cmyk_04_906567);
        }else{
            tipo.setImageResource(R.drawable.ic_if_calendar_clock_299096);
        }
        return v;
    }

    public void remove(Tarefa tarefa) {

        this.tarefas.remove(tarefa);
    }
}
