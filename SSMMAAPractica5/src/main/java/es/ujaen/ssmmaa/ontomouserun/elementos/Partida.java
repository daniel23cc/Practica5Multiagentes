package es.ujaen.ssmmaa.ontomouserun.elementos;

import jade.content.Concept;
import jade.content.onto.annotations.Slot;

public class Partida implements Concept {
    private String idJuego;
    private String idPartida;

    public Partida() {
        this.idJuego = null;
        this.idPartida = null;
    }

    public Partida(String idJuego, String idPartida) {
        this.idJuego = idJuego;
        this.idPartida = idPartida;
    }

    @Slot(mandatory=true)
    public String getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(String idJuego) {
        this.idJuego = idJuego;
    }

    @Slot(mandatory=true)
    public String getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(String idPartida) {
        this.idPartida = idPartida;
    }

    @Override
    public String toString() {
        return "Partida{" +
                "idJuego='" + idJuego + '\'' +
                ", idPartida='" + idPartida + '\'' +
                '}';
    }
}
