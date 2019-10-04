package com.Cloudian.HyperView;
import java.io.IOException;

public class RunResult {
	public static void main(String args[]) {

	    Get_Log_Content_from_Remote_Server getLog = new Get_Log_Content_from_Remote_Server();
	   // Get_Metrics_from_Source getMetrics = new Get_Metrics_from_Source();
	    try {
			getLog.getLogContent();
		//	getMetrics.GetMetrics();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
