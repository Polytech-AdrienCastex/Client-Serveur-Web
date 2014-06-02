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
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
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
        new Thread(new Runnable()
        {
            @Override
            public void run() { requestManager_Runtime(); closeMessage("Request Manager Starter"); }
        }).start();
        
        new Thread(new Runnable()
        {
            @Override
            public void run() { commands_Runtime(); closeMessage("Command Promp"); }
        }).start();
    }
    
    private boolean exit = false;
    
    private void closeMessage(String name)
    {
        System.out.println(">> Closure of \"" + name + "\"");
    }
    private void errorMessage(Exception ex)
    {
        System.err.println("/!\\ A error occured!");
        System.err.println(ex.getMessage());
        exit = true;
    }
    
    private void requestManager_Runtime()
    {
        try
        {
            ServerSocket socket = new ServerSocket(port);
            socket.setSoTimeout(1000);
            while(!exit)
            {
                try
                {
                    Socket s = socket.accept();
                    System.out.println("New Request from : " + s.getInetAddress() + "[" + s.getPort() + "]");
                    ServerRequestManager manager = new ServerRequestManager(this, s);
                    manager.run();
                }
                catch(SocketTimeoutException ex)
                { }
            }
        }
        catch (Exception ex)
        {
            errorMessage(ex);
        }
    }
    
    private void commands_Runtime()
    {
        try
        {
            Scanner scan = new Scanner(System.in);
            
            final Map<String, Runnable> menus = new HashMap<>();
            menus.put("exit", new Runnable()
            {
                @Override
                public void run()
                {
                    System.out.println(">> Closure...");
                    exit = true;
                }
            });
            menus.put("help/?", new Runnable()
            {
                @Override
                public void run()
                {
                    for(String menu : menus.keySet())
                        System.out.println(" :: " + menu);
                }
            });
            
            while(!exit)
            {
                System.out.println(">> Command : ");
                String line = scan.nextLine().trim().toLowerCase();
                
                for(String menu : menus.keySet())
                    for(String m : menu.split("/"))
                        if(m.equals(line))
                            menus.get(menu).run();
            }
        }
        catch (Exception ex)
        {
            errorMessage(ex);
        }
    }
    
}
