/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sockets2;

import sockets.*;
import gui2.Servidor;
import java.io.*;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/**
 *
 * @author Cristy
 */
public class HiloServidor  extends Thread{

    private DataInputStream entrada;
    private DataOutputStream salida;
    private Servidor servidor;
    private Socket cliente;
    public static Vector<HiloServidor> usuariosActivos = new Vector();
    private String nombre;
    private ObjectOutputStream salidaObjeto;

    public HiloServidor(Socket cliente, String nombre, Servidor serv) {
        this.cliente = cliente;
        this.servidor = serv;
        this.nombre = nombre;
        usuariosActivos.add(this);
        
        for(int i=0; i<usuariosActivos.size(); i++){
            usuariosActivos.get(i).enviarMensaje(nombre + " inició sesión!!");
            servidor.addUsuario(nombre);
        }
    }

    private void enviarMensaje(String string) {
        try {
            salida = new DataOutputStream(cliente.getOutputStream());
            salida.writeUTF(string);
            DefaultListModel modelo = new DefaultListModel();
            
            for(int i=0; i<usuariosActivos.size(); i++){
                modelo.addElement(usuariosActivos.get(i).nombre);
            }
            
            salidaObjeto = new ObjectOutputStream(cliente.getOutputStream());
            salidaObjeto.writeObject(modelo);
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run(){
        String mensaje= "";
        while(true){
            try {
                entrada = new DataInputStream(cliente.getInputStream());
                mensaje = entrada.readUTF();
                
                for(int i=0; i<usuariosActivos.size(); i++){
                    usuariosActivos.get(i).enviarMensaje(mensaje);
                    servidor.escribirEnArea(mensaje);
                }
            } catch (IOException ex) {
                Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
                break;
            }
        }
        
        usuariosActivos.removeElement(this);
        servidor.escribirEnArea(nombre + " se ha desconectado");
        servidor.quitarConectado(nombre);
        
        try{
            cliente.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
}
