/**
 * Clase que representa a una boa en la simulación.
 * Extiende la clase Carnivoro.
 */
public class Boa extends Carnivoro {

    /**
     * Constructor de la clase Boa.
     *
     * @param x             La coordenada x inicial de la boa.
     * @param y             La coordenada y inicial de la boa.
     * @param configuracion La configuración de la simulación.
     * @param isla          La isla en la que se encuentra la boa.
     */
    public Boa(int x, int y, Configuracion configuracion, Isla isla) {
        super(x, y, "Boa", configuracion, isla);
        this.peso = configuracion.pesoBoa;
        this.velocidad = configuracion.velocidadBoa;
        this.comidaNecesaria = configuracion.comidaNecesariaBoa;
        this.maxHambre = comidaNecesaria;
        this.caracter = "🐍";
    }

    /**
     * Implementación del método moverse para la boa.
     * La boa se mueve a una ubicación adyacente aleatoria,
     * considerando su velocidad.
     */
    @Override
    public void moverse() {
        Configuracion configuracion = Configuracion.getInstance();
        int velocidad = configuracion.velocidadBoa;

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

        // Mover la boa a la nueva ubicación (si hay espacio)
        if (ubicacionDestino != null && ubicacionDestino.hayEspacioParaAnimal(this)) {
            isla.obtenerUbicacion(x, y).eliminarAnimal(this); // Eliminar de la ubicación actual
            ubicacionDestino.agregarAnimal(this); // Agregar a la nueva ubicación
            this.x = nuevaX;
            this.y = nuevaY;
        }
    }

    /**
     * Implementación del método reproducirse para la boa.
     *
     * @param pareja    La pareja con la que se reproduce la boa.
     * @param ubicacion La ubicación en la que se encuentra la boa.
     */
    @Override
    public void reproducirse(Animal pareja, Ubicacion ubicacion) {
        // Verificar si la pareja es de la misma especie
        if (!(pareja instanceof Boa)) {
            return;
        }

        // Verificar si hay espacio en la ubicación para un nuevo animal
        if (!ubicacion.hayEspacioParaAnimal(this)) {
            return;
        }

        // Crear una nueva boa
        Configuracion configuracion = Configuracion.getInstance();
        Boa nuevaBoa = new Boa(x, y, configuracion, isla);

        // Agregar la nueva boa a la ubicación
        ubicacion.agregarAnimal(nuevaBoa);
    }

    /**
     * Devuelve el carácter Unicode que representa a la boa.
     *
     * @return El carácter Unicode de la boa.
     */
    @Override
    public String toString() {
        return "🐍";
    }
}