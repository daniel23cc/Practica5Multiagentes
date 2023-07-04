package es.ujaen.ssmmaa.ontomouserun.elementos;


import es.ujaen.ssmmaa.ontomouserun.Vocabulario.Orientacion;
import es.ujaen.ssmmaa.ontomouserun.Vocabulario.Pared;
import jade.content.Concept;
import jade.content.onto.annotations.AggregateSlot;
import jade.util.leap.List;


public class Entorno implements Concept {
    private List entornoCasilla;

    public Entorno() {
        this.entornoCasilla = null;
    }

    public Entorno(List entornoCasilla) {
        this.entornoCasilla = entornoCasilla;
    }

    @AggregateSlot(cardMin=4, cardMax = 4, type=Pared.class)
    public List getEntornoCasilla() {
        return entornoCasilla;
    }

    public void setEntornoCasilla(List entornoCasilla) {
        this.entornoCasilla = entornoCasilla;
    }

    public Pared get(Orientacion orientacion) {
        return (Pared) entornoCasilla.get(orientacion.ordinal());
    }

    public void set(Orientacion orientacion, Pared pared) {
        entornoCasilla.remove(orientacion.ordinal());
        entornoCasilla.add(orientacion.ordinal(), pared);
    }

    @Override
    public String toString() {
        return "Entorno{" +
                "entornoCasilla=" + entornoCasilla +
                '}';
    }
}
