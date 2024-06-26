import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
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

            System.out.println("Menor => Maior");
            executar(tarefaOrg, "Saida_SFJ.txt");
            System.out.println();
            System.out.println("Maior => Menor");
            executar(tarefaCont, "Saida_LJF.txt");
            
            sc.close();
            }
            catch (IOException e){
                System.out.println("ERRO: " + e.getMessage());
            }
    }

    private static void executar(LinkedList<Tarefa> tarefa, String arquivoSaida) throws IOException {   
        List<Processador> processador = new ArrayList<Processador>();

        for(int i = 0; i < numProcessador; i++){
            processador.add(new Processador(i + 1));
        }
        int numCiclos = 0;
        while(tarefa.size() != 0){
            for(Processador proc : processador){
                if(numCiclos == proc.getFimTarefa() && tarefa.size() != 0){
                    System.out.println("processador" + proc.getId() + " - " + proc.adicionaTarefa(tarefa.get(0)));
                    tarefa.remove(0);
                }
            }
            numCiclos++;
        }

        try (BufferedWriter escrever = new BufferedWriter(new FileWriter(arquivoSaida))) {
            for (Processador proc : processador) {
                escrever.write("Processador_" + proc.getId() + "\n");
                for (String t : proc.getTarefas()) {
                    escrever.write(t + "\n");
                }
            }
        }
    }
}