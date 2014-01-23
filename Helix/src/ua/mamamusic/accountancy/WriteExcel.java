package ua.mamamusic.accountancy;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import ua.mamamusic.accountancy.model.Artist;
import ua.mamamusic.accountancy.model.DataRow;
import ua.mamamusic.accountancy.model.Distributor;
import ua.mamamusic.accountancy.model.Track;
import ua.mamamusic.accountancy.model.TrackType;
import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class WriteExcel {
	 private WritableCellFormat timesBoldUnderline;
	  private WritableCellFormat times;
	  private String inputFile;
	  private JTable table;
	  
	  
	public void setOutputFile(String inputFile) {
	  this.inputFile = inputFile;
	  }
	
	public void setSourceTable(JTable table) {
		  this.table = table;
		  }

	  public void write() throws IOException, WriteException {
	    File file = new File(inputFile);
	    WorkbookSettings wbSettings = new WorkbookSettings();

	    wbSettings.setLocale(new Locale("en", "EN"));

	    WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
	    workbook.createSheet("Report", 0);
	    WritableSheet excelSheet = workbook.getSheet(0);
	    createLabel(excelSheet);
	    createContent(excelSheet);

	    workbook.write();
	    workbook.close();
	  }

	  private void createLabel(WritableSheet sheet)
	      throws WriteException {
	    // Lets create a times font
	    WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
	    // Define the cell format
	    times = new WritableCellFormat(times10pt);
	    // Lets automatically wrap the cells
	    times.setWrap(true);

	    // create create a bold font with unterlines
	    WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false,
	        UnderlineStyle.SINGLE);
	    timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
	    // Lets automatically wrap the cells
	    timesBoldUnderline.setWrap(true);

	    CellView cv = new CellView();
	    cv.setFormat(times);
	    cv.setFormat(timesBoldUnderline);
	    cv.setAutosize(true);

	    // Write a few headers
	    addCaption(sheet, 0, 0, "Header 1");
	    addCaption(sheet, 1, 0, "This is another header");
	    

	  }

	  private void createContent(WritableSheet sheet) throws WriteException,
	      RowsExceededException {
	    // Write a few number
		  TableModel model = table.getModel();
		  String row;
	    for (int i = 0; i < table.getRowCount(); i++) {
	    	//row = (String)table.getValueAt(i, -1);
	    	
	    	for(int n = 0;n < table.getColumnCount(); n++){
	    		addCellFromObject(sheet, n, i, table.getValueAt(i, n));
	    	}
	    }

	  }
	  
	  private void addCellFromObject(WritableSheet sheet, int column, int row, Object object) throws RowsExceededException, WriteException{
		  if(table.getColumnName(column) == "Quantity"){
			  addNumber(sheet, column, row, (Long)object);
		  }else if(table.getColumnName(column) == "Income"
				  || table.getColumnName(column) == "Artist income"
				  || table.getColumnName(column) == "Company income"){
			  addNumber(sheet, column, row, (Double)object);
			  
		  //			if(object instanceof Artist){
//				addLabel(sheet, column, row, object.toString());
//			}else if(object instanceof Track){
//				addLabel(sheet, column, row, object.toString());
//			}else if(object instanceof Distributor){
//				addLabel(sheet, column, row, object.toString());
//			}else if(object instanceof Double){
//				addNumber(sheet, column, row, (Double)object);
//			}else if(object instanceof Long){
//				addNumber(sheet, column, row, (Long)object);
			}else{
				addLabel(sheet, column, row, object.toString());
			}
		}

	  private void addCaption(WritableSheet sheet, int column, int row, String s)
	      throws RowsExceededException, WriteException {
	    Label label;
	    label = new Label(column, row, s, timesBoldUnderline);
	    sheet.addCell(label);
	  }

	  private void addNumber(WritableSheet sheet, int column, int row,
	      Integer integer) throws WriteException, RowsExceededException {
	    Number number;
	    number = new Number(column, row, integer, times);
	    sheet.addCell(number);
	  }
	  
	  private void addNumber(WritableSheet sheet, int column, int row,
		      Long numb) throws WriteException, RowsExceededException {
		    Number number;
		    number = new Number(column, row, numb, times);
		    sheet.addCell(number);
	  }
	  
	  private void addNumber(WritableSheet sheet, int column, int row,
		      Double doub) throws WriteException, RowsExceededException {
		    Number number;
		    number = new Number(column, row, doub, times);
		    sheet.addCell(number);
		  }

	  private void addLabel(WritableSheet sheet, int column, int row, String s)
	      throws WriteException, RowsExceededException {
	    Label label;
	    label = new Label(column, row, s, times);
	    sheet.addCell(label);
	  }
	} 