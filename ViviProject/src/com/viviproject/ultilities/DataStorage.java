/**
 * Name: $RCSfile: DataStorage.java,v $
 * Version: $Revision: 1.1 $
 * Date: $Date: Jun 6, 2013 3:30:20 PM $
 * Copyright (C) 2013 FPT Software, Inc. All rights reserved.
 */
package com.viviproject.ultilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import android.content.Context;

import com.viviproject.entities.EnPlanSale;
import com.viviproject.entities.UserInformation;

/**
 * @author TiepTD
 */
public final class DataStorage {
	private final String FILE_NAME_DATA_USERINFORMATION = "dataUserInformation";
	private final String FILE_NAME_DATA_PLAN_SALE = "dataEnPlanSale";
	
	private static DataStorage instance = new DataStorage();

	private DataStorage() {
	}

	/**
	 * @return
	 */
	public static DataStorage getInstance() {
		return instance;
	}

	/**
	 * delete all data file
	 */
	public void deleteAllData(Context context) {
		delete_UserInformation(context);
		delete_EnPlanSale(context);
	}

	/**
	 * save data of USER INFORMATION
	 */
	public void save_UserInformation(UserInformation userInformation, Context context) throws IOException {
		FileOutputStream fos = context.openFileOutput(FILE_NAME_DATA_USERINFORMATION, Context.MODE_PRIVATE);
		ObjectOutputStream os = new ObjectOutputStream(fos);
		os.writeObject(userInformation);
		os.close();
		Logger.debug("Finished writing User Information!");
	}

	/**
	 * read data of USER INFORMATION
	 */
	public UserInformation read_UserInformation(Context context) throws StreamCorruptedException, IOException, ClassNotFoundException {
		FileInputStream fis = context.openFileInput(FILE_NAME_DATA_USERINFORMATION);
		ObjectInputStream is = new ObjectInputStream(fis);
		UserInformation userInformation = (UserInformation) is.readObject();
		is.close();
		Logger.debug("Finished reading User Information!");
		return userInformation;
	}

	/**
	 * delete all data of USER INFORMATION
	 */
	public void delete_UserInformation(Context context) {
		File file = context.getFileStreamPath(FILE_NAME_DATA_USERINFORMATION);
		boolean result = file.delete();
		if (result) {
			Logger.debug("Deleted User Information!");
		} else {
			Logger.debug("Not deleted User Information!");
		}
	}

	/**
	 * save data of Plan Sale (Bao cao doanh so)
	 */
	public void save_EnPlanSale(EnPlanSale enPlanSale, Context context) throws IOException {
		FileOutputStream fos = context.openFileOutput(FILE_NAME_DATA_PLAN_SALE, Context.MODE_PRIVATE);
		ObjectOutputStream os = new ObjectOutputStream(fos);
		os.writeObject(enPlanSale);
		os.close();
		Logger.debug("Finished writing EnPlanSale!");
	}

	/**
	 * read data of Plan Sale (Bao cao doanh so)
	 */
	public EnPlanSale read_EnPlanSale(Context context) throws StreamCorruptedException, IOException, ClassNotFoundException {
		FileInputStream fis = context.openFileInput(FILE_NAME_DATA_PLAN_SALE);
		ObjectInputStream is = new ObjectInputStream(fis);
		EnPlanSale enPlanSale = (EnPlanSale) is.readObject();
		is.close();
		Logger.debug("Finished reading EnPlanSale!");
		return enPlanSale;
	}

	/**
	 * delete all data of Plan Sale (Bao cao doanh so)
	 */
	public void delete_EnPlanSale(Context context) {
		File file = context.getFileStreamPath(FILE_NAME_DATA_PLAN_SALE);
		boolean result = file.delete();
		if (result) {
			Logger.debug("Deleted EnPlanSale!");
		} else {
			Logger.debug("Not deleted EnPlanSale!");
		}
	}
}
