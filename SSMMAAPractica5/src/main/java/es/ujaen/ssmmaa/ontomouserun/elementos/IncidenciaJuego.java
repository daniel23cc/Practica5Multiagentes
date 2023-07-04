package es.ujaen.ssmmaa.ontomouserun.elementos;

import es.ujaen.ssmmaa.ontomouserun.Vocabulario.Incidencia;
import jade.content.onto.annotations.Slot;



public class IncidenciaJuego extends SubInform {
    private Juego juego;
    private Incidencia detalle;

    public IncidenciaJuego() {
        this.juego = null;
        this.detalle = null;
    }

    public IncidenciaJuego(Juego juego, Incidencia detalle) {
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
    public Incidencia getDetalle() {
        return detalle;
    }

    public void setDetalle(Incidencia detalle) {
        this.detalle = detalle;
    }

    @Override
    public String toString() {
        return "IncidenciaJuego{" +
                "juego=" + juego +
                ", detalle=" + detalle +
                '}';
    }
}
