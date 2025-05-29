import java.io.*;
import java.nio.charset.StandardCharsets;

public class TestPlanta {
    public static void main(String[] args) throws Exception {
        // Forzar UTF-8 para salida
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        
        // Configurar codificación para Windows usando PowerShell
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            try {
                ProcessBuilder pb = new ProcessBuilder(
                    "powershell.exe",
                    "-Command",
                    "[Console]::OutputEncoding = [System.Text.Encoding]::UTF8"
                );
                pb.inheritIO();
                pb.start().waitFor();
            } catch (Exception e) {
                System.err.println("No se pudo configurar PowerShell: " + e.getMessage());
            }
        }

        // Información del sistema
        System.out.println("=== Información del Sistema ===");
        System.out.println("Sistema operativo: " + System.getProperty("os.name"));
        System.out.println("Versión del sistema: " + System.getProperty("os.version"));
        System.out.println("Codificación por defecto: " + System.getProperty("file.encoding"));
        System.out.println("Codificación de salida: " + System.out.charset());

        // Pruebas de caracteres
        System.out.println("\n=== Prueba de Caracteres ===");
        System.out.println("ASCII: ABCDEF123456");
        System.out.println("Español: áéíóúñ");
        System.out.println("Símbolos: ★ ♠ ♣ ♥ ♦");
        System.out.println("Emojis: 🌿 🌲 🌳 🌴");
        
        // Prueba de objeto Planta
        System.out.println("\n=== Prueba de Planta ===");
        Configuracion config = Configuracion.getInstance();
        Planta planta = new Planta(config);
        System.out.println("Carácter de planta: " + planta.getCaracter());
        
        // Matriz de prueba
        System.out.println("\n=== Matriz de Prueba ===");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print("🌿 ");
            }
            System.out.println();
        }
    }
} 