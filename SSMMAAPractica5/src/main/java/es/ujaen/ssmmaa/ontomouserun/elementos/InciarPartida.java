package es.ujaen.ssmmaa.ontomouserun.elementos;

import jade.content.AgentAction;
import jade.content.onto.annotations.Slot;

public class InciarPartida implements AgentAction {
    private Partida partida;
    private Entorno entornoCasilla;
    private int bombas;

    public InciarPartida() {
        this.partida = null;
        this.entornoCasilla = null;
        this.bombas = 0;
    }

    public InciarPartida(Partida partida, Entorno entornoCasilla, int bombas) {
        this.partida = partida;
        this.entornoCasilla = entornoCasilla;
        this.bombas = bombas;
    }

    @Slot(mandatory=true)
    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    @Slot(mandatory=true)
    public Entorno getEntornoCasilla() {
        return entornoCasilla;
    }

    public void setEntornoCasilla(Entorno entornoCasilla) {
        this.entornoCasilla = entornoCasilla;
    }

    @Slot(mandatory=true)
    public int getBombas() {
        return bombas;
    }

    public void setBombas(int bombas) {
        this.bombas = bombas;
    }

    @Override
    public String toString() {
        return "InciarPartida{" +
                "partida=" + partida +
                ", entornoCasilla=" + entornoCasilla +
                ", bombas=" + bombas +
                '}';
    }
}
