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
import java.util.ArrayList;

import android.content.Context;

import com.viviproject.entities.EnCoverReport;
import com.viviproject.entities.EnDiscountProgram;
import com.viviproject.entities.EnDiscountProgramItem;
import com.viviproject.entities.EnPlanSale;
import com.viviproject.entities.UserInformation;

/**
 * @author TiepTD
 */
public final class DataStorage {
	private final String FILE_NAME_DATA_USERINFORMATION = "dataUserInformation";
	private final String FILE_NAME_DATA_PLAN_SALE = "dataEnPlanSale";
	private final String FILE_NAME_DATA_COVER_REPORT = "dataEnCoverReport";
	private final String FILE_NAME_DATA_LIST_DISCOUNT_PROGRAM = "listEnDiscountProgram";
	private final String FILE_NAME_DATA_DISCOUNT_PROGRAM = "enDiscountProgram";
	
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
		delete_EnCoverReport(context);
		delete_EnDiscountProgram(context);
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
	
	//==========================================
	/**
	 * save data of Cover Production Report (Bao cao do phu)
	 */
	public void save_EnCoverReport(EnCoverReport enCoverReport, Context context) throws IOException {
		FileOutputStream fos = context.openFileOutput(FILE_NAME_DATA_COVER_REPORT, Context.MODE_PRIVATE);
		ObjectOutputStream os = new ObjectOutputStream(fos);
		os.writeObject(enCoverReport);
		os.close();
		Logger.debug("Finished writing EnCoverReport!");
	}

	/**
	 * read data of Cover Production Report (Bao cao do phu)
	 */
	public EnCoverReport read_EnCoverReport(Context context) throws StreamCorruptedException, IOException, ClassNotFoundException {
		FileInputStream fis = context.openFileInput(FILE_NAME_DATA_COVER_REPORT);
		ObjectInputStream is = new ObjectInputStream(fis);
		EnCoverReport enPlanSale = (EnCoverReport) is.readObject();
		is.close();
		Logger.debug("Finished reading EnPlanSale!");
		return enPlanSale;
	}

	/**
	 * delete all data of Cover Production Report (Bao cao do phu)
	 */
	public void delete_EnCoverReport(Context context) {
		File file = context.getFileStreamPath(FILE_NAME_DATA_COVER_REPORT);
		boolean result = file.delete();
		if (result) {
			Logger.debug("Deleted EnCoverReport!");
		} else {
			Logger.debug("Not deleted EnCoverReport!");
		}
	}
	
	//=================== DISCOUNT PROGRAM ITEM=======================
	
	/**
	 * save data of Discount program (khuyen mai)
	 */
	public void save_ListEnCoverReport(ArrayList<EnDiscountProgramItem> listEnDiscountProgram, Context context) throws IOException {
		FileOutputStream fos = context.openFileOutput(FILE_NAME_DATA_LIST_DISCOUNT_PROGRAM, Context.MODE_PRIVATE);
		ObjectOutputStream os = new ObjectOutputStream(fos);
		os.writeObject(listEnDiscountProgram);
		os.close();
		Logger.debug("Finished writing list EnDiscountProgram!");
	}

	/**
	 * read data of Discount program (khuyen mai)
	 */
	public ArrayList<EnDiscountProgramItem> read_ListEnDiscountProgram(Context context) throws StreamCorruptedException, IOException, ClassNotFoundException {
		FileInputStream fis = context.openFileInput(FILE_NAME_DATA_LIST_DISCOUNT_PROGRAM);
		ObjectInputStream is = new ObjectInputStream(fis);
		@SuppressWarnings("unchecked")
		ArrayList<EnDiscountProgramItem> listEnDiscountProgram = (ArrayList<EnDiscountProgramItem>) is.readObject();
		is.close();
		Logger.debug("Finished reading list EnDiscountProgram!");
		return listEnDiscountProgram;
	}

	/**
	 * delete all data of Discount program (khuyen mai)
	 */
	public void delete_ListEnDiscountProgram(Context context) {
		File file = context.getFileStreamPath(FILE_NAME_DATA_LIST_DISCOUNT_PROGRAM);
		boolean result = file.delete();
		if (result) {
			Logger.debug("Deleted EnCoverReport!");
		} else {
			Logger.debug("Not deleted EnCoverReport!");
		}
	}
	
	//=================== DISCOUNT PROGRAM =======================
	/**
	 * save data of Discount program (khuyen mai)
	 */
	public void save_EnDiscountProgram(EnDiscountProgram enDiscountProgram, Context context) throws IOException {
		FileOutputStream fos = context.openFileOutput(FILE_NAME_DATA_DISCOUNT_PROGRAM, Context.MODE_PRIVATE);
		ObjectOutputStream os = new ObjectOutputStream(fos);
		os.writeObject(enDiscountProgram);
		os.close();
		Logger.debug("Finished writing EnDiscountProgram!");
	}

	/**
	 * read data of Discount program (khuyen mai)
	 */
	public EnDiscountProgram read_EnDiscountProgram(Context context) throws StreamCorruptedException, IOException, ClassNotFoundException {
		FileInputStream fis = context.openFileInput(FILE_NAME_DATA_DISCOUNT_PROGRAM);
		ObjectInputStream is = new ObjectInputStream(fis);
		EnDiscountProgram enDiscountProgram = (EnDiscountProgram) is.readObject();
		is.close();
		Logger.debug("Finished reading EnDiscountProgram!");
		return enDiscountProgram;
	}

	/**
	 * delete all data of Discount program (khuyen mai)
	 */
	public void delete_EnDiscountProgram(Context context) {
		File file = context.getFileStreamPath(FILE_NAME_DATA_DISCOUNT_PROGRAM);
		boolean result = file.delete();
		if (result) {
			Logger.debug("Deleted EnCoverReport!");
		} else {
			Logger.debug("Not deleted EnCoverReport!");
		}
	}
}
