package com.Cloudian.HyperView;

public class Get_Top_Five_Buckets{
//file name: cloudian-hyperstore-request-info.log
	public void GetTopFiveBuckets(String logContent) {
	//	String[] TopBuckets = new String[5];
		String[] records = logContent.split("|");
		for(int i=0; i < records.length; i++) {
			String record = records[i];
			System.out.println(record);
			System.out.println("\n");
		}
		
		
	}
	
    
}
