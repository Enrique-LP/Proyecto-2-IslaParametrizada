import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Clase que controla la simulación de la isla.
 */
public class Simulacion {

    private Isla isla;
    private Configuracion configuracion;
    private ScheduledExecutorService scheduledExecutor;
    private ExecutorService executor;
    private AtomicInteger turnoActual;

    /**
     * Constructor de la clase Simulacion.
     *
     * @param configuracion La configuración de la simulación.
     */
    public Simulacion(Configuracion configuracion) {
        this.configuracion = configuracion;
        this.isla = new Isla(configuracion);
        this.scheduledExecutor = Executors.newScheduledThreadPool(3); // Crecimiento de plantas, acciones de animales, estadísticas
        this.executor = Executors.newFixedThreadPool(configuracion.numeroHilosAnimales); // Ajusta el número de hilos según la configuración
        this.turnoActual = new AtomicInteger(0);
    }

    /**
     * Inicializa la simulación.
     */
    public void inicializar() {
        // Inicializar la isla con animales y plantas
        inicializarPoblacion();

        // Programar las tareas periódicas
        scheduledExecutor.scheduleAtFixedRate(crearTareaCrecimientoPlantas(), 0, configuracion.duracionCiclo, TimeUnit.MILLISECONDS);
        scheduledExecutor.scheduleAtFixedRate(crearTareaAccionesAnimales(), 0, configuracion.duracionCiclo, TimeUnit.MILLISECONDS);
        scheduledExecutor.scheduleAtFixedRate(crearTareaMostrarEstadisticas(), 0, configuracion.duracionCiclo, TimeUnit.MILLISECONDS);
    }

    /**
     * Crea una tarea para el crecimiento de las plantas.
     *
     * @return Un objeto Runnable que representa la tarea de crecimiento de las plantas.
     */
    private Runnable crearTareaCrecimientoPlantas() {
        return () -> {
            for (int y = 0; y < isla.getAlto(); y++) {
                for (int x = 0; x < isla.getAncho(); x++) {
                    Ubicacion ubicacion = isla.obtenerUbicacion(x, y);
                    ubicacion.crecerPlantas();
                }
            }
        };
    }

