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
    public static void main(String[] args) throws Exception {
        
        LinkedList<Tarefa> tarefaOrg = new LinkedList<Tarefa>();
        
        List<Processador> processador = new ArrayList<Processador>();

        Scanner sc = new Scanner(System.in);
        
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

            System.out.print("Quantos Processadores? ");
            int numProcessador = sc.nextInt();

            for(int i = 0; i < numProcessador; i++){
                processador.add(new Processador(i));
            }

            for(Tarefa elemento : tarefaOrg){
                System.out.println(elemento);
            }

            for(Tarefa elemento : tarefaCont){
                System.out.println(elemento);
            }

            int numCiclos = 0;
            while(tarefaOrg.size() > 0){
                for(Processador proc : processador){
                    if(numCiclos == proc.getFimTarefa() && tarefaOrg.size() != 0){
                        System.out.println("processador" + proc.getId() + " - " + proc.adicionaTarefa(tarefaOrg.get(0)));
                        tarefaOrg.remove(0);
                    }
                }
                numCiclos++;
                System.out.println("Numero de ciclos: " + numCiclos);
            }

            for(int i = 0; i < numProcessador; i++){
                processador.add(new Processador(i));
            }
            
            numCiclos = 0;
            while(tarefaCont.size() > 0){
                for(Processador proc : processador){
                    if(numCiclos == proc.getFimTarefa() && tarefaCont.size() != 0){
                        System.out.println("processador" + proc.getId() + " - " + proc.adicionaTarefa(tarefaCont.get(0)));
                        tarefaCont.remove(0);
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