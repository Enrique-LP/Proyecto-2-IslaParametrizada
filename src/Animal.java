import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Clase abstracta que representa a un animal en la simulación de la isla.
 * Sirve como base para todas las especies concretas de animales.
 */
public abstract class Animal {

    // Posición en la isla
    protected int x;
    protected int y;

    // Especie del animal (podría ser un String o un Enum)
    protected String especie;

    // Nivel de hambre del animal (0 significa que está satisfecho,
    // maxHambre es el nivel máximo antes de morir)
    protected double nivelHambre;

    // Nivel máximo de hambre antes de morir de inanición
    protected double maxHambre;

    // Peso del animal (en kg)
    protected double peso;

    // Velocidad máxima del animal (número de casillas por turno)
    protected int velocidad;

    // Caracter Unicode que representa al animal
    protected String caracter;

    // Cantidad de comida (en kg) necesaria para saciar completamente el hambre
    protected double comidaNecesaria;

    // Dieta del animal: un mapa que asocia cada especie que este animal
    // puede comer con la probabilidad de éxito (entre 0 y 1)
    protected HashMap<String, Double> dieta;

    // Lock para sincronizar el acceso a los atributos del animal
    protected final Lock lock = new ReentrantLock();

    // Isla donde vive el animal
    protected Isla isla;

    // Estado de vida del animal
    protected boolean vivo;

    /**
     * Constructor de la clase Animal.
     *
     * @param x             La coordenada x inicial del animal.
     * @param y             La coordenada y inicial del animal.
     * @param especie       La especie del animal.
     * @param configuracion La configuración de la simulación.
     * @param isla         La isla donde vive el animal.
     */
    public Animal(int x, int y, String especie, Configuracion configuracion, Isla isla) {
        this.x = x;
        this.y = y;
        this.especie = especie;
        this.dieta = new HashMap<>();
        this.isla = isla;
        this.vivo = true;

        // Inicializar los atributos específicos de la especie desde la configuración
        switch (especie) {
            case "Lobo" -> this.maxHambre = configuracion.comidaNecesariaLobo;
            case "Boa" -> this.maxHambre = configuracion.comidaNecesariaBoa;
            case "Zorro" -> this.maxHambre = configuracion.comidaNecesariaZorro;
            case "Oso" -> this.maxHambre = configuracion.comidaNecesariaOso;
            case "Aguila" -> this.maxHambre = configuracion.comidaNecesariaAguila;
            case "Caballo" -> this.maxHambre = configuracion.comidaNecesariaCaballo;
            case "Ciervo" -> this.maxHambre = configuracion.comidaNecesariaCiervo;
            case "Conejo" -> this.maxHambre = configuracion.comidaNecesariaConejo;
            case "Raton" -> this.maxHambre = configuracion.comidaNecesariaRaton;
            case "Cabra" -> this.maxHambre = configuracion.comidaNecesariaCabra;
            case "Oveja" -> this.maxHambre = configuracion.comidaNecesariaOveja;
            case "Jabali" -> this.maxHambre = configuracion.comidaNecesariaJabali;
            case "Bufalo" -> this.maxHambre = configuracion.comidaNecesariaBufalo;
            case "Pato" -> this.maxHambre = configuracion.comidaNecesariaPato;
            case "Oruga" -> this.maxHambre = configuracion.comidaNecesariaOruga;
            default -> throw new IllegalArgumentException("Especie no reconocida: " + especie);
        }
    }

    /**
     * Método abstracto que representa el movimiento del animal.
     * Cada subclase concreta debe implementar este método según el
     * comportamiento específico del animal.
     */
    public abstract void moverse();

    /**
     * Método que representa la muerte del animal.
     * Cuando un animal muere, es eliminado de su ubicación actual.
     */
    public void morir() {
        lock.lock();
        try {
            Ubicacion ubicacionActual = isla.obtenerUbicacion(x, y);
            if (ubicacionActual != null) {
                ubicacionActual.eliminarAnimal(this);
            }
            this.vivo = false;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Método abstracto que representa la reproducción del animal.
     * Cada subclase concreta debe implementar este método según el
     * comportamiento específico del animal.
     *
     * @param pareja La pareja con la que se reproduce el animal.
     * @param ubicacion La ubicación en la que se encuentra el animal.
     */
    public abstract void reproducirse(Animal pareja, Ubicacion ubicacion);

    /**
     * Método abstracto que representa la acción de comer del animal.
     * Cada subclase concreta debe implementar este método según la dieta
     * específica del animal.
     *
     * @param ubicacion La ubicación actual del animal.
     */
    public abstract void comer(Ubicacion ubicacion);

    // ------ Métodos Getters y Setters ------

    public int getX() {
        return x;
    }

    public void setX(int x) {
        lock.lock();
        try {
            this.x = x;
        } finally {
            lock.unlock();
        }
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        lock.lock();
        try {
            this.y = y;
        } finally {
            lock.unlock();
        }
    }

    public String getEspecie() {
        return especie;
    }

    public double getNivelHambre() {
        lock.lock();
        try {
            return nivelHambre;
        } finally {
            lock.unlock();
        }
    }

    public void setNivelHambre(double nivelHambre) {
        lock.lock();
        try {
            this.nivelHambre = nivelHambre;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Comprueba si el animal está vivo (si su nivel de hambre es menor que
     * el nivel máximo de hambre).
     *
     * @return true si el animal está vivo, false en caso contrario.
     */
    public boolean estaVivo() {
        lock.lock();
        try {
            return vivo && nivelHambre < maxHambre;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Devuelve una representación en forma de cadena de caracteres del animal.
     * Se utilizará para la visualización en la consola.
     *
     * @return El carácter Unicode que representa al animal.
     */
    @Override
    public abstract String toString();

    /**
     * Obtiene el carácter que representa al animal en la visualización.
     * @return El carácter Unicode del animal
     */
    public String getCaracter() {
        return caracter;
    }
}