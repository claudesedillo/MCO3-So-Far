package tcp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by patricktobias on 05/12/2016.
 */
public class TCPClient {

   private String serverAddress;
   private String replicaAddress;

   public TCPClient (String IPAddress, String replicaAddress) {
      this.serverAddress = IPAddress;
      this.replicaAddress = replicaAddress;
   }

   public String forwardData(String input) {

       String feedback = "";
	   
      try {
         String sentence = input;
         Socket clientSocket = new Socket(this.serverAddress, Node.commonPort);
         DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
         BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
         outToServer.writeBytes(sentence + '\n');
         
         feedback = inFromServer.readLine();
         
         //System.out.println("FROM SERVER: " + modifiedSentence);
         clientSocket.close();

      } catch (IOException e) {
    	  e.printStackTrace();
    	  System.out.println("Connection Timeout or Server Unreachable");
      }
      
      return feedback;

   }
   
   public ArrayList<String[]> requestData(String input) { // strictly for SELECT
	   ArrayList <String[]> result = null;
		try {
			Socket clientSocket = new Socket(serverAddress, Node.commonPort);
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			
			outToServer.writeBytes(input + '\n');
			
			ObjectInputStream objectInput = new ObjectInputStream(clientSocket.getInputStream());
			Object object = objectInput.readObject();
			result = (ArrayList <String[]>) object;
			
		} catch (Exception e) {
			System.out.println("Connection Timeout or Server Unreachable");

			input = input.replaceAll(Messages.QUERY_FOR_SELECT, Messages.QUERY_FOR_SELECT_REPLICA);
			
			System.out.println("FINDING REPLICA");
			
			try {
				Socket clientSocket = new Socket(replicaAddress, Node.commonPort);
				DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
				
				outToServer.writeBytes(input + '\n');
				
				ObjectInputStream objectInput = new ObjectInputStream(clientSocket.getInputStream());
				Object object = objectInput.readObject();
				result = (ArrayList <String[]>) object;
				
			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println("Connection Timeout or Server Unreachable");
			}
			
		}
		return result;
   }

}
