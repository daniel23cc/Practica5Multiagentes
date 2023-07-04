package es.ujaen.ssmmaa.ontomouserun.elementos;

import jade.content.onto.annotations.Slot;
import jade.core.AID;

public class Jugador extends AgenteJuego {
    private String nombre;
    private AID agenteRaton;

    public Jugador() {
        this.nombre = null;
        this.agenteRaton = null;
    }

    public Jugador(String nombre, AID agenteJugador) {
        this.nombre = nombre;
        this.agenteRaton = agenteJugador;
    }

    @Slot(mandatory=true)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Slot(mandatory=true)
    public AID getAgenteRaton() {
        return agenteRaton;
    }

    public void setAgenteRaton(AID agenteRaton) {
        this.agenteRaton = agenteRaton;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "nombre='" + nombre + '\'' +
                ", agenteRaton=" + agenteRaton +
                '}';
    }
}
