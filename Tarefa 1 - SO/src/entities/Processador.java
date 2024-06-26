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

    public void adicionaTarefa(Tarefa tarefa){
        this.iniTarefa = fimTarefa;
        this.fimTarefa += tarefa.getTempo();
        tarefas.add(tarefa.getId() + ";" + iniTarefa + ";" + fimTarefa);
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
