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
            
            
            HTTPPacket packRe = new HTTPPacket();
            packRe.setVersion("HTTP/1.1");
            packRe.setURI("/");
            packRe.setMethod("GET");
            packRe.setHost(dest);
            //packRe.setConnection("keep-alive");
            //packRe.setAccept("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            //packRe.setUserAgent("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.132 Safari/537.36 OPR/21.0.1432.67");
            //packRe.setDNT(true);
            //packRe.setAcceptEncoding("gzip,deflate,lzma,sdch");
            packRe.setAcceptLanguage("en-US");
            
            System.out.println(packRe.toRequestString());
            
            OutputStream os = new BufferedOutputStream(s.getOutputStream());
            os.write(packRe.toRequestBytes());
            
            
            System.out.println(":::::::::::::");
            
            
            
            InputStream is = new BufferedInputStream(s.getInputStream());
            byte[] datas = new byte[2000];
            int nb = is.read(datas);
            
            HTTPPacket pack = new HTTPPacket(datas, nb);
            
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
