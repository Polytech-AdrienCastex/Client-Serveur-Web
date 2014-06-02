/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client_serveur_web;

import client.Client;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.Server;

/**
 *
 * @author p1002239
 */
public class ClientServeurWeb
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        Server s = new Server(1700, "H:\\Réseau\\HTTP\\Client-Serveur Web\\datas");
        s.run();
        //new Client();
    }
    
}
