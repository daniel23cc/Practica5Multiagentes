package mouserun.mouse;

import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import javafx.util.Pair;
import mouserun.game.Mouse;
import mouserun.game.Grid;
import mouserun.game.Cheese;

/**
 * Clase que contiene el esqueleto del raton base para las prácticas de
 * Inteligencia Artificial del curso 2020-21.
 *
 * @author Cristóbal José Carmona (ccarmona@ujaen.es)
 */
public class M21A06B2 extends Mouse {

    private HashMap<Pair<Integer, Integer>, Grid> celdasVisitadas;
    private final HashMap<Pair<Integer, Integer>, Grid> celdasVisitadasGlobal;
    private Stack<Grid> pilaMovimientos;
    private Stack<Grid> caminohaciaQueso;
    private boolean BombaPuesta;
    private boolean terminar = false;
    private int numBombas;

    /**
     * Constructor (Puedes modificar el nombre a tu gusto).
     */
    public M21A06B2() {
        super("B2");
        celdasVisitadas = new HashMap<>();
        celdasVisitadasGlobal=new HashMap<>();
        pilaMovimientos = new Stack<>();
        caminohaciaQueso = new Stack<>();
        BombaPuesta = false;
        numBombas=5;
    }

    int movimientoAleatorio(Grid casActual) {
        Random r = new Random();
        ArrayList<Integer> arrayAl = new ArrayList<>();
        if (casActual.canGoUp()) {
            arrayAl.add(Mouse.UP);
        }
        if (casActual.canGoDown()) {
            arrayAl.add(Mouse.DOWN);
        }
        if (casActual.canGoRight()) {
            arrayAl.add(Mouse.RIGHT);
        }
        if (casActual.canGoLeft()) {
            arrayAl.add(Mouse.LEFT);
        }

        return arrayAl.get(r.nextInt(arrayAl.size()));
    }

    private int MarchaAtras(Grid casActual) {
        if (pilaMovimientos.isEmpty()) {
            celdasVisitadas.clear();
            return movimientoAleatorio(casActual);
        } else {
            Grid casAnt = pilaMovimientos.pop();
            int movimiento = -1;
            if (casActual.getX() == casAnt.getX() && casActual.getY() - 1 == casAnt.getY()) {
                movimiento = Mouse.DOWN;
            }
            if (casActual.getX() - 1 == casAnt.getX() && casActual.getY() == casAnt.getY()) {
                movimiento = Mouse.LEFT;
            }
            if (casActual.getX() == casAnt.getX() && casActual.getY() + 1 == casAnt.getY()) {
                movimiento = Mouse.UP;
            }
            if (casActual.getX() + 1 == casAnt.getX() && casActual.getY() == casAnt.getY()) {
                movimiento = Mouse.RIGHT;
            }
            return movimiento;
        }
    }

    private int aniadeMovimientos(Grid currentGrid, Cheese cheese) {
        HashMap<Integer, Integer> opciones = new HashMap<>();
        int mov = -1;
        Pair<Integer, Integer> CasillaArriba = new Pair<>(currentGrid.getX(), currentGrid.getY() + 1);
        Pair<Integer, Integer> CasillaDerecha = new Pair<>(currentGrid.getX() + 1, currentGrid.getY());
        Pair<Integer, Integer> CasillaAbajo = new Pair<>(currentGrid.getX(), currentGrid.getY() - 1);
        Pair<Integer, Integer> CasillaIzquierda = new Pair<>(currentGrid.getX() - 1, currentGrid.getY());

        if (currentGrid.canGoUp() && !celdasVisitadas.containsKey(CasillaArriba)) {
            opciones.put(1, Mouse.UP);
        }
        if (currentGrid.canGoRight() && !celdasVisitadas.containsKey(CasillaDerecha)) {
            opciones.put(2, Mouse.RIGHT);
        }
        if (currentGrid.canGoDown() && !celdasVisitadas.containsKey(CasillaAbajo)) {
            opciones.put(3, Mouse.DOWN);
        }
        if (currentGrid.canGoLeft() && !celdasVisitadas.containsKey(CasillaIzquierda)) {
            opciones.put(4, Mouse.LEFT);
        }

        if (cheese.getY() > currentGrid.getY() && opciones.containsKey(1)) {
            return Mouse.UP;
        } else if (cheese.getY() < currentGrid.getY() && opciones.containsKey(3)) {
            return Mouse.DOWN;
        } else if (cheese.getX() > currentGrid.getX() && opciones.containsKey(2)) {
            return Mouse.RIGHT;
        } else if (cheese.getX() < currentGrid.getX() && opciones.containsKey(4)) {
            return Mouse.LEFT;
        }

        if (!opciones.isEmpty()) {
            if (opciones.containsKey(1)) {
                return Mouse.UP;
            } else if (opciones.containsKey(2)) {
                return Mouse.RIGHT;
            } else if (opciones.containsKey(3)) {
                return Mouse.DOWN;
            } else if (opciones.containsKey(4)) {
                return Mouse.LEFT;
            }
        }

        return mov;
    }

