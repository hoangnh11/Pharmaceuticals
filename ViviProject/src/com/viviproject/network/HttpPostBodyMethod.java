/*
 * Name: $RCSfile: HttpPostBodyMethod.java,v $
 * Version: $Revision: 1.5 $
 * Date: $Date: 2011/09/20 11:55:16 $
 *
 */
package com.viviproject.network;

import java.io.UnsupportedEncodingException;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;


/**
 * @author hoangnh11
 */
public class HttpPostBodyMethod extends HttpConnection
{
    public HttpPostBodyMethod(String url)
    {
        super(url);
    }

    @Override
    protected void doRequest() throws Exception
    {
        String url = this._urlString;
        HttpPost httpRequest = new HttpPost(url);
        if (_command != null)
        {
            try
            {
                httpRequest = (HttpPost) _command.buildHeader((HttpRequestBase)httpRequest);
                httpRequest = _command.buildPostCommand((HttpPost) httpRequest);
                httpRequest = _command.buildBody((HttpPost)httpRequest);
            }
            catch (UnsupportedEncodingException ex)
            {
                throw new UnsupportedEncodingException();
            }
        }

        if (httpRequest != null)
        {
            getData(httpRequest);
        }
    }

    @Override
    protected void doRequest(String encodeType) throws Exception
    {
    }
}
