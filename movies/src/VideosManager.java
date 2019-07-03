import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * £ukasz Szumilas (236068) W04 
 * 
 * 07.11.2017
 * 
 * Aplikacja okienkowa ktora miala miec za zadanie edycje i tworzenie grup Filmow
 * 
 * 
 */

public class VideosManager extends JFrame implements ActionListener
{
  private static final long serialVersionUID = 1L;
  
  public static void main(String[] args) throws MyExceptions
  {
    new VideosManager();
  }
  
  WindowAdapter windowListener = new WindowAdapter()
  {
    public void windowClosed(WindowEvent e)
    {
      JOptionPane.showMessageDialog(null, "Program zakonczyl dzialanie!");
    }
    
    public void windowClosing(WindowEvent e)
    {
      windowClosed(e);
    }
  };
 
  private List<GroupOfVideos> currentList = new Vector<GroupOfVideos>();
  JMenuBar menuBar = new JMenuBar();
  JMenu menuGroups = new JMenu("Grupy");
  JMenu menuAbout = new JMenu("O programie");
  JMenuItem menuNewGroup = new JMenuItem("Nowa grupa");
  JMenuItem menuEditGroup = new JMenuItem("Edytuj grupe");
  JMenuItem menuDeleteGroup = new JMenuItem("Usun grupe");
  JMenuItem menuLoadGroup = new JMenuItem("Wczytaj z pliku");
  JMenuItem menuSaveGroup = new JMenuItem("Zapisz do pliku");
  JMenuItem menuAuthor = new JMenuItem("Wykonal");

  ViewGroupLists viewList;
  
  public VideosManager() throws MyExceptions
  {
    setTitle("Grupy filmow");
    setSize(450, 400);
    setResizable(false);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(3);
    
    
    
 
    setJMenuBar(this.menuBar);
    this.menuBar.add(this.menuGroups);
    this.menuBar.add(this.menuAbout);
    
    this.menuGroups.add(this.menuNewGroup);
    this.menuGroups.add(this.menuEditGroup);
    this.menuGroups.add(this.menuDeleteGroup);
    this.menuGroups.addSeparator();
    this.menuGroups.add(this.menuLoadGroup);
    this.menuGroups.add(this.menuSaveGroup);
    
    this.menuAbout.add(this.menuAuthor);
    
    this.menuNewGroup.addActionListener(this);
    this.menuEditGroup.addActionListener(this);
    this.menuDeleteGroup.addActionListener(this);
    this.menuLoadGroup.addActionListener(this);
    this.menuSaveGroup.addActionListener(this);
    this.menuAuthor.addActionListener(this);

    this.viewList = new ViewGroupLists(this.currentList, 400, 250);
    this.viewList.refreshView();
    
    JPanel panel = new JPanel();
    
    setContentPane(panel);
    
    setVisible(true);
    
  }

@Override
public void actionPerformed(ActionEvent e) {
	 {
		    {
		      Object source = e.getSource();
		      try
		      {
		        if (source == this.menuNewGroup) 
		        {
		          String a = JOptionPane.showInputDialog("Podaj nazwe grupy");
		          if(a.equals("")||a==null) return;
		          String b = JOptionPane.showInputDialog("Podaj typ grupy");
		          if(b.equals("")||b==null) return;
		          GroupOfVideos Group = new GroupOfVideos(b, a);
		          GroupOfVideosWindowMode dialog = new GroupOfVideosWindowMode(null, Group);
		          Group = dialog.getGroup();
		            this.currentList.add(Group);
		          
		        }
		        if (source == this.menuEditGroup) 
		        {
		          int index = this.viewList.getSelectedIndex();
		          if (index >= 0)
		          {
		            Iterator<GroupOfVideos> iterator = this.currentList.iterator();
		            while (index-- > 0) {
		              iterator.next();
		            }
		            new GroupOfVideosWindowMode(this, (GroupOfVideos)iterator.next());
		          }
		        }
		        if (source == this.menuDeleteGroup)
		        {
		          int index = this.viewList.getSelectedIndex();
		          if (index >= 0)
		          {
		            Iterator<GroupOfVideos> iterator = this.currentList.iterator();
		            while (index-- >= 0) {
		              iterator.next();
		            }
		            iterator.remove();
		          }
		        }
		        
		        if (source == this.menuSaveGroup)
		        {
			        int index = this.viewList.getSelectedIndex();
			        if (index >= 0)
			        {
			          Iterator<GroupOfVideos> iterator = this.currentList.iterator();
			          while (index-- > 0) {
			            iterator.next();
			          }
			          GroupOfVideos group = (GroupOfVideos)iterator.next();
			          
			          JFileChooser saveChooser= new JFileChooser();
						if((saveChooser.showSaveDialog(menuSaveGroup)==JFileChooser.APPROVE_OPTION))
						{
							File saveFile = saveChooser.getSelectedFile();
							GroupOfVideos.saveToFile(saveFile.getPath(), group);
							JOptionPane.showMessageDialog(menuSaveGroup, "Dane zostaly zapisane", "Wiadomosc", 1);
						}
			          
			        }
			      }
		        	
		        if (source == this.menuLoadGroup)
		        {
		        	JFileChooser loadChooser= new JFileChooser();
					if(loadChooser.showOpenDialog(menuLoadGroup)==JFileChooser.APPROVE_OPTION)
					{
						File loadFile = loadChooser.getSelectedFile();
						 GroupOfVideos A = GroupOfVideos.loadMovie(loadFile.getPath());
						 this.currentList.add(A);
					}
		        }
		 
		      }
		      catch (MyExceptions er)
		      {
		        JOptionPane.showMessageDialog(this, er.getMessage(), "Blad", 0);
		      }
		      try 
		      {
				this.viewList.refreshView();
		      } 
		      catch (MyExceptions e1) 
		      {
				e1.printStackTrace();
			  }
		    }
		  }
}
    
  }