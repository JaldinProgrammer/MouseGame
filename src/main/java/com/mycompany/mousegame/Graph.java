/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mousegame;

/**
 *
 * @author USUARIO
 */
import java.util.LinkedList;
public class Graph {

    private static final int pnMAXVERTEX = 49;

    private List paVertex[];
    private String paCave[];
    private int pnn;       //"Dimention" de paVertex[]
    private boolean pamark[];
    private int pnMouseIndex; // place where the mouse of the game is located
    LinkedList<Integer> goStore = new LinkedList<>();
    public Graph() {
        this.paVertex = new List[pnMAXVERTEX + 1];      //paVertex[0..pnMAXVERTEX]
        pnn = -1;
        paCave = new String[pnMAXVERTEX + 1];
        pamark = new boolean[pnMAXVERTEX + 1];
        pnMouseIndex = -1;
    }
    
    public int getMouseIndex(){
        return pnMouseIndex;
    }

    public void setMouseIndex(int tnData){
        if (this.isValidVertex(tnData)) {
            pnMouseIndex = tnData;   
        }
    }
    
    public void killMouse(){
        pnMouseIndex = -1;
    }
    
    public void setTrap(int tnOrigin, int tnDestination){
        this.paVertex[tnOrigin].putTrap(tnDestination);
        this.paVertex[tnDestination].putTrap(tnOrigin);
    }
    
    public void deleteTrap(int tnOrigin, int tnDestination){
        this.paVertex[tnOrigin].removeTrap(tnDestination);
        this.paVertex[tnDestination].removeTrap(tnOrigin);
    }
    
    public boolean isTrapSetted(int tnOrigin, int tnDestination){
        return this.paVertex[tnOrigin].trapStatus(tnDestination);
    }
     
    public String mouseLocation(){
        if (this.pnMouseIndex == -1) {
            return "El raton esta muerto en este momento, porfavor invocalo";
        }
        return "\n { EL RATON SE ENCUENTRA EN LA CUEVA NUMERO "+ this.pnMouseIndex+" y la cueva se llama "+this.paCave[this.pnMouseIndex]+" } \n";
    }
    
    public boolean isEmpty() {
        return (pnn == -1);
    }
    
    public boolean isValidEdge(int tnOrigin, int tnDestination){
        return this.paVertex[tnOrigin].exists(tnDestination);
    }

    public String showLocations() {
        String lcCollection = "\n";
        for (int lni = 0; lni <= this.pnn; lni++) {
            lcCollection += (lni + ".- " + paCave[lni]) + "\n";
        }
        return lcCollection;
    }

    public void addVertex(String tcCavesName) {
        if (this.pnn == pnMAXVERTEX) {
            System.err.println("Graph.addVertex: Demasiados vértices (solo se permiten " + (pnMAXVERTEX + 1) + ")");
            return;
        }

        this.pnn++;
        paVertex[this.pnn] = new List();
        paCave[this.pnn] = tcCavesName;
    }

    public int vertexAmount() {
        return this.pnn + 1;
    }

    public boolean isValidVertex(int tnVertex) {
        return (0 <= tnVertex && tnVertex <= this.pnn);
    }

    public void addEdge(int tnOrigin, int tnDestination) {  //Create egde origin-->destination

        if (!isValidVertex(tnOrigin) || !isValidVertex(tnDestination)) {
            return;
        }
        this.paVertex[tnOrigin].add(tnDestination);
        this.paVertex[tnDestination].add(tnOrigin);
    }

    public void delEdge(int tnOrigin, int tnDestination) {  //Delete egde origin-->destination
        if (!isValidVertex(tnOrigin) || !isValidVertex(tnDestination)) {
            return;
        }
        this.paVertex[tnOrigin].del(tnDestination);
        this.paVertex[tnDestination].del(tnOrigin);
    }

    private void unmarkAll() {
        for (int lni = 0; lni <= this.pnn; lni++) {
            this.pamark[lni] = false;
        }
    }

    private void mark(int tndata) {
        if (isValidVertex(tndata)) {
            this.pamark[tndata] = true;
        }
    }

    private void unmark(int tndata) {
        if (isValidVertex(tndata)) {
            this.pamark[tndata] = false;
        }
    }

