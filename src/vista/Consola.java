package vista;

import model.Carta;
import model.Jugador;
import model.LineaJuego;
import model.Color;
import java.util.Scanner;

public class Consola {
    private Scanner scanner;
    
    // Códigos ANSI para colores en consola
    private static final String RESET = "\u001B[0m"; // Resetear color a los valores por defecto
    private static final String ROJO = "\u001B[31m";
    private static final String AMARILLO = "\u001B[33m";
    private static final String VERDE = "\u001B[32m";
    private static final String AZUL = "\u001B[34m";
    private static final String NEGRO = "\u001B[30m";
    private static final String BLANCO = "\u001B[37m";
    private static final String CYAN = "\u001B[36m";
    private static final String MAGENTA = "\u001B[35m";
    
    // Estilos
    private static final String NEGRITA = "\u001B[1m";
    private static final String FONDO_BLANCO = "\u001B[47m";
    private static final String FONDO_ROJO = "\u001B[41m";
    private static final String FONDO_AMARILLO = "\u001B[43m";
    private static final String FONDO_VERDE = "\u001B[42m";
    private static final String FONDO_AZUL = "\u001B[44m";
    private static final String FONDO_NEGRO = "\u001B[40m";
    
    public Consola() {
        this.scanner = new Scanner(System.in);
    }
    
    // Método para limpiar la consola
    public void limpiarConsola() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // Si no funciona, imprime líneas en blanco
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
    