    private int ExploracionQueso(Grid currentGrid, Cheese cheese) {
        Pair<Integer, Integer> CasillaActual = new Pair<>(currentGrid.getX(), currentGrid.getY());
        if (!celdasVisitadas.containsKey(CasillaActual)) {
            celdasVisitadas.put(CasillaActual, currentGrid);
        }
        
        if(!celdasVisitadasGlobal.containsKey(CasillaActual)){
            celdasVisitadasGlobal.put(CasillaActual, currentGrid);
            incExploredGrids();
        }
        int queMuevo = aniadeMovimientos(currentGrid, cheese);
        if (queMuevo != -1) {
            pilaMovimientos.add(currentGrid);
            return queMuevo;
        } else {
            return MarchaAtras(currentGrid);
        }
    }

    private int aniadeMovimientos2(Grid currentGrid) {
        int mov = -1;
        Pair<Integer, Integer> CasillaArriba = new Pair<>(currentGrid.getX(), currentGrid.getY() + 1);
        Pair<Integer, Integer> CasillaDerecha = new Pair<>(currentGrid.getX() + 1, currentGrid.getY());
        Pair<Integer, Integer> CasillaAbajo = new Pair<>(currentGrid.getX(), currentGrid.getY() - 1);
        Pair<Integer, Integer> CasillaIzquierda = new Pair<>(currentGrid.getX() - 1, currentGrid.getY());

        if (currentGrid.canGoUp() && !celdasVisitadas.containsKey(CasillaArriba)) {
            mov = Mouse.UP;
        } else if (currentGrid.canGoRight() && !celdasVisitadas.containsKey(CasillaDerecha)) {
            mov = Mouse.RIGHT;
        } else if (currentGrid.canGoDown() && !celdasVisitadas.containsKey(CasillaAbajo)) {
            mov = Mouse.DOWN;
        } else if (currentGrid.canGoLeft() && !celdasVisitadas.containsKey(CasillaIzquierda)) {
            mov = Mouse.LEFT;
        }
        return mov;
    }

    private int ExploracionReloj(Grid currentGrid) {
        Pair<Integer, Integer> CasillaActual = new Pair<>(currentGrid.getX(), currentGrid.getY());
        if (!celdasVisitadas.containsKey(CasillaActual)) {
            celdasVisitadas.put(CasillaActual, currentGrid);
        }
        
        if (!celdasVisitadasGlobal.containsKey(CasillaActual)) {
            celdasVisitadasGlobal.put(CasillaActual, currentGrid);
            incExploredGrids();
        }
        int queMuevo = aniadeMovimientos2(currentGrid);
        if (queMuevo != -1) {
            pilaMovimientos.add(currentGrid);
            return queMuevo;
        } else {
            return MarchaAtras(currentGrid);
        }
    }

    public int ExploracionAleatoria(Grid currentGrid) {
        Pair<Integer, Integer> CasillaActual = new Pair<>(currentGrid.getX(), currentGrid.getY());
        Random r = new Random();
        if (!celdasVisitadas.containsKey(CasillaActual)) {
            celdasVisitadas.put(CasillaActual, currentGrid);
        }
        if (!celdasVisitadasGlobal.containsKey(CasillaActual)) {
            celdasVisitadasGlobal.put(CasillaActual, currentGrid);
            incExploredGrids();
        }
        ArrayList<Integer> queMuevo = aniadeMovimientos3(currentGrid);
        if (!queMuevo.isEmpty()) {
            pilaMovimientos.add(currentGrid);
            return queMuevo.get(r.nextInt(queMuevo.size()));
        } else {
            return MarchaAtras(currentGrid);
        }

    }

