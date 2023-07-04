package es.ujaen.ssmmaa.ontomouserun.elementos;

import jade.content.onto.annotations.Slot;
import jade.core.AID;

public class Organizador extends AgenteJuego {
    private String nombre;
    private AID agenteLaberinto;

    public Organizador() {
        this.nombre = null;
        this.agenteLaberinto = null;
    }

    public Organizador(String nombre, AID agenteLaberinto) {
        this.nombre = nombre;
        this.agenteLaberinto = agenteLaberinto;
    }

    @Slot(mandatory=true)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Slot(mandatory=true)
    public AID getAgenteLaberinto() {
        return agenteLaberinto;
    }

    public void setAgenteLaberinto(AID agenteLaberinto) {
        this.agenteLaberinto = agenteLaberinto;
    }
}
