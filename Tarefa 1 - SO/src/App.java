import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import entities.Processador;
import entities.Tarefa;

public class App {
    public static void main(String[] args) throws Exception {
        
        LinkedList<Tarefa> tarefa = new LinkedList<Tarefa>();
        List<Processador> processador = new ArrayList<Processador>();

        Scanner sc = new Scanner(System.in);
        
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

            System.out.print("Quantos Processadores? ");
            int numProcessador = sc.nextInt();

            for(int i = 0; i < numProcessador; i++){
                processador.add(new Processador(i));
            }

            for(Tarefa elemento : tarefa){
                System.out.println(elemento);
            }

            int numCiclos = 0;
            while(tarefa.size() > 0){
                for(Processador proc : processador){
                    if(numCiclos == proc.getFimTarefa() && tarefa.size() != 0){
                        System.out.println("processador" + proc.getId() + " - " + proc.adicionaTarefa(tarefa.get(0)));
                        tarefa.remove(0);
                    }
                }
                numCiclos++;
                System.out.println("Numero de ciclos: " + numCiclos);
            }
            
            }
            catch (IOException e){
                System.out.println("ERRO: " + e.getMessage());
            }
        sc.close();
    }
}
