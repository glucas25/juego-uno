package model;

import controlador.Validador;

public class Maquina extends Jugador {
    
    public Maquina(String nombre, boolean esHumano) {
        super(nombre, esHumano);
    }

    //Método para que la máquina elija una carta válida para jugar
    public Carta elegirCarta(LineaJuego lineaJuego) {
        for (int i = 0; i < this.getCantidadCartas(); i++) {
            Carta cartaMano = this.getCarta(i);
            if (Validador.validarJugada(cartaMano, lineaJuego)) {
                return cartaMano;
            }
        }
        return null; // No hay carta válida para jugar
    }

    
}
