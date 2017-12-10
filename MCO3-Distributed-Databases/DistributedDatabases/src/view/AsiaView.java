package view;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import tcp.Node;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JRadioButton;

public class AsiaView extends JFrame{
	
	private Node node = new Node(3);
	private JTable resultTable;
	private JScrollPane dataScrollPane;
	private JCheckBox chckbxAsiaAndAfrica, chckbxEuropeAndAmerica, chckbxAllRegions;
	private JScrollPane queryPreviewScrollPane;
	private JLabel queryPreview;
	private ButtonGroup targetButtonGroup;
	private JRadioButton allRB, euRB;
	
	public AsiaView() {
		super("Node 3: Asia and Africa");
		getContentPane().setBackground(Color.WHITE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(940, 600);
		getContentPane().setLayout(null);
		
		dataScrollPane = new JScrollPane();
		dataScrollPane.setBounds(6, 374, 912, 176);
		getContentPane().add(dataScrollPane);
		
		resultTable = new JTable(){
			public boolean isCellEditable(int data, int columns){
				return false;
			}
		};
		dataScrollPane.setViewportView(resultTable);
		
		chckbxAsiaAndAfrica = new JCheckBox("Asia and Africa");
		chckbxAsiaAndAfrica.setBackground(Color.WHITE);
		chckbxAsiaAndAfrica.setBounds(6, 144, 184, 23);
		getContentPane().add(chckbxAsiaAndAfrica);
		
		chckbxEuropeAndAmerica = new JCheckBox("Europe and America");
		chckbxEuropeAndAmerica.setBackground(Color.WHITE);
		chckbxEuropeAndAmerica.setBounds(6, 92, 184, 23);
		getContentPane().add(chckbxEuropeAndAmerica);
		
		chckbxAllRegions = new JCheckBox("All Regions");
		chckbxAllRegions.setBackground(Color.WHITE);
		chckbxAllRegions.setBounds(6, 118, 184, 23);
		getContentPane().add(chckbxAllRegions);
		
		queryPreviewScrollPane = new JScrollPane();
		queryPreviewScrollPane.setBounds(6, 254, 912, 84);
		getContentPane().add(queryPreviewScrollPane);
		
		queryPreview = new JLabel("");
		queryPreviewScrollPane.setViewportView(queryPreview);
		
		JLabel lblResult = new JLabel("Result");
		lblResult.setBounds(6, 349, 46, 14);
		getContentPane().add(lblResult);
		
		JLabel lblLocalConcurrency = new JLabel("Read Operations (Local)");
		lblLocalConcurrency.setBounds(10, 11, 206, 23);
		getContentPane().add(lblLocalConcurrency);
		
		JLabel lblInsertToLocal = new JLabel("Insert to local node");
		lblInsertToLocal.setBounds(196, 15, 155, 14);
		getContentPane().add(lblInsertToLocal);
		
		JButton localInsert1 = new JButton("INSERT Statement 1");
		localInsert1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				queryPreview.setText(SQLQueries.insertAsiaAfricaData1);
			}
		});
		localInsert1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				node.executeQueryAt(Node.ASIA_AFRICA_NODE_NUMBER, SQLQueries.insertAsiaAfricaData1, SQLQueries.countryCodeAsAf1);
			}
		});
		localInsert1.setBounds(196, 33, 155, 23);
		getContentPane().add(localInsert1);
		
		JButton localInsert2 = new JButton("INSERT Statement 2");
		localInsert2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				queryPreview.setText(SQLQueries.insertAsiaAfricaData2);
			}
		});
		localInsert2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				node.executeQueryAt(Node.ASIA_AFRICA_NODE_NUMBER, SQLQueries.insertAsiaAfricaData2, SQLQueries.countryCodeAsAf2);
			}
		});
		localInsert2.setBounds(196, 62, 155, 23);
		getContentPane().add(localInsert2);
		
		JLabel lblUpdate = new JLabel("Update local node");
		lblUpdate.setBounds(196, 96, 155, 14);
		getContentPane().add(lblUpdate);
		
		JButton localUpdate1 = new JButton("UPDATE Statement 1");
		localUpdate1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				queryPreview.setText(SQLQueries.updateAsiaData1);
			}
		});
		localUpdate1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				node.executeQueryAt(Node.ASIA_AFRICA_NODE_NUMBER, SQLQueries.updateAsiaData1, SQLQueries.countryCodeAsAf1);
			}
		});
		localUpdate1.setBounds(196, 121, 155, 23);
		getContentPane().add(localUpdate1);
		
		JButton localSelect1 = new JButton("US$ Per Liter of Diesel");
		localSelect1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				queryPreview.setText(SQLQueries.readAllData1);
			}
		});
		localSelect1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//chckbxAsiaAndAfrica, chckbxEuropeAndAmerica, chckbxAllRegions
				ArrayList <Integer> nodesChecked = new ArrayList<>();
				ArrayList <String> queries = new ArrayList<>();
				
				if (chckbxAllRegions.isSelected()) {
					nodesChecked.add(Node.BOTH_NODE_NUMBER);
					queries.add(SQLQueries.readAllData1);
				}
				
				if (chckbxEuropeAndAmerica.isSelected()) {
					nodesChecked.add(Node.EUROPE_AMERICA_NODE_NUMBER);
					queries.add(SQLQueries.readEuropeData1);
				}
				
				if (chckbxAsiaAndAfrica.isSelected()) {
					nodesChecked.add(Node.ASIA_AFRICA_NODE_NUMBER);
					queries.add(SQLQueries.readAsiaData1);
				}
					
				ArrayList<String[]> dataRetrieved = node.retrieveData(nodesChecked, queries);
				String[][] answersArray = new String[dataRetrieved.size()][dataRetrieved.get(0).length];

				String[] headers = {"Country Code", "Country Name", "Region", "US$ Per Liter of Diesel", "yearc" };
				
				for(int i = 0; i < dataRetrieved.size(); i++){
					for(int j = 0; j < dataRetrieved.get(i).length; j++){
						System.out.print(dataRetrieved.get(i)[j]);
						answersArray[i][j] = dataRetrieved.get(i)[j];
					}
					System.out.println();
				};
				
				resultTable.setModel(new DefaultTableModel(null, headers));
				
				int added = 0;
				for (int i = 0; i < dataRetrieved.size(); i++) {
					if (!existsInTable(resultTable, answersArray[i])) {
						((DefaultTableModel)resultTable.getModel()).addRow(answersArray[i]);
						added++;
					}
				}
				
				System.out.println("NUMBER OF ROWS RETRIEVED: " + added);
			}
		});
		localSelect1.setBounds(6, 33, 155, 23);
		getContentPane().add(localSelect1);
		
		JButton localSelect2 = new JButton("US$ Per Liter of Gas");
		localSelect2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				queryPreview.setText(SQLQueries.readAllData2);
			}
		});
		localSelect2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//chckbxAsiaAndAfrica, chckbxEuropeAndAmerica, chckbxAllRegions
				ArrayList <Integer> nodesChecked = new ArrayList<>();
				ArrayList <String> queries = new ArrayList<>();
				
				if (chckbxAllRegions.isSelected()) {
					nodesChecked.add(Node.BOTH_NODE_NUMBER);
					queries.add(SQLQueries.readAllData2);
				}
				
				if (chckbxEuropeAndAmerica.isSelected()) {
					nodesChecked.add(Node.EUROPE_AMERICA_NODE_NUMBER);
					queries.add(SQLQueries.readEuropeData2);
				}
				
				if (chckbxAsiaAndAfrica.isSelected()) {
					nodesChecked.add(Node.ASIA_AFRICA_NODE_NUMBER);
					queries.add(SQLQueries.readAsiaData2);
				}
					
				ArrayList<String[]> dataRetrieved = node.retrieveData(nodesChecked, queries);
				String[][] answersArray = new String[dataRetrieved.size()][dataRetrieved.get(0).length];

				String[] headers = {"Country Code", "Country Name", "Region", "US$ Per Liter of Gas", "yearc" };
				
				for(int i = 0; i < dataRetrieved.size(); i++){
					for(int j = 0; j < dataRetrieved.get(i).length; j++){
						System.out.print(dataRetrieved.get(i)[j]);
						answersArray[i][j] = dataRetrieved.get(i)[j];
					}
					System.out.println();
				};
				
				resultTable.setModel(new DefaultTableModel(null, headers));
				
				int added = 0;
				for (int i = 0; i < dataRetrieved.size(); i++) {
					if (!existsInTable(resultTable, answersArray[i])) {
						((DefaultTableModel)resultTable.getModel()).addRow(answersArray[i]);
						added++;
					}
				}
				
				System.out.println("NUMBER OF ROWS RETRIEVED: " + added);
			}
		});
		localSelect2.setBounds(6, 61, 155, 23);
		getContentPane().add(localSelect2);
		
		JButton loclaUpdate2 = new JButton("Update Statement 2");
		loclaUpdate2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				queryPreview.setText(SQLQueries.updateAsiaData2);
			}
		});
		loclaUpdate2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				node.executeQueryAt(Node.ASIA_AFRICA_NODE_NUMBER, SQLQueries.updateAsiaData2, SQLQueries.countryCodeAsAf2);
			}
		});
		loclaUpdate2.setBounds(196, 147, 155, 23);
		getContentPane().add(loclaUpdate2);
		
		JLabel lblDeleteFromLocal = new JLabel("Delete from local node");
		lblDeleteFromLocal.setBounds(387, 15, 155, 14);
		getContentPane().add(lblDeleteFromLocal);
		
		JButton localDelete1 = new JButton("DELETE Statement 1");
		localDelete1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				queryPreview.setText(SQLQueries.deleteAsiaData1);
			}
		});
		localDelete1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				node.executeQueryAt(Node.ASIA_AFRICA_NODE_NUMBER, SQLQueries.deleteAsiaData1, SQLQueries.countryCodeAsAf1);
			}
		});
		localDelete1.setBounds(387, 33, 155, 23);
		getContentPane().add(localDelete1);
		
		JButton localDelete2 = new JButton("DELETE Statement 2");
		localDelete2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				queryPreview.setText(SQLQueries.readAllData1);
			}
		});
		localDelete2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				node.executeQueryAt(Node.ASIA_AFRICA_NODE_NUMBER, SQLQueries.deleteAsiaData2, SQLQueries.countryCodeAsAf2);
			}
		});
		localDelete2.setBounds(387, 61, 155, 23);
		getContentPane().add(localDelete2);
		
		JLabel lblNewLabel = new JLabel("<html> Hover over the buttons <br> to see the statements! </html>");
		lblNewLabel.setBounds(387, 90, 155, 39);
		getContentPane().add(lblNewLabel);
		
		JLabel lblGlobalTransactions = new JLabel("Insert (Global Transaction)");
		lblGlobalTransactions.setBounds(565, 15, 155, 14);
		getContentPane().add(lblGlobalTransactions);
		
		JButton globalInsert1 = new JButton("INSERT Statement 1");
		globalInsert1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if(allRB.isSelected()){
					queryPreview.setText(SQLQueries.insertAllData1);
				}
				else{
					queryPreview.setText(SQLQueries.insertEuropeData1);
				}
			}
		});
		globalInsert1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(allRB.isSelected())
					node.executeQueryAt(Node.BOTH_NODE_NUMBER, SQLQueries.insertAllData1, SQLQueries.countryCodeEu3);
				else
					node.executeQueryAt(Node.EUROPE_AMERICA_NODE_NUMBER, SQLQueries.insertEuropeData1, SQLQueries.countryCodeEu1);
			}
		});
		globalInsert1.setBounds(565, 33, 155, 23);
		getContentPane().add(globalInsert1);
		
		JButton globalInsert2 = new JButton("INSERT Statement 2");
		globalInsert2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if(allRB.isSelected()){
					queryPreview.setText(SQLQueries.insertAllData2);
				}
				else{
					queryPreview.setText(SQLQueries.insertEuropeData2);
				}
			}
		});
		globalInsert2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(allRB.isSelected())
					node.executeQueryAt(Node.BOTH_NODE_NUMBER, SQLQueries.insertAllData2, SQLQueries.countryCodeEu3);
				else
					node.executeQueryAt(Node.EUROPE_AMERICA_NODE_NUMBER, SQLQueries.insertEuropeData2, SQLQueries.countryCodeAsAf4);
			}
		});
		globalInsert2.setBounds(565, 62, 155, 23);
		getContentPane().add(globalInsert2);
		
		JButton globalUpdate1 = new JButton("UPDATE Statement 1");
		globalUpdate1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if(allRB.isSelected()){
					queryPreview.setText(SQLQueries.updateAllData1);
				}
				else{
					queryPreview.setText(SQLQueries.updateEuropeData1);
				}
			}
		});
		globalUpdate1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(allRB.isSelected())
					node.executeQueryAt(Node.BOTH_NODE_NUMBER, SQLQueries.updateAllData1, SQLQueries.countryCodeAsAf1);
				else
					node.executeQueryAt(Node.EUROPE_AMERICA_NODE_NUMBER, SQLQueries.updateEuropeData1, SQLQueries.countryCodeEu1);
			}
		});
		globalUpdate1.setBounds(565, 121, 155, 23);
		getContentPane().add(globalUpdate1);
		
		JButton globalUpdate2 = new JButton("UPDATE Statement 2");
		globalUpdate2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if(allRB.isSelected()){
					queryPreview.setText(SQLQueries.updateAllData2);
				}
				else{
					queryPreview.setText(SQLQueries.updateEuropeData2);
				}
			}
		});
		globalUpdate2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(allRB.isSelected())
					node.executeQueryAt(Node.BOTH_NODE_NUMBER, SQLQueries.updateAllData2, SQLQueries.countryCodeEu5);
				else
					node.executeQueryAt(Node.EUROPE_AMERICA_NODE_NUMBER, SQLQueries.updateEuropeData2, SQLQueries.countryCodeEu1);
			}
		});
		globalUpdate2.setBounds(565, 147, 155, 23);
		getContentPane().add(globalUpdate2);
		
		JButton globalDelete1 = new JButton("DELETE Statement 1");
		globalDelete1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if(allRB.isSelected()){
					queryPreview.setText(SQLQueries.deleteAllData1);
				}
				else{
					queryPreview.setText(SQLQueries.deleteEuropeData1);
				}
			}
		});
		globalDelete1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(allRB.isSelected())
					node.executeQueryAt(Node.BOTH_NODE_NUMBER, SQLQueries.deleteAllData1, SQLQueries.countryCodeEu3);
				else
					node.executeQueryAt(Node.EUROPE_AMERICA_NODE_NUMBER, SQLQueries.deleteEuropeData1, SQLQueries.countryCodeEu1);
			}
		});
		globalDelete1.setBounds(738, 33, 155, 23);
		getContentPane().add(globalDelete1);
		
		JButton globalDelete2 = new JButton("DELETE Statement 2");
		globalDelete2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if(allRB.isSelected()){
					queryPreview.setText(SQLQueries.deleteAllData2);
				}
				else{
					queryPreview.setText(SQLQueries.insertEuropeData2);
				}
			}
		});
		globalDelete2.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(allRB.isSelected())
					node.executeQueryAt(Node.BOTH_NODE_NUMBER, SQLQueries.deleteAllData2, SQLQueries.countryCodeEu4);
				else
					node.executeQueryAt(Node.EUROPE_AMERICA_NODE_NUMBER, SQLQueries.deleteEuropeData2, SQLQueries.countryCodeEu1);
			}
		});
		globalDelete2.setBounds(738, 62, 155, 23);
		getContentPane().add(globalDelete2);
		
		JLabel lblUpdateglobalTransaction = new JLabel("Update (Global Transaction)");
		lblUpdateglobalTransaction.setBounds(565, 96, 180, 14);
		getContentPane().add(lblUpdateglobalTransaction);
		
		JLabel lblDeleteglobalTransaction = new JLabel("Delete (Global Transaction)");
		lblDeleteglobalTransaction.setBounds(738, 15, 180, 14);
		getContentPane().add(lblDeleteglobalTransaction);
		
		JLabel lblQueryPreview = new JLabel("Query Preview");
		lblQueryPreview.setBounds(6, 229, 97, 14);
		getContentPane().add(lblQueryPreview);
		
		JLabel lblTarget = new JLabel("Target");
		lblTarget.setBounds(738, 96, 46, 14);
		getContentPane().add(lblTarget);
		
		allRB = new JRadioButton("Node 1: All regions");
		allRB.setBackground(Color.WHITE);
		allRB.setBounds(738, 121, 192, 23);
		getContentPane().add(allRB);
		
		euRB = new JRadioButton("Node 2: Europe and America");
		euRB.setBackground(Color.WHITE);
		euRB.setBounds(738, 147, 192, 23);
		getContentPane().add(euRB);
		
		targetButtonGroup = new ButtonGroup();
		targetButtonGroup.add(allRB);
		targetButtonGroup.add(euRB);
	}
	
	public boolean existsInTable(JTable table, Object[] entry) {
	    // Get row and column count
	    int rowCount = table.getRowCount();
	    int colCount = table.getColumnCount();

	    // Get Current Table Entry
	    String curEntry = "";
	    for (Object o : entry) {
	        String e = o.toString();
	        curEntry = curEntry + " " + e;
	    }

	    // Check against all entries
	    for (int i = 0; i < rowCount; i++) {
	        String rowEntry = "";
	        for (int j = 0; j < colCount; j++)
	            rowEntry = rowEntry + " " + table.getValueAt(i, j).toString();
	        if (rowEntry.equalsIgnoreCase(curEntry)) {
	            return true;
	        }
	    }
	    return false;
	}
}
