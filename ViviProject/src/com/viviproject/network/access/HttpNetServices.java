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
	public String getRegions(NetParameter[] netParameters) throws Exception {
		HttpFunctionInfo functionInfo = HttpFunctionFactory.getRegions(netParameters);
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
	public String trackingLocation(NetParameter[] headers, NetParameter[] netParameters) throws Exception {
		HttpFunctionInfo functionInfo = HttpFunctionFactory.trackingLocation(headers, netParameters);
		return executer(functionInfo);
	}
	
	@Override
	public String createStores(NetParameter[] netParameters, String token) throws Exception {
		HttpFunctionInfo functionInfo = HttpFunctionFactory.createStores(netParameters, token);
		return executer(functionInfo);
	}
	
	@Override
	public String updateStores(NetParameter[] netParameters, String token, String id) throws Exception {
		HttpFunctionInfo functionInfo = HttpFunctionFactory.updateStores(netParameters, token, id);
		return executer(functionInfo);
	}
	
	@Override
	public String getProducts(NetParameter[] netParameters) throws Exception {
		HttpFunctionInfo functionInfo = HttpFunctionFactory.getProducts(netParameters);
		return executer(functionInfo);
	}

	@Override
	public String reportImages(String token, NetParameter[] netParameters) throws Exception{
		HttpFunctionInfo functionInfo = HttpFunctionFactory.reportImages(token, netParameters);
		return executer(functionInfo);
	}
	
	@Override
	public String prepareSale(NetParameter[] netParameters, String token) throws Exception {
		HttpFunctionInfo functionInfo = HttpFunctionFactory.prepareSale(netParameters, token);
		return executer(functionInfo);
	}
	
	@Override
	public String createSale(NetParameter[] netParameters, String token) throws Exception {
		HttpFunctionInfo functionInfo = HttpFunctionFactory.createSale(netParameters, token);
		return executer(functionInfo);
	}
	
	@Override
	public String getProductsSimple(NetParameter[] netParameters) throws Exception {
		HttpFunctionInfo functionInfo = HttpFunctionFactory.getProductsSimple(netParameters);
		return executer(functionInfo);
	}
	
	@Override
	public String sendReportInventory(NetParameter[] netParameters, String token) throws Exception {
		HttpFunctionInfo functionInfo = HttpFunctionFactory.sendReportInventory(netParameters, token);
		return executer(functionInfo);
	}
	
	@Override
	public String feedback(NetParameter[] netParameters, String token) throws Exception {
		HttpFunctionInfo functionInfo = HttpFunctionFactory.feedback(netParameters, token);
		return executer(functionInfo);
	}
	
	@Override
	public String getGimics(NetParameter[] netParameters) throws Exception {
		HttpFunctionInfo functionInfo = HttpFunctionFactory.getGimics(netParameters);
		return executer(functionInfo);
	}
	
	@Override
	public String createGimic(NetParameter[] netParameters, String token) throws Exception {
		HttpFunctionInfo functionInfo = HttpFunctionFactory.createGimic(netParameters, token);
		return executer(functionInfo);
	}
	
	@Override
	public String getSales(NetParameter[] netParameters) throws Exception {
		HttpFunctionInfo functionInfo = HttpFunctionFactory.getSales(netParameters);
		return executer(functionInfo);
	}
	
	@Override
	public String delivery(String token, String id) throws Exception {
		HttpFunctionInfo functionInfo = HttpFunctionFactory.delivery(token, id);
		return executer(functionInfo);
	}
	
	@Override
	public String getSalesOrder(NetParameter[] netParameters, String id) throws Exception {
		HttpFunctionInfo functionInfo = HttpFunctionFactory.getSalesOrder(netParameters, id);
		return executer(functionInfo);
	}
	
	@Override
	public String createSale(NetParameter[] netParameters, String token, String id) throws Exception {
		HttpFunctionInfo functionInfo = HttpFunctionFactory.createSale(netParameters, token, id);
		return executer(functionInfo);
	}
	
	@Override
	public String getListDelivery(NetParameter[] netParameters) throws Exception {
		HttpFunctionInfo functionInfo = HttpFunctionFactory.getListDelivery(netParameters);
		return executer(functionInfo);
	}
}
