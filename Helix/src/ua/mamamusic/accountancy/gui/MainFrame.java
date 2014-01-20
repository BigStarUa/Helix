package ua.mamamusic.accountancy.gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.SplashScreen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.swing.AbstractButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JButton;
import javax.swing.JTable;

import ua.mamamusic.accountancy.HibernateUtil;
import ua.mamamusic.accountancy.IconFactory;
import ua.mamamusic.accountancy.ReadExcel;
import ua.mamamusic.accountancy.action.ArtistsListOpenAction;
import ua.mamamusic.accountancy.action.DistributorsListOpenAction;
import ua.mamamusic.accountancy.action.ReportFormOpenAction;
import ua.mamamusic.accountancy.action.TrackListOpenAction;
import ua.mamamusic.accountancy.action.TrackTypeListOpenAction;
import ua.mamamusic.accountancy.action.TrashFormOpenAction;
import ua.mamamusic.accountancy.action.UploadFormOpenAction;
import ua.mamamusic.accountancy.model.Distributor;
import ua.mamamusic.accountancy.session.DistributorManagerImpl;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.border.MatteBorder;

import java.awt.Color;



public class MainFrame extends JFrame implements ChangeListener {

	private JPanel contentPane;
	JFileChooser fc;
	private JButton btnOpenFile;
	private JTable table;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenuItem mntmExit;
	private JMenu mnCatalog;
	private JMenuItem mntmDistributors;
	private DistributorsListOpenAction openDistributorAction;
	private TabPane tabbedPane;
	private JMenuItem mntmUploader;
	private UploadFormOpenAction openUploadFormAction;
	private JMenuItem mntmArtists;
	private ArtistsListOpenAction openArtistAction;
	private TrackTypeListOpenAction openTrackTypeAction;
	private JMenuItem mntmTrackType;
	private JToolBar toolBar;
	private JButton btnDistributor;
	private JButton btnArtist;
	private JButton btnTracktype;
	private JButton btnUpload;
	private JButton btnReports;
	private ReportFormOpenAction openReportFormAction;
	private JButton btnTrack;
	private AbstractButton mntmTrack;
	private TrackListOpenAction openTrackAction;
	private AbstractButton btnTrash;
	private TrashFormOpenAction openTrashFormAction;

	/**
	 * Create the frame.
	 */
	public MainFrame(String title) {
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setPreferredSize(new Dimension(850, 550));
		
		tabbedPane = TabPane.getInstance();
		tabbedPane.addChangeListener(this);
		
		//getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		mnCatalog = new JMenu("Catalog");
		menuBar.add(mnCatalog);
		
		//mntmDistributors = new JMenuItem("Distributors");
		openDistributorAction = new DistributorsListOpenAction(tabbedPane, "Distributors List");
		mntmDistributors = new JMenuItem(openDistributorAction);
		mntmDistributors.setText("Distributors List");
		mnCatalog.add(mntmDistributors);
		
		openArtistAction = new ArtistsListOpenAction(tabbedPane, "Artists List");
		mntmArtists = new JMenuItem(openArtistAction);
		mntmArtists.setText("Artists List");
		mnCatalog.add(mntmArtists);
		
		openTrackAction = new TrackListOpenAction(tabbedPane, "Track List");
		mntmTrack = new JMenuItem(openTrackAction);
		mntmTrack.setText("Track List");
		mnCatalog.add(mntmTrack);
		
		openTrackTypeAction = new TrackTypeListOpenAction(tabbedPane, "TrackType List");
		mntmTrackType = new JMenuItem(openTrackTypeAction);
		mntmTrackType.setText("TrackType List");
		mnCatalog.add(mntmTrackType);
		
		openUploadFormAction = new UploadFormOpenAction(tabbedPane, "Uploader");
		mntmUploader = new JMenuItem(openUploadFormAction);
		mntmUploader.setText("Uploader");
//		mntmUploader.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				tabbedPane.addClosableTab(new UploadForm(), "Uploader");
//			}
//		});
		mnCatalog.add(mntmUploader);
		
		openReportFormAction = new ReportFormOpenAction(tabbedPane, "Reports");
		openTrashFormAction = new TrashFormOpenAction(tabbedPane, "Trash");
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		toolBar = new JToolBar();
		toolBar.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.LIGHT_GRAY));
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		btnDistributor = new JButton();
		btnDistributor.setAction(openDistributorAction);
		btnDistributor.setIcon(IconFactory.DISTRIBUTOR32_ICON);
		btnDistributor.setFocusable(false);
		toolBar.add(btnDistributor);
		
		btnArtist = new JButton();
		btnArtist.setAction(openArtistAction);
		btnArtist.setIcon(IconFactory.ARTIST32_ICON);
		btnArtist.setFocusable(false);
		toolBar.add(btnArtist);
		
		btnTrack = new JButton();
		btnTrack.setAction(openTrackAction);
		btnTrack.setIcon(IconFactory.HEADPHONES32_ICON);
		btnTrack.setFocusable(false);
		toolBar.add(btnTrack);
		
		btnTracktype = new JButton();
		btnTracktype.setAction(openTrackTypeAction);
		btnTracktype.setIcon(IconFactory.TRACK_TYPE32_ICON);
		btnTracktype.setFocusable(false);
		toolBar.add(btnTracktype);
		
		JSeparator toolBarSeparator = new JToolBar.Separator();
		toolBar.add(toolBarSeparator);
		
		btnUpload = new JButton();
		btnUpload.setAction(openUploadFormAction);
		btnUpload.setIcon(IconFactory.IMPORT32_ICON);
		btnUpload.setFocusable(false);
		toolBar.add(btnUpload);
		
		toolBarSeparator = new JToolBar.Separator();
		toolBar.add(toolBarSeparator);
		
		btnReports = new JButton();
		btnReports.setFocusable(false);
		btnReports.setAction(openReportFormAction);
		btnReports.setIcon(IconFactory.CHART32_ICON);
		toolBar.add(btnReports);
		
		toolBarSeparator = new JToolBar.Separator();
		toolBar.add(toolBarSeparator);
		
		btnTrash = new JButton();
		btnTrash.setFocusable(false);
		btnTrash.setAction(openTrashFormAction);
		btnTrash.setIcon(IconFactory.TRASH32_ICON);
		toolBar.add(btnTrash);
		
		//Create file chooser
		fc = new JFileChooser();
		
		
	}


	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		
	}

}
