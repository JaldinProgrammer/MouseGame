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
public class List {

    private Node poL;
    private int pnn;

    public List() {
        this.poL = null;
        this.pnn = 0;
    }

    public void add(int tnData, boolean tlTrapStatus) { //Insert data to the list and setting trap status
        Node loBeforeNode = null;
        Node loPointer = this.poL;

        while (loPointer != null && tnData >= loPointer.getData()) {
            loBeforeNode = loPointer;
            loPointer = loPointer.getLink();
        }

        Node loNewNode;
        if (loBeforeNode == null) {   // insert at the begging of the list
            loNewNode = new Node(tnData, tlTrapStatus);
            loNewNode.setLink(this.poL);
            this.poL = loNewNode;
            this.pnn++;
        } else {
            if (loBeforeNode.getData() != tnData) {     // insert between 2 existing nodes
                loNewNode = new Node(tnData, tlTrapStatus);
                loBeforeNode.setLink(loNewNode);
                loNewNode.setLink(loPointer);
                this.pnn++;
            }
        }
    }
    
    public void add(int tnData) { //Insert data to the list
        Node loBeforeNode = null;
        Node loPointer = this.poL;

        while (loPointer != null && tnData >= loPointer.getData()) {
            loBeforeNode = loPointer;
            loPointer = loPointer.getLink();
        }

        Node loNewNode;
        if (loBeforeNode == null) {    // insert at the begging of the list
            loNewNode = new Node(tnData);
            loNewNode.setLink(this.poL);
            this.poL = loNewNode;
            this.pnn++;
        } else {
            if (loBeforeNode.getData() != tnData) {  // insert between 2 nodes
                loNewNode = new Node(tnData);
                loBeforeNode.setLink(loNewNode);
                loNewNode.setLink(loPointer);
                this.pnn++;
            }
        }
    }
    
    public void del(int tndata){     //delete node with data equal to tndata
        Node loBeforeNode = null;
        Node loPointer   = this.poL;
        
        while (loPointer != null && tndata > loPointer.getData()){
            loBeforeNode = loPointer;
            loPointer = loPointer.getLink();
        }
        
        if (loPointer != null && loPointer.getData() == tndata){  //data exists
            if (loBeforeNode == null)
                this.poL = this.poL.getLink();    //data were the first of the list
            else
                loBeforeNode.setLink(loPointer.getLink());
            
            loPointer.setLink(null);
            this.pnn--;    
        }
    }
    
    private Node nodeGet(int tndata){  //returns node that belongs to the tndata
        Node lopointer = this.poL;
        
        while (lopointer != null && tndata > lopointer.getData()){
            lopointer = lopointer.getLink();
        }
        
        if (lopointer != null && lopointer.getData() == tndata)
            return lopointer;
        
        return null;    
    }
    
    public boolean exists(int tndata){  //returns true if tndata exists
        return (nodeGet(tndata) != null);
    }
    
    public int get(int tnk){  //returns the tnk element from the list from 0 to lenght()-1
        Node lopointer = this.poL;
        int lni=0;
        while (lopointer != null){
            if (lni==tnk)
                return lopointer.getData();
            
            lopointer = lopointer.getLink();
            lni++;
        }
        System.err.println("Lista.get: Fuera de rango");
        return -1;
    }
    
    public void putTrap(int tnDestination){
        nodeGet(tnDestination).activateTrap();
    }
    
    public void removeTrap(int tnDestination){
        nodeGet(tnDestination).desactivateTrap();
    }
    
    public int length(){
        return this.pnn;
    }
    
    public boolean trapStatus(int tnData){ // returns true id trap exists
        if (exists(tnData)) {
            return nodeGet(tnData).trapStatus();
        }
        return false;
    }
    
    @Override
    public String toString(){ 
        String lcCollection = "[";
        String lcComa="";
       
        Node loPointer  = this.poL;
        while (loPointer != null){
            lcCollection += lcComa + loPointer.getData() + " ->Trampa " + ( loPointer.trapStatus()?"Activada":"Desactivada" );
            lcComa=", ";
            loPointer = loPointer.getLink();
        }
       
       return lcCollection+"]";
    }
    

}