    private ArrayList<Integer> aniadeMovimientos3(Grid currentGrid) {
        ArrayList<Integer> opciones = new ArrayList<>();
        int mov = -1;
        Pair<Integer, Integer> CasillaArriba = new Pair<>(currentGrid.getX(), currentGrid.getY() + 1);
        Pair<Integer, Integer> CasillaDerecha = new Pair<>(currentGrid.getX() + 1, currentGrid.getY());
        Pair<Integer, Integer> CasillaAbajo = new Pair<>(currentGrid.getX(), currentGrid.getY() - 1);
        Pair<Integer, Integer> CasillaIzquierda = new Pair<>(currentGrid.getX() - 1, currentGrid.getY());

        if (currentGrid.canGoUp() && !celdasVisitadas.containsKey(CasillaArriba)) {
            opciones.add(Mouse.UP);
        }
        if (currentGrid.canGoRight() && !celdasVisitadas.containsKey(CasillaDerecha)) {
            opciones.add(Mouse.RIGHT);
        }
        if (currentGrid.canGoDown() && !celdasVisitadas.containsKey(CasillaAbajo)) {
            opciones.add(Mouse.DOWN);
        }
        if (currentGrid.canGoLeft() && !celdasVisitadas.containsKey(CasillaIzquierda)) {
            opciones.add(Mouse.LEFT);
        }
        return opciones;
    }

    private boolean esCruce(Grid currentGrid) {
        return (currentGrid.canGoUp() && currentGrid.canGoDown() && currentGrid.canGoLeft() && currentGrid.canGoRight());
    }

    private ArrayList<Grid> calculoHijos(Grid posRelativadelQueso,Grid posRaton) {
        int disX=posRelativadelQueso.getX()-posRaton.getX();
        int disY=posRelativadelQueso.getY()-posRaton.getY();
        
        Pair<Integer, Integer> CasillaArriba = new Pair<>(posRelativadelQueso.getX(), posRelativadelQueso.getY() + 1);
        Pair<Integer, Integer> CasillaDerecha = new Pair<>(posRelativadelQueso.getX() + 1, posRelativadelQueso.getY());
        Pair<Integer, Integer> CasillaAbajo = new Pair<>(posRelativadelQueso.getX(), posRelativadelQueso.getY() - 1);
        Pair<Integer, Integer> CasillaIzquierda = new Pair<>(posRelativadelQueso.getX() - 1, posRelativadelQueso.getY());
        
        
        ArrayList<Grid> dev = new ArrayList<>();//VECTOR DE HIJOS DE LA CASILLA ACTUAL EN EL PROCESO DE REUBICACIÓN DE BFS, INICIALMENTE VACIO
        Pair<Integer, Integer> actual = new Pair<>(posRelativadelQueso.getX(), posRelativadelQueso.getY());
        
        if(abs(disY)<abs(disX)){
            if(disY<0){
                if ((celdasVisitadas.get(actual).canGoUp()) && (celdasVisitadas.containsKey(CasillaArriba))){
                    dev.add(celdasVisitadas.get(CasillaArriba));
                }
                if (celdasVisitadas.get(actual).canGoRight() && (celdasVisitadas.containsKey(CasillaDerecha))) {
                    dev.add(celdasVisitadas.get(CasillaDerecha));
                }
                if ((celdasVisitadas.get(actual).canGoLeft()) && (celdasVisitadas.containsKey(CasillaIzquierda))) {
                    dev.add(celdasVisitadas.get(CasillaIzquierda));
                }
                if ((celdasVisitadas.get(actual).canGoDown()) && (celdasVisitadas.containsKey(CasillaAbajo))) {
                    dev.add(celdasVisitadas.get(CasillaAbajo));
                }
            }else{
                if ((celdasVisitadas.get(actual).canGoDown()) && (celdasVisitadas.containsKey(CasillaAbajo))) {
                    dev.add(celdasVisitadas.get(CasillaAbajo));
                }
                if (celdasVisitadas.get(actual).canGoRight() && (celdasVisitadas.containsKey(CasillaDerecha))) {
                    dev.add(celdasVisitadas.get(CasillaDerecha));
                }
                if ((celdasVisitadas.get(actual).canGoLeft()) && (celdasVisitadas.containsKey(CasillaIzquierda))) {
                    dev.add(celdasVisitadas.get(CasillaIzquierda));
                }
                if ((celdasVisitadas.get(actual).canGoUp()) && (celdasVisitadas.containsKey(CasillaArriba))){
                    dev.add(celdasVisitadas.get(CasillaArriba));
                }
                
            }
        }else{
            if(disY>0){
               
                if (celdasVisitadas.get(actual).canGoRight() && (celdasVisitadas.containsKey(CasillaDerecha))) {
                    dev.add(celdasVisitadas.get(CasillaDerecha));
                }
                if ((celdasVisitadas.get(actual).canGoUp()) && (celdasVisitadas.containsKey(CasillaArriba))){
                    dev.add(celdasVisitadas.get(CasillaArriba));
                }
                if ((celdasVisitadas.get(actual).canGoDown()) && (celdasVisitadas.containsKey(CasillaAbajo))) {
                    dev.add(celdasVisitadas.get(CasillaAbajo));
                }
                if ((celdasVisitadas.get(actual).canGoLeft()) && (celdasVisitadas.containsKey(CasillaIzquierda))) {
                    dev.add(celdasVisitadas.get(CasillaIzquierda));
                }
                
            }else{
                if ((celdasVisitadas.get(actual).canGoLeft()) && (celdasVisitadas.containsKey(CasillaIzquierda))) {
                    dev.add(celdasVisitadas.get(CasillaIzquierda));
                }                
                if ((celdasVisitadas.get(actual).canGoUp()) && (celdasVisitadas.containsKey(CasillaArriba))){
                    dev.add(celdasVisitadas.get(CasillaArriba));
                }
                if ((celdasVisitadas.get(actual).canGoDown()) && (celdasVisitadas.containsKey(CasillaAbajo))) {
                    dev.add(celdasVisitadas.get(CasillaAbajo));
                }
                if (celdasVisitadas.get(actual).canGoRight() && (celdasVisitadas.containsKey(CasillaDerecha))) {
                    dev.add(celdasVisitadas.get(CasillaDerecha));
                }
            }
        }
        return dev;
    }

