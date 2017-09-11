package com.jvhuaxia.wifipasswdshower;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WIFIStateInflateUtils {
    public static List<WIFIState> readWIFIFile(String filePath) throws IOException {
	ArrayList<String> wifiNames = new ArrayList<String>();
	ArrayList<String> wifiPasswords = new ArrayList<String>();
	int flag = 0;
	List<WIFIState> resultWifiStates = new ArrayList<WIFIState>();
	File file = new File(filePath);
	if (file.exists()) {
	    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
	    while (true) {
		String readLine = bufferedReader.readLine();
		if (readLine == null)
		    break;
		else if (!readLine.equals("")) {
		    readLine = readLine.trim();
		    if (readLine.equals("network={"))
			flag = 1;
		    else if (flag != 0)
			if (readLine.startsWith("ssid=\""))
			    wifiNames.add(readLine.substring(readLine.indexOf("\"") + 1, readLine.length() - 1).trim());
			else if (readLine.startsWith("psk=\""))
			    wifiPasswords.add(readLine.substring(readLine.indexOf("\"") + 1, readLine.length() - 1).trim());
		    if (readLine.equals("}")) {
			flag = 0;
			if (wifiNames.size() - wifiPasswords.size() == 1)
			    wifiPasswords.add("нч");
		    }
		}
	    }
	    if (bufferedReader != null) {
		bufferedReader.close();
	    }
	}
	for (int i = 0; i < wifiNames.size(); i++)
	    resultWifiStates.add(new WIFIState(wifiNames.get(i), wifiPasswords.get(i)));
	return resultWifiStates;
    }
}
