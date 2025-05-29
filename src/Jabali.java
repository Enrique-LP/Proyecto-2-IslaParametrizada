/**
 * Clase que representa a un jabalí en la simulación.
 * Extiende la clase Herbivoro.
 */
public class Jabali extends Herbivoro {

    /**
     * Constructor de la clase Jabali.
     *
     * @param x             La coordenada x inicial del jabalí.
     * @param y             La coordenada y inicial del jabalí.
     * @param configuracion La configuración de la simulación.
     * @param isla          La isla en la que se encuentra el jabalí.
     */
    public Jabali(int x, int y, Configuracion configuracion, Isla isla) {
        super(x, y, "Jabali", configuracion, isla);
        this.peso = configuracion.pesoJabali;
        this.velocidad = configuracion.velocidadJabali;
        this.comidaNecesaria = configuracion.comidaNecesariaJabali;
        this.maxHambre = comidaNecesaria;
        this.caracter = "🐗";
    }

    /**
     * Implementación del método moverse para el jabalí.
     * El jabalí se mueve a una ubicación adyacente aleatoria,
     * considerando su velocidad.
     */
    @Override
    public void moverse() {
        Configuracion configuracion = Configuracion.getInstance();
        int velocidad = configuracion.velocidadJabali;

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

        // Mover el jabalí a la nueva ubicación (si hay espacio)
        if (ubicacionDestino != null && ubicacionDestino.hayEspacioParaAnimal(this)) {
            isla.obtenerUbicacion(x, y).eliminarAnimal(this); // Eliminar de la ubicación actual
            ubicacionDestino.agregarAnimal(this); // Agregar a la nueva ubicación
            this.x = nuevaX;
            this.y = nuevaY;
        }
    }

    /**
     * Implementación del método reproducirse para el jabalí.
     *
     * @param pareja    La pareja con la que se reproduce el jabalí.
     * @param ubicacion La ubicación en la que se encuentra el jabalí.
     */
    @Override
    public void reproducirse(Animal pareja, Ubicacion ubicacion) {
        // Verificar si la pareja es de la misma especie
        if (!(pareja instanceof Jabali)) {
            return;
        }

        // Verificar si hay espacio en la ubicación para un nuevo animal
        if (!ubicacion.hayEspacioParaAnimal(this)) {
            return;
        }

        // Crear un nuevo jabalí
        Configuracion configuracion = Configuracion.getInstance();
        Jabali nuevoJabali = new Jabali(x, y, configuracion, isla);

        // Agregar el nuevo jabalí a la ubicación
        ubicacion.agregarAnimal(nuevoJabali);
    }

    /**
     * Devuelve el carácter Unicode que representa al jabalí.
     *
     * @return El carácter Unicode del jabalí.
     */
    @Override
    public String toString() {
        return "🐗";
    }
}