    private void DFS(Grid posQueso, Grid posRaton) {
        //caminohaciaQueso.clear();
        terminar = false;
        Grid queso = celdasVisitadas.get(new Pair<>(posQueso.getX(), posQueso.getY()));
        Stack<Grid> camino = new Stack<>();
        Stack<Grid> caminoDef=new Stack<>();
        int tDef=9999;
        celdasVisitadas.put(new Pair<>(posRaton.getX(), posRaton.getY()), posRaton);
        camino.add(queso);
        ArrayList<Grid> hijos = calculoHijos(queso, posRaton);
        for (int i = 0; i < hijos.size(); i++) {
            DFSrecursivo(hijos.get(i), posRaton, camino);
            if(camino.size()<tDef){
                tDef=camino.size();
                caminoDef=camino;
            }
        }
        //if (terminar) {
                //return caminohaciaQueso.isEmpty();
                caminohaciaQueso=caminoDef;
        //}
        //return caminohaciaQueso.isEmpty();
    }

    boolean cortaRecursividad(Grid g, Stack<Grid> s) {
        for (Grid i : s) {
            if (i.getX() == g.getX() && i.getY() == g.getY()) {
                return true;
            }
        }
        return false;
    }

    private void DFSrecursivo(Grid posRelativaRespectoQueso, Grid posRaton, Stack<Grid> acumulado) {
        if (!terminar) {
            if (posRelativaRespectoQueso.getX() == posRaton.getX() && posRelativaRespectoQueso.getY() == posRaton.getY()) {
                //caminohaciaQueso = acumulado;
                terminar = true;
            } else {
                Stack<Grid> caminoAux = acumulado;
                caminoAux.add(posRelativaRespectoQueso);
                ArrayList<Grid> hijos = calculoHijos(posRelativaRespectoQueso, posRaton);
                for (int i = 0; i < hijos.size(); i++) {
                    if (!cortaRecursividad(hijos.get(i), caminoAux)) {
                        DFSrecursivo(hijos.get(i), posRaton, caminoAux);
                        acumulado = caminoAux;
                    }
                }
                if (!terminar) {
                    caminoAux.pop();
                    acumulado = caminoAux;
                }
            }
        }

    }

