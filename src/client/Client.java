/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import client_serveur_web.Converters;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import server.*;

/**
 *
 * @author p1002239
 */
public class Client
{
    public Client()
    {
        try
        {
            String dest = "www.google.fr";
            
            Socket s = new Socket(dest, 80);
            s.setSoTimeout(2000);
            
            
            HTTPPacket packRe = new HTTPRequest(dest, "/", "GET", "HTTP/1.1");
            packRe.setHeader(HTTPPacket.ACCEPT_LANGUAGE, "en-US");
            
            System.out.println(packRe);
            
            OutputStream os = new BufferedOutputStream(s.getOutputStream());
            os.write(packRe.toBytes());
            
            
            System.out.println(":::::::::::::");
            
            
            
            InputStream is = new BufferedInputStream(s.getInputStream());
            byte[] datas = new byte[2000];
            int nb = is.read(datas);
            
            HTTPPacket pack = new HTTPResponse(datas, nb);
            
            s.setSoTimeout(100);
            if(pack.isChunked())
            { // If the response is chunked
                try
                {
                    do
                    {
                        datas = new byte[2000];
                        nb = is.read(datas);
                        pack.addMessage(datas, nb); // Concat datas
                    } while(nb > 0);
                }
                catch(SocketTimeoutException ex)
                { }
            }
            
            
            File f = new File("a.html");
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(pack.getMessage());
            
            is.close();
            os.close();
            s.close();
        }
        catch (IOException ex)
        { }
    }
    
    
    
    
}
