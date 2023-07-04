package es.ujaen.ssmmaa.ontomouserun.elementos;

import jade.content.onto.annotations.AggregateSlot;
import jade.content.onto.annotations.Slot;
import jade.util.leap.List;

public class ClasificacionJuego extends SubInform {
    private Juego juego;
    private List listaJugadores;
    private List listaPuntuacion;

    public ClasificacionJuego() {
        this.juego = null;
        this.listaJugadores = null;
        this.listaPuntuacion = null;
    }

    public ClasificacionJuego(Juego juego, List listaJugadores, List listaPuntuacion) {
        this.juego = juego;
        this.listaJugadores = listaJugadores;
        this.listaPuntuacion = listaPuntuacion;
    }

    @Slot(mandatory=true)
    public Juego getJuego() {
        return juego;
    }

    public void setJuego(Juego juego) {
        this.juego = juego;
    }

    @AggregateSlot(cardMin=1, type=Jugador.class)
    public List getListaJugadores() {
        return listaJugadores;
    }

    public void setListaJugadores(List listaJugadores) {
        this.listaJugadores = listaJugadores;
    }

    @AggregateSlot(cardMin=1, type=String.class)
    public List getListaPuntuacion() {
        return listaPuntuacion;
    }

    public void setListaPuntuacion(List listaPuntuacion) {
        this.listaPuntuacion = listaPuntuacion;
    }

    @Override
    public String toString() {
        return "ClasificacionJuego{" +
                "juego=" + juego +
                ", listaJugadores=" + listaJugadores +
                ", listaPuntuacion=" + listaPuntuacion +
                '}';
    }
}
