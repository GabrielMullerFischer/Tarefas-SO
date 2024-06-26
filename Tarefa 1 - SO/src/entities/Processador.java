package entities;

import java.util.ArrayList;
import java.util.List;

public class Processador {
    
    private int id;
    private int iniTarefa;
    private int fimTarefa;
    private List<String> tarefas;

    public Processador(int id){
        this.id = id;
        this.iniTarefa = 0;
        this.fimTarefa = 0;
        this.tarefas = new ArrayList<>();
    }

    public String adicionaTarefa(Tarefa tarefa){
        this.iniTarefa = fimTarefa;
        this.fimTarefa += tarefa.getTempo();
        String resultado = tarefa.getId() + ";" + iniTarefa + ";" + fimTarefa;
        tarefas.add(resultado);
        return resultado;
    }

    public int getId() {
        return id;
    }

    public int getFimTarefa() {
        return fimTarefa;
    }

    public List<String> getTarefas() {
        return tarefas;
    }
}
