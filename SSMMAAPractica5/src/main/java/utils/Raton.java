/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import es.ujaen.ssmmaa.ontomouserun.elementos.AgenteJuego;

/**
 *
 * @author danie
 */
public class Raton extends AgenteJuego {

    private Raton raton;

    public Raton() {
        this.raton = null;
    }

    public Raton getRaton() {
        return raton;
    }

    public void setRaton(Raton raton) {
        this.raton = raton;
    }
}
