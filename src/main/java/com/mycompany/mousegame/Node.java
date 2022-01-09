package com.mycompany.mousegame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author USUARIO
 */
public class Node {
    private int pnData;
    private boolean plIsTrap; 
    private Node poLink;
    
    public Node(){
        this(0,false);
    }
    
    public Node(int tnData, boolean tlTrap) {
        this.pnData = tnData;
        this.plIsTrap = tlTrap;
        this.poLink = null;
    }
    
    public Node(int tnData){
        this.pnData = tnData;
        this.plIsTrap = false;
        this.poLink = null;
    }
    
    public void setData(int tnData) {
        this.pnData = tnData;
    }

    public int getData() {
        return this.pnData;
    }
    
    public void activateTrap() {
        this.plIsTrap = true;
    }
    
    public void desactivateTrap() {
        this.plIsTrap = false;
    }
    
    public boolean trapStatus() {
        return this.plIsTrap;
    }

    public Node getLink() {
        return this.poLink;
    }
    
    public void setLink(Node tolink) {
        this.poLink = tolink;
    }
    
}
