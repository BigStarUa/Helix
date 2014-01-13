package ua.mamamusic.accountancy;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.SplashScreen;
import java.awt.Toolkit;
import java.net.URL;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import ua.mamamusic.accountancy.gui.ArtistsList;
import ua.mamamusic.accountancy.gui.DistributorsList;
import ua.mamamusic.accountancy.gui.MainFrame;
import ua.mamamusic.accountancy.gui.TrackTypeList;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				final SplashScreen splash = SplashScreen.getSplashScreen();
		        if (splash == null) {
		            System.out.println("SplashScreen.getSplashScreen() returned null");
		            //return;
		        }
				Locale.setDefault(Locale.ENGLISH);
				HibernateUtil.getSessionFactory();
				
				// initialization
				DistributorsList.getInstance();
				ArtistsList.getInstance();
				TrackTypeList.getInstance();
				
				URL url = ClassLoader.getSystemResource("icon/add24.png");
				Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon/mamamusic.jpg"));

				
				JFrame mainFrame = new MainFrame("MAMAMUSIC"); 
				mainFrame.setIconImage(img);
				mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				mainFrame.setPreferredSize(new Dimension(800, 700));
				mainFrame.pack();
				mainFrame.setVisible(true);
				mainFrame.toFront();
			}
		});	
	}

}
