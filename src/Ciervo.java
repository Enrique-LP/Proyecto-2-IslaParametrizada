/**
 * Clase que representa a un ciervo en la simulación.
 * Extiende la clase Herbivoro.
 */
public class Ciervo extends Herbivoro {

    /**
     * Constructor de la clase Ciervo.
     *
     * @param x             La coordenada x inicial del ciervo.
     * @param y             La coordenada y inicial del ciervo.
     * @param configuracion La configuración de la simulación.
     * @param isla          La isla en la que se encuentra el ciervo.
     */
    public Ciervo(int x, int y, Configuracion configuracion, Isla isla) {
        super(x, y, "Ciervo", configuracion, isla);
        this.peso = configuracion.pesoCiervo;
        this.velocidad = configuracion.velocidadCiervo;
        this.comidaNecesaria = configuracion.comidaNecesariaCiervo;
        this.maxHambre = comidaNecesaria;
        this.caracter = "🦌";
    }

    /**
     * Implementación del método moverse para el ciervo.
     * El ciervo se mueve a una ubicación adyacente aleatoria,
     * considerando su velocidad.
     */
    @Override
    public void moverse() {
        Configuracion configuracion = Configuracion.getInstance();
        int velocidad = configuracion.velocidadCiervo;

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

        // Mover el ciervo a la nueva ubicación (si hay espacio)
        if (ubicacionDestino != null && ubicacionDestino.hayEspacioParaAnimal(this)) {
            isla.obtenerUbicacion(x, y).eliminarAnimal(this); // Eliminar de la ubicación actual
            ubicacionDestino.agregarAnimal(this); // Agregar a la nueva ubicación
            this.x = nuevaX;
            this.y = nuevaY;
        }
    }

    /**
     * Implementación del método reproducirse para el ciervo.
     *
     * @param pareja    La pareja con la que se reproduce el ciervo.
     * @param ubicacion La ubicación en la que se encuentra el ciervo.
     */
    @Override
    public void reproducirse(Animal pareja, Ubicacion ubicacion) {
        // Verificar si la pareja es de la misma especie
        if (!(pareja instanceof Ciervo)) {
            return;
        }

        // Verificar si hay espacio en la ubicación para un nuevo animal
        if (!ubicacion.hayEspacioParaAnimal(this)) {
            return;
        }

        // Crear un nuevo ciervo
        Configuracion configuracion = Configuracion.getInstance();
        Ciervo nuevoCiervo = new Ciervo(x, y, configuracion, isla);

        // Agregar el nuevo ciervo a la ubicación
        ubicacion.agregarAnimal(nuevoCiervo);
    }

    /**
     * Devuelve el carácter Unicode que representa al ciervo.
     *
     * @return El carácter Unicode del ciervo.
     */
    @Override
    public String toString() {
        return "🦌";
    }
}