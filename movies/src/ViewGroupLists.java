import java.awt.Dimension;
import java.util.List;
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
 * Klasa pomocnicza ktora miala wyswietlac liste grup
 * 
 * 
 */

class ViewGroupLists extends JScrollPane
{
  private static final long serialVersionUID = 1L;
  private List<GroupOfVideos> list;
  private JTable table;
  private DefaultTableModel tableModel;
  
  public ViewGroupLists (List<GroupOfVideos> list, int width, int height)
  {
    this.list = list;
    setPreferredSize(new Dimension(width, height));
    setBorder(BorderFactory.createTitledBorder("Lista grup:"));
    
    String[] tableHeader = { "Nazwa grupy", "Typ kolekcji", "Liczba filmow" };
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
  
  void refreshView() throws MyExceptions
  {
    this.tableModel.setRowCount(0);
    for (GroupOfVideos group : this.list)
    {
      {
        String[] row = { group.getName(), group.getType1(), Integer.toString(group.collection.size()) };
        this.tableModel.addRow(row);
      }
    }
  }
  
  int getSelectedIndex()
  {
    int index = this.table.getSelectedRow();
    if (index < 0) {
      JOptionPane.showMessageDialog(this, "Zaznacz grupe!", "Blad", 0);
    }
    return index;
  }
}