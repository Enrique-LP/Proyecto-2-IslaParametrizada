import java.util.concurrent.ThreadLocalRandom;

/**
 * Clase abstracta que representa a un animal herbívoro en la simulación.
 * Extiende la clase Animal y proporciona una implementación parcial del método comer.
 */
public abstract class Herbivoro extends Animal {

    /**
     * Constructor de la clase Herbivoro.
     *
     * @param x             La coordenada x inicial del animal.
     * @param y             La coordenada y inicial del animal.
     * @param especie       La especie del animal.
     * @param configuracion La configuración de la simulación.
     * @param isla          La isla en la que se encuentra el animal.
     */
    public Herbivoro(int x, int y, String especie, Configuracion configuracion, Isla isla) {
        super(x, y, especie, configuracion, isla);
        this.nivelHambre = 0;
        inicializarDieta(configuracion);
    }

    private void inicializarDieta(Configuracion configuracion) {
        switch (especie) {
            case "Caballo" -> dieta.put("Planta", configuracion.probabilidadCaballoComePlanta);
            case "Ciervo" -> dieta.put("Planta", configuracion.probabilidadCiervoComePlanta);
            case "Conejo" -> dieta.put("Planta", configuracion.probabilidadConejoComePlanta);
            case "Raton" -> dieta.put("Planta", configuracion.probabilidadRatonComePlanta);
            case "Cabra" -> dieta.put("Planta", configuracion.probabilidadCabraComePlanta);
            case "Oveja" -> dieta.put("Planta", configuracion.probabilidadOvejaComePlanta);
            case "Jabali" -> dieta.put("Planta", configuracion.probabilidadJabaliComePlanta);
            case "Bufalo" -> dieta.put("Planta", configuracion.probabilidadBufaloComePlanta);
            case "Pato" -> {
                dieta.put("Planta", configuracion.probabilidadPatoComePlanta);
                dieta.put("Oruga", configuracion.probabilidadPatoComeOruga);
            }
            case "Oruga" -> dieta.put("Planta", configuracion.probabilidadOrugaComePlanta);
            default -> System.out.println("especie no implementada");
        }
    }

    /**
     * Implementación del método comer para los herbívoros.
     * Los herbívoros comen plantas que se encuentran en su ubicación actual.
     *
     * @param ubicacion La ubicación actual del animal.
     */
    @Override
    public void comer(Ubicacion ubicacion) {
        // Si el animal no tiene hambre, no necesita comer
        if (nivelHambre <= 0) {
            return;
        }

        // Verificar si hay plantas en la ubicación
        if (ubicacion.tienePlanta()) {
            // Obtener la probabilidad de comer planta
            double probabilidad = dieta.getOrDefault("Planta", 0.0);
            
            // Intentar comer con más insistencia si tiene mucha hambre
            double factorHambre = nivelHambre / maxHambre;
            probabilidad = Math.min(1.0, probabilidad * (1 + factorHambre));

            // Generar un número aleatorio para determinar si come
            if (ThreadLocalRandom.current().nextDouble() <= probabilidad) {
                // Calcular cuánto come (más si tiene más hambre)
                double cantidadAConsumir = Math.min(comidaNecesaria * (1 + factorHambre), ubicacion.obtenerCantidadPlantas());
                
                // Consumir las plantas
                ubicacion.consumirPlanta(cantidadAConsumir);
                
                // Reducir el nivel de hambre proporcionalmente a lo que comió
                nivelHambre = Math.max(0, nivelHambre - cantidadAConsumir);
            }
        }
    }
}