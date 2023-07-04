package es.ujaen.ssmmaa.ontomouserun.elementos;


import es.ujaen.ssmmaa.ontomouserun.Vocabulario.Motivo;
import jade.content.Predicate;
import jade.content.onto.annotations.Slot;



public class Justificacion implements Predicate {
    private Juego juego;
    private Motivo detalle;

    public Justificacion() {
        this.juego = null;
        this.detalle = null;
    }

    public Justificacion(Juego juego, Motivo detalle) {
        this.juego = juego;
        this.detalle = detalle;
    }

    @Slot(mandatory=true)
    public Juego getJuego() {
        return juego;
    }

    public void setJuego(Juego juego) {
        this.juego = juego;
    }

    @Slot(mandatory=true)
    public Motivo getDetalle() {
        return detalle;
    }

    public void setDetalle(Motivo detalle) {
        this.detalle = detalle;
    }

    @Override
    public String toString() {
        return "Justificacion{" +
                "juego=" + juego +
                ", detalle=" + detalle +
                '}';
    }
}
