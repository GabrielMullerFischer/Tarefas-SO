import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import entities.Tarefa;

public class App {
    public static void main(String[] args) throws Exception {
        
        LinkedList<Tarefa> tarefa = new LinkedList<Tarefa>();

        
            try (BufferedReader leitor = new BufferedReader(new FileReader("tarefas.txt"))){
                String linha;
                
            while ((linha = leitor.readLine()) != null){
                int espaco = linha.indexOf(' ');
                if(espaco != -1){
                    String id = linha.substring(0, espaco);
                    int tempo = Integer.parseInt(linha.substring(espaco + 1).trim());
                    Tarefa tarefaAux = new Tarefa(id, tempo);
                    int i = 0;
                    while (i < tarefa.size() && tarefa.get(i).getTempo() <= tempo) {
                        i++;                            
                    }
                    tarefa.add(i, tarefaAux);            
                }
            }
            }
            catch (IOException e){
                System.out.println("ERRO: " + e.getMessage());
            }


            for (Tarefa elemento : tarefa){
                System.out.println(elemento);
            }

            System.out.println("\n\n");

            for (int i = tarefa.size() - 1; i >= 0; i--) {
                Tarefa elemento = tarefa.get(i);
                System.out.println(elemento);
            }
    }
}
