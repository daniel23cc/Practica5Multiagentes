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
public interface ConstantesInterface {

   

//    public static final int WIDTH_MIN;
//    public static int WIDTH_MAX;
//    public static int HEIGHT_MIN;
//    public static int HEIGHT_MAX;
//    public static int MIN_QUESOS;
//    public static int MAX_QUESOS;
//    public static int DURACION;
    public static int MIN_NUM_RATONES_FACIL = 2;
    public static int MAX_NUM_RATONES_FACIL = 4;

    public static int MIN_NUM_RATONES_NORMALL = 4;
    public static int MAX_NUM_RATONES_NORMAL = 8;

    public static int MIN_NUM_RATONES_DIFICIL = 8;
    public static int MAX_NUM_RATONES_DIFICIL = 12;

    public static final String NOMBRE_FICHERO = "configuracion.txt";
    public static final String TIPO_SERVICIO = "SERVICIO"; //padre

    

    public static Random aleatorio = new Random();

}
