package model;
import java.util.ArrayList;


public class LineaJuego {
    private ArrayList<Carta> linea;
    private Color colorActivo;
    
    //Constructor por defecto
    public LineaJuego() {
        this.linea = new ArrayList<Carta>();
        this.colorActivo = null;
    }

    //Getter de la linea de juego
    public ArrayList<Carta> getLinea() {
        return linea;
    }


    //Obtener el color activo de la ultima carta en la linea de juego
    public Color getColorActivo() {
        //if (this.linea.isEmpty()) {
        //    return null;
        //}
        if (this.colorActivo != null) {
            return this.colorActivo;
        } else {
            return null;
        }

        //return this.getUltimaCartaJuego().getColor();
        //Carta ultimaCarta = this.linea.get(this.linea.size() - 1);
        //colorActivo = ultimaCarta.getColor();
        //return ultimaCarta.getColor();
        //return this.colorActivo;
    }

    //Metodo para cambair el color activo
    public void setColorActivo(Color color) {
        this.colorActivo = color;

    }


    //Metodo para agregar una carta a la linea de juego
    public void addCartaJuego(Carta carta) {
        this.linea.add(carta);
    }

    //Metodo para obtener la ultima carta en la linea de juego
    public Carta getUltimaCartaJuego() {
        if (this.linea.isEmpty()) {
            return null;
        }
        return this.linea.get(this.linea.size() - 1);
    }

    public Carta inicializarCarta(Baraja baraja){
        if (!baraja.estaVacia()){
            baraja.barajar();
            for(int i=0;i<baraja.getCartas().size();i++){
                if(baraja.getCartas().get(i).getTipo()==TipoCarta.NUMERO){
                    this.linea.add(baraja.getCartas().get(i));
                    //Actualizar color activo
                    this.setColorActivo(baraja.getCartas().get(i).getColor());
                    return baraja.getCartas().remove(i);
                }
            }
            
        }
        this.linea.add(baraja.getCartas().get(0));
        //Actualizar color activo
        this.setColorActivo(baraja.getCartas().get(0).getColor());
        return baraja.getCartas().remove(0);
    }

}