    /**
     * Crea una tarea para las acciones de los animales.
     *
     * @return Un objeto Runnable que representa la tarea de acciones de los animales.
     */
    private Runnable crearTareaAccionesAnimales() {
        return () -> {
            for (int y = 0; y < isla.getAlto(); y++) {
                for (int x = 0; x < isla.getAncho(); x++) {
                    Ubicacion ubicacion = isla.obtenerUbicacion(x, y);
                    for (Animal animal : ubicacion.getAnimales()) {
                        executor.submit(() -> {
                            // Acciones del animal: comer, moverse, reproducirse, morir
                            if (animal.estaVivo()) {
                                animal.comer(ubicacion);
                                animal.moverse();
                                // Reproducirse (con probabilidad)
                                if (Math.random() < 0.5) { // Ajustar la probabilidad según sea necesario
                                    Animal pareja = ubicacion.getAnimales().stream()
                                            .filter(a -> a.getEspecie().equals(animal.getEspecie()) && a != animal)
                                            .findAny()
                                            .orElse(null);
                                    if (pareja != null) {
                                        animal.reproducirse(pareja, ubicacion);
                                    }
                                }

                                // Incrementar el hambre del animal y verificar si muere
                                animal.setNivelHambre(animal.getNivelHambre() + 0.1);
                                if (animal.getNivelHambre() >= animal.maxHambre) {
                                    animal.morir();
                                }
                            }
                        });
                    }
                }
            }
            try {
                executor.awaitTermination(configuracion.duracionCiclo, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                // Manejar la excepción
            }
        };
    }

    /**
     * Crea una tarea para mostrar estadísticas de la simulación.
     *
     * @return Un objeto Runnable que representa la tarea de mostrar estadísticas.
     */
    private Runnable crearTareaMostrarEstadisticas() {
        return () -> {
            // Limpiar la pantalla (50 líneas en blanco)
            for (int i = 0; i < 50; i++) System.out.println();

            System.out.println("\n=== Turno: " + turnoActual.incrementAndGet() + " ===\n");
            isla.mostrarEstado();

            // Mostrar leyenda
            System.out.println("\nLeyenda:");
            System.out.println("🐺: Lobo    🐻: Oso     🦊: Zorro   🦅: Águila  🐍: Boa");
            System.out.println("🐎: Caballo 🦌: Ciervo  🐰: Conejo  🐁: Ratón   🐐: Cabra");
            System.out.println("🐑: Oveja   🐗: Jabalí  🦬: Búfalo  🦆: Pato    🐛: Oruga");
            System.out.println("🌿: Planta  ⬜: Vacío");
        };
    }

    /**
     * Ejecuta la simulación.
     */
    public void ejecutar() {
        System.out.println("\n=== Iniciando simulación ===\n");
        
        // Mostrar estado inicial
        System.out.println("=== Estado inicial ===\n");
        isla.mostrarEstado();

        // Bucle principal de la simulación
        while (turnoActual.get() < configuracion.maximoTurnos) {
            try {
                Thread.sleep(configuracion.duracionCiclo);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        // Detener la simulación
        System.out.println("\n=== Finalizando simulación ===\n");
        detenerSimulacion();
    }

    /**
     * Detiene la simulación y los hilos.
     */
    public void detenerSimulacion() {
        scheduledExecutor.shutdown();
        executor.shutdown();
    }

    private void inicializarPoblacion() {
        // Lista de todos los animales a colocar
        for (int i = 0; i < configuracion.numeroInicialLobos; i++) {
            colocarAnimalEnUbicacionAleatoria(new Lobo(0, 0, configuracion, isla));
        }
        for (int i = 0; i < configuracion.numeroInicialConejos; i++) {
            colocarAnimalEnUbicacionAleatoria(new Conejo(0, 0, configuracion, isla));
        }
        for (int i = 0; i < configuracion.numeroInicialZorros; i++) {
            colocarAnimalEnUbicacionAleatoria(new Zorro(0, 0, configuracion, isla));
        }
        for (int i = 0; i < configuracion.numeroInicialOsos; i++) {
            colocarAnimalEnUbicacionAleatoria(new Oso(0, 0, configuracion, isla));
        }
        for (int i = 0; i < configuracion.numeroInicialAguilas; i++) {
            colocarAnimalEnUbicacionAleatoria(new Aguila(0, 0, configuracion, isla));
        }
        for (int i = 0; i < configuracion.numeroInicialCaballos; i++) {
            colocarAnimalEnUbicacionAleatoria(new Caballo(0, 0, configuracion, isla));
        }
        for (int i = 0; i < configuracion.numeroInicialCiervos; i++) {
            colocarAnimalEnUbicacionAleatoria(new Ciervo(0, 0, configuracion, isla));
        }
        for (int i = 0; i < configuracion.numeroInicialRatones; i++) {
            colocarAnimalEnUbicacionAleatoria(new Raton(0, 0, configuracion, isla));
        }
        for (int i = 0; i < configuracion.numeroInicialCabras; i++) {
            colocarAnimalEnUbicacionAleatoria(new Cabra(0, 0, configuracion, isla));
        }
        for (int i = 0; i < configuracion.numeroInicialOvejas; i++) {
            colocarAnimalEnUbicacionAleatoria(new Oveja(0, 0, configuracion, isla));
        }
        for (int i = 0; i < configuracion.numeroInicialJabalies; i++) {
            colocarAnimalEnUbicacionAleatoria(new Jabali(0, 0, configuracion, isla));
        }
        for (int i = 0; i < configuracion.numeroInicialBufalos; i++) {
            colocarAnimalEnUbicacionAleatoria(new Bufalo(0, 0, configuracion, isla));
        }
        for (int i = 0; i < configuracion.numeroInicialPatos; i++) {
            colocarAnimalEnUbicacionAleatoria(new Pato(0, 0, configuracion, isla));
        }
        for (int i = 0; i < configuracion.numeroInicialOrugas; i++) {
            colocarAnimalEnUbicacionAleatoria(new Oruga(0, 0, configuracion, isla));
        }
        for (int i = 0; i < configuracion.numeroInicialBoas; i++) {
            colocarAnimalEnUbicacionAleatoria(new Boa(0, 0, configuracion, isla));
        }
    }

    private void colocarAnimalEnUbicacionAleatoria(Animal animal) {
        boolean colocado = false;
        int maxIntentos = isla.getAncho() * isla.getAlto();
        int intentos = 0;

        while (!colocado && intentos < maxIntentos) {
            int x = (int) (Math.random() * isla.getAncho());
            int y = (int) (Math.random() * isla.getAlto());
            Ubicacion ubicacion = isla.obtenerUbicacion(x, y);

            if (ubicacion != null && ubicacion.hayEspacioParaAnimal(animal)) {
                animal.setX(x);
                animal.setY(y);
                ubicacion.agregarAnimal(animal);
                colocado = true;
            }
            intentos++;
        }

        // Si no se pudo colocar después de los intentos, forzar la colocación
        if (!colocado) {
            // Buscar sistemáticamente una ubicación
            for (int y = 0; y < isla.getAlto() && !colocado; y++) {
                for (int x = 0; x < isla.getAncho() && !colocado; x++) {
                    Ubicacion ubicacion = isla.obtenerUbicacion(x, y);
                    animal.setX(x);
                    animal.setY(y);
                    ubicacion.agregarAnimal(animal);
                    colocado = true;
                }
            }
        }
    }

}