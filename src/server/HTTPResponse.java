/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import client_serveur_web.Converters;
import java.util.ArrayList;

/**
 *
 * @author Adrien
 */
public class HTTPResponse extends HTTPPacket
{
    public HTTPResponse()
    {
        super();
        
        super.setHeader(HTTPPacket.STATUS, 200);
        super.setHeader(HTTPPacket.STATUS_MESSAGE, "OK");
    }
    public HTTPResponse(int error)
    {
        super();
        
        super.setHeader(HTTPPacket.STATUS, error);
        super.setHeader(HTTPPacket.STATUS_MESSAGE, "Error");
    }
    public HTTPResponse(int error, String errorMessage)
    {
        super();
        
        super.setHeader(HTTPPacket.STATUS, error);
        super.setHeader(HTTPPacket.STATUS_MESSAGE, errorMessage);
    }
    
    public HTTPResponse(byte[] datas, int length)
    {
        super(datas, length);
    }
    
    @Override
    protected String[] getFirstHeadersName()
    {
        return new String[]
        {
            VERSION,
            STATUS,
            STATUS_MESSAGE
        };
    }
}
