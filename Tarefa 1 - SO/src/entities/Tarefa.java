package entities;

public class Tarefa {

    private String id;
    private int tempo;

    
    public Tarefa(String id, int tempo) {
        this.id = id;
        this.tempo = tempo;
    }
    
    public String getId() {
        return id;
    }

    public int getTempo() {
        return tempo;
    }
}
