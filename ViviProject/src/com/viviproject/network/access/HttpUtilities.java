/*
 * Name: $RCSfile: HttpUtilities.java,v $
 * Version: $Revision: 1.24 $
 * Date: $Date: 2013/03/26 11:58:54 $
 *
 */

package com.viviproject.network.access;

import com.viviproject.network.NetParameter;

/**
 * @author hoangnh11
 */
public final class HttpUtilities
{
    public static NetParameter getHeaderAccept()
    {
        return new NetParameter("Accept","application/xml");
    }
    
    public static NetParameter getHeaderContentType()
    {
        return new NetParameter("Content-Type",
            "application/x-www-form-urlencoded");
    }
    
    public static NetParameter getHeaderContentTypeUTF8()
    {
        return new NetParameter("Content-Type",
            "application/x-www-form-urlencoded; charset=utf-8");
    }
    
    public static NetParameter getHeaderContentTypeSoap()
    {
        return new NetParameter("Content-Type",
            "application/soap+xml");
    }
    
    
    public static NetParameter getHeaderContentJSon()
    {
        return new NetParameter("Content-Type",
            "application/json; charset=utf-8");
    }
    
    public static NetParameter getHeaderXDevice(String value)
    {
        return new NetParameter("X-Device", value);
    }

    public static NetParameter getHeaderUserAgent(String value)
    {
        return new NetParameter("user-agent", value);
    }
    
//    public static NetParameter getHeaderUserId()
//    {    
//        return new NetParameter("user-id", AcFuncHome._userId);
//    }
    
}
