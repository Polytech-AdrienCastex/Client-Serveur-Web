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
public class HTTPRequest extends HTTPPacket
{
    public HTTPRequest(String destination, String uri, String method, String version)
    {
        super();
        
        super.setHeader(HTTPPacket.VERSION, version);
        super.setHeader(HTTPPacket.URI, uri);
        super.setHeader(HTTPPacket.METHOD, method);
        super.setHeader(HTTPPacket.HOST, destination);
    }
    
    public HTTPRequest(byte[] datas, int length)
    {
        super(datas, length);
    }
    
    @Override
    protected String[] getFirstHeadersName()
    {
        return new String[]
        {
            METHOD,
            URI,
            VERSION
        };
    }
}
