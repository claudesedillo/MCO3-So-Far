package view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
	
	private static Log log;
	private String logFilename;
	private String transactionFilename;
	
	private Log() {
		
	}
	
	public static Log getInstance() {
		if (log == null) {
			log = new Log();
		}
		return log;
	}
	
	public void initializeLogFiles(int nodeNum, String logFilename, String transactionFilename) {
		this.logFilename = "log/" + logFilename;
		this.transactionFilename = "log/" + transactionFilename;

		try {
			PrintWriter writer = new PrintWriter(this.logFilename, "UTF-8");
			writer.println("LOG FILE OF NODE " + nodeNum);
			writer.close();
			
			writer = new PrintWriter(this.transactionFilename, "UTF-8");
			writer.println("TRANSACTIONS OF NODE " + nodeNum);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int newTransaction(String statement) {
		int transactionNumber = getLastTransactionNumber() + 1;
		statement = "<T" + transactionNumber + " " + statement + ">";
		
		try {
		    PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(this.transactionFilename, true)));
		    writer.println(statement);
		    writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return transactionNumber;
	}
	
	public void writeToLog(int transactionNum, String message) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		//System.out.println(dateFormat.format(date)); 
		
		message = "<T" + transactionNum + " " + dateFormat.format(date) + " " + message + ">";
		
		try {
		    PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilename, true)));
		    writer.println(message);
		    writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getLastTransactionNumber() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.transactionFilename));
			
		    String line = br.readLine();
		    String last = line;
		    while (line != null) {
		    	last = line;
		        line = br.readLine();
		    }
		    
		    br.close();
		    
		    last = last.split(" ")[0];
		    last = last.substring(1);
		    return Integer.parseInt(last);
		} catch(Exception e) {
			
		}
		
		return 0;
	}
	
	public int writeTransaction(String statement) {
		newTransaction(statement);
		int transactionNumber = getLastTransactionNumber();
		writeToLog(transactionNumber, "START");			
		writeToLog(transactionNumber, "EXECUTE");
		writeToLog(transactionNumber, "COMMIT");
		
		return transactionNumber;
	}

}
