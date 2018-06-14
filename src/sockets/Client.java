/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sockets;

import gui.Cliente;
import gui.IniciarCliente;

/**
 *
 * @author Cristy
 */

public class Client {
    public static void main (String args[]){
        //Cliente c = new Cliente();
        IniciarCliente ic = new IniciarCliente();
        ic.setVisible(true);
        
    }
}
