package navegador.up.edu.br.cortex;

import java.io.Serializable;
import java.util.Date;

public class Tarefa implements Serializable{
    private Integer id;
    private String titulo;
    private String descricao;
    private String dataInicio;
    private String dataFim;
    private String sequencial;
    private int statusTarefa;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public String getSequencial() {
        return sequencial;
    }

    public void setSequencial(String sequencial) {
        this.sequencial = sequencial;
    }

    public int getStatusTarefa() {
        return statusTarefa;
    }

    public void setStatusTarefa(int statusTarefa) {
        this.statusTarefa = statusTarefa;
    }

    @Override
    public boolean equals(Object o){
        if(id == null || ((Tarefa)o).getId() == null){
            return false;
        }
        return id.equals(((Tarefa)o).getId());
    }
}