    private int seguirCamino(Grid currentGrid) {
        int pos = -1;
        Grid proxCelda = caminohaciaQueso.pop(); //siguiente movimiento hacia el queso
        if (proxCelda.getX() == currentGrid.getX() && proxCelda.getY() == currentGrid.getY() + 1) {
            pos = Mouse.UP;
        }
        if (proxCelda.getX() == currentGrid.getX() && proxCelda.getY() == currentGrid.getY() - 1) {
            pos = Mouse.DOWN;
        }
        if (proxCelda.getX() == currentGrid.getX() + 1 && proxCelda.getY() == currentGrid.getY()) {
            pos = Mouse.RIGHT;
        }
        if (proxCelda.getX() == currentGrid.getX() - 1 && proxCelda.getY() == currentGrid.getY()) {
            pos = Mouse.LEFT;
        }
        return pos;
    }

    private boolean bombaAleatorio(){
        if(getSteps()>=50){
            Random r=new Random();
            return(r.nextInt(100)<5);
        }
        return false;
    }
    
    private boolean probabilidadCasilla(Grid currentGrid){
        if(getSteps()>=30){
            int prob=0;
            Random r=new Random();
            Pair<Integer, Integer> CasillaArriba = new Pair<>(currentGrid.getX(), currentGrid.getY() + 1);
            Pair<Integer, Integer> CasillaDerecha = new Pair<>(currentGrid.getX() + 1, currentGrid.getY());
            Pair<Integer, Integer> CasillaAbajo = new Pair<>(currentGrid.getX(), currentGrid.getY() - 1);
            Pair<Integer, Integer> CasillaIzquierda = new Pair<>(currentGrid.getX() - 1, currentGrid.getY());

            if(!currentGrid.canGoUp()){
                prob+=3;
            }
            if(!currentGrid.canGoRight()){
                prob+=2;
            }
            if(!currentGrid.canGoLeft()){
                prob+=2;
            }
            if(!currentGrid.canGoDown()){
                prob+=3;
            }
            
            return(r.nextInt(100)<=prob);
            
        }
        return false;
    }
    
    /**
     */
    @Override
    public int move(Grid currentGrid, Cheese cheese) {
        
        if (!BombaPuesta && numBombas>0) {
            if (esCruce(currentGrid) || bombaAleatorio()) {
                BombaPuesta = true;
                return Mouse.BOMB;
            } else {
                BombaPuesta = false;
                if (celdasVisitadas.containsKey(new Pair<>(cheese.getX(), cheese.getY()))) { 
                    if (caminohaciaQueso.isEmpty()) {
                        DFS(new Grid(cheese.getX(), cheese.getY()), currentGrid);
                        pilaMovimientos.add(currentGrid);
                        return seguirCamino(currentGrid);
                    } else {
                        pilaMovimientos.add(currentGrid);
                        return seguirCamino(currentGrid);
                    }
                } else {
                    return ExploracionReloj(currentGrid);
                }
            }
        } else {
            BombaPuesta = false;
            if (celdasVisitadas.containsKey(new Pair<>(cheese.getX(), cheese.getY()))) { 
                if (caminohaciaQueso.isEmpty()) {
                    DFS(new Grid(cheese.getX(), cheese.getY()), currentGrid);
                    pilaMovimientos.add(currentGrid);
                    return seguirCamino(currentGrid);
                } else {
                    pilaMovimientos.add(currentGrid);
                    return seguirCamino(currentGrid);
                }
            } else {
                return ExploracionReloj(currentGrid);
            }
        }

       
    }

    /**
     * @brief Método que se llama cuando aparece un nuevo queso
     */
    @Override
    public void newCheese() 
    {
        caminohaciaQueso.clear();
    }

    /**
     * @brief Método que se llama cuando el raton pisa una bomba
     */
    @Override
    public void respawned() {
//        caminohaciaQueso.clear();
//        pilaMovimientos.clear();
//        celdasVisitadas.clear();
        celdasVisitadas = new HashMap<>();
        pilaMovimientos = new Stack<>();
        caminohaciaQueso = new Stack<>();
    }

}
