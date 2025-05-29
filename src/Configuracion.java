/**
 * Clase que almacena todos los parámetros de configuración de la simulación.
 * Se implementa como un Singleton para que haya una única instancia accesible
 * desde cualquier parte del código.
 */
public class Configuracion {

    // Instancia única de la clase (Singleton)
    private static Configuracion instance = null;

    // ------ Parámetros de la Isla ------
    public int anchoIsla = 100;
    public int altoIsla = 20;
    public int numeroHilosAnimales = 10; // Número de hilos para procesar las acciones de los animales

    // ------ Parámetros de Tiempo ------
    public int duracionCiclo = 500; // Duración de un turno en milisegundos

    // ------ Parámetros de Población Inicial ------
    // (Valores de ejemplo, se pueden modificar)
    public int numeroInicialLobos = 10;
    public int numeroInicialConejos = 50;
    public int numeroInicialZorros = 20;
    public int numeroInicialOsos = 5;
    public int numeroInicialAguilas = 15;
    public int numeroInicialCaballos = 10;
    public int numeroInicialCiervos = 15;
    public int numeroInicialRatones = 100;
    public int numeroInicialCabras = 20;
    public int numeroInicialOvejas = 25;
    public int numeroInicialJabalies = 15;
    public int numeroInicialBufalos = 10;
    public int numeroInicialPatos = 30;
    public int numeroInicialOrugas = 80;
    public int numeroInicialBoas = 8; // Número inicial de boas en la simulación

    // ------ Parámetros de Plantas ------
    public double cantidadInicialPlantas = 100;
    public double tasaCrecimientoPlantas = 20;
    public final double cantidadMaximaPlantas = 200; // Límite máximo de plantas por ubicación

    // ------ Condiciones de Parada de la Simulación ------
    public int maximoTurnos = 1000; // Número máximo de turnos
    public boolean detenerSiExtincion = true; // Detener si se extingue alguna especie

    // ------ Parámetros de Reproducción ------
    // (Valores de ejemplo, se pueden modificar)
    public int criasPorLobo = 3;
    public int criasPorConejo = 5;
    public int criasPorZorro = 3;
    public int criasPorOso = 2;
    public int criasPorAguila = 2;
    public int criasPorCaballo = 1;
    public int criasPorCiervo = 1;
    public int criasPorRaton = 6;
    public int criasPorCabra = 2;
    public int criasPorOveja = 2;
    public int criasPorJabali = 4;
    public int criasPorBufalo = 1;
    public int criasPorPato = 4;
    public int criasPorOruga = 10;

    // ------ Limite de animales por especie por ubicacion ------
    public int maxLobosPorUbicacion = 2;
    public int maxConejosPorUbicacion = 3;
    public int maxZorrosPorUbicacion = 2;
    public int maxOsosPorUbicacion = 2;
    public int maxAguilasPorUbicacion = 2;
    public int maxCaballosPorUbicacion = 2;
    public int maxCiervosPorUbicacion = 2;
    public int maxRatonesPorUbicacion = 3;
    public int maxCabrasPorUbicacion = 2;
    public int maxOvejasPorUbicacion = 2;
    public int maxJabaliesPorUbicacion = 2;
    public int maxBufalosPorUbicacion = 2;
    public int maxPatosPorUbicacion = 2;
    public int maxOrugasPorUbicacion = 3;
    public int maxBoasPorUbicacion = 2;

    // ------ Características de los animales ------
    // (Valores de ejemplo, se pueden modificar)
    // Lobo
    public double pesoLobo = 50;
    public int velocidadLobo = 3;
    public double comidaNecesariaLobo = 8;
    // Boa
    public double pesoBoa = 15;
    public int velocidadBoa = 1;
    public double comidaNecesariaBoa = 3;
    // Zorro
    public double pesoZorro = 8;
    public int velocidadZorro = 2;
    public double comidaNecesariaZorro = 2;
    // Oso
    public double pesoOso = 500;
    public int velocidadOso = 2;
    public double comidaNecesariaOso = 80;
    // Aguila
    public double pesoAguila = 6;
    public int velocidadAguila = 3;
    public double comidaNecesariaAguila = 1;
    // Caballo
    public double pesoCaballo = 400;
    public int velocidadCaballo = 4;
    public double comidaNecesariaCaballo = 60;
    // Ciervo
    public double pesoCiervo = 300;
    public int velocidadCiervo = 4;
    public double comidaNecesariaCiervo = 50;
    // Conejo
    public double pesoConejo = 2;
    public int velocidadConejo = 2;
    public double comidaNecesariaConejo = 0.45;
    // Raton
    public double pesoRaton = 0.05;
    public int velocidadRaton = 1;
    public double comidaNecesariaRaton = 0.01;
    // Cabra
    public double pesoCabra = 60;
    public int velocidadCabra = 3;
    public double comidaNecesariaCabra = 10;
    // Oveja
    public double pesoOveja = 70;
    public int velocidadOveja = 3;
    public double comidaNecesariaOveja = 15;
    // Jabali
    public double pesoJabali = 400;
    public int velocidadJabali = 2;
    public double comidaNecesariaJabali = 50;
    // Bufalo
    public double pesoBufalo = 700;
    public int velocidadBufalo = 3;
    public double comidaNecesariaBufalo = 100;
    // Pato
    public double pesoPato = 1;
    public int velocidadPato = 4;
    public double comidaNecesariaPato = 0.15;
    // Oruga
    public double pesoOruga = 0.01;
    public int velocidadOruga = 0;
    public double comidaNecesariaOruga = 0;

