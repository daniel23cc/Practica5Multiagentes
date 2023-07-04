package es.ujaen.ssmmaa.ontomouserun.elementos;

import jade.content.AgentAction;
import jade.content.onto.annotations.Slot;

public class PedirMovimiento implements AgentAction {
    private Partida partida;
    private Posicion posicion;

    public PedirMovimiento() {
        this.partida = null;
        this.posicion = null;
    }

    public PedirMovimiento(Partida partida, Posicion posicion) {
        this.partida = partida;
        this.posicion = posicion;
    }

    @Slot(mandatory=true)
    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    @Slot(mandatory=true)
    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    @Override
    public String toString() {
        return "PedirMovimiento{" +
                "partida=" + partida +
                ", posicion=" + posicion +
                '}';
    }
}
