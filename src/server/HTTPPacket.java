/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import client_serveur_web.Converters;

/**
 *
 * @author p1002239
 */
public class HTTPPacket
{
    // <editor-fold desc="Constructors" defaultstate="collapsed">
    public HTTPPacket()
    {
        
    }
    
    public HTTPPacket(byte[] datas, int length)
    {
        String content = Converters.GetStringFromByteArray(datas, length);
        
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
                
                switch(req[0].toLowerCase())
                {
                    case "accept:":
                        setAccept(data);
                        break;
                        
                    case "accept-language:":
                        setAcceptLanguage(data);
                        break;
                        
                    case "user-agent:":
                        setUserAgent(data);
                        break;
                        
                    case "accept-encoding:":
                        setAcceptEncoding(data);
                        break;
                        
                    case "accept-charset:":
                        setAcceptCharset(data);
                        break;
                        
                    case "host:":
                        setHost(data);
                        break;
                        
                    case "if-modified-since:":
                        setIfModifiedSince(data);
                        break;
                        
                    case "if-unmodified-since:":
                        setIfUnmodifiedSince(data);
                        break;
                        
                    case "authorization:":
                        setAuthorization(data);
                        break;
                        
                    case "cookie:":
                        setCookie(data);
                        break;
                        
                    case "dnt:":
                        setDNT(data);
                        break;
                        
                    case "connection:":
                        setConnection(data);
                        break;
                }
            }
        }
        /*
        String message = "";
        for(int i = line_nb; i < lines.length; i++)
            message += (i == line_nb ? "" : "\n") + lines[i];
        setMessage(message);*/
        int rest = length - offset_char;
        byte[] dest = new byte[rest];
        System.arraycopy(datas, offset_char, dest, 0, rest);
        setMessage(dest);
    }
    // </editor-fold>
    
    
    // <editor-fold desc="Members" defaultstate="collapsed">
    // <editor-fold desc="URI" defaultstate="collapsed">
    private String uri = null;
    public String getURI()
    {
        return uri;
    }
    public void setURI(String uri)
    {
        this.uri = uri;
    }
    // </editor-fold>
    
    // <editor-fold desc="Method" defaultstate="collapsed">
    private String method = null;
    public String getMethod()
    {
        return method;
    }
    public void setMethod(String method)
    {
        this.method = method;
    }
    // </editor-fold>
    
    // <editor-fold desc="Version" defaultstate="collapsed">
    private String version = null;
    public String getVersion()
    {
        return version;
    }
    public void setVersion(String version)
    {
        this.version = version;
    }
    // </editor-fold>
    
    // <editor-fold desc="Content Type" defaultstate="collapsed">
    private String contentType = null;
    public String getContentType()
    {
        return contentType;
    }
    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }
    // </editor-fold>
    
    // <editor-fold desc="Accept" defaultstate="collapsed">
    private String accept = null;
    public String getAccept()
    {
        return accept;
    }
    public void setAccept(String accept)
    {
        this.accept = accept;
    }
    // </editor-fold>
    
    // <editor-fold desc="Accept Language" defaultstate="collapsed">
    private String acceptLanguage = null;
    public String getAcceptLanguage()
    {
        return acceptLanguage;
    }
    public void setAcceptLanguage(String acceptLanguage)
    {
        this.acceptLanguage = acceptLanguage;
    }
    // </editor-fold>
    
    // <editor-fold desc="User Agent" defaultstate="collapsed">
    private String userAgent = null;
    public String getUserAgent()
    {
        return userAgent;
    }
    public void setUserAgent(String userAgent)
    {
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
    
    // <editor-fold desc="Host" defaultstate="collapsed">
    private String host = null;
    public String getHost()
    {
        return host;
    }
    public void setHost(String host)
    {
        this.host = host;
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
    
    // <editor-fold desc="Status Message" defaultstate="collapsed">
    private String statusMessage = "OK";
    public String getStatusMessage()
    {
        return statusMessage;
    }
    public void setStatusMessage(String statusMessage)
    {
        this.statusMessage = statusMessage;
    }
    // </editor-fold>
    
    // <editor-fold desc="Status" defaultstate="collapsed">
    private Integer status = 200;
    public Integer getStatus()
    {
        return status;
    }
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    public void setStatus(String status)
    {
        this.status = Integer.parseInt(status);
    }
    // </editor-fold>
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
        return Converters.ConcatBytes(new byte[][]
        {
            HTTPConverters.getFirstLine(new String[]
            {
                getMethod(),
                getURI(),
                getVersion()
            }),
            HTTPConverters.getHeader("Host", getHost()),
            HTTPConverters.getHeader("Connection", this.getConnection()),
            HTTPConverters.getHeader("Accept", this.getAccept()),
            HTTPConverters.getHeader("DNT", this.getDNT()),
            HTTPConverters.getHeader("User-Agent", this.getUserAgent()),
            HTTPConverters.getHeader("Accept-Encoding", this.getAcceptEncoding()),
            HTTPConverters.getHeader("Accept-Language", this.getAcceptLanguage()),
            HTTPConverters.getEmptyLine()
        });
    }
    public byte[] toResponseBytes()
    {
        return Converters.ConcatBytes(new byte[][]
        {
            HTTPConverters.getFirstLine(new String[]
            {
                getVersion(),
                getStatus().toString(),
                getStatusMessage()
            }),
            HTTPConverters.getHeader("Content-Type", this.getContentType()),
            HTTPConverters.getEmptyLine(),
            getMessage()
        });
    }
    private static class HTTPConverters
    {
        public static byte[] getFirstLine(String[] datas)
        {
            String line = "";

            for(String data : datas)
                line += (line.length() > 0 ? " " : "") + data;

            return Converters.GetByteArrayFromString(line + "\r\n");
        }
        public static byte[] getHeader(String headerName, String data)
        {
            if(data == null)
                return null;

            String line = headerName + ": " + data + "\r\n";

            return Converters.GetByteArrayFromString(line);
        }
        public static byte[] getHeader(String headerName, boolean data)
        {
            String line = headerName + ": " + (data ? "1" : "0") + "\r\n";

            return Converters.GetByteArrayFromString(line);
        }
        public static byte[] getEmptyLine()
        {
            return Converters.GetByteArrayFromString("\r\n");
        }
    }
    // </editor-fold>
    
}
