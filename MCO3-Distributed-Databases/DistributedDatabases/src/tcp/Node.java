package tcp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import view.Log;
import view.SQLQueries;

public class Node {
   public static final int commonPort = 5136;

   public static final String IP_EUROPE_AMERICA = "192.168.171.2";
   public static final String IP_ASIA_AFRICA = "192.168.171.2";
   public static final String IP_BOTH = "192.168.171.2";

   public static final int BOTH_NODE_NUMBER = 1;
   public static final int EUROPE_AMERICA_NODE_NUMBER = 2;
   public static final int ASIA_AFRICA_NODE_NUMBER = 3;

   int nodeNumber;

   //TCPClient myClient;
   
   TCPClient allClient = new TCPClient(IP_BOTH, IP_EUROPE_AMERICA);
   TCPClient europeAmericaClient = new TCPClient(IP_EUROPE_AMERICA, IP_ASIA_AFRICA);
   TCPClient asiaAfricaClient = new TCPClient(IP_ASIA_AFRICA, IP_BOTH);

   Connection mainConn; // set auto commit false
   Connection replicaConn;

   TCPServer myServer = new TCPServer(this);

   public Node (int nodeNumber) { // set this different from each other
      this.nodeNumber = nodeNumber;
      
      Properties connectionProps = new Properties();
      connectionProps.put("user", MySQLSettings.user);
      connectionProps.put("password", MySQLSettings.password);

      switch(nodeNumber) {
         case BOTH_NODE_NUMBER:
        	 
        	try {
        		this.mainConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/allregions", connectionProps);
        	} catch (SQLException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
        	    
        	try {
        		this.replicaConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/asia_africa", connectionProps);
        	} catch (SQLException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
            break;
            
         case EUROPE_AMERICA_NODE_NUMBER:
        	 
        	try {
        		this.mainConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/europe_america", connectionProps);
     		} catch (SQLException e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     		}
     	    
     	    try {
     	    	this.replicaConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/allregions", connectionProps);
     		} catch (SQLException e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     		}
            break;
            
         case ASIA_AFRICA_NODE_NUMBER:
        	 
        	try {
        		this.mainConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/asia_africa", connectionProps);
      		} catch (SQLException e) {
      			// TODO Auto-generated catch block
      			e.printStackTrace();
      		}
      	    
      	    try {
      	    	this.replicaConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/europe_america", connectionProps);
      		} catch (SQLException e) {
      			// TODO Auto-generated catch block
      			e.printStackTrace();
      		}
            break;
      }
      
      Log.getInstance().initializeLogFiles(nodeNumber, "log.txt", "transactions.txt");
      
      myServer.start();
      
    try {
		this.mainConn.setAutoCommit(false);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    try {
		this.replicaConn.setAutoCommit(false);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      
   }


   public boolean executeQueryAt(int targetNode, String SQLQuery, String countrycode) { // for inserting, updating, deleting
	  
      switch (targetNode) {
      
         case EUROPE_AMERICA_NODE_NUMBER: // europe
			 return sendData( allClient, europeAmericaClient, europeAmericaClient, asiaAfricaClient, SQLQuery);

         case ASIA_AFRICA_NODE_NUMBER: // asia
			 return sendData( allClient, asiaAfricaClient, allClient, europeAmericaClient, SQLQuery );

         case BOTH_NODE_NUMBER:
			 String region = getRegionFromCountryCode(countrycode);

			 if (region.contains("Europe") || region.contains("America"))
				 return sendData( allClient, europeAmericaClient, europeAmericaClient, asiaAfricaClient, SQLQuery);
			 else
				 return sendData( allClient, asiaAfricaClient, allClient, europeAmericaClient, SQLQuery );

            
      }

	   return false;
      
   }
   
   public ArrayList<String[]> retrieveData (ArrayList <Integer> targetNodes, ArrayList <String> queries) {
	   ArrayList<String[]> out = new ArrayList<>();
	   
	   for (int i = 0; i < targetNodes.size(); i++) {
		   if (targetNodes.get(i) == this.nodeNumber) { // if local read
			   PreparedStatement pst;
				try {
					pst = mainConn.prepareStatement(queries.get(i));
					ResultSet rs = pst.executeQuery();
					ResultSetMetaData metadata = rs.getMetaData();
					
					int numberOfColumns = metadata.getColumnCount();
					
					while (rs.next()) {
						System.out.println("There is a result!");
						String[] toPlace = new String[numberOfColumns];
						for(int j = 0; j < numberOfColumns; j++){
							toPlace[j] = rs.getObject(j+1) + ""; 
						}
						out.add(toPlace);
					}
					
					return out;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		   } else {  // for global read
			   String statement = queries.get(i) + Messages.QUERY_FOR_SELECT;
			   
			   switch (targetNodes.get(i)) {
			   
			   case EUROPE_AMERICA_NODE_NUMBER:
				   out.addAll(europeAmericaClient.requestData(statement));
				   break;
			   case ASIA_AFRICA_NODE_NUMBER:
				   out.addAll(asiaAfricaClient.requestData(statement));
				   break;
			   case BOTH_NODE_NUMBER:
				   out.addAll(allClient.requestData(statement));
				   break;
				   
			   }
		   }
	   }
	   
	   return out;
	   
   }

   public boolean sendData(TCPClient clientForMain1, TCPClient clientForMain2,
		   TCPClient clientForRep1, TCPClient clientForRep2, String SQLQuery) {

	   String queryForMain = SQLQuery + Messages.QUERY_FOR_MAIN;
	   String queryForReplica = SQLQuery + Messages.QUERY_FOR_REPLICA;
	   
	   	String reply1 = clientForMain1.forwardData(queryForMain);
		String reply2 = clientForMain2.forwardData(queryForMain);
		String reply3 = clientForRep1.forwardData(queryForReplica);
		String reply4 = clientForRep2.forwardData(queryForReplica);
		
		System.out.println("Reply1: " + reply1 + " Reply2: " + reply2 + " Reply3: " + reply3 + " Reply4: " + reply4);
		
		if (reply1.contains(Messages.READY) && reply2.contains(Messages.READY)
				&& reply3.contains(Messages.READY) && reply4.contains(Messages.READY)) {
			clientForMain1.forwardData(Messages.COMMIT_FOR_MAIN);
			clientForMain2.forwardData(Messages.COMMIT_FOR_MAIN);
			clientForRep1.forwardData(Messages.COMMIT_FOR_REPLICA);
			clientForRep2.forwardData(Messages.COMMIT_FOR_REPLICA);
			return true;
		}
		else {
			clientForMain1.forwardData(Messages.ABORT);
			clientForMain2.forwardData(Messages.ABORT);
			clientForRep1.forwardData(Messages.ABORT);
			clientForRep2.forwardData(Messages.ABORT);
		}
		
		return false;
	   
   }
   
   public Connection getMainConnection() {
	   return mainConn;
   }

   public Connection getReplicaConnection() {
	   return replicaConn;
   }
   
   public void startServerThread() {
      this.myServer.start();
   }
   
   public String getRegionFromCountryCode (String countrycode) {
	   String region = "";
	   String sql = SQLQueries.getRegionFromCountryCode + countrycode + "'";
	   
	   try {
		PreparedStatement pstmt = getMainConnection().prepareStatement(sql);
		
		ResultSet rs = pstmt.executeQuery();
		
		if (rs.next())
			region = rs.getString(1);
			
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

		return region;
   }
}
