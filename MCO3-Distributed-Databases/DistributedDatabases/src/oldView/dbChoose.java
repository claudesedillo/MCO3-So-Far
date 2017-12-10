package oldView;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class dbChoose extends JFrame{
	
	public dbChoose(String title){
		super(title);

		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		JPanel contentPane = new JPanel();
		contentPane.setLayout(new GridBagLayout());
		contentPane.setPreferredSize(new Dimension(250,450));
		this.add(contentPane);
		this.pack();
		this.setLocationRelativeTo(null);
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		
		c.gridy = 0;
		JButton AllRegions = new JButton("1. All Regions Repository");
		AllRegions.setPreferredSize(new Dimension(250, 150));
		AllRegions.setFocusPainted(false);
		AllRegions.setBackground(new Color(8, 120, 48));
		AllRegions.setForeground(Color.white);
		AllRegions.setBorder(new LineBorder(Color.white));
		contentPane.add(AllRegions, c);
		AllRegions.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				AllView AllView = new AllView("Node 1: All Regions");

				AllView.setVisible(true);
				hideMainView();

				System.out.println("WOW");
			}
		});
		
		
		c.gridy = 1;
		JButton EuNa = new JButton("2. Europe and America Repository");
		EuNa.setPreferredSize(new Dimension(250, 150));
		EuNa.setFocusPainted(false);
		EuNa.setBackground(new Color(8, 120, 48));
		EuNa.setForeground(Color.white);
		EuNa.setBorder(new LineBorder(Color.white));
		contentPane.add(EuNa, c);
		EuNa.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				EuNaView EuNaView = new EuNaView("Node 2: Europe and North America");
				
				EuNaView.setVisible(true);
				hideMainView();
			}
		});
		
		
		
		c.gridy = 2;
		JButton AsAf = new JButton("3. Asia and Africa Repository");
		AsAf.setPreferredSize(new Dimension(250, 150));
		AsAf.setFocusPainted(false);
		AsAf.setBackground(new Color(8, 120, 48));
		AsAf.setForeground(Color.white);
		AsAf.setBorder(new LineBorder(Color.white));
		contentPane.add(AsAf, c);
		AsAf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				AsAfView AsAfView = new AsAfView("Node 3: Asia and Africa");

				AsAfView.setVisible(true);
				hideMainView();
			}
		});
		
		
	}
	public void hideMainView(){
		this.setVisible(false);
	}
	
	
}
