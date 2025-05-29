import java.util.concurrent.ThreadLocalRandom;

/**
 * Clase abstracta que representa a un animal carnívoro en la simulación.
 * Extiende la clase Animal y proporciona una implementación parcial del método comer.
 */
public abstract class Carnivoro extends Animal {

    /**
     * Constructor de la clase Carnivoro.
     *
     * @param x             La coordenada x inicial del animal.
     * @param y             La coordenada y inicial del animal.
     * @param especie       La especie del animal.
     * @param configuracion La configuración de la simulación.
     * @param isla          La isla en la que se encuentra el animal.
     */
    public Carnivoro(int x, int y, String especie, Configuracion configuracion, Isla isla) {
        super(x, y, especie, configuracion, isla);
        this.nivelHambre = 0;
        inicializarDieta(configuracion);
    }

    /**
     * Inicializa la dieta del carnívoro según su especie.
     * 
     * @param configuracion La configuración que contiene las probabilidades de caza
     */
    protected void inicializarDieta(Configuracion configuracion) {
        switch (especie) {
            case "Lobo" -> {
                dieta.put("Conejo", configuracion.probabilidadLoboComeConejo);
                dieta.put("Caballo", configuracion.probabilidadLoboComeCaballo);
                dieta.put("Ciervo", configuracion.probabilidadLoboComeCiervo);
                dieta.put("Raton", configuracion.probabilidadLoboComeRaton);
                dieta.put("Cabra", configuracion.probabilidadLoboComeCabra);
                dieta.put("Oveja", configuracion.probabilidadLoboComeOveja);
                dieta.put("Jabali", configuracion.probabilidadLoboComeJabali);
                dieta.put("Bufalo", configuracion.probabilidadLoboComeBufalo);
                dieta.put("Pato", configuracion.probabilidadLoboComePato);
            }
            case "Boa" -> {
                dieta.put("Zorro", configuracion.probabilidadBoaComeZorro);
                dieta.put("Conejo", configuracion.probabilidadBoaComeConejo);
                dieta.put("Raton", configuracion.probabilidadBoaComeRaton);
                dieta.put("Pato", configuracion.probabilidadBoaComePato);
            }
            case "Zorro" -> {
                dieta.put("Conejo", configuracion.probabilidadZorroComeConejo);
                dieta.put("Raton", configuracion.probabilidadZorroComeRaton);
                dieta.put("Pato", configuracion.probabilidadZorroComePato);
                dieta.put("Oruga", configuracion.probabilidadZorroComeOruga);
            }
            case "Oso" -> {
                dieta.put("Boa", configuracion.probabilidadOsoComeBoa);
                dieta.put("Caballo", configuracion.probabilidadOsoComeCaballo);
                dieta.put("Ciervo", configuracion.probabilidadOsoComeCiervo);
                dieta.put("Conejo", configuracion.probabilidadOsoComeConejo);
                dieta.put("Raton", configuracion.probabilidadOsoComeRaton);
                dieta.put("Cabra", configuracion.probabilidadOsoComeCabra);
                dieta.put("Oveja", configuracion.probabilidadOsoComeOveja);
                dieta.put("Jabali", configuracion.probabilidadOsoComeJabali);
                dieta.put("Bufalo", configuracion.probabilidadOsoComeBufalo);
                dieta.put("Pato", configuracion.probabilidadOsoComePato);
            }
            case "Aguila" -> {
                dieta.put("Zorro", configuracion.probabilidadAguilaComeZorro);
                dieta.put("Conejo", configuracion.probabilidadAguilaComeConejo);
                dieta.put("Raton", configuracion.probabilidadAguilaComeRaton);
                dieta.put("Pato", configuracion.probabilidadAguilaComePato);
            }
        }
    }

    /**
     * Implementación del método comer para los carnívoros.
     * Los carnívoros comen otros animales que se encuentran en su misma ubicación.
     *
     * @param ubicacion La ubicación actual del animal.
     */
    @Override
    public void comer(Ubicacion ubicacion) {
        // Si el animal no tiene hambre, no necesita comer
        if (nivelHambre <= 0) {
            return;
        }

        // Obtener la lista de animales en la ubicación
        var animalesEnUbicacion = ubicacion.getAnimales();

        // Intentar comerse a cada animal en la ubicación
        for (Animal presa : animalesEnUbicacion) {
            // Un carnívoro no se come a sí mismo ni a animales de su misma especie
            if (this == presa || this.especie.equals(presa.especie)) {
                continue;
            }

            // Obtener la probabilidad de que este carnívoro se coma a la presa
            double probabilidad = dieta.getOrDefault(presa.getEspecie(), 0.0);

            // Generar un número aleatorio para determinar si el intento de caza es exitoso
            if (ThreadLocalRandom.current().nextDouble() <= probabilidad) {
                // El carnívoro se come a la presa
                presa.morir(); // La presa es eliminada de la simulación

                // Reducir el nivel de hambre del carnívoro
                // (podríamos usar un valor fijo o basarlo en el peso de la presa,
                // por ahora uso un valor fijo que es la comidaNecesaria del depredador)
                nivelHambre = Math.max(0, nivelHambre - comidaNecesaria);

                // Si el carnívoro ha saciado su hambre, no necesita seguir comiendo
                if (nivelHambre <= 0) {
                    return;
                }
            }
        }
    }
}