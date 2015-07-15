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
	
//	String storeFailDeductMilesReport(NetParameter[] netParameters) throws Exception;

	String login(NetParameter[] netParameters) throws Exception;
	String getUserInformation(NetParameter[] netParameters) throws Exception;
	String getStores(NetParameter[] netParameters) throws Exception;
}
