package oldView;
import view.SQLQueries;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import tcp.Node;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class AllView extends JFrame implements ItemListener{
	
	private Node masterNode = new Node(1);
	
	private JCheckBox node1Chk;
	private JCheckBox node2Chk;
	private JCheckBox node3Chk;
	private JRadioButton node2RB;
	private JRadioButton node3RB;
	private JButton locSelect1RB;
	private JButton locSelect2RB;
	private JButton insert1RB;
	private JButton insert2RB;
	private JButton update1RB;
	private JButton update2RB;
	private JButton delete1RB;
	private JButton delete2RB;
	private JButton gloInsert1RB;
	private JButton gloInsert2RB;
	private JButton gloUpdate1RB;
	private JButton gloUpdate2RB;
	private JButton gloDelete1RB;
	private JButton gloDelete2RB;
	private String[] columns;
	private String[][] data;
	private JTable dataDisplay;
	

	public AllView(String title){
		super(title);

		initGUI();

		//masterNode.startServerThread();
	}

	public void initGUI () {
		GridBagConstraints mainC = new GridBagConstraints();
		GridBagConstraints locC = new GridBagConstraints();
		GridBagConstraints gloC = new GridBagConstraints();
		//europeAmericaNode.startServerThread();
		
		this.setVisible(false);
		this.setSize(1500, 900);
		this.setLayout(new GridBagLayout());
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/*+++++++++Main Panel+++++++++++*/
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		mainPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
		mainPanel.setBackground(Color.WHITE);
		this.add(mainPanel, mainC);
		
		mainC.gridx = 0;
		mainC.gridy = 0;
		JLabel choiceLbl = new JLabel("Node 1: Main Site");
		choiceLbl.setForeground(Color.BLACK);
		mainPanel.add(choiceLbl, mainC);
		
		/*----------Local Panel-----------*/
		mainC.gridx = 0;
		mainC.gridy = 1;
		mainC.fill = GridBagConstraints.BOTH;
		mainC.weightx = 0.6;
		mainC.weighty = 1;
		JPanel locPanel = new JPanel();
		locPanel.setLayout(new GridBagLayout());
		locPanel.setBackground(new Color(8, 120, 48)); //Lozoll Green
		mainPanel.add(locPanel, mainC);	
		
		mainC.fill = GridBagConstraints.NONE;
		
		locC.gridx = 0;
		locC.gridy = 0;
		locC.gridwidth = 4;
		JLabel localTLbl = new JLabel("LOCAL TRANSACTIONS");
		localTLbl.setForeground(Color.white);
		locPanel.add(localTLbl, locC);
		
		locC.gridwidth = 1;
			
		
		
		
		/*----------------------Local Select Panel----------------------*/
		locC.gridy = 1;
		locC.gridheight = 3;
		locC.gridwidth = 4;
		locC.fill = GridBagConstraints.BOTH;
		locC.weighty = 0.9;
		locC.weightx = 0.6;
		JPanel locSelectPanel = new JPanel();
		locSelectPanel.setLayout(new GridBagLayout());
		locSelectPanel.setOpaque(false);
		locSelectPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		locPanel.add(locSelectPanel, locC);

		GridBagConstraints locSelectC = new GridBagConstraints();
		
		//locSelectC.fill = GridBagConstraints.BOTH;
		
		locSelectC.gridx = 0;
		locSelectC.gridy = 0;
		locSelectC.weightx = 0.25;
		locSelectC.weighty = 0.34;
		JLabel locSelectLbl = new JLabel("SELECT");
		locSelectLbl.setForeground(Color.white);
		locSelectPanel.add(locSelectLbl, locSelectC);
		
		
		locSelectC.gridx = 1;
		node1Chk = new JCheckBox("Node 1");
		node1Chk.setOpaque(false);
		node1Chk.setForeground(Color.white);
		locSelectPanel.add(node1Chk, locSelectC);
		
		locSelectC.gridx = 2;
		node2Chk = new JCheckBox("Node 2");
		node2Chk.setOpaque(false);
		node2Chk.setForeground(Color.white);
		locSelectPanel.add(node2Chk, locSelectC);
		
		locSelectC.gridx = 3;
		node3Chk = new JCheckBox("Node 3");
		node3Chk.setOpaque(false);
		node3Chk.setForeground(Color.white);
		locSelectPanel.add(node3Chk, locSelectC);
		
		node1Chk.addItemListener(this);
		node2Chk.addItemListener(this);
		node3Chk.addItemListener(this);
		
		locSelectC.gridx = 0;
		locSelectC.gridwidth = 4;
		locSelectC.weighty = 0.33;
		locSelectC.insets = new Insets(5,5,5,5);
		
		locSelect1RB = new JButton("View Average Literacy Rate per country in each region in 2015");
		locSelect1RB.setPreferredSize(new Dimension(500, 50));
		locSelect1RB.setFocusPainted(false);
		locSelect1RB.setBackground(new Color(8, 120, 48));
		locSelect1RB.setForeground(Color.white);
		locSelect1RB.setBorder(new LineBorder(Color.white));
		locSelect1RB.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ArrayList <Integer> nodesChecked = new ArrayList<>();
				ArrayList <String> queries = new ArrayList<>();
				
				if (node1Chk.isSelected()) {
					nodesChecked.add(Node.BOTH_NODE_NUMBER);
					queries.add(SQLQueries.readAllData1);
				}
				
				if (node2Chk.isSelected()) {
					nodesChecked.add(Node.EUROPE_AMERICA_NODE_NUMBER);
					queries.add(SQLQueries.readEuropeData1);
				}
				
				if (node3Chk.isSelected()) {
					nodesChecked.add(Node.ASIA_AFRICA_NODE_NUMBER);
					queries.add(SQLQueries.readAsiaData1);
				}
					
				ArrayList<String[]> dataRetrieved = masterNode.retrieveData(nodesChecked, queries);
				String[][] answersArray = new String[dataRetrieved.size()][dataRetrieved.get(0).length];

				String[] headers = {"region", "countryname", "seriescode", "yearc", "Average Literacy Rate per Country in Region"};
				
				for(int i = 0; i < dataRetrieved.size(); i++){
					for(int j = 0; j < dataRetrieved.get(i).length; j++){
						System.out.print(dataRetrieved.get(i)[j]);
						answersArray[i][j] = dataRetrieved.get(i)[j];
					}
					System.out.println();
				};
				
				dataDisplay.setModel(new DefaultTableModel(null, headers));
				
				int added = 0;
				for (int i = 0; i < dataRetrieved.size(); i++) {
					if (!existsInTable(dataDisplay, answersArray[i])) {
						((DefaultTableModel)dataDisplay.getModel()).addRow(answersArray[i]);
						added++;
					}
				}
				
				System.out.println("NUMBER OF ROWS RETRIEVED: " + added);
			}
			
		});
		
		locSelect2RB = new JButton("Query 2 Here");
		locSelect2RB = new JButton("View Percentage of children out of school per country in each region in 2015");
		locSelect2RB.setPreferredSize(new Dimension(500,50));
		locSelect2RB.setFocusPainted(false);
		locSelect2RB.setBackground(new Color(8, 120, 48));
		locSelect2RB.setForeground(Color.white);
		locSelect2RB.setBorder(new LineBorder(Color.white));		
		locSelect2RB.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ArrayList <Integer> nodesChecked = new ArrayList<>();
				ArrayList <String> queries = new ArrayList<>();
				
				if (node1Chk.isSelected()) {
					nodesChecked.add(Node.BOTH_NODE_NUMBER);
					queries.add(SQLQueries.readAllData2);
				}
				
				if (node2Chk.isSelected()) {
					nodesChecked.add(Node.EUROPE_AMERICA_NODE_NUMBER);
					queries.add(SQLQueries.readEuropeData2);
				}
				
				if (node3Chk.isSelected()) {
					nodesChecked.add(Node.ASIA_AFRICA_NODE_NUMBER);
					queries.add(SQLQueries.readAsiaData2);
				}
					
				ArrayList<String[]> dataRetrieved = masterNode.retrieveData(nodesChecked, queries);
				String[][] answersArray = new String[dataRetrieved.size()][dataRetrieved.get(0).length];
				
				String[] headers = {"region", "countryname", "seriescode", "yearc", "Percentage of Children out of School"};
				
				for(int i = 0; i < dataRetrieved.size(); i++){
					for(int j = 0; j < dataRetrieved.get(i).length; j++){
						System.out.print(dataRetrieved.get(i)[j]);
						answersArray[i][j] = dataRetrieved.get(i)[j];
					}
					System.out.println();
				};
				
				dataDisplay.setModel(new DefaultTableModel(null, headers));
				
				int added = 0;
				for (int i = 0; i < dataRetrieved.size(); i++) {
					if (!existsInTable(dataDisplay, answersArray[i])) {
						((DefaultTableModel)dataDisplay.getModel()).addRow(answersArray[i]);
						added++;
					}
				}
				
				System.out.println("NUMBER OF ROWS RETRIEVED: " + added);
			}
			
		});
		locSelectC.insets = new Insets(0,0,0,0);
		
		
		ButtonGroup localSelectGroup = new ButtonGroup();
		localSelectGroup.add(locSelect1RB);
		localSelectGroup.add(locSelect2RB);
		
		locSelectC.gridy = 1;
		locSelectPanel.add(locSelect1RB, locSelectC);
		
		locSelectC.gridy = 2;
		locSelectPanel.add(locSelect2RB, locSelectC);
		
		
		locSelectC.gridwidth = 1;
		
		/*----------------------Local Insert Panel----------------------*/
		locC.gridheight = 1;
		locC.gridwidth = 1;
		locC.gridy = 1;
		locC.gridx = 4;
		JPanel locInsertPanel = new JPanel();
		locInsertPanel.setLayout(new GridBagLayout());
		locInsertPanel.setOpaque(false);
		locInsertPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		locPanel.add(locInsertPanel, locC);
		
		GridBagConstraints locInsertC = new GridBagConstraints();
		
		locInsertC.gridx = 0;
		locInsertC.gridy = 0;
		
		JLabel locInsertLbl = new JLabel("INSERT");
		locInsertLbl.setForeground(Color.white);
		locInsertPanel.add(locInsertLbl, locInsertC);
		
		insert1RB = new JButton("Insert [COL, SE.ADT.LITR.ZS, 2016 [YR2016], 92.5771] data into this Node");
		insert1RB.setPreferredSize(new Dimension(500,25));
		insert1RB.setFocusPainted(false);
		insert1RB.setBackground(new Color(8, 120, 48));
		insert1RB.setForeground(Color.white);
		insert1RB.setBorder(new LineBorder(Color.white));
		
		insert1RB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				masterNode.executeQueryAt(Node.BOTH_NODE_NUMBER, SQLQueries.insertAllData1, SQLQueries.countryCodeEu3);
				
			}
			
		});
		

		insert2RB = new JButton("Insert [MNE, SE.PRM.UNER.ZS, 2016 [YR2016], 5.322] data into this Node");
		insert2RB.setPreferredSize(new Dimension(500,25));
		insert2RB.setFocusPainted(false);
		insert2RB.setBackground(new Color(8, 120, 48));
		insert2RB.setForeground(Color.white);
		insert2RB.setBorder(new LineBorder(Color.white));
		
		insert2RB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				masterNode.executeQueryAt(Node.BOTH_NODE_NUMBER, SQLQueries.insertAllData2, SQLQueries.countryCodeAsAf4);
				
			}
			
		});

		
		ButtonGroup insertGroup = new ButtonGroup();
		insertGroup.add(insert1RB);
		insertGroup.add(insert2RB);
		
		locInsertC.gridy = 1;
		locInsertPanel.add(insert1RB, locInsertC);
		
		locInsertC.gridy = 2;
		locInsertPanel.add(insert2RB, locInsertC);
				
		
		/*----------------------Local Update Panel----------------------*/
		locC.gridy = 2;
		JPanel locUpdatePanel = new JPanel();
		locUpdatePanel.setLayout(new GridBagLayout());
		locUpdatePanel.setOpaque(false);
		locUpdatePanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		locPanel.add(locUpdatePanel, locC);
		
		GridBagConstraints locUpdateC = new GridBagConstraints();
		
		locUpdateC.gridx = 0;
		locUpdateC.gridy = 0;
		
		JLabel locUpdateLbl = new JLabel("UPDATE");
		locUpdateLbl.setForeground(Color.white);
		locUpdatePanel.add(locUpdateLbl, locUpdateC);
		
		update1RB = new JButton("Update the data of the country with seriescode, SE.ADT.LITR.ZS, "+
				 "and country code COL in year 2015 to 90.387");
		update1RB.setPreferredSize(new Dimension(650,25));
		update1RB.setFocusPainted(false);
		update1RB.setBackground(new Color(8, 120, 48));
		update1RB.setForeground(Color.white);
		update1RB.setBorder(new LineBorder(Color.white));
		update1RB.setSelected(true);
		
		update1RB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				masterNode.executeQueryAt(Node.BOTH_NODE_NUMBER, SQLQueries.updateAllData1, SQLQueries.countryCodeAsAf1);
				
			}
			
		});
		
		update2RB = new JButton("Update the data of the country with seriescode, SE.PRM.UNER.ZS, "+
				 "and country code MNE in year 2015 to 5.938");
		update2RB.setPreferredSize(new Dimension(650,25));
		update2RB.setFocusPainted(false);
		update2RB.setBackground(new Color(8, 120, 48));
		update2RB.setForeground(Color.white);
		update2RB.setBorder(new LineBorder(Color.white));
		
		update2RB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				masterNode.executeQueryAt(Node.BOTH_NODE_NUMBER, SQLQueries.updateAllData2, SQLQueries.countryCodeEu5);
				
			}
			
		});
		
		ButtonGroup updateGroup = new ButtonGroup();
		updateGroup.add(update1RB);
		updateGroup.add(update2RB);
		
		locUpdateC.gridy = 1;
		locUpdatePanel.add(update1RB, locUpdateC);
		
		locUpdateC.gridy = 2;
		locUpdatePanel.add(update2RB, locUpdateC);
		
		/*----------------------Local Delete Panel----------------------*/
		locC.gridy = 3;
		JPanel locDeletePanel = new JPanel();
		locDeletePanel.setLayout(new GridBagLayout());
		locDeletePanel.setOpaque(false);
		locDeletePanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		locPanel.add(locDeletePanel, locC);
		
		GridBagConstraints locDeleteC = new GridBagConstraints();
		
		locDeleteC.gridx = 0;
		locDeleteC.gridy = 0;
		
		JLabel locDeleteLbl = new JLabel("DELETE");
		locDeleteLbl.setForeground(Color.white);
		locDeletePanel.add(locDeleteLbl, locDeleteC);
		
		delete1RB = new JButton("Delete data with year 2016, country code COL, and series code SE.ADT.LITR.ZS");
		delete1RB.setSelected(true);
		delete1RB.setPreferredSize(new Dimension(625,25));
		delete1RB.setFocusPainted(false);
		delete1RB.setBackground(new Color(8, 120, 48));
		delete1RB.setForeground(Color.white);
		delete1RB.setBorder(new LineBorder(Color.white));
		
		delete1RB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				masterNode.executeQueryAt(Node.BOTH_NODE_NUMBER, SQLQueries.deleteAllData1, SQLQueries.countryCodeEu3);
				
			}
			
		});
		
		delete2RB = new JButton("Delete data with year 2016, country code MNE, and series code SE.SEC.UNER.LO.ZS or SE.PRM.UNER.ZS");
		delete2RB.setPreferredSize(new Dimension(625,25));
		delete2RB.setFocusPainted(false);
		delete2RB.setBackground(new Color(8, 120, 48));
		delete2RB.setForeground(Color.white);
		delete2RB.setBorder(new LineBorder(Color.white));
		
		delete2RB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				masterNode.executeQueryAt(Node.BOTH_NODE_NUMBER, SQLQueries.deleteAllData2, SQLQueries.countryCodeEu4);
				
			}
			
		});
		
		ButtonGroup deleteGroup = new ButtonGroup();
		deleteGroup.add(delete1RB);
		deleteGroup.add(delete2RB);
		
		locDeleteC.gridy = 1;
		locDeletePanel.add(delete1RB, locDeleteC);
		
		locDeleteC.gridy = 2;
		locDeletePanel.add(delete2RB, locDeleteC);
		
		/*--------------Separator of Local and Global------------*/
		mainC.gridx = 1;
		mainC.weightx = 0.1;
		mainC.fill = GridBagConstraints.VERTICAL;
		mainPanel.add(new JSeparator(SwingConstants.VERTICAL), mainC);
		
		
		/*-----------Global Panel-----------*/
		mainC.gridx = 2;
		mainC.weightx = 0.3;
		mainC.weighty = 1;
		mainC.fill = GridBagConstraints.BOTH;
		
		JPanel gloPanel = new JPanel();
		gloPanel.setLayout(new GridBagLayout());
		gloPanel.setBackground(new Color(8, 120, 48));
		mainPanel.add(gloPanel, mainC);
		
		mainC.fill = GridBagConstraints.NONE;
		
		gloC.gridx = 0;
		gloC.gridy = 0;
		gloC.gridwidth = 4;
		gloC.fill = GridBagConstraints.BOTH;
		gloC.weighty = 1;
		gloC.weightx = 1;
		JLabel globalTLbl = new JLabel("GLOBAL TRANSACTIONS");
		globalTLbl.setForeground(Color.white);
		gloPanel.add(globalTLbl, gloC);
		
		gloC.gridwidth = 1;
		
		gloC.gridy = 1;	
		JLabel targetLbl = new JLabel("TARGET:");
		targetLbl.setForeground(Color.white);
		gloPanel.add(targetLbl, gloC);
		
		node2RB = new JRadioButton("Node 2");
		node2RB.setOpaque(false);
		node2RB.setForeground(Color.white);
		node2RB.setSelected(true);
		
		node3RB = new JRadioButton("Node 3");
		node3RB.setOpaque(false);
		node3RB.setForeground(Color.white);
		
		ButtonGroup gloTargetsGrp = new ButtonGroup();
		gloTargetsGrp.add(node2RB);
		gloTargetsGrp.add(node3RB);
		
		gloC.gridx = 1;
		gloPanel.add(node2RB, gloC);
		
		gloC.gridx = 2;
		gloPanel.add(node3RB, gloC);
		
		/*---------Global Insert Panel---------*/
		gloC.gridheight = 1;
		gloC.gridwidth = 3;
		gloC.gridx = 0;
		gloC.gridy = 2;
		JPanel gloInsertPanel = new JPanel();
		gloInsertPanel.setLayout(new GridBagLayout());
		gloInsertPanel.setOpaque(false);
		gloInsertPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		gloPanel.add(gloInsertPanel, gloC);
		
		GridBagConstraints gloInsertC = new GridBagConstraints();
		
		gloInsertC.gridx = 0;
		gloInsertC.gridy = 0;
		
		JLabel gloInsertLbl = new JLabel("INSERT");
		gloInsertLbl.setForeground(Color.white);
		gloInsertPanel.add(gloInsertLbl, gloInsertC);
		
		gloInsert1RB = new JButton("Insert [1] into Target");
		gloInsert1RB.setPreferredSize(new Dimension(150,25));
		gloInsert1RB.setFocusPainted(false);
		gloInsert1RB.setBackground(new Color(8, 120, 48));
		gloInsert1RB.setForeground(Color.white);
		gloInsert1RB.setBorder(new LineBorder(Color.white));
		gloInsert1RB.setSelected(true);
		
		gloInsert1RB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (node2RB.isSelected())
					masterNode.executeQueryAt(Node.EUROPE_AMERICA_NODE_NUMBER, SQLQueries.insertAsiaAfricaData1, SQLQueries.countryCodeAsAf1);
				else
					masterNode.executeQueryAt(Node.ASIA_AFRICA_NODE_NUMBER, SQLQueries.insertEuropeData1, SQLQueries.countryCodeEu1);
				
			}
			
		});
		
		gloInsert2RB = new JButton("Insert [2] into Target");
		gloInsert2RB.setPreferredSize(new Dimension(150,25));
		gloInsert2RB.setFocusPainted(false);
		gloInsert2RB.setBackground(new Color(8, 120, 48));
		gloInsert2RB.setForeground(Color.white);
		gloInsert2RB.setBorder(new LineBorder(Color.white));
		gloInsert2RB.setSelected(true);
		
		gloInsert2RB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (node2RB.isSelected())
					masterNode.executeQueryAt(Node.EUROPE_AMERICA_NODE_NUMBER, SQLQueries.insertEuropeData2, SQLQueries.countryCodeEu2);
				else
					masterNode.executeQueryAt(Node.ASIA_AFRICA_NODE_NUMBER, SQLQueries.insertAsiaAfricaData2, SQLQueries.countryCodeAsAf2);
				
			}
			
		});
		
		ButtonGroup gloInsertGroup = new ButtonGroup();
		gloInsertGroup.add(gloInsert1RB);
		gloInsertGroup.add(gloInsert2RB);
		
		gloInsertC.gridy = 1;
		gloInsertPanel.add(gloInsert1RB, gloInsertC);
		
		gloInsertC.gridy = 2;
		gloInsertPanel.add(gloInsert2RB, gloInsertC);
		
		
		/*---------Global Update Panel---------*/
		gloC.gridheight = 1;
		gloC.gridwidth = 3;
		gloC.gridx = 0;
		gloC.gridy = 3;
		JPanel gloUpdatePanel = new JPanel();
		gloUpdatePanel.setLayout(new GridBagLayout());
		gloUpdatePanel.setOpaque(false);
		gloUpdatePanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		gloPanel.add(gloUpdatePanel, gloC);
		
		GridBagConstraints gloUpdateC = new GridBagConstraints();
		
		gloUpdateC.gridx = 0;
		gloUpdateC.gridy = 0;
		
		JLabel gloUpdateLbl = new JLabel("UPDATE");
		gloUpdateLbl.setForeground(Color.white);
		gloUpdatePanel.add(gloUpdateLbl, gloUpdateC);
		
		gloUpdate1RB = new JButton("Update [1] into Target");
		gloUpdate1RB.setSelected(true);
		gloUpdate1RB.setPreferredSize(new Dimension(150,25));
		gloUpdate1RB.setFocusPainted(false);
		gloUpdate1RB.setBackground(new Color(8, 120, 48));
		gloUpdate1RB.setForeground(Color.white);
		gloUpdate1RB.setBorder(new LineBorder(Color.white));
		gloUpdate1RB.setSelected(true);
		
		gloUpdate1RB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (node2RB.isSelected())
					masterNode.executeQueryAt(Node.EUROPE_AMERICA_NODE_NUMBER, SQLQueries.updateEuropeData1, SQLQueries.countryCodeAsAf2);
				else
					masterNode.executeQueryAt(Node.ASIA_AFRICA_NODE_NUMBER, SQLQueries.insertEuropeData2, SQLQueries.countryCodeEu2);
				
			}
			
		});
		
		gloUpdate2RB = new JButton("Update [2] into Target");
		gloUpdate2RB.setPreferredSize(new Dimension(150,25));
		gloUpdate2RB.setFocusPainted(false);
		gloUpdate2RB.setBackground(new Color(8, 120, 48));
		gloUpdate2RB.setForeground(Color.white);
		gloUpdate2RB.setBorder(new LineBorder(Color.white));
		gloUpdate2RB.setSelected(true);
		
		ButtonGroup gloUpdateGroup = new ButtonGroup();
		gloUpdateGroup.add(gloUpdate1RB);
		gloUpdateGroup.add(gloUpdate2RB);
		
		gloUpdateC.gridy = 1;
		gloUpdatePanel.add(gloUpdate1RB, gloUpdateC);
		
		gloUpdateC.gridy = 2;
		gloUpdatePanel.add(gloUpdate2RB, gloUpdateC);
		
		/*---------Global Delete Panel---------*/
		gloC.gridheight = 1;
		gloC.gridwidth = 3;
		gloC.gridx = 0;
		gloC.gridy = 4;
		JPanel gloDeletePanel = new JPanel();
		gloDeletePanel.setLayout(new GridBagLayout());
		gloDeletePanel.setOpaque(false);
		gloDeletePanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		gloPanel.add(gloDeletePanel, gloC);
		
		GridBagConstraints gloDeleteC = new GridBagConstraints();
		
		gloDeleteC.gridx = 0;
		gloDeleteC.gridy = 0;
		
		JLabel gloDeleteLbl = new JLabel("DELETE");
		gloDeleteLbl.setForeground(Color.white);
		gloDeletePanel.add(gloDeleteLbl, gloDeleteC);
		
		gloDelete1RB = new JButton("Delete [1] into Target");
		gloDelete1RB.setSelected(true);
		gloDelete1RB.setPreferredSize(new Dimension(150,25));
		gloDelete1RB.setFocusPainted(false);
		gloDelete1RB.setBackground(new Color(8, 120, 48));
		gloDelete1RB.setForeground(Color.white);
		gloDelete1RB.setBorder(new LineBorder(Color.white));
		gloDelete1RB.setSelected(true);
		
		gloDelete2RB = new JButton("Delete [2] into Target");
		gloDelete2RB.setPreferredSize(new Dimension(150,25));
		gloDelete2RB.setFocusPainted(false);
		gloDelete2RB.setBackground(new Color(8, 120, 48));
		gloDelete2RB.setForeground(Color.white);
		gloDelete2RB.setBorder(new LineBorder(Color.white));
		gloDelete2RB.setSelected(true);
		
		ButtonGroup gloDeleteGroup = new ButtonGroup();
		gloDeleteGroup.add(gloDelete1RB);
		gloDeleteGroup.add(gloDelete2RB);
		
		gloDeleteC.gridy = 1;
		gloDeletePanel.add(gloDelete1RB, gloDeleteC);
		
		gloDeleteC.gridy = 2;
		gloDeletePanel.add(gloDelete2RB, gloDeleteC);
		
		
		
		/*--------------------Data Table--------------------*/
		String[] columns = {"This", "Depends", "On", "The data"}; //This depends on the data to be displayed
		String[][] data = {{"This", "Depends", "On", "The data"},
						   {"This", "Depends", "On", "The data"},
						   {"This", "Depends", "On", "The data"},
						   {"This", "Depends", "On", "The data"}
							}; //The stuff to import
		
		dataDisplay = new JTable(data, columns){
			public boolean isCellEditable(int data, int columns){
				return false;
			}
			public Component prepareRenderer(TableCellRenderer r, int data, int columns){
				Component c = super.prepareRenderer(r, data, columns);
				
				if(data % 2 == 0)
					c.setBackground(Color.WHITE);
				else
					c.setBackground(new Color(112, 133, 232));
				
				if(isCellSelected(data, columns))
					c.setBackground(new Color(8, 120, 48));
				
				
				
				return c;
			}
		};
		dataDisplay.setPreferredScrollableViewportSize(new Dimension(1000, 400));
		dataDisplay.setFillsViewportHeight(true);
		
		mainC.gridx = 0;
		mainC.gridy = 3;
		mainC.gridwidth = 3;
		mainC.fill = GridBagConstraints.BOTH;
		mainC.weightx = 1;
		mainC.weighty = 1;
		JScrollPane jps = new JScrollPane(dataDisplay);
		mainPanel.add(jps, mainC);
		
		mainC.gridwidth = 1;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
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
