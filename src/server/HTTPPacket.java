/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import client_serveur_web.Converters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author p1002239
 */
public abstract class HTTPPacket
{
    // <editor-fold desc="Constructors" defaultstate="collapsed">
    public HTTPPacket()
    { }
    
    public HTTPPacket(byte[] datas, int length)
    {
        String content = Converters.GetStringFromByteArray(datas, length);
        //System.out.println(content);
        String[] lines = content.split("\r\n");
        int offset_char = 0;
        
        String[] req;
        String data;
        
        int line_nb = 0;
        for(String line : lines)
        {
            line_nb++;
            offset_char += line.length() + 2;
            
            if(line.trim().length() == 0) // Rencontre de la ligne séparatrice avec le message
                 break;
            
            req = line.trim().split(" ");
            if(line_nb == 1)
            { // Request/Status line
                String[] firstNames = getFirstHeadersName();
                for(int i = 0; i < firstNames.length && i < req.length; i++)
                    setHeader(firstNames[i], req[i]);
            }
            else
            { // Headers
                data = line.substring(req[0].length() + 1);
                
                setHeader(req[0], data);
            }
        }
        
        int rest = length - offset_char;
        byte[] dest = new byte[rest];
        System.arraycopy(datas, offset_char, dest, 0, rest);
        setMessage(dest);
    }
    // </editor-fold>
    
    
    // <editor-fold desc="Headers manager" defaultstate="collapsed">
    private Map headers = new HashMap();
    public String getHeader(String name)
    {
        name = name.toLowerCase();
        if(headers.containsKey(name))
            return (String)headers.get(name);
        else
            return null;
    }
    public void setHeader(String name, Object value)
    {
        headers.put(name.toLowerCase(), value.toString());
    }
    // </editor-fold>
    
    public static final String ACCEPT = "Accept";
    public static final String ACCEPT_CHARSET = "Accept-Charset";
    public static final String ACCEPT_ENCODING = "Accept-Encoding";
    public static final String ACCEPT_LANGUAGE = "Accept-Language";
    public static final String ACCEPT_DATETIME = "Accept-Datetime";
    public static final String AUTHORIZATION = "Authorization";
    public static final String CACHE_CONTROL = "Cache-Control";
    public static final String CONNECTION = "Connection";
    
    public static final String DNT = "DNT";
    public static final String URI = "URI";
    public static final String METHOD = "Method";
    public static final String VERSION = "Version";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String HOST = "Host";
    public static final String USER_AGENT = "User-Agent";
    public static final String TRANSFER_ENCODING = "Transfer-Encoding";
    
    public static final String STATUS = "Status";
    public static final String STATUS_MESSAGE = "Status-Message";
    
    // <editor-fold desc="Chunck" defaultstate="collapsed">
    public boolean isChunked()
    {
        return "chunked".equals(getHeader("Transfer-Encoding"));
    }
    public void setChunked()
    {
        setHeader("Transfer-Encoding", "chunked");
    }
    // </editor-fold>
    
    // <editor-fold desc="Message" defaultstate="collapsed">
    private byte[] message = null;
    public String getStringMessage()
    {
        return Converters.GetStringFromByteArray(message, message.length);
    }
    public byte[] getMessage()
    {
        return message;
    }
    public void setMessage(String message)
    {
        this.message = Converters.GetByteArrayFromString(message);
    }
    public void setMessage(byte[] message)
    {
        this.message = message;
    }
    public void setMessage(byte[] message, int length)
    {
        this.message = new byte[length];
        System.arraycopy(message, 0, this.message, 0, length);
    }
    public void addMessage(byte[] message, int length)
    {
        byte[] newDatas = new byte[this.message.length + length];
        
        System.arraycopy(this.message, 0, newDatas, 0, this.message.length);
        System.arraycopy(message, 0, newDatas, this.message.length, length);
        
        this.message = newDatas;
    }
    // </editor-fold>
    
    
    // <editor-fold desc="Converters" defaultstate="collapsed">
    @Override
    public String toString()
    {
        byte[] result = toBytes();
        return Converters.GetStringFromByteArray(result, result.length);
    }
    
    
    
    protected abstract String[] getFirstHeadersName();
    
    public byte[] toBytes()
    {
        ArrayList<byte[]> arry = new ArrayList();
        
        arry.add(getFirstLine(getFirstHeadersName()));
        arry.addAll(getHeadersByte());
        arry.add(getEmptyLine());
        arry.add(getMessage());
        
        return Converters.ConcatBytes(arry.toArray(new byte[arry.size()][]));
    }
    private ArrayList<byte[]> getHeadersByte()
    {
        ArrayList<byte[]> list = new ArrayList();
        
        for(Object name : headers.keySet())
            list.add(getHeaderByte((String)name));
        
        return list;
    }
    private byte[] getHeaderByte(String name)
    {
        String value = getHeader(name);
        if(value == null)
            return null;
        
        String line = name + ": " + getHeader(name) + "\r\n";
        return Converters.GetByteArrayFromString(line);
    }
    private byte[] getFirstLine(String[] names)
    {
        String line = "";

        for(String name : names)
            line += (line.length() > 0 ? " " : "") + getHeader(name);

        return Converters.GetByteArrayFromString(line + "\r\n");
    }
    private byte[] getEmptyLine()
    {
        return Converters.GetByteArrayFromString("\r\n");
    }
    // </editor-fold>
    
}
