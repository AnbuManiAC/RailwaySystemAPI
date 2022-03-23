package com.railway.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.railway.train.Station;
import com.railway.train.Train;

public class PropertyReader {
	
	private Properties prop;
	
	private PropertyReader() {
		prop = new Properties();
	}

	private static PropertyReader instance = null;
	
	public static PropertyReader getInstance() {
		if(instance == null) {
			instance = new PropertyReader();
		}
		return instance;
	}
	
	public void loadData() throws IOException {
		
		loadData(PropertyReader.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "com/railway/db/config.properties");
	}
	public void loadData(String filePath) throws IOException {
		
		File file = new File(filePath);
		
		InputStream input = new FileInputStream(file);
                    
        prop.load(input);
        
        StationTable stations = StationTable.getInstance();
        TrainTable trains = TrainTable.getInstance();
        UserTable users = UserTable.getInstance();
        
        Station s1 = stations.createStation(prop.getProperty("station1"));
        Station s2 = stations.createStation(prop.getProperty("station2"));
        Station s3 = stations.createStation(prop.getProperty("station3"));

		stations.insertStation(s1);
		stations.insertStation(s2);
		stations.insertStation(s3);
    	
    	Train train1 = trains.createTrain(prop.getProperty("train1Number"), prop.getProperty("train1Name"), s1, s2, prop.getProperty("startdate"), prop.getProperty("enddate"));
    	Train train2 = trains.createTrain(prop.getProperty("train2Number"), prop.getProperty("train2Name"), s1, s3, prop.getProperty("startdate"), prop.getProperty("enddate"));

    	trains.insertTrain(train1);
    	trains.insertTrain(train2);
    	
    	User user = users.createUser(prop.getProperty("username"), prop.getProperty("password"));
    	users.insertUser(user);
    	

    		    	
	}
	
}