    // Método para mostrar el título del juego
    public void mostrarTitulo() {
        limpiarConsola();
        System.out.println(CYAN + NEGRITA);
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║                                                           ║");
        System.out.println("║              ██╗   ██╗███╗   ██╗ ██████╗                  ║");
        System.out.println("║              ██║   ██║████╗  ██║██╔═══██╗                 ║");
        System.out.println("║              ██║   ██║██╔██╗ ██║██║   ██║                 ║");
        System.out.println("║              ██║   ██║██║╚██╗██║██║   ██║                 ║");
        System.out.println("║              ╚██████╔╝██║ ╚████║╚██████╔╝                 ║");
        System.out.println("║               ╚═════╝ ╚═╝  ╚═══╝ ╚═════╝                  ║");
        System.out.println("║                                                           ║");
        System.out.println("║              ¡Bienvenido al Juego de UNO!                 ║");
        System.out.println("║                                                           ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
        System.out.println(RESET);
    }
    
    // Método para mostrar una línea separadora
    public void mostrarSeparador() {
        System.out.println(CYAN + "═══════════════════════════════════════════════════════════" + RESET);
    }
    
    // Método para obtener el color ANSI según el color de la carta
    private String obtenerColorANSI(Color color) {
        switch (color) {
            case R:
                return ROJO;
            case A:
                return AMARILLO;
            case V:
                return VERDE;
            case Z:
                return AZUL;
            case N:
                return BLANCO; // Negro se muestra en blanco para visibilidad
            default:
                return RESET;
        }
    }
    
    // Método para obtener el color de fondo ANSI
    private String obtenerFondoANSI(Color color) {
        switch (color) {
            case R:
                return FONDO_ROJO;
            case A:
                return FONDO_AMARILLO;
            case V:
                return FONDO_VERDE;
            case Z:
                return FONDO_AZUL;
            case N:
                return FONDO_NEGRO;
            default:
                return RESET;
        }
    }
    
    // Método para mostrar una carta con formato visual atractivo
    public void mostrarCartaVisual(Carta carta) {
        String colorFondo = obtenerFondoANSI(carta.getColor());
        String colorTexto = carta.getColor() == Color.N ? BLANCO : NEGRO;
        
        System.out.println(colorFondo + colorTexto + "┌─────────┐" + RESET);
        System.out.println(colorFondo + colorTexto + "│         │" + RESET);
        System.out.println(colorFondo + colorTexto + "│  " + NEGRITA + String.format("%-5s", carta.toString()) + RESET + colorFondo + colorTexto + "  │" + RESET);
        System.out.println(colorFondo + colorTexto + "│         │" + RESET);
        System.out.println(colorFondo + colorTexto + "└─────────┘" + RESET);
    }
    
    // Método para mostrar la línea de juego
    public void mostrarLineaDeJuego(LineaJuego linea) {
        System.out.println("\n" + MAGENTA + NEGRITA + "┌─── CARTA EN JUEGO ───┐" + RESET);
        mostrarCartaVisual(linea.getUltimaCartaJuego());
        System.out.println(MAGENTA + "└──────────────────────┘" + RESET);
        
        // Mostrar color activo si es diferente al de la carta
        if (linea.getColorActivo() != linea.getUltimaCartaJuego().getColor()) {
            String colorTexto = obtenerColorANSI(linea.getColorActivo());
            System.out.println(colorTexto + NEGRITA + "⚠ Color activo: " + linea.getColorActivo() + RESET);
        }
    }
    
    // Método para mostrar la mano de un jugador de forma visual
    public void mostrarManoJugador(Jugador jugador) {
        System.out.println("\n" + CYAN + NEGRITA + "╔═════ MANO DE " + jugador.getNombre().toUpperCase() + " ═════╗" + RESET);
        
        if (jugador.getCantidadCartas() == 0) {
            System.out.println(AMARILLO + "   ¡Sin cartas!" + RESET);
        } else {
            for (int i = 0; i < jugador.getCantidadCartas(); i++) {
                Carta carta = jugador.getCarta(i);
                String colorTexto = obtenerColorANSI(carta.getColor());
                String numero = String.format("[%d]", i + 1);
                System.out.println("  " + BLANCO + numero + RESET + " " + colorTexto + NEGRITA + carta.toString() + RESET);
            }
        }
        
        System.out.println(CYAN + "╚═══════════════════════════════╝" + RESET);
        
        // Mostrar UNO si solo tiene una carta
        if (jugador.tieneUNO()) {
            mostrarUNO();
        }
    }
    
    // Método para mostrar el estado completo del juego
    public void mostrarEstadoJuego(LineaJuego linea, Jugador jugador, Jugador maquina, boolean cambioColor, Color colorActivo) {
        limpiarConsola();
        mostrarSeparador();
        
        // Mostrar mano de la máquina
        mostrarManoJugador(maquina);
        
        // Mostrar carta en juego
        mostrarLineaDeJuego(linea);

        if (cambioColor){
            mostrarCambioColor(colorActivo);
        }
        
        // Mostrar mano del jugador
        mostrarManoJugador(jugador);
        
        mostrarSeparador();

        
    }
    
    // Método para leer el índice de carta que quiere jugar el jugador
    public int leerIndiceCartaJugador(int cantidadCartas) {
        System.out.print("\n" + VERDE + "➤ Elige el número de carta a jugar (1-" + cantidadCartas + ") o 0 para robar: " + RESET);
        
        while (true) {
            try {
                int indice = Integer.parseInt(scanner.nextLine());
                if (indice >= 0 && indice <= cantidadCartas) {
                    return indice;
                } else {
                    System.out.print(ROJO + "✗ Número inválido. Intenta de nuevo: " + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.print(ROJO + "✗ Debes ingresar un número. Intenta de nuevo: " + RESET);
            }
        }
    }
    
    // Método para leer el color seleccionado cuando se juega un comodín negro
    public Color leerColorSeleccionado() {
        System.out.println("\n" + AMARILLO + "⚡ Has jugado un comodín. Elige el color:" + RESET);
        System.out.println("  " + ROJO + "[R]" + RESET + " Rojo");
        System.out.println("  " + AMARILLO + "[A]" + RESET + " Amarillo");
        System.out.println("  " + VERDE + "[V]" + RESET + " Verde");
        System.out.println("  " + AZUL + "[Z]" + RESET + " Azul");
        System.out.print(VERDE + "➤ Tu elección: " + RESET);
        
        while (true) {
            String entrada = scanner.nextLine().toUpperCase().trim();
            switch (entrada) {
                case "R":
                    return Color.R;
                case "A":
                    return Color.A;
                case "V":
                    return Color.V;
                case "Z":
                    return Color.Z;
                default:
                    System.out.print(ROJO + "✗ Color inválido. Usa R, A, V o Z: " + RESET);
            }
        }
    }
    
    // Método para mostrar mensaje de turno
    public void mostrarTurno(String nombreJugador) {
        System.out.println("\n" + CYAN + NEGRITA + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);
        System.out.println(CYAN + NEGRITA + "   🎮 TURNO DE: " + nombreJugador.toUpperCase() + RESET);
        System.out.println(CYAN + NEGRITA + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);
    }
    
    // Método para mostrar mensaje UNO
    public void mostrarUNO() {
        System.out.println("\n" + ROJO + NEGRITA + "┏━━━━━━━━━━━━━━━━━━━━━━━┓" + RESET);
        System.out.println(ROJO + NEGRITA + "┃   ██╗   ██╗███╗   ██╗ ┃" + RESET);
        System.out.println(ROJO + NEGRITA + "┃   ██║   ██║████╗  ██║ ┃" + RESET);
        System.out.println(ROJO + NEGRITA + "┃   ██║   ██║██╔██╗ ██║ ┃" + RESET);
        System.out.println(ROJO + NEGRITA + "┃   ██║   ██║██║╚██╗██║ ┃" + RESET);
        System.out.println(ROJO + NEGRITA + "┃   ╚██████╔╝██║ ╚████║ ┃" + RESET);
        System.out.println(ROJO + NEGRITA + "┃    ╚═════╝ ╚═╝  ╚═══╝ ┃" + RESET);
        System.out.println(ROJO + NEGRITA + "┗━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
    }
    
    // Método para mostrar efectos especiales
    public void mostrarEfectoEspecial(String efecto) {
        System.out.println("\n" + MAGENTA + NEGRITA + "⚡ " + efecto + " ⚡" + RESET);
        pausa(1500);
    }
    
    // Método para mostrar mensaje de jugada inválida
    public void mostrarJugadaInvalida() {
        System.out.println("\n" + ROJO + NEGRITA + "✗ ¡Jugada inválida! Esa carta no se puede jugar." + RESET);
        pausa(2000);
    }
    
    // Método para mostrar que un jugador robó carta(s)
    public void mostrarRobarCarta(String nombreJugador, int cantidad) {
        String cartas = cantidad == 1 ? "carta" : "cartas";
        System.out.println("\n" + AMARILLO + "➤ " + nombreJugador + " robó " + cantidad + " " + cartas + " 🃏" + RESET);
        pausa(1500);
    }
    
    // Método para mostrar al ganador
    public void mostrarGanador(String nombreGanador) {
        limpiarConsola();
        System.out.println("\n" + VERDE + NEGRITA);
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║                                                           ║");
        System.out.println("║              🏆  ¡TENEMOS UN GANADOR!  🏆                ║");
        System.out.println("║                                                           ║");
        System.out.println("║              ¡Felicidades " + String.format("%-20s", nombreGanador) + "!       ║");
        System.out.println("║                                                           ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
        System.out.println(RESET);
    }
    
    // Método para mostrar mensaje de turno bloqueado
    public void mostrarTurnoBloqueado(String nombreJugador) {
        System.out.println("\n" + ROJO + NEGRITA + "🚫 " + nombreJugador + " pierde su turno! 🚫" + RESET);
        pausa(2000);
    }
    
    // Método para mostrar un mensaje genérico
    public void mostrarMensaje(String mensaje) {
        System.out.println("\n" + CYAN + "ℹ " + mensaje + RESET);
    }
    
    // Método para hacer una pausa (útil para dar tiempo al usuario de ver mensajes)
    public void pausa(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    //Mostrar cambio de color
    public void mostrarCambioColor(Color color) {

        if (color!=null){
            switch (color) {
                case R:
                    System.out.println("  " + ROJO + "Cambio de Color: Rojo" + RESET);
                    break;
                case A:
                    System.out.println("  " + AMARILLO + "Cambio de Color: Amarillo" + RESET);
                    break;
                case V:
                    System.out.println("  " + VERDE + "Cambio de Color: Verde" + RESET);
                    break;
                case Z:
                    System.out.println("  " + AZUL + "Cambio de Color: Azul" + RESET);
                    break;
                default:
                    break;
            }
        }
    }
    
    // Método para esperar que el usuario presione Enter
    public void esperarEnter(String mensaje) {
        System.out.print("\n" + VERDE + mensaje + RESET);
        scanner.nextLine();
    }
    
    // Método para cerrar el scanner
    public void cerrar() {
        scanner.close();
    }
}