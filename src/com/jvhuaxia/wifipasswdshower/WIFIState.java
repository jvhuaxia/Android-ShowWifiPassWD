package com.jvhuaxia.wifipasswdshower;

public class WIFIState {
    @Override
    public String toString() {
	return "WIFIState [wifiName=" + wifiName + ", wifiPassword=" + wifiPassword + "]";
    }

    private String wifiName;
    private String wifiPassword;

    public WIFIState(String wifiName, String wifiPassword) {
	super();
	this.wifiName = wifiName;
	this.wifiPassword = wifiPassword;
    }

    public String getWifiName() {
	return wifiName;
    }

    public void setWifiName(String wifiName) {
	this.wifiName = wifiName;
    }

    public String getWifiPassword() {
	return wifiPassword;
    }

    public void setWifiPassword(String wifiPassword) {
	this.wifiPassword = wifiPassword;
    }
}
