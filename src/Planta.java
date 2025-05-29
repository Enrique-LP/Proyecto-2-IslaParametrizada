/**
 * Clase que representa una planta en la simulación de la isla.
 */
public class Planta {

    // Cantidad de biomasa o unidades de la planta
    private double cantidad;

    // Tasa de crecimiento de la planta (unidades por turno)
    private double tasaCrecimiento;
    
    // Cantidad máxima que puede alcanzar la planta
    private final double cantidadMaxima;
    
    // Caracter Unico de la planta
    private final String caracter;

    // Indica si la planta ha sido completamente consumida
    private boolean consumidaCompletamente;

    /**
     * Constructor de la clase Planta.
     *
     * @param configuracion La configuración de la simulación.
     */
    public Planta(Configuracion configuracion) {
        this.cantidad = configuracion.cantidadInicialPlantas;
        this.tasaCrecimiento = configuracion.tasaCrecimientoPlantas;
        this.cantidadMaxima = configuracion.cantidadMaximaPlantas;
        this.caracter = "🌿";
        this.consumidaCompletamente = false;
    }

    /**
     * Incrementa la cantidad de la planta según su tasa de crecimiento.
     */
    public void crecer() {
        if (!consumidaCompletamente && cantidad < cantidadMaxima) {
            cantidad = Math.min(cantidadMaxima, cantidad + tasaCrecimiento);
        }
    }

    /**
     * Reduce la cantidad de la planta cuando un animal la come.
     *
     * @param cantidadComida La cantidad que el animal consume.
     */
    public void serComida(double cantidadComida) {
        cantidad -= cantidadComida;
        if (cantidad <= 0) {
            cantidad = 0;
            consumidaCompletamente = true; // Marcar como completamente consumida
        }
    }

    // ------ Métodos Getters y Setters ------

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public boolean estaConsumidaCompletamente() {
        return consumidaCompletamente;
    }

    /**
     * Devuelve una representación en forma de cadena de caracteres de la planta.
     * Se utilizará para la visualización en la consola.
     *
     * @return El carácter Unicode que representa a la planta.
     */
    @Override
    public String toString() {
        return consumidaCompletamente ? "⬜" : "🌿";
    }

    public String getCaracter() {
        return consumidaCompletamente ? "⬜" : caracter;
    }
}