/**
 * Clase que representa a una oveja en la simulación.
 * Extiende la clase Herbivoro.
 */
public class Oveja extends Herbivoro {

    /**
     * Constructor de la clase Oveja.
     *
     * @param x             La coordenada x inicial de la oveja.
     * @param y             La coordenada y inicial de la oveja.
     * @param configuracion La configuración de la simulación.
     * @param isla          La isla en la que se encuentra la oveja.
     */
    public Oveja(int x, int y, Configuracion configuracion, Isla isla) {
        super(x, y, "Oveja", configuracion, isla);
        this.peso = configuracion.pesoOveja;
        this.velocidad = configuracion.velocidadOveja;
        this.comidaNecesaria = configuracion.comidaNecesariaOveja;
        this.maxHambre = comidaNecesaria;
        this.caracter = "🐑";
    }

    /**
     * Implementación del método moverse para la oveja.
     * La oveja se mueve a una ubicación adyacente aleatoria,
     * considerando su velocidad.
     */
    @Override
    public void moverse() {
        Configuracion configuracion = Configuracion.getInstance();
        int velocidad = configuracion.velocidadOveja;

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

        // Mover la oveja a la nueva ubicación (si hay espacio)
        if (ubicacionDestino != null && ubicacionDestino.hayEspacioParaAnimal(this)) {
            isla.obtenerUbicacion(x, y).eliminarAnimal(this); // Eliminar de la ubicación actual
            ubicacionDestino.agregarAnimal(this); // Agregar a la nueva ubicación
            this.x = nuevaX;
            this.y = nuevaY;
        }
    }

    /**
     * Implementación del método reproducirse para la oveja.
     *
     * @param pareja    La pareja con la que se reproduce la oveja.
     * @param ubicacion La ubicación en la que se encuentra la oveja.
     */
    @Override
    public void reproducirse(Animal pareja, Ubicacion ubicacion) {
        // Verificar si la pareja es de la misma especie
        if (!(pareja instanceof Oveja)) {
            return;
        }

        // Verificar si hay espacio en la ubicación para un nuevo animal
        if (!ubicacion.hayEspacioParaAnimal(this)) {
            return;
        }

        // Crear una nueva oveja
        Configuracion configuracion = Configuracion.getInstance();
        Oveja nuevaOveja = new Oveja(x, y, configuracion, isla);

        // Agregar la nueva oveja a la ubicación
        ubicacion.agregarAnimal(nuevaOveja);
    }

    /**
     * Devuelve el carácter Unicode que representa a la oveja.
     *
     * @return El carácter Unicode de la oveja.
     */
    @Override
    public String toString() {
        return "🐑";
    }
}