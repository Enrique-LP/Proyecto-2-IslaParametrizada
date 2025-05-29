import java.util.concurrent.ThreadLocalRandom;

/**
 * Clase que representa a un pato en la simulación.
 * Extiende la clase Herbivoro.
 */
public class Pato extends Herbivoro {

    /**
     * Constructor de la clase Pato.
     *
     * @param x             La coordenada x inicial del pato.
     * @param y             La coordenada y inicial del pato.
     * @param configuracion La configuración de la simulación.
     * @param isla          La isla en la que se encuentra el pato.
     */
    public Pato(int x, int y, Configuracion configuracion, Isla isla) {
        super(x, y, "Pato", configuracion, isla);
        this.peso = configuracion.pesoPato;
        this.velocidad = configuracion.velocidadPato;
        this.comidaNecesaria = configuracion.comidaNecesariaPato;
        this.maxHambre = comidaNecesaria;
        this.caracter = "🦆";
    }

    /**
     * Implementación del método moverse para el pato.
     * El pato se mueve a una ubicación adyacente aleatoria,
     * considerando su velocidad.
     */
    @Override
    public void moverse() {
        Configuracion configuracion = Configuracion.getInstance();
        int velocidad = configuracion.velocidadPato;

        // Obtener una dirección aleatoria (0: arriba, 1: derecha, 2: abajo, 3: izquierda)
        int direccion = (int) (Math.random() * 4);

        // Calcular el desplazamiento en función de la dirección y la velocidad
        int deltaX = 0;
        int deltaY = 0;
        switch (direccion) {
            case 0 -> deltaY = -velocidad; // Arriba
            case 1 -> deltaX = velocidad; // Derecha
            case 2 -> deltaY = velocidad; // Abajo
            case 3 -> deltaX = -velocidad; // Izquierda
        }

        // Calcular las nuevas coordenadas
        int nuevaX = x + deltaX;
        int nuevaY = y + deltaY;

        // Asegurarse de que las nuevas coordenadas estén dentro de los límites de la isla
        nuevaX = Math.max(0, Math.min(isla.getAncho() - 1, nuevaX));
        nuevaY = Math.max(0, Math.min(isla.getAlto() - 1, nuevaY));

        // Obtener la ubicación de destino
        Ubicacion ubicacionDestino = isla.obtenerUbicacion(nuevaX, nuevaY);

        // Mover el pato a la nueva ubicación (si hay espacio)
        if (ubicacionDestino != null && ubicacionDestino.hayEspacioParaAnimal(this)) {
            isla.obtenerUbicacion(x, y).eliminarAnimal(this); // Eliminar de la ubicación actual
            ubicacionDestino.agregarAnimal(this); // Agregar a la nueva ubicación
            this.x = nuevaX;
            this.y = nuevaY;
        }
    }

    /**
     * Implementación del método reproducirse para el pato.
     *
     * @param pareja    La pareja con la que se reproduce el pato.
     * @param ubicacion La ubicación en la que se encuentra el pato.
     */
    @Override
    public void reproducirse(Animal pareja, Ubicacion ubicacion) {
        // Verificar si la pareja es de la misma especie
        if (!(pareja instanceof Pato)) {
            return;
        }

        // Verificar si hay espacio en la ubicación para un nuevo animal
        if (!ubicacion.hayEspacioParaAnimal(this)) {
            return;
        }

        // Crear un nuevo pato
        Configuracion configuracion = Configuracion.getInstance();
        Pato nuevoPato = new Pato(x, y, configuracion, isla);

        // Agregar el nuevo pato a la ubicación
        ubicacion.agregarAnimal(nuevoPato);
    }

    /**
     * Implementación del método comer para el pato.
     * El pato come plantas y orugas.
     *
     * @param ubicacion La ubicación actual del pato.
     */
    @Override
    public void comer(Ubicacion ubicacion) {
        // Si el pato no tiene hambre, no necesita comer
        if (nivelHambre <= 0) {
            return;
        }

        // Primero, el pato intenta comer plantas (lógica heredada de Herbivoro)
        super.comer(ubicacion);

        // Si después de comer plantas, el pato todavía tiene hambre, intenta comer orugas
        if (nivelHambre > 0) {
            // Obtener la lista de animales en la ubicación
            var animalesEnUbicacion = ubicacion.getAnimales();

            // Intentar comerse a cada animal en la ubicación
            for (Animal presa : animalesEnUbicacion) {
                // El pato solo come orugas
                if (!(presa instanceof Oruga)) {
                    continue;
                }

                // Obtener la probabilidad de que el pato se coma a la oruga
                double probabilidad = dieta.getOrDefault(presa.getEspecie(), 0.0);

                // Generar un número aleatorio para determinar si el intento de caza es exitoso
                if (ThreadLocalRandom.current().nextDouble() <= probabilidad) {
                    // El pato se come a la oruga
                    presa.morir(); // La oruga es eliminada de la simulación

                    // Reducir el nivel de hambre del pato
                    nivelHambre = Math.max(0, nivelHambre - comidaNecesaria);

                    // Si el pato ha saciado su hambre, no necesita seguir comiendo
                    if (nivelHambre <= 0) {
                        return;
                    }
                }
            }
        }
    }

    /**
     * Devuelve el carácter Unicode que representa al pato.
     *
     * @return El carácter Unicode del pato.
     */
    @Override
    public String toString() {
        return "🦆";
    }
}