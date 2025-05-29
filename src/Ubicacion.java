import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Clase que representa una ubicación (celda) en la isla.
 * Contiene información sobre los animales y la cantidad de plantas en esa ubicación.
 */
public class Ubicacion {

    private int x;
    private int y;
    private List<Animal> animales;
    private Planta planta;
    private final Lock lock = new ReentrantLock(); // Lock para sincronizar el acceso

    /**
     * Constructor de la clase Ubicacion.
     *
     * @param x            La coordenada x de la ubicación.
     * @param y            La coordenada y de la ubicación.
     * @param configuracion La configuración de la simulación.
     */
    public Ubicacion(int x, int y, Configuracion configuracion) {
        this.x = x;
        this.y = y;
        // Se usa CopyOnWriteArrayList para permitir la concurrencia
        this.animales = new CopyOnWriteArrayList<>();
        this.planta = new Planta(configuracion);
    }

    /**
     * Agrega un animal a la ubicación, si hay espacio.
     *
     * @param animal El animal que se va a agregar.
     * @return true si se ha podido agregar el animal, false en caso contrario.
     */
    public boolean agregarAnimal(Animal animal) {
        lock.lock();
        try {
            Configuracion configuracion = Configuracion.getInstance();
            int maximoAnimales = 0;

            // Obtener el límite máximo de animales de la especie actual
            if (animal instanceof Lobo) {
                maximoAnimales = configuracion.maxLobosPorUbicacion;
            } else if (animal instanceof Boa) {
                maximoAnimales = configuracion.maxBoasPorUbicacion;
            } else if (animal instanceof Zorro) {
                maximoAnimales = configuracion.maxZorrosPorUbicacion;
            } else if (animal instanceof Oso) {
                maximoAnimales = configuracion.maxOsosPorUbicacion;
            } else if (animal instanceof Aguila) {
                maximoAnimales = configuracion.maxAguilasPorUbicacion;
            } else if (animal instanceof Caballo) {
                maximoAnimales = configuracion.maxCaballosPorUbicacion;
            } else if (animal instanceof Ciervo) {
                maximoAnimales = configuracion.maxCiervosPorUbicacion;
            } else if (animal instanceof Conejo) {
                maximoAnimales = configuracion.maxConejosPorUbicacion;
            } else if (animal instanceof Raton) {
                maximoAnimales = configuracion.maxRatonesPorUbicacion;
            } else if (animal instanceof Cabra) {
                maximoAnimales = configuracion.maxCabrasPorUbicacion;
            } else if (animal instanceof Oveja) {
                maximoAnimales = configuracion.maxOvejasPorUbicacion;
            } else if (animal instanceof Jabali) {
                maximoAnimales = configuracion.maxJabaliesPorUbicacion;
            } else if (animal instanceof Bufalo) {
                maximoAnimales = configuracion.maxBufalosPorUbicacion;
            } else if (animal instanceof Pato) {
                maximoAnimales = configuracion.maxPatosPorUbicacion;
            } else if (animal instanceof Oruga) {
                maximoAnimales = configuracion.maxOrugasPorUbicacion;
            }

            // Verificar si ya se ha alcanzado el límite para esta especie
            if (obtenerCantidadAnimales(animal.getEspecie()) >= maximoAnimales) {
                return false; // No se puede agregar el animal
            }

            animales.add(animal);
            return true;

        } finally {
            lock.unlock();
        }
    }

    /**
     * Elimina un animal de la ubicación.
     *
     * @param animal El animal que se va a eliminar.
     */
    public void eliminarAnimal(Animal animal) {
        lock.lock();
        try {
            animales.remove(animal);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Devuelve la cantidad de animales de una especie determinada en la ubicación.
     *
     * @param especie La especie de la que se quiere saber la cantidad.
     * @return El número de animales de esa especie en la ubicación.
     */
    public int obtenerCantidadAnimales(String especie) {
        lock.lock();
        try {
            int cantidad = 0;
            for (Animal a : animales) {
                if (a.getEspecie().equals(especie)) {
                    cantidad++;
                }
            }
            return cantidad;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Devuelve la cantidad total de animales en la ubicación.
     *
     * @return El número total de animales en la ubicación.
     */
    public int obtenerCantidadAnimales() {
        return animales.size();
    }

    /**
     * Devuelve la cantidad de plantas en la ubicación.
     *
     * @return La cantidad de plantas.
     */
    public double obtenerCantidadPlantas() {
        lock.lock();
        try {
            return planta.getCantidad();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Hace crecer las plantas en la ubicación.
     */
    public void crecerPlantas() {
        lock.lock();
        try {
            planta.crecer();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Comprueba si hay espacio para agregar un animal de una especie determinada
     * en la ubicación, según el límite máximo por especie.
     *
     * @param animal El animal que se quiere agregar.
     * @return true si hay espacio para el animal, false en caso contrario.
     */
    public boolean hayEspacioParaAnimal(Animal animal) {
        return obtenerCantidadAnimales(animal.getEspecie()) < obtenerMaximoAnimalesPorEspecie(animal.getEspecie());
    }

    public String getPlantaString() {
        return planta.toString();
    }

    public void consumirPlanta(double cantidad) {
        planta.serComida(cantidad);
    }

    /**
     * Obtiene el primer animal de la ubicación.
     * @return El primer animal en la lista o null si no hay animales.
     */
    public Animal obtenerAnimal() {
        lock.lock();
        try {
            return animales.isEmpty() ? null : animales.get(0);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Verifica si hay una planta en esta ubicación.
     * @return true si hay una planta con cantidad mayor a 0, false en caso contrario.
     */
    public boolean tienePlanta() {
        lock.lock();
        try {
            return planta != null && planta.getCantidad() > 0.1;
        } finally {
            lock.unlock();
        }
    }

    // ------ Métodos Getters y Setters ------

    public List<Animal> getAnimales() {
        return animales;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Obtiene el límite máximo de animales de una especie determinada para esta ubicación.
     *
     * @param especie La especie de la que se quiere obtener el límite.
     * @return El límite máximo de animales de esa especie en esta ubicación.
     */
    private int obtenerMaximoAnimalesPorEspecie(String especie) {
        Configuracion configuracion = Configuracion.getInstance();
        switch (especie) {
            case "Lobo":
                return configuracion.maxLobosPorUbicacion;
            case "Boa":
                return configuracion.maxBoasPorUbicacion;
            case "Zorro":
                return configuracion.maxZorrosPorUbicacion;
            case "Oso":
                return configuracion.maxOsosPorUbicacion;
            case "Aguila":
                return configuracion.maxAguilasPorUbicacion;
            case "Caballo":
                return configuracion.maxCaballosPorUbicacion;
            case "Ciervo":
                return configuracion.maxCiervosPorUbicacion;
            case "Conejo":
                return configuracion.maxConejosPorUbicacion;
            case "Raton":
                return configuracion.maxRatonesPorUbicacion;
            case "Cabra":
                return configuracion.maxCabrasPorUbicacion;
            case "Oveja":
                return configuracion.maxOvejasPorUbicacion;
            case "Jabali":
                return configuracion.maxJabaliesPorUbicacion;
            case "Bufalo":
                return configuracion.maxBufalosPorUbicacion;
            case "Pato":
                return configuracion.maxPatosPorUbicacion;
            case "Oruga":
                return configuracion.maxOrugasPorUbicacion;
            default:
                return 0; // Valor predeterminado o lanzar una excepción
        }
    }
}