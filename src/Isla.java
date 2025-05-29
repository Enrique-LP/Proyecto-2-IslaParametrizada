import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Clase que representa la isla en la simulación.
 * Contiene una matriz de Ubicaciones, que representan las celdas de la isla.
 */
public class Isla {

    private int ancho;
    private int alto;
    private Ubicacion[][] cuadricula;
    private final Lock lock = new ReentrantLock();

    /**
     * Constructor de la clase Isla.
     *
     * @param configuracion La configuración de la simulación.
     */
    public Isla(Configuracion configuracion) {
        this.ancho = configuracion.anchoIsla;
        this.alto = configuracion.altoIsla;
        this.cuadricula = new Ubicacion[alto][ancho];

        inicializar();
    }

    /**
     * Inicializa la isla, creando las instancias de Ubicacion para cada celda
     * y estableciendo una cantidad inicial de plantas en cada una.
     */
    private void inicializar() {
        Configuracion configuracion = Configuracion.getInstance();
        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                cuadricula[i][j] = new Ubicacion(j, i, configuracion);
            }
        }
    }

    /**
     * Devuelve la Ubicacion en las coordenadas especificadas.
     *
     * @param x La coordenada x.
     * @param y La coordenada y.
     * @return La Ubicacion en las coordenadas dadas, o null si las coordenadas
     *         están fuera de los límites de la isla.
     */
    public Ubicacion obtenerUbicacion(int x, int y) {
        lock.lock();
        try {
            if (x >= 0 && x < ancho && y >= 0 && y < alto) {
                return cuadricula[y][x];
            } else {
                return null; // Fuera de los límites de la isla
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Imprime el estado actual de la isla en la consola.
     */
    public void mostrarEstado() {
        System.out.println("Estado actual de la isla:");
        System.out.println();
        
        // Imprimir borde superior
        printBorderTop();
        
        // Imprimir contenido de la isla
        for (int i = 0; i < alto; i++) {
            System.out.print("│ "); // Borde izquierdo
            for (int j = 0; j < ancho; j++) {
                Ubicacion ubicacion = cuadricula[i][j];
                Animal animal = ubicacion.obtenerAnimal();
                
                if (animal != null) {
                    System.out.print(animal.getCaracter());
                } else if (ubicacion.tienePlanta()) {
                    System.out.print("🌿");
                } else {
                    System.out.print("⬜");
                }
                
                // Agregar espacio después de cada celda excepto la última
                if (j < ancho - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println(" │"); // Borde derecho
        }
        
        // Imprimir borde inferior
        printBorderBottom();
        System.out.println();
    }

    /**
     * Imprime el borde superior de la isla.
     */
    private void printBorderTop() {
        System.out.print("┌─");
        for (int j = 0; j < ancho - 1; j++) {
            System.out.print("──");
        }
        System.out.println("─┐");
    }

    /**
     * Imprime el borde inferior de la isla.
     */
    private void printBorderBottom() {
        System.out.print("└─");
        for (int j = 0; j < ancho - 1; j++) {
            System.out.print("──");
        }
        System.out.println("─┘");
    }

    // ------ Métodos Getters ------

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }
}