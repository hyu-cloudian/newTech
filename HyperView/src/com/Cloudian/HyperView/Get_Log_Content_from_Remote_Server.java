package com.Cloudian.HyperView;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Get_Log_Content_from_Remote_Server {
	
	 private final static int SAFEINTERVAL = 10;
	 private final static int MININTERVAL = 100;
	 
	 int interval = 5000;
	 
	 private final static String filePath = "/Users/haiyuyu/Desktop/cloudian-hyperstore-request-info.log.2019-01-28.1";
	 private final static String logPosPath = "/Users/haiyuyu/Desktop/logPos";
	 private final static String errorLogPath = "/Users/haiyuyu/Desktop/errorLog";
	 
	 private Map<String, Integer> bucketCount = new HashMap<>();
	 private Map<String, Integer> objectCount = new HashMap<>();
	 
	 private long totalObjectSize = 0;
	 private long logCount = 0;
	 private long avgObjectSize = 0;
	 
	 private int topBucketNum = 5;
	 private int topObjectNum = 10;
	 
	 
	 public void getLogContent() throws IOException {
		    RandomAccessFile raf = new RandomAccessFile(filePath, "rw");
		    long len = getLogPos();
		    //When cur pos is before the end of the log file
			while(len <= raf.length()) {
				System.out.println("Length: "+len);
			    raf.seek(len);
			    String logContent = "" + raf.readLine();
			    
			    String[] strsToGetBucket = logContent.split("/");
			    String[] strsToGetSize = logContent.split("\\|");
			    
			    //when cur pos reaches the end of the file
			    if(strsToGetBucket == null || strsToGetBucket.length < 3) {
			    	if(logCount != 0) {
			    		avgObjectSize = totalObjectSize/logCount;
			    	}
			    //	System.out.println("totalSize is: " + totalObjectSize);
			    //	System.out.println("count is:" + logCount);
			    	System.out.println("avgSize is: "+ avgObjectSize);  
			    	
			    	
			    	
			    	//printMap(bucketCount);
			    	String[] bucketResults = TopRankingElements(bucketCount, topBucketNum);
			    	for(int i = topBucketNum - 1; i >= 0; i--) {
			    		System.out.println(bucketResults[i]);
			    	}   
			    	
                  //  printMap(objectCount);
			    	
			    	String[] objectResults = TopRankingElements(objectCount, topObjectNum);
			    	for(int i = topObjectNum - 1; i >= 0; i--) {
			    		System.out.println(objectResults[i]);
			    	}
			    	break;
			    }   
			    
			    String url = strsToGetBucket[2];
			    String objectSize = strsToGetSize[7];
			    String[] elements = url.split("%2F");
			    
			    if(elements == null || elements.length < 2) {
			    	if(logCount != 0) {
			    		avgObjectSize = totalObjectSize / logCount;
			    	}
			    	System.out.println("totalSize is: " + totalObjectSize);
			    	System.out.println("count is:" + logCount);
			    	System.out.println("avgSize is: "+ avgObjectSize);  
			    	
			    	
			    	//printMap(bucketCount);
			    /*	String[] bucketResults = TopRanking(bucketCount, topBucketNum);
			    	for(int i = topBucketNum - 1; i >= 0; i--) {
			    		System.out.println(bucketResults[i]);
			    	}   */
			    	
			    //	printMap(objectCount);  
			    	String[] objectResults = TopRankingElements(objectCount, topObjectNum);
			    	for(int i = topObjectNum - 1; i >= 0; i--) {
			    		System.out.println(objectResults[i]);
			    	}
			    	
			  
			    	break;
			    }
			    
			    String bucket = elements[0];
			    String object = elements[1];
			    bucketCount.put(bucket, bucketCount.getOrDefault(bucket, 0) + 1);
			    objectCount.put(object, objectCount.getOrDefault(object, 0) + 1);
			    System.out.println("size is: "+ objectSize);
			    totalObjectSize += Integer.parseInt(objectSize);
			    System.out.println("\n");
			    len += logContent.length() + 1;
		        saveLogPos(len);
		        logCount++;
			}   
	       
		    
	/*	    while(true) {
		    	try {
		    		if(interval >= MININTERVAL) {
		    			Thread.sleep(interval);
		    		} else if (interval >= SAFEINTERVAL && interval < MININTERVAL) {
		    			Thread.sleep(MININTERVAL);
		    		} else {
		    			addIntervalErrorInfo(interval);
		    			throw new ArithmeticException("The interval num is too small"); 
		    		}
		    		//raf.writeBytes("It is a test from Haiyu");
					raf.seek(len);
				    System.out.println("" + raf.readLine());
			        len = raf.length();
			        saveLogPos(len);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }   */             
		    
	 }
	 
	 private void saveLogPos(long len) throws IOException {
		 RandomAccessFile raf = new RandomAccessFile(logPosPath, "rw");
		 raf.writeBytes(""+len);
		 raf.close();
	 }
	 
	 private long getLogPos() throws IOException {
		 RandomAccessFile raf = new RandomAccessFile(logPosPath, "rw");
		 String logLength = "" + raf.readLine();
		 raf.close();
		 return Long.valueOf(logLength);
	 }
	 
	 private void addIntervalErrorInfo(int interval) throws IOException {
		 RandomAccessFile raf = new RandomAccessFile(errorLogPath, "rw");
		 raf.seek(raf.length());
		 raf.writeBytes(""+interval+"\n");
		 raf.close();
	 }
	 
	 public void printMap(Map<String, Integer> map) {
		 for(String key: map.keySet()) {
			 System.out.println(key+":"+map.get(key));
		 }
	 }
	 
	 public String[] TopRankingElements(Map<String, Integer> map, int num) {
		 String[] TopRankedResults = new String[num];
		 
		 PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(num, (c1, c2) -> {
				if(c1.getValue().equals(c2.getValue())) {
					return 0;
				}
				return c1.getValue() < c2.getValue() ? -1 : 1;
			}	 
         );
		 
		 for(Map.Entry<String,Integer> entry: map.entrySet())
	        {
	            pq.offer(entry);
	            if(pq.size() > num)
	                pq.poll();
	        }
		 
            int start = 0;
            
	        while(!pq.isEmpty()) {
	        	TopRankedResults[start] = pq.poll().getKey();
	        	start++;
	        }
	        return TopRankedResults;
	 } 
	 
}
