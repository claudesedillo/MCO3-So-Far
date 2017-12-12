package tcp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import tcp.Node;
import view.Log;

public class TCPServer extends Thread {

   private Node serversNode;

   public TCPServer (Node node) {
      this.serversNode = node;
   }
   
   @Override
   public void run() {
       try {
    	   System.out.println("SERVER INITIALIZED");
          ServerSocket welcomeSocket = new ServerSocket(Node.commonPort);
          String clientSentence;
          String capitalizedSentence;

          while(true)
          {
             Socket connectionSocket = welcomeSocket.accept();
             BufferedReader inFromClient =
                     new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
             DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			 ObjectOutputStream objectOutput = new ObjectOutputStream(connectionSocket.getOutputStream());
			 
             clientSentence = inFromClient.readLine();
             //System.out.println("Received: " + clientSentence); // objectoutputstream for resultset from select; 
             
             processMessage (clientSentence, outToClient, objectOutput);
             
             //capitalizedSentence = clientSentence.toUpperCase() + '\n';
             //outToClient.writeBytes(capitalizedSentence);
          }
       } catch (IOException e) {
          e.printStackTrace();
       }
    }
   
   public void processMessage (String clientSentence, DataOutputStream outToClient, ObjectOutputStream objectOutput) {
	   if (Messages.isCommitOrAbort(clientSentence)) {
		   if (clientSentence.contains(Messages.COMMIT_FOR_MAIN)) {
			   try {
				serversNode.getMainConnection().commit();
				
				Log.getInstance().writeToLog(Log.getInstance().getLastTransactionNumber(), "COMMIT FOR MAIN");
				
				System.out.println("SERVER: MAIN DB HAS BEEN COMMITTED");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   } else if (clientSentence.contains(Messages.COMMIT_FOR_REPLICA)) {
			   try {
					serversNode.getReplicaConnection().commit();
					
					Log.getInstance().writeToLog(Log.getInstance().getLastTransactionNumber(), "COMMIT FOR REPLICA");

					System.out.println("SERVER: REPLICA DB HAS BEEN COMMITTED");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		   } else if (clientSentence.contains(Messages.ABORT)) {
			   try {
					serversNode.getMainConnection().rollback();
					
					Log.getInstance().writeToLog(Log.getInstance().getLastTransactionNumber(), "ABORT FOR MAIN");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			   
			   try {
					serversNode.getReplicaConnection().rollback();
					Log.getInstance().writeToLog(Log.getInstance().getLastTransactionNumber(), "ABORT FOR REPLICA");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		   }
		   
		   try {
			outToClient.writeBytes(Messages.JUST_CONTINUE + '\n');
			System.out.println("Sent Message: Just continue");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
	   } else {
		   if (clientSentence.contains(Messages.QUERY_FOR_MAIN)) {
			   clientSentence = clientSentence.replaceAll(Messages.QUERY_FOR_MAIN, "");
			   System.out.println("Received for Main: " + clientSentence);
			   
			   PreparedStatement prepStatement = null;
			   int num = 0;
			   try {
				prepStatement = serversNode.getMainConnection().prepareStatement(clientSentence);
				
				prepStatement.execute();
				
				num = Log.getInstance().newTransaction(clientSentence);
				Log.getInstance().writeToLog(num, "MAIN START");
				Log.getInstance().writeToLog(num, "MAIN EXECUTE");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			   
			   try {
				   Log.getInstance().writeToLog(num, "MAIN READY");
					outToClient.writeBytes(Messages.READY + '\n');
					System.out.println("Sent Message: " + Messages.READY);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			   
		   } else if (clientSentence.contains(Messages.QUERY_FOR_REPLICA)) {
			   clientSentence = clientSentence.replaceAll(Messages.QUERY_FOR_REPLICA, "");
			   System.out.println("Received for Replica: " + clientSentence);
			   
			   PreparedStatement prepStatement = null;
			   int num = 0;
			   try {
				prepStatement = serversNode.getReplicaConnection().prepareStatement(clientSentence);
				
				prepStatement.execute();
				num = Log.getInstance().newTransaction(clientSentence);
				Log.getInstance().writeToLog(num, "REPLICA START");
				Log.getInstance().writeToLog(num, "REPLICA EXECUTE");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			   try {
				Log.getInstance().writeToLog(num, "REPLICA READY");
				outToClient.writeBytes(Messages.READY + '\n');
				System.out.println("Sent Message: " + Messages.READY);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   } else if (clientSentence.contains(Messages.QUERY_FOR_SELECT) || clientSentence.contains(Messages.QUERY_FOR_SELECT_REPLICA)) {
			   System.out.println("Received for Select: " + clientSentence);
			   
			   try {
					PreparedStatement pstmt = null;
					
					if (clientSentence.contains(Messages.QUERY_FOR_SELECT)) {
						clientSentence = clientSentence.replaceAll(Messages.QUERY_FOR_SELECT, "");
						pstmt = serversNode.getMainConnection().prepareStatement(clientSentence);
					} else{
					   clientSentence = clientSentence.replaceAll(Messages.QUERY_FOR_SELECT_REPLICA, "");
					   pstmt = serversNode.getReplicaConnection().prepareStatement(clientSentence);
				   }
					
					ResultSet rs = pstmt.executeQuery();
					
					// Write rs to server

					ArrayList <String[]> result = new ArrayList <String[]> ();
					ResultSetMetaData metadata = rs.getMetaData();
					
					int numberOfColumns = metadata.getColumnCount();
					
					while (rs.next()) {
						String[] toPlace = new String[numberOfColumns];
						for(int j = 0; j < numberOfColumns; j++){
							toPlace[j] = rs.getObject(j+1) + "";
						}
						result.add(toPlace);
				          
					}

				   System.out.println(result.size());
					
					//DataTransferObject dto = new DataTransferObject(rs);
					
					try {
						objectOutput.writeObject(result);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		   }
	   }
   }

}
