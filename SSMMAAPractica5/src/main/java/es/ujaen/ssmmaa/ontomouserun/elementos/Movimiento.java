package es.ujaen.ssmmaa.ontomouserun.elementos;

import es.ujaen.ssmmaa.ontomouserun.Vocabulario.Accion;
import jade.content.Concept;
import jade.content.onto.annotations.Slot;


public class Movimiento implements Concept {
    private Accion accion;
    private Posicion posicion;

    public Movimiento() {
        this.accion = null;
        this.posicion = null;
    }

    public Movimiento(Accion accion, Posicion posicion) {
        this.accion = accion;
        this.posicion = posicion;
    }

    @Slot(mandatory=true)
    public Accion getAccion() {
        return accion;
    }

    public void setAccion(Accion accion) {
        this.accion = accion;
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
        return "Movimiento{" +
                "accion=" + accion +
                ", posicion=" + posicion +
                '}';
    }
}
