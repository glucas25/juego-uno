package controlador;

import model.*;
import vista.Consola;
import java.util.Scanner;

public class Juego {
    private Consola consola;
    private Baraja baraja;
    private Jugador jugador;
    private Jugador maquina;
    private LineaJuego lineaJuego;
    private boolean turnoJugador=true;
    private boolean saltarTurno=false;
    private boolean cambioColor=false;
    private Color colorActivo;
    

    public Juego(){
        this.consola = new Consola();

        // Mostrar el título del juego al iniciar el juego
        this.consola.mostrarTitulo();

            Scanner scanner = new Scanner(System.in);
            //Solicitar el nombre del jugador
            this.consola.mostrarMensaje("Ingrese el nombre del Jugador: ");
            System.out.print("➤ ");
            String nombreJugador = scanner.nextLine().trim();
            this.jugador = new Jugador(nombreJugador,true);
    

        //Crear la máquina
        this.maquina = new Maquina("Skynet",false);
    
        //Crea la mesa de juego
        this.lineaJuego = new LineaJuego();
        this.turnoJugador = true;
        this.saltarTurno = false;

        inicializarJuego();

    }

    //Inicializar el juego
    public void inicializarJuego(){
        //Crear y barajar la baraja
        baraja = new Baraja();
        baraja.InicializarBaraja();
        baraja.barajar();

        consola.mostrarSeparador();

         //Repartir cartas a los jugadores
        consola.mostrarMensaje("Repartiendo cartas...");
        baraja.repartirCartas(jugador, 7);
        baraja.repartirCartas(maquina, 7);
        consola.pausa(1000);

        //Inicializando la primera carta
        consola.mostrarMensaje("¡Carta inicial: ..." + lineaJuego.inicializarCarta(baraja));
        

        consola.mostrarSeparador();

        //consola.esperarEnter("Presione enter iniciar el juego...");
        jugar();
    }


    public void jugar() {
        while (true) {
            consola.mostrarEstadoJuego(lineaJuego,jugador, maquina,cambioColor,colorActivo);
            //controlador para presentar por pantalla cuando halla cambio de color
            cambioColor=false;

            //Indicar de quien es el turno
            if (turnoJugador){
                consola.mostrarTurno(jugador.getNombre());
            } else {
                consola.mostrarTurno(maquina.getNombre());
            }
            //Ejecutar turno
            if (turnoJugador){
                turnoJugador();
            } else {

                turnoMaquina();
            }
            
            //Cambiar turno
            cambiarTurno();
            

            //Verificar si hay ganador
            if (verificarGanador()){
                break;
            }
        }
    }
    
    public void turnoJugador() {
        // Leer el índice directamente desde consola
        int indiceCarta = consola.leerIndiceCartaJugador(jugador.getCantidadCartas());
        
        // Si el jugador elige 0, roba una carta y termina el turno
        if (indiceCarta == 0) {
            consola.mostrarMensaje("Has decidido robar una carta.");
            robarCartas(jugador, 1);
            consola.pausa(1500);
            consola.esperarEnter("Presione enter continuar con el siguiente turno...");
            return; // Termina el turno
        }
        
        // Convertir a índice de array (el usuario ve 1-7, pero el array es 0-6)
        int indiceArray = indiceCarta - 1;
        
        // Obtener la carta seleccionada (SIN quitarla todavía)
        Carta cartaSeleccionada = jugador.getCarta(indiceArray);
        
        //Validar la carta antes de realizar algun procedimiento
        if (!Validador.validarJugada(cartaSeleccionada, lineaJuego)) {
            // La carta no es válida
            consola.mostrarJugadaInvalida();
            // Volver a pedir que elija otra carta
            turnoJugador();
            return;
        }
        
        
        // Quitar la carta de la mano del jugador
        jugador.quitarCarta(indiceArray);
        
        // Agregar la carta a la línea de juego
        lineaJuego.addCartaJuego(cartaSeleccionada);
        
        // Si es un comodín negro, pedir el color
        if (cartaSeleccionada.getColor() == Color.N) {
            Color colorElegido = consola.leerColorSeleccionado();
            lineaJuego.setColorActivo(colorElegido);
            cambioColor = true;
            colorActivo = colorElegido;
            consola.mostrarMensaje("Color cambiado a: " + colorElegido);
            consola.pausa(1000);
        }
        
        // Mostrar la carta jugada
        consola.mostrarMensaje("Has jugado: " + cartaSeleccionada.toString());
        consola.pausa(1000);
        
        // Aplicar efectos especiales de la carta
        aplicarEfectoEspecial(cartaSeleccionada);
        
        consola.esperarEnter("Presione enter continuar con el siguiente turno...");
    }

    
    public void turnoMaquina(){
        consola.mostrarMensaje("La máquina está pensando...");
        consola.pausa(1500);
        
        boolean cartaJugada = false;
        
        for (int i = 0; i < maquina.getCantidadCartas(); i++){
            Carta carta = maquina.getCarta(i);
            
            if (Validador.validarJugada(carta, lineaJuego)){
                // Quitar la carta de la mano
                maquina.quitarCarta(i);
                
                // Agregar a la línea de juego
                lineaJuego.addCartaJuego(carta);
                
                // Si es comodín negro, elegir color
                if (carta.getColor() == Color.N) {
                    Color colorElegido = elegirColorMaquina();
                    lineaJuego.setColorActivo(colorElegido);
                    cambioColor = true;
                    colorActivo = colorElegido;
                    
                    //Mostrar la  eleccion de la maquina
                    consola.mostrarMensaje("La máquina elige el color: " + colorElegido);
                    consola.pausa(1000);
                }
                
                // Mostrar la carta jugada
                consola.mostrarMensaje("La máquina ha jugado: " + carta.toString());
                consola.pausa(1000);
                
                // Aplicar efectos especiales
                aplicarEfectoEspecial(carta);
                
                cartaJugada = true;
                // Salir del bucle
                break;
            }
        }
        
        // Si no encontró carta válida, roba una carta
        if (!cartaJugada) {
            consola.mostrarMensaje("La máquina no tiene jugada válida y roba una carta.");
            robarCartas(maquina, 1);
            consola.pausa(1000);
        }

        consola.esperarEnter("Presione enter continuar con el siguiente turno...");
    }



