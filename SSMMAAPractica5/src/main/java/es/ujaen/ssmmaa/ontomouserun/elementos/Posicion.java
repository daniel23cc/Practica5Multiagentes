package es.ujaen.ssmmaa.ontomouserun.elementos;

import jade.content.Concept;
import jade.content.onto.annotations.Slot;

public class Posicion implements Concept {
    private int corX;
    private int corY;

    public Posicion() {
        this.corX = 0;
        this.corX = 0;
    }

    public Posicion(int corX, int corY) {
        this.corX = corX;
        this.corY = corY;
    }

    @Slot(mandatory=true)
    public int getCorX() {
        return corX;
    }

    public void setCorX(int corX) {
        this.corX = corX;
    }

    @Slot(mandatory=true)
    public int getCorY() {
        return corY;
    }

    public void setCorY(int corY) {
        this.corY = corY;
    }

    @Override
    public String toString() {
        return "Posicion{" +
                "corX=" + corX +
                ", corY=" + corY +
                '}';
    }
}
