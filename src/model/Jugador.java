package model;

import controlador.Validador;
import java.util.ArrayList;

public class Jugador {
    private String nombre;
    private ArrayList<Carta> mano;
    private boolean esHumano;

    //Constructor por defecto
    public Jugador() {
    }
    //Constructor con parametros
    public Jugador(String nombre, boolean esHumano) {
        this.nombre = nombre;
        this.esHumano = esHumano;
        this.mano = new ArrayList<Carta>();
    }
    //getters
    public String getNombre() {
        return nombre;
    }
    public boolean isEsHumano() {
        return esHumano;
    }

    //Metodo para agregar una carta a la mano del jugador
    public void addCarta(Carta carta) {
        this.mano.add(carta);
    }

    //Metodo para quitar una carta de la mano del jugador segun el indice
    //Devuelve la carta que se quita
    public Carta quitarCarta(int index) {
        if (index < 0 || index >= this.mano.size()) {
            throw new IllegalArgumentException("Índice de carta inválido");
        }
        return this.mano.remove(index);
    }
    //Metodo para obtener una carta de la mano del jugador según su índice
    //Devuelve la carta en el índice especificado
    public Carta getCarta(int index) {
        if (index < 0 || index >= this.mano.size()) {
            throw new IllegalArgumentException("Índice de carta inválido");
        }
        return this.mano.get(index);
    }
    //Metodo para obtener la cantidad de cartas en la mano del jugador
    public int getCantidadCartas() {
        return this.mano.size();
    }
    //Metodo para mostrar la mano del jugador
    public void mostrarMano() {
        System.out.println("Mano de " + this.nombre + ":");
        if (this.mano.isEmpty()) {
            System.out.println("La mano está vacía.");
            return;
        }
        for (int i = 0; i < this.mano.size(); i++) {
            System.out.println((i + 1) + ". " + this.mano.get(i).toString());
        }
    }

    //Metodo para verificar si el jugador tiene UNO
    public boolean tieneUNO() {
        return this.mano.size() == 1;
    }

    //Método para verificar si el jugador tiene cartas para jugar
    public boolean tieneCartaValida(LineaJuego lineaJuego) {
        for (Carta carta : this.mano) {
            if (Validador.validarJugada(carta, lineaJuego)) {
                return true;
            }
        }
        return false;
    }

    

}