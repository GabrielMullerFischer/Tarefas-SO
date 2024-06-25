import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import entities.Processador;
import entities.Tarefa;

public class App {
    static int numProcessador;
    public static void main(String[] args) throws Exception {
        
        LinkedList<Tarefa> tarefaOrg = new LinkedList<Tarefa>();
        
            try (BufferedReader leitor = new BufferedReader(new FileReader("tarefas.txt"))){
                String linha;
                System.out.println();
                
            while ((linha = leitor.readLine()) != null){
                int espaco = linha.indexOf(' ');
                if(espaco != -1){
                    String id = linha.substring(0, espaco);
                    int tempo = Integer.parseInt(linha.substring(espaco + 1).trim());
                    Tarefa tarefaAux = new Tarefa(id, tempo);
                    int i = 0;
                    while (i < tarefaOrg.size() && tarefaOrg.get(i).getTempo() <= tempo) {
                        i++;                            
                    }
                    tarefaOrg.add(i, tarefaAux);            
                }
            }

            LinkedList<Tarefa> tarefaCont = new LinkedList<>(tarefaOrg);
            Collections.reverse(tarefaCont);

            Scanner sc = new Scanner(System.in);
    
            System.out.print("Quantos Processadores? ");
            numProcessador = sc.nextInt();

            executar(tarefaOrg);
            executar(tarefaCont);
            
            sc.close();
            }
            catch (IOException e){
                System.out.println("ERRO: " + e.getMessage());
            }
    }

    private static void executar(LinkedList<Tarefa> tarefa) {   
            List<Processador> processador = new ArrayList<Processador>();

            for(int i = 0; i < numProcessador; i++){
                processador.add(new Processador(i));
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
}