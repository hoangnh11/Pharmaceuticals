/*
 * Name: $RCSfile: HttpDeleteMethod.java,v $
 * Version: $Revision: 1.10 $
 * Date: $Date: 2012/10/31 04:01:55 $
 *
 */
package com.viviproject.network;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpRequestBase;

import android.util.Log;


/**
 * @author hoangnh11
 */
public class HttpDeleteMethod extends HttpConnection
{
    public HttpDeleteMethod(String url)
    {
        super(url);
    }

    @Override
    protected void doRequest() throws Exception
    {
        HttpRequestBase httpRequest = null;
        String url = this._urlString;

        if (_command != null)
        {
            url = _command.buildGetCommandUrl(url);
            Log.e("HttpDeleteMethod - doRequest", "url = " + url);
            try
            {
                URI uir = new URI(url);
                httpRequest = new HttpDelete(uir);
                httpRequest = _command.buildHeader(httpRequest);
            }
            catch (URISyntaxException ex)
            {
                throw new URISyntaxException(ex.getInput(),ex.getReason());
            }

        }

        if (httpRequest != null)
        {
            getData(httpRequest);
        }
    }

    @Override
    protected void doRequest(String encodeType) throws Exception {}
}
