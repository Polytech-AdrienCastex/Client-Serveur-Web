/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author Adrien
 */
class ServerRequestManager implements Runnable
{
    public ServerRequestManager(Server server, Socket socket)
    {
        this.socket = socket;
        this.server = server;
    }
    
    protected final Socket socket;
    protected final Server server;
    
    private static final String[] defaultFiles = new String[]
    {
        "index.html",
        "index.htm",
        "index.php"
    };

    @Override
    public void run()
    {
        try
        {
            socket.setSoTimeout(2000);
            
            InputStream is = new BufferedInputStream(socket.getInputStream());
            OutputStream os = new BufferedOutputStream(socket.getOutputStream());
            
            byte[] datas = new byte[2000];
            int nb = is.read(datas);

            HTTPPacket pack = new HTTPRequest(datas, nb);


            
            // Searching the file or default files
            File f = new File(server.getCurrentDirectory() + pack.getHeader(HTTPPacket.URI));
            if(f.isDirectory())
                for(String defaultFile : defaultFiles)
                {
                    f = new File(f.getAbsolutePath() + "/" + defaultFile);
                    if(f.exists())
                        break;
                }
            
            HTTPPacket packRe;
            if(!f.exists())
            { // File not found
                System.err.println("File not found : " + f.getAbsolutePath());
                
                packRe = new HTTPResponse(404, "Requested file not found");
                
            }
            else
            { // File found
                System.out.println("File sent : " + f.getAbsolutePath());
                
                packRe = new HTTPResponse();
                
                packRe.setHeader(HTTPPacket.CONTENT_TYPE, Files.probeContentType(f.toPath()));

                FileInputStream fos = new FileInputStream(f);

                packRe.setMessage(new byte[0]);
                nb = fos.read(datas);
                while(nb > 0)
                {
                    packRe.addMessage(datas, nb);
                    nb = fos.read(datas);
                }
                fos.close();
            }
            packRe.setHeader(HTTPPacket.VERSION, "HTTP/1.1");
            
            os.write(packRe.toBytes());

            is.close();
            os.close();
            socket.close();
        }
        catch(Exception ex)
        {
            System.err.println("Error : " + ex.getMessage());
            //ex.printStackTrace();
        }
    }
    
}
