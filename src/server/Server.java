/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import client_serveur_web.Converters;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author p1002239
 */
public class Server implements Runnable
{
    public Server(int port)
    {
        this.port = port;
        currentDir = new File(System.getProperty("user.dir")); // Current path
    }
    public Server(int port, String currentDirectory)
    {
        this.port = port;
        currentDir = new File(currentDirectory);
    }
    public Server(int port, File currentDirectory)
    {
        this.port = port;
        currentDir = new File(currentDirectory.getAbsolutePath()); // Clone
    }
    
    protected int port;
    public int getCurrentPort()
    {
        return port;
    }
    
    private final File currentDir;
    public File getCurrentDirectory()
    {
        return currentDir;
    }
    
    
    @Override
    public void run()
    {
        try
        {
            ServerSocket socket = new ServerSocket(port);
            try
            {
                do
                {
                    Socket s = socket.accept();
                    System.out.println("New Request");
                    ServerRequestManager manager = new ServerRequestManager(this, s);
                    manager.run();
                } while(true);
            }
            catch(SocketTimeoutException ex)
            { }
        }
        catch (Exception ex)
        {
            System.out.println("/!\\ Une erreur s'est produite!");
            System.out.println(ex.getMessage());
        }
    }
    
    
}
