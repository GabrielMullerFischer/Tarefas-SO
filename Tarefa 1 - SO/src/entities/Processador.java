package entities;

public class Processador {
    
    private int id;
    private int iniTarefa;
    private int fimTarefa;

    public Processador(int id){
        this.id = id;
        this.iniTarefa = 0;
        this.fimTarefa = 0;
    }

    public String adicionaTarefa(Tarefa tarefa){
        this.iniTarefa = fimTarefa;
        this.fimTarefa += tarefa.getTempo();
        return tarefa.getId() + ";" + iniTarefa + ";" + fimTarefa; 
    }

    public int getId() {
        return id;
    }

    public int getFimTarefa() {
        return fimTarefa;
    }
}
