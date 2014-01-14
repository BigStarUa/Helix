package ua.mamamusic.accountancy;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.RowFilter;
import javax.swing.SwingWorker;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import ua.mamamusic.accountancy.model.Artist;
import ua.mamamusic.accountancy.model.ArtistAlias;
import ua.mamamusic.accountancy.model.DataRow;
import ua.mamamusic.accountancy.model.Distributor;
import ua.mamamusic.accountancy.model.ProductEntity;
import ua.mamamusic.accountancy.model.Track;
import ua.mamamusic.accountancy.model.TrackAlias;
import ua.mamamusic.accountancy.model.TrackType;
import ua.mamamusic.accountancy.model.TrackTypeAlias;
import ua.mamamusic.accountancy.model.UploadListTableModel;
import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


public class ReadExcel extends SwingWorker<List, Object> {
	private String inputFile;
	private File file;
	private JTable table;
	private JLabel label;
	private JToolBar toolBar;
	private TableRowSorter<TableModel> sorter;
	private Distributor dist;
	private List<Artist> artistList;
	private List<TrackType> typeList;
	private UploadFormListener listener;

	public ReadExcel(final File file, final JTable table, final JLabel label,
			final TableRowSorter<TableModel> sorter, Distributor dist, List<Artist> artistList, List<TrackType> typeList, UploadFormListener listener){
		this.file = file;
		this.table = table;
		this.label = label;
		this.sorter = sorter;
		this.dist = dist;
		this.artistList = artistList;
		this.typeList = typeList;
		this.listener = listener;
	}
	  public void setInputFile(String inputFile) {
	    this.inputFile = inputFile;
	  }

	  public void read() throws IOException  {
	    File inputWorkbook = new File(inputFile);
	    Workbook w;
	    try {
	      w = Workbook.getWorkbook(inputWorkbook);
	      // Get the first sheet
	      Sheet sheet = w.getSheet(0);
	      // Loop over first 10 column and lines

	      for (int j = 0; j < sheet.getColumns(); j++) {
	        for (int i = 0; i < sheet.getRows(); i++) {
	          Cell cell = sheet.getCell(j, i);
	          CellType type = cell.getType();
	          if (type == CellType.LABEL) {
	            System.out.println("I got a label "
	                + cell.getContents());
	          }

	          if (type == CellType.NUMBER) {
	            System.out.println("I got a number "
	                + cell.getContents());
	          }

	        }
	      }
	    } catch (BiffException e) {
	      e.printStackTrace();
	    }
	  }
	  
	  public List<DataRow> putToArray() throws IOException  {
		    File inputWorkbook = new File(inputFile);
		    Workbook w;
		    //List list = new ArrayList<String[]>();
		    List<DataRow> list = new ArrayList<DataRow>();
		    String[] array;
		    DataRow product;
		    
		    try {
		      w = Workbook.getWorkbook(inputWorkbook);
		      // Get the first sheet
		      Sheet sheet = w.getSheet(0);
		      //array = new String[sheet.getColumns()];
		      // Loop over first 10 column and lines
		      int columnCount = dist.getColumnCount();
		      int columnArtist = dist.getColumnArtist();
		      int columnTrack = dist.getColumnTrack();
		      int columnPrice = dist.getColumnPrice();
		      int columnQuantity = dist.getColumnQuantity();
		      int columnTrackType = dist.getColumnTrackType();

		      for (int j = 0; j < sheet.getRows(); j++) {
		    	  //array = new String[columnCount];
		    	  product = new DataRow();
		        //for (int i = 0; i < sheet.getRows(); i++) {
		    	  for (int i = 0; i <= columnCount; i++) {
		          Cell cell = sheet.getCell(i, j);
		          CellType type = cell.getType();
		          
		          if(i == columnArtist){
		        	  String artist = cell.getContents();
		        	  product.setColumnArtist(artist);
		        	  //Artist art = matchArtist(artist);
		        	  //product.setArtist(matchArtist(artist));
		          }else if(i == columnTrack){
		        	  product.setColumnTrack(cell.getContents());
		          }else if(i == columnPrice){
		        	  product.setColumnPrice(cell.getContents());
		          }else if(i == columnQuantity){
		        	  product.setColumnQuantity(cell.getContents());
		          }else if(i == columnTrackType){
		        	  product.setColumnTrackType(cell.getContents());
		          }
		          
		          //array[i] = cell.getContents();

		        }
		        list.add(product);
		      }
		      //return list;
		    } catch (BiffException e) {
		      e.printStackTrace();
		    }
		    return list;
		  }

	  public void start() throws Exception{
		  List<DataRow> list = putToArray();
			NumberFormat defForm = NumberFormat.getInstance();
			Number number;
			
			for(int i=0; i < list.size(); i++){
				DataRow row = list.get(i);
				row.setDistributor(dist);

			    try {
			    	number = defForm.parse(row.getColumnQuantity());
			    	row.setQuantity(number.intValue());
			        
			        number = defForm.parse(row.getColumnPrice());
			        row.setIncome(number.doubleValue());
			        
			        
			      } catch (ParseException pe) {
			        System.err.println("not parseable!");
			      }
				
			}
			TableModel model = new UploadListTableModel(list); 
			table.setModel(model);
			sorter.setModel(model);
			listener.fireTableChanged();
	  }
	@Override
	protected List<DataRow> doInBackground() throws Exception {
		publish(1);
		List<DataRow> list = putToArray();
		NumberFormat defForm = NumberFormat.getInstance();
		Number number;
		
		for(int i=0; i < list.size(); i++){
			DataRow prod = list.get(i);
			prod.setDistributor(dist);

		    try {
		    	number = defForm.parse(prod.getColumnQuantity());
		        prod.setQuantity(number.intValue());
		        
		        number = defForm.parse(prod.getColumnPrice());
		        prod.setIncome(number.doubleValue());
		        
		        
		      } catch (ParseException pe) {
		        System.err.println("not parseable!");
		      }
			
		}
		TableModel model = new UploadListTableModel(list); 
		table.setModel(model);
		sorter.setModel(model);
		listener.fireTableChanged();
		return null;
	}
	@Override
	protected void process(List<Object> chunks) {
		label.setVisible(true);
		super.process(chunks);
	}
	@Override
	protected void done() {
		super.done();
		label.setVisible(false);
	}
	
	private Artist matchArtist(String artist){
		for(Artist art : artistList){
			for(ArtistAlias alias : art.getAliasSet()){
				if(alias.getName().equalsIgnoreCase(artist.trim())){
					return art;
				}
			}
		}
		return null;
	}
	
	private Track matchTrack(Artist artist, String track){

		if(artist == null) return null;
		if(artist.getTrackSet() == null) return null;
		for(Track t : artist.getTrackSet()){
			if(t.getAliasSet() == null) continue;
			for(TrackAlias alias : t.getAliasSet()){
				if(alias.getName().equalsIgnoreCase(track.trim())){
					return t;
				}
			}
		}
		return null;
	}
	
	private TrackType matchType(String type){
		for(TrackType t : typeList){
			for(TrackTypeAlias alias : t.getTypeSet()){
				if(alias.getName().equalsIgnoreCase(type.trim())){
					return t;
				}
			}
		}
		return null;
	}
	
}
