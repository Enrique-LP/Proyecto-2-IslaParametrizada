/**
 * Clase que representa a un águila en la simulación.
 * Extiende la clase Carnivoro.
 */
public class Aguila extends Carnivoro {

    /**
     * Constructor de la clase Aguila.
     *
     * @param x             La coordenada x inicial del águila.
     * @param y             La coordenada y inicial del águila.
     * @param configuracion La configuración de la simulación.
     * @param isla          La isla en la que se encuentra el águila.
     */
    public Aguila(int x, int y, Configuracion configuracion, Isla isla) {
        super(x, y, "Aguila", configuracion, isla);
        this.peso = configuracion.pesoAguila;
        this.velocidad = configuracion.velocidadAguila;
        this.comidaNecesaria = configuracion.comidaNecesariaAguila;
        this.maxHambre = comidaNecesaria;
        this.caracter = "🦅";
    }

    /**
     * Implementación del método moverse para el águila.
     * El águila se mueve a una ubicación adyacente aleatoria,
     * considerando su velocidad.
     */
    @Override
    public void moverse() {
        Configuracion configuracion = Configuracion.getInstance();
        int velocidad = configuracion.velocidadAguila;

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

        // Mover el águila a la nueva ubicación (si hay espacio)
        if (ubicacionDestino != null && ubicacionDestino.hayEspacioParaAnimal(this)) {
            isla.obtenerUbicacion(x, y).eliminarAnimal(this); // Eliminar de la ubicación actual
            ubicacionDestino.agregarAnimal(this); // Agregar a la nueva ubicación
            this.x = nuevaX;
            this.y = nuevaY;
        }
    }

    /**
     * Implementación del método reproducirse para el águila.
     *
     * @param pareja    La pareja con la que se reproduce el águila.
     * @param ubicacion La ubicación en la que se encuentra el águila.
     */
    @Override
    public void reproducirse(Animal pareja, Ubicacion ubicacion) {
        // Verificar si la pareja es de la misma especie
        if (!(pareja instanceof Aguila)) {
            return;
        }

        // Verificar si hay espacio en la ubicación para un nuevo animal
        if (!ubicacion.hayEspacioParaAnimal(this)) {
            return;
        }

        // Crear una nueva águila
        Configuracion configuracion = Configuracion.getInstance();
        Aguila nuevaAguila = new Aguila(x, y, configuracion, isla);

        // Agregar el nuevo águila a la ubicación
        ubicacion.agregarAnimal(nuevaAguila);
    }

    /**
     * Devuelve el carácter Unicode que representa al águila.
     *
     * @return El carácter Unicode del águila.
     */
    @Override
    public String toString() {
        return "🦅";
    }
}