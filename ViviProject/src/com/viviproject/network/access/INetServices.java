/*
 * Name: $RCSfile: INetServices.java,v $
 * Version: $Revision: 1.189 $
 * Date: $Date: 2013/04/01 02:09:59 $
 *
 */

package com.viviproject.network.access;

import com.viviproject.network.NetParameter;

/**
 * @author hoangnh11
 */
public interface INetServices {

	String login(NetParameter[] netParameters) throws Exception;
	String getUserInformation(NetParameter[] netParameters) throws Exception;
	String getRegions(NetParameter[] netParameters) throws Exception;
	String getStores(NetParameter[] netParameters) throws Exception;
	String getStoresLine(NetParameter[] netParameters, String day) throws Exception;
	String trackingLocation(NetParameter[] headers, NetParameter[] netParameters) throws Exception;
	String createStores(NetParameter[] netParameters, String token) throws Exception;
	String updateStores(NetParameter[] netParameters, String token, String id) throws Exception;
	String getProducts(NetParameter[] netParameters) throws Exception;
	String reportImages(String token, NetParameter[] netParameters) throws Exception;
	String prepareSale(NetParameter[] netParameters, String token) throws Exception;
	String createSale(NetParameter[] netParameters, String token) throws Exception;
	String getProductsSimple(NetParameter[] netParameters) throws Exception;
	String sendReportInventory(NetParameter[] netParameters, String token) throws Exception;
	String feedback(NetParameter[] netParameters, String token) throws Exception;
	String getGimics(NetParameter[] netParameters) throws Exception;
	String createGimic(NetParameter[] netParameters, String token) throws Exception;
	String getSales(NetParameter[] netParameters) throws Exception;
	String delivery(NetParameter[] netParameters, String token, String id) throws Exception;
	String getSalesOrder(NetParameter[] netParameters, String id) throws Exception;
	String createSale(NetParameter[] netParameters, String token, String id) throws Exception;
	String getListDelivery(NetParameter[] netParameters) throws Exception;
	String orderCancel(NetParameter[] netParameters, String token) throws Exception;
	String refund(NetParameter[] netParameters, String token) throws Exception;
	String storesNoSale(NetParameter[] netParameters) throws Exception;
	String getStoreWaitApprove(NetParameter[] netParameters) throws Exception;
	String lineChange(NetParameter[] netParameters, String token) throws Exception;
	String search(NetParameter[] netParameters) throws Exception;
	String searchDelivedOrder(NetParameter[] netParameters) throws Exception;
	String locationChange(NetParameter[] netParameters, String token) throws Exception;
}
