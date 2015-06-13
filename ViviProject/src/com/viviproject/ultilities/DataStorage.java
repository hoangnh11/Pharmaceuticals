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
import java.util.List;

import android.content.Context;

/**
 * @author TiepTD
 */
public final class DataStorage {
	private final String FILE_NAME_DATA_ENMYPROFILE = "dataEnMyProfile";

	private final String FILE_NAME_DATA_EXPORTWKCUSTOMERINTERFACE = FILE_NAME_DATA_ENMYPROFILE
			+ "_ExportWkCustomerInterface";

	private static DataStorage instance = new DataStorage();

	private DataStorage() {
	}

	/**
	 * @return
	 */
	public static DataStorage getInstance() {
		return instance;
	}

//	/**
//	 * delete all data file
//	 */
//	public void deleteAllData(Context context) {
//		delete_ExportWkCustomerInterface(context);		
//	}

//	/**
//	 * save data of _ExportWkCustomerInterface
//	 */
//	public void save_ExportWkCustomerInterface(EnExportWkCustomerInterface enExportWkCustomerInterface,
//			Context context) throws IOException {
//		FileOutputStream fos = context.openFileOutput(FILE_NAME_DATA_EXPORTWKCUSTOMERINTERFACE, Context.MODE_PRIVATE);
//		ObjectOutputStream os = new ObjectOutputStream(fos);
//		os.writeObject(enExportWkCustomerInterface);
//		os.close();
//		Logger.debug("Finished writing _ExportWkCustomerInterface!");
//	}
//
//	/**
//	 * read data of _ExportWkCustomerInterface
//	 */
//	public EnExportWkCustomerInterface read_ExportWkCustomerInterface(
//			Context context) throws StreamCorruptedException, IOException, ClassNotFoundException {
//		FileInputStream fis = context.openFileInput(FILE_NAME_DATA_EXPORTWKCUSTOMERINTERFACE);
//		ObjectInputStream is = new ObjectInputStream(fis);
//		EnExportWkCustomerInterface enExportWkCustomerInterface = (EnExportWkCustomerInterface) is.readObject();
//		is.close();
//		Logger.debug("Finished reading _ExportWkCustomerInterface!");
//		return enExportWkCustomerInterface;
//	}
//
//	/**
//	 * delete all data of _ExportWkCustomerInterface
//	 */
//	public void delete_ExportWkCustomerInterface(Context context) {
//		File file = context
//				.getFileStreamPath(FILE_NAME_DATA_EXPORTWKCUSTOMERINTERFACE);
//		boolean result = file.delete();
//		if (result) {
//			Logger.debug("Deleted _ExportWkCustomerInterface!");
//		} else {
//			Logger.debug("Not deleted _ExportWkCustomerInterface!");
//		}
//	}

}
