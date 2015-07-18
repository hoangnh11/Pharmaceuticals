/*
 * Name: $RCSfile: HttpNetServices.java,v $
 * Version: $Revision: 1.273 $
 * Date: $Date: 2013/04/01 02:09:58 $
 *
 */

package com.viviproject.network.access;

import com.viviproject.network.HttpCommand;
import com.viviproject.network.HttpConnection;
import com.viviproject.network.HttpDeleteMethod;
import com.viviproject.network.HttpGetMethod;
import com.viviproject.network.HttpPostBodyMethod;
import com.viviproject.network.HttpPostMethod;
import com.viviproject.network.HttpPutBodyMethod;
import com.viviproject.network.HttpPutMethod;
import com.viviproject.network.HttpsMethod;
import com.viviproject.network.NetParameter;

/**
 * @author hoangnh11
 */
public final class HttpNetServices implements INetServices {

	public final static HttpNetServices Instance = new HttpNetServices();

	private HttpCommand getHttpCommand(HttpFunctionInfo func) {
		HttpCommand cmd = new HttpCommand();
		cmd.setParam(func.getParams());
		cmd.setHeader(func.getHeader());
		cmd.setBody(func.getBody());
		return cmd;
	}

	private String executer(HttpFunctionInfo funcInfo) throws Exception {
		HttpConnection httpConnection = null;
		switch (funcInfo.getMethodType()) {
		case HttpConnection.HTTP_METHOD_POST:
			httpConnection = new HttpPostMethod(funcInfo.getUrl());
			break;
		case HttpConnection.HTTP_METHOD_GET:
			httpConnection = new HttpGetMethod(funcInfo.getUrl());
			break;
		case HttpConnection.HTTPS_METHOD:
			httpConnection = new HttpsMethod(funcInfo.getUrl());
			break;
		case HttpConnection.HTTP_METHOD_PUT:
			httpConnection = new HttpPutMethod(funcInfo.getUrl());
			break;
		case HttpConnection.HTTP_METHOD_DELETE:

			httpConnection = new HttpDeleteMethod(funcInfo.getUrl());
			break;
		case HttpConnection.HTTP_METHOD_POST_BODY:
			httpConnection = new HttpPostBodyMethod(funcInfo.getUrl());
			break;
		case HttpConnection.HTTP_METHOD_PUT_BODY:
			httpConnection = new HttpPutBodyMethod(funcInfo.getUrl());
			break;
		}
		HttpCommand cmd = getHttpCommand(funcInfo);
		return httpConnection.makeRequest(cmd);
	}
	
//	@Override
//	public String storeFailDeductMilesReport(NetParameter[] netParameters) throws Exception {
//		HttpFunctionInfo functionInfo = HttpFunctionFactory.storeFailDeductMilesReport(netParameters);
//		return executer(functionInfo);
//	}	
	
	@Override
	public String login(NetParameter[] netParameters) throws Exception {
		HttpFunctionInfo functionInfo = HttpFunctionFactory.login(netParameters);
		return executer(functionInfo);
	}
	
	@Override
	public String getUserInformation(NetParameter[] netParameters) throws Exception {
		HttpFunctionInfo functionInfo = HttpFunctionFactory.getUserInformation(netParameters);
		return executer(functionInfo);
	}
	
	@Override
	public String getStores(NetParameter[] netParameters) throws Exception {
		HttpFunctionInfo functionInfo = HttpFunctionFactory.getStores(netParameters);
		return executer(functionInfo);
	}
	
	@Override
	public String getStoresLine(NetParameter[] netParameters, String day) throws Exception {
		HttpFunctionInfo functionInfo = HttpFunctionFactory.getStoresLine(netParameters, day);
		return executer(functionInfo);
	}

	@Override
	public String trackingLocation(NetParameter[] headers, NetParameter[] netParameters, String body) throws Exception {
		HttpFunctionInfo functionInfo = HttpFunctionFactory.trackingLocation(headers, netParameters, body);
		return executer(functionInfo);
	}
	
	@Override
	public String createStores(NetParameter[] netParameters, String token) throws Exception {
		HttpFunctionInfo functionInfo = HttpFunctionFactory.createStores(netParameters, token);
		return executer(functionInfo);
	}
	
	@Override
	public String updateStores(NetParameter[] netParameters, String id) throws Exception {
		HttpFunctionInfo functionInfo = HttpFunctionFactory.updateStores(netParameters, id);
		return executer(functionInfo);
	}
}
