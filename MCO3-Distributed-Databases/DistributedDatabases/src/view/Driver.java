package view;

import java.io.IOException;

import javax.swing.SwingUtilities;

import oldView.dbChoose;

public class Driver {
	public static void main(String[] args) throws IOException {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				ChooseDB choosedb = new ChooseDB();
				//dbChoose dbChoose = new dbChoose("What database are you using?");


			}
		});


	}
}