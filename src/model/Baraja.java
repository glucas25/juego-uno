package model;
import java.util.ArrayList;
import java.util.Collections;

public class Baraja {
    private ArrayList<Carta> cartas;

    public Baraja() {
        this.cartas = new ArrayList<>();
    }

    public void InicializarBaraja() {
        // Lógica para inicializar la baraja con cartas de UNO

        
        for (Color color : Color.values()) {
            //Crear cartas por cada color
            if (color != Color.N) { // Excluir el color
                // Agregar cartas numéricas
                for (int numero = 0; numero <= 9; numero++) {
                    cartas.add(new Carta(color, TipoCarta.NUMERO, numero));
                    }
                // Agregar comodines de color
                cartas.add(new Carta(color, TipoCarta.BLOQUEO, -1));
                cartas.add(new Carta(color, TipoCarta.BLOQUEO, -1));
                cartas.add(new Carta(color, TipoCarta.REVERSE, -1));
                cartas.add(new Carta(color, TipoCarta.REVERSE, -1));
                cartas.add(new Carta(color, TipoCarta.MAS2, -1));
                cartas.add(new Carta(color, TipoCarta.MAS2, -1));
                cartas.add(new Carta(color, TipoCarta.MAS4, -1));
                cartas.add(new Carta(color, TipoCarta.MAS4, -1));
            }

            if (color == Color.N) {
                // Agregar cartas de cambio de color
                cartas.add(new Carta(color, TipoCarta.CAMBIO_COLOR, -1));
                cartas.add(new Carta(color, TipoCarta.CAMBIO_COLOR, -1));
                cartas.add(new Carta(color, TipoCarta.MAS2, -1));
                cartas.add(new Carta(color, TipoCarta.MAS2, -1));
                cartas.add(new Carta(color, TipoCarta.MAS4, -1));
                cartas.add(new Carta(color, TipoCarta.MAS4, -1));
            }
        }
    }

    // Método para obtener las cartas de la baraja
    public ArrayList<Carta> getCartas() {
        return cartas;
    }

    //Metodo para barajar las cartas
    public void barajar() {
        Collections.shuffle(cartas);
    }


    public Carta robarCarta() {
        if (estaVacia()) {
            throw new IllegalStateException("No hay cartas disponibles para robar.");
        }
        Carta cartaRobada = this.cartas.remove(0);
        if (estaVacia()) {
            System.out.println("La baraja está vacía después de robar una carta.");
        }
        return cartaRobada;
    }

    public boolean estaVacia() {
        if (this.cartas.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public Carta obtenerCartaInicial() {
        if (estaVacia()) {
            throw new IllegalStateException("No hay cartas disponibles en la baraja.");
        }
        return this.cartas.remove(0);
    }

    //Metodo para mostrar las cartas de la baraja
    public void mostrarCartas() {
        System.out.println("Cartas en la baraja:");
        for (Carta carta : cartas) {
            System.out.println(cartas.indexOf(carta) + 1 + ". " + carta.toString());
        }
    }

    public void repartirCartas(Jugador jugador, int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            if (!estaVacia()) {
                Carta cartarepartida=new Carta();
                cartarepartida = this.cartas.remove(i);
                jugador.addCarta(cartarepartida);
            } else {
                System.out.println("No hay más cartas para repartir. El juego queda EMPATADO");
                System.out.println("Gracias por jugar UNO!");
                
                System.exit(0);
                break;
            }
        }
    }

    

}
