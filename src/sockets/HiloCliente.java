/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sockets;


import gui.Cliente;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/**
 *
 * @author Cristy
 */
public class HiloCliente extends Thread{
    
    private Cliente cliente;
    private ObjectInputStream entradaObjeto;
    private DataInputStream entrada;
    private Socket socketCliente;

    public HiloCliente(Socket cliente, Cliente c) {
        this.socketCliente = cliente;
        this.cliente = c;
    }
    
    @Override
    public void run(){
        while(true){
            try {
                entrada = new DataInputStream(socketCliente.getInputStream());
                cliente.escribirEnArea(">> " +entrada.readUTF());
                entradaObjeto = new ObjectInputStream(socketCliente.getInputStream());
                try {
                    cliente.actualizaLista((DefaultListModel) entradaObjeto.readObject());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    
}
