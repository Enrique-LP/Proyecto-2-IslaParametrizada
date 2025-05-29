/**
 * Clase que representa a un conejo en la simulación.
 * Extiende la clase Herbivoro.
 */
public class Conejo extends Herbivoro {

    /**
     * Constructor de la clase Conejo.
     *
     * @param x             La coordenada x inicial del conejo.
     * @param y             La coordenada y inicial del conejo.
     * @param configuracion La configuración de la simulación.
     * @param isla          La isla en la que se encuentra el conejo.
     */
    public Conejo(int x, int y, Configuracion configuracion, Isla isla) {
        super(x, y, "Conejo", configuracion, isla);
        this.peso = configuracion.pesoConejo;
        this.velocidad = configuracion.velocidadConejo;
        this.comidaNecesaria = configuracion.comidaNecesariaConejo;
        this.maxHambre = comidaNecesaria;
        this.caracter = "🐰";
    }

    /**
     * Implementación del método moverse para el conejo.
     * Por ahora, el conejo se mueve a una ubicación adyacente aleatoria.
     */
    @Override
    public void moverse() {
        Configuracion configuracion = Configuracion.getInstance();
        int velocidad = configuracion.velocidadConejo;

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

        // Mover el conejo a la nueva ubicación (si hay espacio)
        if (ubicacionDestino != null && ubicacionDestino.hayEspacioParaAnimal(this)) {
            isla.obtenerUbicacion(x, y).eliminarAnimal(this); // Eliminar de la ubicación actual
            ubicacionDestino.agregarAnimal(this); // Agregar a la nueva ubicación
            this.x = nuevaX;
            this.y = nuevaY;
        }
    }

    /**
     * Implementación del método reproducirse para el conejo.
     *
     * @param pareja    La pareja con la que se reproduce el conejo.
     * @param ubicacion La ubicación en la que se encuentra el conejo.
     */
    @Override
    public void reproducirse(Animal pareja, Ubicacion ubicacion) {
        // Verificar si la pareja es de la misma especie
        if (!(pareja instanceof Conejo)) {
            return;
        }

        // Verificar si hay espacio en la ubicación para un nuevo conejo
        if (!ubicacion.hayEspacioParaAnimal(this)) {
            return;
        }

        // Crear un nuevo conejo
        Configuracion configuracion = Configuracion.getInstance();
        Conejo nuevoConejo = new Conejo(x, y, configuracion, isla);

        // Agregar el nuevo conejo a la ubicación
        ubicacion.agregarAnimal(nuevoConejo);
    }

    /**
     * Devuelve el carácter Unicode que representa al conejo.
     *
     * @return El carácter Unicode del conejo.
     */
    @Override
    public String toString() {
        return "🐇";
    }
}