import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/*
 * £ukasz Szumilas (236068) W04 
 * 
 * 07.11.2017
 * 
 * Klasa pomocnicza ktora wyswietla liste filmow
 * 
 * 
 */

public class ViewGroupVideos 	extends JScrollPane
{
	  private static final long serialVersionUID = 1L;
	  private GroupOfVideos list;
	  private JTable table;
	  private String type;
	  private DefaultTableModel tableModel;
	  
	  public ViewGroupVideos(GroupOfVideos list, int width, int height)
	  {
		  this.type = list.getType1();
	    this.list = list;
	    setPreferredSize(new Dimension(width, height));
	    setBorder(BorderFactory.createTitledBorder("Lista filmow:"));
	    
	    String[] tableHeader = { "Tytul", "Rok produkcji", "Dlugosc", "Rodzaj" };
	    this.tableModel = new DefaultTableModel(tableHeader, 0);
	    this.table = new JTable(this.tableModel)
	    {
	      private static final long serialVersionUID = 1L;
	      
	      public boolean isCellEditable(int rowIndex, int colIndex)
	      {
	        return false;
	      }
	    };
	    this.table.setSelectionMode(0);
	    this.table.setRowSelectionAllowed(true);
	    setViewportView(this.table);
	  }
	  

	  ViewGroupVideos(ViewGroupVideos paramViewGroupOfPeople, TableModel $anonymous0)
	  {
	    super((Component) $anonymous0);
	  }
	  

	void showList() throws MyExceptions
	  {
	    this.tableModel.setRowCount(0);
	    
	    for (Videos group : this.list.collection) 
	    {
	      {
	        String[] row = { group.getTitle(), Integer.toString(group.getYear()), Integer.toString(group.getLength()),group.getKind() };
	        this.tableModel.addRow(row);
	      }
	    }
	  }
	  
	  int getSelectedIndex()
	  {
	    int index = this.table.getSelectedRow();
	    if (index < 0) { JOptionPane.showMessageDialog(table, "Zaznacz grupe!");
	    }
	    return index;
	  }
	  
	   String getType1()
	   {
		return this.type;
	   }
	}