    //Metodo para elegir el color que mas tiene la maquina
    private Color elegirColorMaquina() {
        int contadorRojo = 0;
        int contadorAmarillo = 0;
        int contadorVerde = 0;
        int contadorAzul = 0;
        
        for (int i = 0; i < maquina.getCantidadCartas(); i++) {
            Carta carta = maquina.getCarta(i);
            Color colorCarta = carta.getColor();
            
            switch (colorCarta) {
                case R:
                    contadorRojo++;
                    break;
                case A:
                    contadorAmarillo++;
                    break;
                case V:
                    contadorVerde++;
                    break;
                case Z:
                    contadorAzul++;
                    break;
                case N:
                    break;
            }
        }
        
        int mayorCantidad = contadorRojo;
        Color colorElegido = Color.R;
        
        if (contadorAmarillo > mayorCantidad) {
            mayorCantidad = contadorAmarillo;
            colorElegido = Color.A;
        }
        
        if (contadorVerde > mayorCantidad) {
            mayorCantidad = contadorVerde;
            colorElegido = Color.V;
        }
        
        if (contadorAzul > mayorCantidad) {
            mayorCantidad = contadorAzul;
            colorElegido = Color.Z;
        }
        
        return colorElegido;
    }



    //Aplicar efectors especiales a cartas
    private void aplicarEfectoEspecial(Carta carta){
    if (carta.getTipo() == TipoCarta.BLOQUEO){
        saltarTurno = true;
        consola.mostrarEfectoEspecial("¡BLOQUEADO!");
        
    } else if(carta.getTipo() == TipoCarta.REVERSE){
        saltarTurno = true;
        consola.mostrarEfectoEspecial("¡REVERSA!");
        
    } else if (carta.getTipo() == TipoCarta.MAS2){
        // El siguiente jugador es el contrario del actual
        Jugador siguienteJugador = turnoJugador ? maquina : jugador;
        baraja.repartirCartas(siguienteJugador, 2);
        consola.mostrarEfectoEspecial("¡+2 CARTAS para " + siguienteJugador.getNombre() + "!");
        
    } else if (carta.getTipo() == TipoCarta.MAS4){
        // El siguiente jugador es el contrario del actual
        Jugador siguienteJugador = turnoJugador ? maquina : jugador;
        baraja.repartirCartas(siguienteJugador, 4);
        consola.mostrarEfectoEspecial("¡+4 CARTAS para " + siguienteJugador.getNombre() + "!");
    }
    
    consola.pausa(1000);
}
    
    //Robar cartas
    private void robarCartas(Jugador jugador, int cantidad){
        baraja.repartirCartas(jugador, cantidad);
        consola.mostrarRobarCarta(jugador.getNombre(), cantidad);
        consola.pausa(1000);
    }

    private void cambiarTurno(){
        if (saltarTurno){
            if(turnoJugador){
                consola.mostrarTurnoBloqueado(maquina.getNombre());
            } else {
                consola.mostrarTurnoBloqueado(jugador.getNombre());
            }
            saltarTurno = false;
        } else{
            turnoJugador =!turnoJugador;
        }
    }

    private boolean verificarGanador(){
        if (jugador.getCantidadCartas()==0){
            consola.mostrarGanador(jugador.getNombre());
            return true;
        } else if (maquina.getCantidadCartas()==0){
            consola.mostrarGanador(maquina.getNombre());
            return true;
        } else {
            return false;
        }
    }
}
