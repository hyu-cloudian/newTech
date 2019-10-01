package com.Cloudian.HyperView;

public class Config_File {
    private static Config_File instance = new Config_File();
	private String name;
	private int interval;
	private int minInterval;
	private int safeInterval;
	
	private Config_File() {
		this.name = "test";
	}
	public static Config_File getInstance() {
		return instance;
	}
}
