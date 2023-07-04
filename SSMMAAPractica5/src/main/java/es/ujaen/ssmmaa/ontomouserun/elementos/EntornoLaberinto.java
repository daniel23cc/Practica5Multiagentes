package es.ujaen.ssmmaa.ontomouserun.elementos;

import jade.content.Predicate;
import jade.content.onto.annotations.Slot;

public class EntornoLaberinto implements Predicate {
    private Partida partida;
    private Posicion posicion;
    private Entorno entorno;

    public EntornoLaberinto() {
        this.partida = null;
        this.posicion = null;
        this.entorno = null;
    }

    public EntornoLaberinto(Partida partida, Posicion posicion, Entorno entorno) {
        this.partida = partida;
        this.posicion = posicion;
        this.entorno = entorno;
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

    @Slot(mandatory=true)
    public Entorno getEntorno() {
        return entorno;
    }

    public void setEntorno(Entorno entorno) {
        this.entorno = entorno;
    }

    @Override
    public String toString() {
        return "EntornoLaberinto{" +
                "partida=" + partida +
                ", posicion=" + posicion +
                ", entorno=" + entorno +
                '}';
    }
}
