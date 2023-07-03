/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package utils;

import java.util.Random;

/**
 *
 * @author javierarandaizquierdo
 */
public class Constantes {

    public static Random aleatorio = new Random();

    public enum ModoJuego {
        BUSQUEDA, TORNEO, ELIMINATORIA;
    }

    public enum DificultadJuego {
        BUSQUEDAD, FACIL, NORMAL, DIFICIL;
    }

    public enum NombreServicio { //hijo
        CLIENTE, RESTAURANTE, COCINA, MONITOR
    }

    public static int WIDTH_MIN;
    public static int WIDTH_MAX;
    public static int HEIGHT_MIN;
    public static int HEIGHT_MAX;
    public static int MIN_QUESOS;
    public static int MAX_QUESOS;
    public static int DURACION;
    public static final String NOMBRE_FICHERO = "configuracion.txt";

}
