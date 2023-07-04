package es.ujaen.ssmmaa.ontomouserun.elementos;

import jade.content.Predicate;
import jade.content.onto.annotations.Slot;

public class JuegoAceptado implements Predicate {
    private Juego juego;
    private AgenteJuego agenteJuego;

    public JuegoAceptado() {
        this.juego = null;
        this.agenteJuego = null;
    }

    @Slot(mandatory=true)
    public Juego getJuego() {
        return juego;
    }

    public void setJuego(Juego juego) {
        this.juego = juego;
    }

    @Slot(mandatory=true)
    public AgenteJuego getAgenteJuego() {
        return agenteJuego;
    }

    public void setAgenteJuego(AgenteJuego agenteJuego) {
        this.agenteJuego = agenteJuego;
    }

    @Override
    public String toString() {
        return "JuegoAceptado{" +
                "juego=" + juego +
                ", agenteJuego=" + agenteJuego +
                '}';
    }
}
