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
public class HTTPPacket
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
                
                if(line.startsWith("HTTP"))
                { // Status
                    setVersion(req[0]);
                    setStatus(req[1]);
                    setStatusMessage(line.trim().substring(req[0].length() + req[1].length() + 2));
                }
                else
                { // Request
                    setMethod(req[0]);
                    setURI(req[1]);
                    setVersion(req[2]);
                }
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
    public void setHeader(String name, String value)
    {
        headers.put(name.toLowerCase(), value);
    }
    // </editor-fold>
    
    
    // <editor-fold desc="Members" defaultstate="collapsed">
    // <editor-fold desc="URI" defaultstate="collapsed">
    public String getURI()
    {
        return getHeader("URI");
    }
    public void setURI(String uri)
    {
        setHeader("URI", uri);
    }
    // </editor-fold>
    
    // <editor-fold desc="Method" defaultstate="collapsed">
    public String getMethod()
    {
        return getHeader("Method");
    }
    public void setMethod(String method)
    {
        setHeader("Method", method);
    }
    // </editor-fold>
    
    // <editor-fold desc="Version" defaultstate="collapsed">
    private Float version = null;
    public Float getVersion()
    {
        if(version == null)
        {
            String data = getHeader("Version");
        }
        
        if(version == null)
            version = 1.1f;
        
        return version;
    }
    private Float getVersionFromString(String data)
    {
        Float version = null;
        
        if(data != null)
        {
            String[] vals = data.trim().split("/");
            if(vals.length >= 2)
            {
                try
                {
                    version = Float.parseFloat(vals[1]);
                }
                catch(NumberFormatException ex)
                { }
            }
        }
        
        return version;
    }
    public void setVersion(String version)
    {
        setVersion(getVersionFromString(version));
    }
    public void setVersion(Float version)
    {
        this.version = version;
        setHeader("Version", "HTTP/" + version);
    }
    // </editor-fold>
    
    // <editor-fold desc="Content Type" defaultstate="collapsed">
    public String getContentType()
    {
        return getHeader("Content-Type");
    }
    public void setContentType(String contentType)
    {
        setHeader("Content-Type", contentType);
    }
    // </editor-fold>
    
    // <editor-fold desc="Accept" defaultstate="collapsed">
    public String getAccept()
    {
        return getHeader("Accept");
    }
    public void setAccept(String accept)
    {
        setHeader("Accept", accept);
    }
    // </editor-fold>
    
    // <editor-fold desc="Accept Language" defaultstate="collapsed">
    public String getAcceptLanguage()
    {
        return getHeader("Accept-Language");
    }
    public void setAcceptLanguage(String acceptLanguage)
    {
        setHeader("Accept-Language", acceptLanguage);
    }
    // </editor-fold>
    
    // <editor-fold desc="Transfer Encoding" defaultstate="collapsed">
    public boolean isChunked()
    {
        return "chunked".equals(getHeader("Transfer-Encoding"));
    }
    public void setChunked()
    {
        setHeader("Transfer-Encoding", "chunked");
    }
    // </editor-fold>
    
    // <editor-fold desc="Host" defaultstate="collapsed">
    public String getHost()
    {
        return getHeader("Host");
    }
    public void setHost(String host)
    {
        setHeader("Host", host);
    }
    // </editor-fold>
    
    // <editor-fold desc="TODO... " defaultstate="collapsed">
    /*
    // <editor-fold desc="User Agent" defaultstate="collapsed">
    public String getUserAgent()
    {
        return getHeader("Accept-Language");
    }
    public void setUserAgent(String userAgent)
    {
        setHeader("Transfer-Encoding", "chunked");
        this.userAgent = userAgent;
    }
    // </editor-fold>
    
    // <editor-fold desc="Accept Encoding" defaultstate="collapsed">
    private String acceptEncoding = null;
    public String getAcceptEncoding()
    {
        return acceptEncoding;
    }
    public void setAcceptEncoding(String acceptEncoding)
    {
        this.acceptEncoding = acceptEncoding;
    }
    // </editor-fold>
    
    // <editor-fold desc="Accept Charset" defaultstate="collapsed">
    private String acceptCharset = null;
    public String getAcceptCharset()
    {
        return acceptCharset;
    }
    public void setAcceptCharset(String acceptCharset)
    {
        this.acceptCharset = acceptCharset;
    }
    // </editor-fold>
    
    
    // <editor-fold desc="If Modified Since" defaultstate="collapsed">
    private String ifModifiedSince = null;
    public String getIfModifiedSince()
    {
        return ifModifiedSince;
    }
    public void setIfModifiedSince(String ifModifiedSince)
    {
        this.ifModifiedSince = ifModifiedSince;
    }
    // </editor-fold>
    
    // <editor-fold desc="If Unmodified Since" defaultstate="collapsed">
    private String ifUnmodifiedSince = null;
    public String getIfUnmodifiedSince()
    {
        return ifUnmodifiedSince;
    }
    public void setIfUnmodifiedSince(String ifUnmodifiedSince)
    {
        this.ifUnmodifiedSince = ifUnmodifiedSince;
    }
    // </editor-fold>
    
    // <editor-fold desc="Authorization" defaultstate="collapsed">
    private String authorization = null;
    public String getAuthorization()
    {
        return authorization;
    }
    public void setAuthorization(String authorization)
    {
        this.authorization = authorization;
    }
    // </editor-fold>
    
    // <editor-fold desc="Cookie" defaultstate="collapsed">
    private String cookie = null;
    public String getCookie()
    {
        return cookie;
    }
    public void setCookie(String cookie)
    {
        this.cookie = cookie;
    }
    // </editor-fold>
    
    // <editor-fold desc="DNT" defaultstate="collapsed">
    private Boolean dnt = false;
    public Boolean getDNT()
    {
        return dnt;
    }
    public void setDNT(String dnt)
    {
        this.dnt = dnt == "1";
    }
    public void setDNT(Boolean dnt)
    {
        this.dnt = dnt;
    }
    // </editor-fold>
    
    // <editor-fold desc="Connection" defaultstate="collapsed">
    private String connection = null;
    public String getConnection()
    {
        return connection;
    }
    public void setConnection(String connection)
    {
        this.connection = connection;
    }
    // </editor-fold>
    */
    // </editor-fold>
    // </editor-fold>
    
    
    // <editor-fold desc="Status Message" defaultstate="collapsed">
    private String statusMessage = null;
    public String getStatusMessage()
    {
        if(statusMessage == null)
        {
            String data = getHeader("Status");
            if(data != null)
            {
                String[] vals = data.trim().split(" ");
                if(vals.length >= 2)
                    statusMessage = vals[1];
            }
        }
        
        if(statusMessage == null)
            statusMessage = "OK";
            
        return statusMessage;
    }
    public void setStatusMessage(String statusMessage)
    {
        this.statusMessage = statusMessage;
        setHeader("Status", getStatus() + " " + getStatusMessage());
    }
    // </editor-fold>
    
    // <editor-fold desc="Status" defaultstate="collapsed">
    private Integer status = null;
    public Integer getStatus()
    {
        if(status == null)
        {
            String data = getHeader("Status");
            if(data != null)
            {
                String[] vals = data.trim().split(" ");
                if(vals.length >= 1)
                {
                    try
                    {
                        status = Integer.parseInt(vals[0]);
                    }
                    catch(NumberFormatException ex)
                    { }
                }
            }
        }
        
        if(status == null)
            status = 200;
        
        return status;
    }
    public void setStatus(Integer status)
    {
        this.status = status;
        setHeader("Status", getStatus() + " " + getStatusMessage());
    }
    public void setStatus(String status)
    {
        this.status = Integer.parseInt(status);
        setHeader("Status", getStatus() + " " + getStatusMessage());
    }
    // </editor-fold>
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
    
    
    // <editor-fold desc="To String" defaultstate="collapsed">
    public String toRequestString()
    {
        byte[] result = toRequestBytes();
        return Converters.GetStringFromByteArray(result, result.length);
    }
    public String toResponseString()
    {
        byte[] result = toResponseBytes();
        return Converters.GetStringFromByteArray(result, result.length);
    }
    // </editor-fold>
    
    // <editor-fold desc="To Bytes" defaultstate="collapsed">
    public byte[] toRequestBytes()
    {
        ArrayList<byte[]> arry = new ArrayList();
        
        arry.add(HTTPConverters.getFirstLine(new String[]
            {
                getMethod(),
                getURI(),
                getHeader("Version")
            }));
        arry.addAll(getHeadersByte());
        arry.add(HTTPConverters.getEmptyLine());
        arry.add(getMessage());
        
        return Converters.ConcatBytes(arry.toArray(new byte[arry.size()][]));
    }
    public byte[] toResponseBytes()
    {
        ArrayList<byte[]> arry = new ArrayList();
        
        arry.add(HTTPConverters.getFirstLine(new String[]
            {
                getHeader("Version"),
                getStatus().toString(),
                getStatusMessage()
            }));
        arry.addAll(getHeadersByte());
        
        arry.add(HTTPConverters.getEmptyLine());
        arry.add(getMessage());
        
        return Converters.ConcatBytes(arry.toArray(new byte[arry.size()][]));
    }
    protected ArrayList<byte[]> getHeadersByte()
    {
        ArrayList<byte[]> list = new ArrayList();
        
        for(Object name : headers.keySet())
            list.add(getHeaderByte((String)name));
        
        return list;
    }
    protected byte[] getHeaderByte(String name)
    {
        String value = getHeader(name);
        if(value == null)
            return null;
        
        String line = name + ": " + getHeader(name) + "\r\n";
        return Converters.GetByteArrayFromString(line);
    }
    protected static class HTTPConverters
    {
        protected static byte[] getFirstLine(String[] datas)
        {
            String line = "";

            for(String data : datas)
                line += (line.length() > 0 ? " " : "") + data;

            return Converters.GetByteArrayFromString(line + "\r\n");
        }
        protected static byte[] getEmptyLine()
        {
            return Converters.GetByteArrayFromString("\r\n");
        }
    }
    // </editor-fold>
    
}
