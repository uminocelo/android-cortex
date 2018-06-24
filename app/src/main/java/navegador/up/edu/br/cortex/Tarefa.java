package navegador.up.edu.br.cortex;

import java.io.Serializable;
import java.util.Date;

public class Tarefa implements Serializable{
    private Integer id;
    private String titulo;
    private String descricao;
    private Date dataInicio;
    private Date dataFim;
    private String sequencial;
    private boolean statusTarefa;

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

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public String getSequencial() {
        return sequencial;
    }

    public void setSequencial(String sequencial) {
        this.sequencial = sequencial;
    }

    public boolean isStatusTarefa() {
        return statusTarefa;
    }

    public void setStatusTarefa(boolean statusTarefa) {
        this.statusTarefa = statusTarefa;
    }

    public boolean equals(Object o){
        if(id == null || ((Tarefa)o).getId() == null){
            return false;
        }
        return id.equals(((Tarefa)o).getId());
    }
}
