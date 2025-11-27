package controlador;
import model.*;

public class Validador {

    //Metodo para validar si una jugada es valida
    public static boolean validarJugada(Carta carta, LineaJuego lineaJuego){
        
        // 1. Si es comodín negro, SIEMPRE se puede jugar
        if (carta.esComodinNegro()) {
            return true;
        }

        // Validar regla: no puede responder comodín con comodín
        if (carta.esComodinNegro() && lineaJuego.getColorActivo().equals(Color.N)){
            return false;
        }
        
        // 2. Si es del mismo color que el color activo
        if (carta.getColor() == lineaJuego.getColorActivo()) {
            return true;
        }
        
        // 3. Si ambas son cartas numéricas con el mismo número
        if (carta.getTipo() == TipoCarta.NUMERO &&
            lineaJuego.getUltimaCartaJuego().getTipo() == TipoCarta.NUMERO) {
            if (carta.getNumero() == lineaJuego.getUltimaCartaJuego().getNumero()) {
                return true;
            }
        }
        
        // 4. Si no cumple ninguna condición, no es válida
        return false;
    }
}
