package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChooseDB extends JFrame{

	public ChooseDB(){
		setTitle("Distributed Databases");
		setSize(450, 300);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel lblChooseNode = new JLabel("Which node are you?");
		lblChooseNode.setFont(new Font("Arial", Font.PLAIN, 24));
		lblChooseNode.setBounds(97, 11, 229, 30);
		getContentPane().add(lblChooseNode);
		
		JButton btnNewButton = new JButton("<html>All Regions <br><br> Replica: Asia and Africa </html>");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AllRegionsView arView = new AllRegionsView();
				arView.setVisible(true);
				hideMainView();
			}
		});
		btnNewButton.setBounds(0, 52, 145, 209);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("<html>Europe And America <br><br> Replica: All regions </html>");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EuropeView euView = new EuropeView();
				euView.setVisible(true);
				hideMainView();
			}
		});
		btnNewButton_1.setBounds(144, 52, 153, 209);
		getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("<html>Asia and Africa <br><br> Replica: Europe and America </html>");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AsiaView asView = new AsiaView();
				asView.setVisible(true);
				hideMainView();
			}
		});
		btnNewButton_2.setBounds(295, 52, 139, 209);
		getContentPane().add(btnNewButton_2);
		}
	
	public void hideMainView(){
		this.setVisible(false);
	}
}
