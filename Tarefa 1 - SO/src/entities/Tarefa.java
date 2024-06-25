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
    public void setId(String id) {
        this.id = id;
    }
    public int getTempo() {
        return tempo;
    }
    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    @Override
    public String toString() {
        return id + " " + tempo;
    }
}
