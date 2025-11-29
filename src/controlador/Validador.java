package controlador;
import model.*;

public class Validador {
    public static String mensajeError="";

    //Metodo para validar si una jugada es valida
    public static boolean validarJugada(Carta carta, LineaJuego lineaJuego){
        Carta cartaEnJuego = lineaJuego.getUltimaCartaJuego();
        Color colorActivo = lineaJuego.getColorActivo();
    
        // Regla: Si la carta a jugar es un comodín negro (+4 o cambio de color), siempre es válida.
        if (carta.esComodinNegro()){
            return true;
        }
    
        // Regla: No se puede responder a un comodín con otro comodín (excepto los negros que ya se manejaron).
        if (carta.esComodin() && cartaEnJuego.esComodin()) {
            mensajeError = "No puedes jugar un comodín sobre otro comodín.";
            return false;
        }
    
        // Regla: La carta coincide con el color activo.
        if (carta.getColor() == colorActivo) {
            return true;
        }
    
        // Regla: Si los colores no coinciden, verificar si el tipo o número son iguales (y no son comodines negros).
        // Coincide por número (ambas son numéricas y tienen el mismo número).
        if (carta.esNumero() && cartaEnJuego.esNumero() && carta.getNumero() == cartaEnJuego.getNumero()) {
            return true;
        }

        // Regla: Si los colores no coinciden, verificar si son comodines del mismo tipo (ej. +2R sobre +2A).
        if (carta.esComodin() && cartaEnJuego.esComodin() && carta.getTipo() == cartaEnJuego.getTipo()) {
            return true;
        }
        mensajeError = "La carta debe coincidir con el color (" + cartaEnJuego.nombreColor() + ") o el número/tipo de la carta en juego.";
        return false;
    }

}
