package model;

public class Carta {
    private Color color;
    private TipoCarta tipo;
    private int numero;

    // Constructor
    public Carta() {
    }
    public Carta(Color color, TipoCarta tipo, int numero) {
        if (validarCarta(color, tipo, numero)){
            this.color = color;
            this.tipo = tipo;
            this.numero = numero;
        }
    }

    // Getters
    public Color getColor() {
        return color;
    }
    public TipoCarta getTipo() {
        return tipo;
    }
    public int getNumero() {
        return numero;
    }


    //Metodo para verificar si la carta es de tipo NUMERO
    public boolean esNumero(){
        return this.tipo == TipoCarta.NUMERO;
    }
    //Metodo para verificar si la carta es de tipo ESPECIAL
    public boolean esComodin(){
        return this.tipo != TipoCarta.NUMERO;
    }
    //Metodo para verificar si la carta es de color N
    public boolean esComodinNegro(){
        return this.color == Color.N;
    }

    //Método toString
  @Override
    public String toString() {
        if (tipo == TipoCarta.NUMERO) {
            return numero + "" + color + " (" + nombreColor() + " " + numero + ")";
        } else {
            return codigoTipoCarta() + "" + color + " (" + nombreColor() + " " + nombreTipoCarta() + ")";
        }
    }

    public String mostrarCarta(){
        if (tipo == TipoCarta.NUMERO) {
            return numero + "" + color;
        } else {
            return codigoTipoCarta() + "" + color;
        }
    }


    // Método para obtener el código del tipo de carta
    private String codigoTipoCarta (){
        String codigo="";
        switch (this.tipo){
            case REVERSE:
                codigo="^";
                return codigo;
            case BLOQUEO:
                codigo="&";
                return codigo;
            case MAS2:
                codigo="+2";
                return codigo;
            case MAS4:
                codigo="+4";
                return codigo;
            case CAMBIO_COLOR:
                codigo="%";
                return codigo;
            default:
                break;
        }
        return codigo;
    }

    //Metodo para obtener el nombre del color
    public String nombreColor(){
        String nombre="";
        switch (this.color){
            case R:
                nombre="Rojo";
                return nombre;
            case Z:
                nombre="Azul";
                return nombre;
            case V:
                nombre="Verde";
                return nombre;
            case A:
                nombre="Amarillo";
                return nombre;
            case N:
                nombre="Negro";
                return nombre;
            default:
                break;
        }
        return nombre;
    }

    //  //Metodo para obtener el nombre del tipo de carta
    public String nombreTipoCarta(){
        String nombre="";
        switch (this.tipo){
            case NUMERO:
                nombre="Número";
                return nombre;
            case REVERSE:
                nombre="Reversa";
                return nombre;
            case BLOQUEO:
                nombre="Bloqueo";
                return nombre;
            case MAS2:
                nombre="Más 2";
                return nombre;
            case MAS4:
                nombre="Más 4";
                return nombre;
            case CAMBIO_COLOR:
                nombre="Cambio de color";
                return nombre;
            default:
                break;
        }
        return nombre;
    }



    // Método para validar la carta
    private boolean validarCarta(Color color, TipoCarta tipo, int valor) {
        boolean esValida = false;
        if (color==null || tipo==null) {
            throw new IllegalArgumentException("Color o Tipo de carta no pueden ser nulos");
        } else if (color==Color.N && tipo == TipoCarta.NUMERO) {
            throw new IllegalArgumentException("Una carta de color N no puede ser de tipo NUMERO");
        } else if (tipo == TipoCarta.CAMBIO_COLOR && color != Color.N) {
            throw new IllegalArgumentException("Una carta de tipo CAMBIO_COLOR debe ser de color N");
        } else if (tipo == TipoCarta.NUMERO && (valor < 0 && valor > 9)) {
            throw new IllegalArgumentException("El valor de una carta NUMERO debe estar entre 0 y 9");
        }
        esValida= true;
    return esValida;
    }

}
