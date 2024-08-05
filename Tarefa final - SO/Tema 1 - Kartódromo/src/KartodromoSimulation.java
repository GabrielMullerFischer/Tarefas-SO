import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.*;

class Cliente implements Runnable {
    private String nome;
    private int idade;
    private Kartodromo kartodromo;
    private boolean isAtendido;

    public Cliente(String nome, int idade, Kartodromo kartodromo) {
        this.nome = nome;
        this.idade = idade;
        this.kartodromo = kartodromo;
        this.isAtendido = false;
    }

    public int getIdade() {
        return idade;
    }

    public String getNome() {
        return nome;
    }

    public boolean isAtendido() {
        return isAtendido;
    }

    @Override
    public void run() {
        try {
            if (idade <= 14) {
                // Criança busca primeiro o capacete
                isAtendido = kartodromo.pegarCapacete(this);
                if (isAtendido) {
                    kartodromo.pegarKart(this);
                }
            } else {
                // Adulto busca primeiro o kart
                isAtendido = kartodromo.pegarKart(this);
                if (isAtendido) {
                    kartodromo.pegarCapacete(this);
                }
            }

            if (isAtendido) {
                // Simula tempo na pista
                Thread.sleep(new Random().nextInt(500) + 100); // Reduzido para teste rápido
                kartodromo.devolverRecursos(this);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Kartodromo {
    private Semaphore karts;
    private Semaphore capacetes;
    private int totalClientesAtendidos;
    private long totalTempoEspera;
    private List<Cliente> clientesNaoAtendidos;
    private ReentrantLock lock;

    public Kartodromo(int numKarts, int numCapacetes) {
        this.karts = new Semaphore(numKarts, true);
        this.capacetes = new Semaphore(numCapacetes, true);
        this.totalClientesAtendidos = 0;
        this.totalTempoEspera = 0;
        this.clientesNaoAtendidos = new ArrayList<>();
        this.lock = new ReentrantLock();
    }

    public boolean pegarKart(Cliente cliente) {
        long start = System.currentTimeMillis();
        try {
            if (karts.tryAcquire(1, TimeUnit.SECONDS)) { // Reduzido para teste rápido
                lock.lock();
                totalTempoEspera += (System.currentTimeMillis() - start);
                totalClientesAtendidos++;
                lock.unlock();
                return true;
            } else {
                clientesNaoAtendidos.add(cliente);
                return false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean pegarCapacete(Cliente cliente) {
        long start = System.currentTimeMillis();
        try {
            if (capacetes.tryAcquire(1, TimeUnit.SECONDS)) { // Reduzido para teste rápido
                lock.lock();
                totalTempoEspera += (System.currentTimeMillis() - start);
                totalClientesAtendidos++;
                lock.unlock();
                return true;
            } else {
                clientesNaoAtendidos.add(cliente);
                return false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void devolverRecursos(Cliente cliente) {
        karts.release();
        capacetes.release();
    }

    public void imprimirRelatorio() {
        System.out.println("Total de clientes atendidos: " + totalClientesAtendidos);
        System.out.println("Tempo médio de espera: " + (totalClientesAtendidos == 0 ? 0 : totalTempoEspera / totalClientesAtendidos) + " ms");
        System.out.println("Clientes não atendidos: " + clientesNaoAtendidos.size());
        for (Cliente cliente : clientesNaoAtendidos) {
            System.out.println("Cliente " + cliente.getNome() + " (idade " + cliente.getIdade() + ") esperou e não foi atendido.");
        }
    }
}

public class KartodromoSimulation {
    public static void main(String[] args) {
        Kartodromo kartodromo = new Kartodromo(10, 10);
        ExecutorService executor = Executors.newCachedThreadPool();
        Random random = new Random();

        // Simula a chegada de 100 clientes ao longo de 1000 segundos
        for (int i = 0; i < 100; i++) {
            String nome = "Cliente" + i;
            int idade = random.nextInt(50); // Idade entre 0 e 50 anos
            Cliente cliente = new Cliente(nome, idade, kartodromo);
            executor.execute(cliente);

            // Espera um tempo aleatório antes de criar o próximo cliente
            try {
                Thread.sleep(random.nextInt(10)); // Reduzido para teste rápido
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Espera a conclusão de todas as threads
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES); // Reduzido para teste rápido
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Imprime o relatório ao final do dia
        kartodromo.imprimirRelatorio();
    }
}
