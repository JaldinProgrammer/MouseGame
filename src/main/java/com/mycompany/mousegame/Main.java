/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mousegame;

import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;

/**
 *
 * @author JaldinDeveloper
 */
public class Main {

    public static void main(String[] args) {
        startGame();
    }

    private static void startGame() {
        Graph loGraph = new Graph();
        ImageIcon loImage;
        int lnkey;
        do {
            lnkey = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "Opciones \n\n"
                    + "1.Anadir cueva \n"
                    + "2.Anadir tunel \n"
                    + "3.Cargar mapa default \n"
                    + "4.Invocar a un Raton \n"
                    + "5.Mover al raton \n"
                    + "6.Poner una trampa \n"
                    + "7.Donde esta el raton? \n"
                    + "8.SALIR \n"
                    + "9.Mostrar Mapa \n"
                    + "10.Quitar trampa \n"
                    + "11.Buscar camino \n"
                    + "12.CLEAR \n"
            ));

            switch (lnkey) {
                case 1: // add cave
                    String lcName = (JOptionPane.showInputDialog(null,
                            "Nombre de la nueva cueva?: \n\n"));
                    if (lcName.length() == 0) {
                        JOptionPane.showMessageDialog(null, "Nombre vacio, ingrese algun nombre");
                    } else {
                        loGraph.addVertex(lcName);
                    }
                    break;
                case 2: //add tunnel
                    if (loGraph.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "El mapa esta vacio crack, inserta algunas cuevas");
                    } else {
                        int lnOrigin = Integer.parseInt(JOptionPane.showInputDialog(null,
                                "Seleccione ORIGEN \n\n" + loGraph.getAllVertex()));
                        int lnDestination = Integer.parseInt(JOptionPane.showInputDialog(null,
                                "Seleccione DESTINO \n\n" + loGraph.getAllVertex()));
                        if (loGraph.isValidVertex(lnOrigin) && loGraph.isValidVertex(lnDestination)) {
                            loGraph.addEdge(lnOrigin, lnDestination);
                        } else {
                            JOptionPane.showMessageDialog(null, "No se pudo crear tunel, uno o mas vertices son invalidos");
                        }
                    }
                    break;
                case 3: // default map
                    loGraph = new Graph();
                    
                    //Caves insertion
                    loGraph.addVertex("A"); //0
                    loGraph.addVertex("B"); //1
                    loGraph.addVertex("C"); //2
                    loGraph.addVertex("D"); //3
                    loGraph.addVertex("E"); //4
                    loGraph.addVertex("F"); //5
                    loGraph.addVertex("G"); //6
                    loGraph.addVertex("H"); //7
                    loGraph.addVertex("I"); //8
                    //Tunnels insertion
                    loGraph.addEdge(0, 1);
                    loGraph.addEdge(0, 2);
                    loGraph.addEdge(0, 5);
                    loGraph.addEdge(5, 6);
                    loGraph.addEdge(5, 8);
                    loGraph.addEdge(8, 7);
                    loGraph.addEdge(7, 4);
                    loGraph.addEdge(7, 2);
                    loGraph.addEdge(2, 6);
                    loGraph.addEdge(2, 3);
                    loGraph.addEdge(3, 1);
                    //traps insertion
                    loGraph.setTrap(6, 2);
                    loGraph.setTrap(2, 3);
                    loGraph.setTrap(7, 4);

                    System.out.println(loGraph.toString());
                    loImage = new ImageIcon("MapCaveDefault.png");
                    JOptionPane.showMessageDialog(null, "Mapa cargado exitosamente", "succesful", JOptionPane.INFORMATION_MESSAGE, loImage);
                    break;
                case 4: //invoque Mouse
                    if (loGraph.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "El mapa esta vacio crack, inserta algunas cuevas");
                    } else {
                        int lnOrigin = Integer.parseInt(JOptionPane.showInputDialog(null,
                                "Seleccione ORIGEN para spawnear al raton \n\n" + loGraph.getAllVertex()));
                        loGraph.setMouseIndex(lnOrigin);
                    }
                    break;
                case 5: // move Mouse
                    if (loGraph.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "El mapa esta vacio crack, inserta algunas cuevas");
                    } else {
                        if (loGraph.getMouseIndex() == -1) {
                            JOptionPane.showMessageDialog(null, "El raton se encuentra muerto, invocalo primero");
                        } else {
                            int lnDestination = Integer.parseInt(JOptionPane.showInputDialog(null,
                                    "Seleccione ORIGEN para mover al raton \n\n" + loGraph.getEdges(loGraph.getMouseIndex())));
                            if (loGraph.isTrapSetted(loGraph.getMouseIndex(), lnDestination)) { // The mouse went through a tunnel with a trap
                                loGraph.killMouse();
                                JOptionPane.showMessageDialog(null, "Game Over bro, invoca otro raton");
                            } else {
                                loGraph.setMouseIndex(lnDestination);
                            }
                        }
                    }
                    break;
                case 6: // set trap
                    if (loGraph.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "El mapa esta vacio crack, inserta algunas cuevas");
                    } else {
                        int lnOrigin = Integer.parseInt(JOptionPane.showInputDialog(null,
                                "Seleccione ORIGEN \n\n" + loGraph.getAllVertex()));
                        int lnDestination = Integer.parseInt(JOptionPane.showInputDialog(null,
                                "Seleccione DESTINO \n\n" + loGraph.getEdges(lnOrigin)));
                        if (loGraph.isValidEdge(lnOrigin, lnDestination)) {
                            loGraph.setTrap(lnOrigin, lnDestination);
                        } else {
                            JOptionPane.showMessageDialog(null, "No se pudo poner trampa");
                        }
                    }
                    break;
                case 7: // where is the mouse?
                    JOptionPane.showMessageDialog(null, loGraph.mouseLocation());
                    break;
                case 8: // exit
                    JOptionPane.showMessageDialog(null, "Bye :,)");
                    break;
                case 9: // show Map
                    JOptionPane.showMessageDialog(null, loGraph.toString());
                    break;
                case 10: // delete trap
                    if (loGraph.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "El mapa esta vacio crack, inserta algunas cuevas");
                    } else {
                        int lnOrigin = Integer.parseInt(JOptionPane.showInputDialog(null,
                                "Seleccione ORIGEN \n\n" + loGraph.getAllVertex()));
                        int lnDestination = Integer.parseInt(JOptionPane.showInputDialog(null,
                                "Seleccione DESTINO \n\n" + loGraph.getEdges(lnOrigin)));
                        if (loGraph.isValidEdge(lnOrigin, lnDestination)) {
                            loGraph.deleteTrap(lnOrigin, lnDestination);
                        } else {
                            JOptionPane.showMessageDialog(null, "No se pudo borrar trampa");
                        }
                    }
                    break;
                case 11: // looking for the path to the destination
                    if (loGraph.getMouseIndex() == -1) {
                        JOptionPane.showMessageDialog(null, "El raton esta muerto, mejor invoca otro");
                    } else {
                        int lnDestination = Integer.parseInt(JOptionPane.showInputDialog(null,
                                "Seleccione Destino \n\n" + loGraph.getAllVertex()));
                        JOptionPane.showMessageDialog(null, loGraph.searchPath(lnDestination));
                    }
                    break;
                case 12: // show Map
                    loGraph = new Graph();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Indice incorrecto, vuelva a marcar un indice dentro del menu");
            }
        } while (lnkey != 8);
    }
}
