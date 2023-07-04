package es.ujaen.ssmmaa.ontomouserun.elementos;


import jade.content.onto.annotations.Slot;
import jade.core.AID;

public class Monitor extends AgenteJuego {
    private String nombre;
    private AID agenteMonitor;

    public Monitor() {
        this.nombre = null;
        this.agenteMonitor = null;
    }

    public Monitor(String nombre, AID agenteMonitor) {
        this.nombre = nombre;
        this.agenteMonitor = agenteMonitor;
    }

    @Slot(mandatory=true)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Slot(mandatory=true)
    public AID getAgenteMonitor() {
        return agenteMonitor;
    }

    public void setAgenteMonitor(AID agenteMonitor) {
        this.agenteMonitor = agenteMonitor;
    }

    @Override
    public String toString() {
        return "Monitor{" +
                "nombre='" + nombre + '\'' +
                ", agenteMonitor=" + agenteMonitor +
                '}';
    }
}