    private boolean isMarked(int tndata) {   //returns true if the node is already marked
        return this.pamark[tndata];
    }
    
    public String searchPath(int tnDestination){
        if (!this.isValidVertex(tnDestination)) {
            return "No es una cueva valida";
        }    
        goStore.clear();
        this.unmarkAll();
        goStore.add(this.pnMouseIndex);
        this.searchPathMask(this.pnMouseIndex,tnDestination,false);        
        String lcCollection = "\n |";
        if (goStore.isEmpty()) {
            return "no se pudo llegar:c";
        }
        while(!goStore.isEmpty()){
            lcCollection+= this.paCave[goStore.removeFirst()] + " ->> ";
        }
        return lcCollection+"||";
    }
    
    private boolean searchPathMask(int tnOrigin,int tnDestination, boolean tlwalked){
        mark(tnOrigin);
        if (tlwalked) {
            goStore.add(tnOrigin);
            tlwalked = false;
        }        
        if (tnOrigin == tnDestination) { // ask if we're on our destination
            goStore.add(tnDestination);
            return true;
        }
        for (int lni = 0; lni < this.paVertex[tnOrigin].length(); lni++) {
            int lnConnected = this.paVertex[tnOrigin].get(lni);       
            if (!isMarked(lnConnected) && !this.paVertex[tnOrigin].trapStatus(lnConnected)) {
                tlwalked = true;
                tlwalked = searchPathMask(lnConnected,tnDestination,tlwalked);
            }
        }
        if (!tlwalked) {
            goStore.removeLast();
            return tlwalked;
        }   
        return tlwalked;      
    }

    public void dfs(int tnvertex) {  // Depth-First Search
        if (!isValidVertex(tnvertex)) {
            return;
        }
        unmarkAll();
        dfs1Mask(tnvertex);
    }

    private void dfs1Mask(int tnvertex) {
        mark(tnvertex);
        for (int lni = 0; lni < this.paVertex[tnvertex].length(); lni++) {   //for (each w adjacent a v)
            int lnw = this.paVertex[tnvertex].get(lni);
            if (!isMarked(lnw)) {
                dfs1Mask(lnw);
            }
        }
    }

    public void printListas() {
        if (vertexAmount() == 0) {
            System.out.println("(Grafo Vacío)");
        } else {
            for (int lni = 0; lni <= this.pnn; lni++) {
                System.out.println(this.paCave[lni] + " = V[" + lni + "]-->" + this.paVertex[lni]);
            }
        }
    }

    @Override
    public String toString() {
        if (vertexAmount() == 0) {
            return "(Grafo Vacío)";
        }
        final String lcSpace = ", ";

        unmarkAll();
        String lcCollection = "[";
        String lcComma = "";
        for (int lni = 0; lni <= this.pnn; lni++) {
            for (int lnk = 0; lnk <= this.pnn; lnk++) {
                if (this.paVertex[lni].exists(lnk)) {
                    String lcEdge = "(" + lni + ".-" + this.paCave[lni] + "->> | Trampa = " + (this.paVertex[lni].trapStatus(lnk) ? "Activada  " : "Desactivada  ") + "  |->>" + lnk + ".-" + this.paCave[lnk] + ")\n";
                    lcCollection += lcComma + lcEdge;
                    lcComma = lcSpace;
                    mark(lni);
                    mark(lnk);
                }
            }

            if (!isMarked(lni)) {
                lcCollection += lcCollection + lni;
                lcComma = lcSpace;
            }
        }

        return lcCollection + "]";
    }

    public String getEdges(int tnData) {
        if (!isValidVertex(tnData)) {
            return "no es vertice valido";
        }
        String lcCollection = "[ \n";
        for (int lni = 0; lni < this.paVertex[tnData].length(); lni++) {
            lcCollection += this.paVertex[tnData].get(lni) + ".- CUEVA = " + this.paCave[this.paVertex[tnData].get(lni)] + " \n";
        }
        lcCollection += "]";
        return lcCollection;
    }
    
    public String getAllVertex(){
        String lcCollection = "[ \n";
        for (int lni = 0; lni <= this.pnn; lni++) {
        lcCollection += lni+ ".- CUEVA = " + this.paCave[lni] + " \n";    
        }
        lcCollection += "]";
        return lcCollection;
    }

    
}