    // ------ Probabilidades de depredación ------
    // (Se podrían almacenar en un HashMap<String, HashMap<String, Double>>
    // para un acceso más eficiente, pero por ahora lo dejo como variables
    // individuales por simplicidad)
    // Lobo
    public double probabilidadLoboComeCaballo = 0.1;
    public double probabilidadLoboComeCiervo = 0.15;
    public double probabilidadLoboComeConejo = 0.6;
    public double probabilidadLoboComeRaton = 0.8;
    public double probabilidadLoboComeCabra = 0.6;
    public double probabilidadLoboComeOveja = 0.7;
    public double probabilidadLoboComeJabali = 0.15;
    public double probabilidadLoboComeBufalo = 0.1;
    public double probabilidadLoboComePato = 0.4;
    // Boa
    public double probabilidadBoaComeZorro = 0.15;
    public double probabilidadBoaComeConejo = 0.2;
    public double probabilidadBoaComeRaton = 0.4;
    public double probabilidadBoaComePato = 0.1;
    // Zorro
    public double probabilidadZorroComeConejo = 0.7;
    public double probabilidadZorroComeRaton = 0.9;
    public double probabilidadZorroComePato = 0.6;
    public double probabilidadZorroComeOruga = 0.4;
    // Oso
    public double probabilidadOsoComeBoa = 0.8;
    public double probabilidadOsoComeCaballo = 0.4;
    public double probabilidadOsoComeCiervo = 0.8;
    public double probabilidadOsoComeConejo = 0.8;
    public double probabilidadOsoComeRaton = 0.9;
    public double probabilidadOsoComeCabra = 0.7;
    public double probabilidadOsoComeOveja = 0.7;
    public double probabilidadOsoComeJabali = 0.5;
    public double probabilidadOsoComeBufalo = 0.2;
    public double probabilidadOsoComePato = 0.1;
    // Aguila
    public double probabilidadAguilaComeZorro = 0.1;
    public double probabilidadAguilaComeConejo = 0.9;
    public double probabilidadAguilaComeRaton = 0.9;
    public double probabilidadAguilaComePato = 0.8;
    // Caballo
    public double probabilidadCaballoComePlanta = 1;
    // Ciervo
    public double probabilidadCiervoComePlanta = 1;
    // Conejo
    public double probabilidadConejoComePlanta = 1;
    // Raton
    public double probabilidadRatonComePlanta = 1;
    public double probabilidadRatonComeOruga = 0.9;
    // Cabra
    public double probabilidadCabraComePlanta = 1;
    // Oveja
    public double probabilidadOvejaComePlanta = 1;
    // Jabali
    public double probabilidadJabaliComePlanta = 1;
    public double probabilidadJabaliComeRaton = 0.5;
    public double probabilidadJabaliComeOruga = 0.9;
    // Bufalo
    public double probabilidadBufaloComePlanta = 1;
    // Pato
    public double probabilidadPatoComePlanta = 1;
    public double probabilidadPatoComeOruga = 0.9;
    // Oruga
    public double probabilidadOrugaComePlanta = 1;

    // ------ Constructor Privado (Singleton) ------

    private Configuracion() {
        // Constructor privado para evitar la creación de instancias desde fuera
    }

    // ------ Método para Obtener la Instancia Única (Singleton) ------

    public static Configuracion getInstance() {
        if (instance == null) {
            instance = new Configuracion();
        }
        return instance;
    }

    // ------ Método para Cargar la Configuración desde un Archivo (Opcional) ------

    // public void cargarDesdeArchivo(String rutaArchivo) {
    //     // Implementación para leer la configuración desde un archivo
    //     // (por ejemplo, un archivo de propiedades, JSON o YAML)
    // }
}