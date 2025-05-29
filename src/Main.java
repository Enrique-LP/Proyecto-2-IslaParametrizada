public class Main {

    public static void main(String[] args) {
        // Establecer la codificación del sistema
        System.setProperty("file.encoding", "UTF-8");
        
        // Configurar la salida para usar UTF-8
        try {
            // Intentar configurar la consola para Windows
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                // Intentar cambiar la página de códigos a UTF-8
                ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "chcp", "65001");
                pb.inheritIO();
                Process process = pb.start();
                process.waitFor();
            }
        } catch (Exception e) {
            System.err.println("No se pudo configurar la codificación de la consola");
        }

        // Crear una instancia de la configuración
        Configuracion configuracion = Configuracion.getInstance();

        // Configurar los parámetros de la simulación
        configuracion.anchoIsla = 10;
        configuracion.altoIsla = 10;
        configuracion.duracionCiclo = 1000; // 1 segundo por turno
        configuracion.maximoTurnos = 100;

        // Ajustar la población inicial para una mejor distribución
        configuracion.numeroInicialLobos = 2;
        configuracion.numeroInicialConejos = 4;
        configuracion.numeroInicialZorros = 2;
        configuracion.numeroInicialOsos = 2;
        configuracion.numeroInicialAguilas = 2;
        configuracion.numeroInicialCaballos = 3;
        configuracion.numeroInicialCiervos = 3;
        configuracion.numeroInicialRatones = 5;
        configuracion.numeroInicialCabras = 3;
        configuracion.numeroInicialOvejas = 3;
        configuracion.numeroInicialJabalies = 2;
        configuracion.numeroInicialBufalos = 2;
        configuracion.numeroInicialPatos = 4;
        configuracion.numeroInicialOrugas = 6;
        configuracion.numeroInicialBoas = 2;

        // Ajustar los parámetros de las plantas
        configuracion.cantidadInicialPlantas = 5.0;
        configuracion.tasaCrecimientoPlantas = 0.5;

        // Crear una instancia de la simulación
        Simulacion simulacion = new Simulacion(configuracion);

        // Inicializar y ejecutar la simulación
        simulacion.inicializar();
        simulacion.ejecutar();
    }
}