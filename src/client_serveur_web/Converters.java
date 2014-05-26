/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client_serveur_web;

import java.io.UnsupportedEncodingException;

/**
 *
 * @author p1002239
 */
public class Converters
{
    public static String GetStringFromByteArray(byte[] datas)
    {
        return GetStringFromByteArray(datas, datas.length, 0);
    }
    public static String GetStringFromByteArray(byte[] datas, int length)
    {
        return GetStringFromByteArray(datas, length, 0);
    }
    public static String GetStringFromByteArray(byte[] datas, int length, int offset)
    {
        try
        {
            return new String(datas, offset, length, "UTF-8");
        }
        catch (UnsupportedEncodingException ex)
        {
            return null;
        }
    }
    public static byte[] GetByteArrayFromString(String str)
    {
        try
        {
            if(str == null)
                return new byte[0];
            
            byte[] datas = str.getBytes("UTF-8");
            return datas;
        }
        catch (UnsupportedEncodingException ex)
        {
            return null;
        }
    }
        
    public static byte[] ConcatBytes(byte[][] array)
    {
        int totalLen = 0;
        for(byte[] b : array)
            if(b != null)
                totalLen += b.length;
        
        byte[] datas = new byte[totalLen];
        int cursor = 0;
        
        for(byte[] b : array)
        {
            if(b != null && b.length > 0)
            {
                System.arraycopy(b, 0, datas, cursor, b.length);
                cursor += b.length;
            }
        }
        
        return datas;
    }
}

