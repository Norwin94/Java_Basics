import java.awt.Dialog;
import java.awt.Dialog.ModalityType;
import java.awt.Font;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * £ukasz Szumilas (236068) W04 
 * 
 * 07.11.2017
 * 
 * Klasa przechowujaca aplikacje okienkowa grupy filmow i ich edycji
 * 
 * 
 */

public class GroupOfVideosWindowMode extends JDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private GroupOfVideos currentGroup;

	

	public static void main(String[] args) throws MyExceptions 
	  {
		try 
		{
			GroupOfVideos currentGroup = new GroupOfVideos("ArrayList", "Nowy");
			currentGroup.add(new Videos("Titanic", 2016,55,"Dramat"));
			currentGroup.add(new Videos("Titanic", 1997,12,"Thriller"));
			currentGroup.add(new Videos("Boredom", 1923,123,"Komedia"));
			new GroupOfVideosWindowMode(null, currentGroup);
		} catch (MyExceptions e) 
		{
			throw new MyExceptions ("Nie ma takiego typu kolekcji lub zla nazwa!");
		}
	      
	  }
	
	  public GroupOfVideos getGroup()
	  {
		  return currentGroup;
	  }

	  JMenuBar menu = new JMenuBar();
	  JMenu seeVideos = new JMenu("Lista filmow");
	  JMenu sort = new JMenu("Sortowanie");
	  JMenu prop = new JMenu("Wlasciwosci");
	  JMenuItem menuNewVideo = new JMenuItem("Dodaj nowy film");
	  JMenuItem menuEditVideo = new JMenuItem("Edytuj film");
	  JMenuItem menuDeleteVideo = new JMenuItem("Usun film");
	  JMenuItem menuLoadVideo = new JMenuItem("Wczytaj film z pliku");
	  JMenuItem menuSaveVideo = new JMenuItem("Zapisz film do pliku");
	  JMenuItem menuSortTitle = new JMenuItem("Sortuj wg tytulu");
	  JMenuItem menuSortYear = new JMenuItem("Sortuj wg daty produkcji");
	  JMenuItem menuSortLength = new JMenuItem("Sortuj wg dlugosci filmu");
	  JMenuItem menuSortKind = new JMenuItem("Sortuj wg rodzaju");
	  JMenuItem menuChangeName = new JMenuItem("Zmien nazwe");
	  JMenuItem menuChangeType = new JMenuItem("Zmien typ kolekcji");
	  JMenuItem menuAuthor = new JMenuItem("O programie");
	  Font font = new Font("MonoSpaced", 1, 12);
	  JLabel labelGroupName = new JLabel("        Nazwa grupy: ");
	  JLabel labelGroupType = new JLabel("    Rodzaj kolekcji: ");
	  JTextField fieldGroupName = new JTextField(15);
	  JTextField fieldGroupType = new JTextField(15);
	  
	  ViewGroupVideos viewList;
	  
	  
	  public GroupOfVideosWindowMode(Window parent, GroupOfVideos group) throws MyExceptions
	  {
		  
			setTitle("VideosWindowMode");  
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			setSize(380, 350);
			setResizable(false);
			   if (parent != null)
			    {
			      Point location = parent.getLocation();
			      location.translate(100, 100);
			      setLocation(location);
			    }
			    else
			    {
			      setLocationRelativeTo(null);
			    }
			   
			   this.currentGroup = group;
	    
	    setJMenuBar(this.menu);
	    this.menu.add(this.seeVideos);
	    this.menu.add(this.sort);
	    this.menu.add(this.prop);
	    this.menu.add(this.menuAuthor);
	    
	    this.seeVideos.add(this.menuNewVideo);
	    this.seeVideos.add(this.menuEditVideo);
	    this.seeVideos.add(this.menuDeleteVideo);
	    this.seeVideos.addSeparator();
	    this.seeVideos.add(this.menuLoadVideo);
	    this.seeVideos.add(this.menuSaveVideo);
	    
	    this.sort.add(this.menuSortTitle);
	    this.sort.add(this.menuSortYear);
	    this.sort.add(this.menuSortKind);
	    this.sort.add(this.menuSortLength);
	    
	    this.prop.add(this.menuChangeName);
	    this.prop.add(this.menuChangeType);
	    
	    this.menuNewVideo.addActionListener(this);
	    this.menuEditVideo.addActionListener(this);
	    this.menuDeleteVideo.addActionListener(this);
	    this.menuLoadVideo.addActionListener(this);
	    this.menuSaveVideo.addActionListener(this);
	    this.menuSortTitle.addActionListener(this);
	    this.menuSortYear.addActionListener(this);
	    this.menuSortLength.addActionListener(this);
	    this.menuSortKind.addActionListener(this);
	    this.menuChangeName.addActionListener(this);
	    this.menuChangeType.addActionListener(this);
	    this.menuAuthor.addActionListener(this);
	    
	    this.labelGroupName.setFont(this.font);
	    this.labelGroupType.setFont(this.font);
	    
	    this.fieldGroupName.setEditable(false);
	    this.fieldGroupType.setEditable(false);
	    
	    this.viewList = new ViewGroupVideos(this.currentGroup = group, 380, 250);
	    this.viewList.showList();
	    
	    
	    JPanel panel = new JPanel();
	    
	    panel.add(this.labelGroupName);
	    panel.add(this.fieldGroupName);
	    panel.add(this.labelGroupType);
	    panel.add(this.fieldGroupType);

	    panel.add(this.viewList);


	    this.fieldGroupName.setText(this.currentGroup.getName());
	    this.fieldGroupType.setText(this.currentGroup.getType1());
	    
	    setContentPane(panel);
	    
	    setVisible(true);
	  }
	  
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		try 
		{
		Object source = e.getSource();
	    if ((source == this.menuNewVideo))
	      {
	    	
	        Videos newPerson = VideosDialogue.createNewVideo(this);
	        if(newPerson == null) {JOptionPane.showMessageDialog(menuNewVideo, "Dane nie zostaly zapisane", "Wiadomosc", 1); return;}
	        this.currentGroup.add(newPerson);
	        
	      }
	      if ((source == this.menuEditVideo))
	      {
	        int index = this.viewList.getSelectedIndex();
	        if (index >= 0)
	        {
	          Iterator<Videos> iterator = this.currentGroup.iterator();
	          while (index-- > 0) {
	            iterator.next();
	          }
	          VideosDialogue.changeVideo(this, (Videos)iterator.next());
	        }
	      }
	      if ((source == this.menuDeleteVideo))
	      {
	        int index = this.viewList.getSelectedIndex();
	        if (index >= 0)
	        {
	          Iterator<Videos> iterator = this.currentGroup.iterator();
	          while (index-- >= 0) {
	            iterator.next();
	          }
	          iterator.remove();
	        }
	      }
	      if ((source == this.menuLoadVideo))
	      {
	    	  JFileChooser loadChooser= new JFileChooser();
	    	  
				if((loadChooser.showOpenDialog(menuLoadVideo)==JFileChooser.APPROVE_OPTION))
				{
					File loadFile = loadChooser.getSelectedFile();
					Videos NewVideo = Videos.loadMovie(loadFile.getPath());
					this.currentGroup.add(NewVideo);
				}
	      }
	      if ((source == this.menuSaveVideo))
	      {
	        int index = this.viewList.getSelectedIndex();
	        if (index >= 0)
	        {
	          Iterator<Videos> iterator = this.currentGroup.iterator();
	          while (index-- > 0) {
	            iterator.next();
	          }
	          Videos person = (Videos)iterator.next();
	          
	          JFileChooser saveChooser= new JFileChooser();
				if((saveChooser.showSaveDialog(menuSaveVideo)==JFileChooser.APPROVE_OPTION))
				{
					File saveFile = saveChooser.getSelectedFile();
					Videos.saveMovie(person, saveFile.getPath());
					JOptionPane.showMessageDialog(menuSaveVideo, "Dane zostaly zapisane", "Wiadomosc", 1);
				}
	          
	        }
	      }
	     
	      if (source == this.menuSortTitle) {
	        this.currentGroup.sortTitle();
	      }
	      if (source == this.menuSortYear) {
	        this.currentGroup.sortYear();
	      }
	      if (source == this.menuSortKind) {
	        this.currentGroup.sortJob();
	      }
	      if (source == this.menuSortLength) {
		        this.currentGroup.sortLength();
		      }
	      
	      if (source == this.menuChangeName)
	      {
	        String a = JOptionPane.showInputDialog("Zmien nazwe grupy");
	        if(a.equals("")||a==null) return;
	        this.currentGroup.setName(a);
	        this.fieldGroupName.setText(a);
	      }
	      
	      if (source == this.menuChangeType)
	      {
	    	String type1 = JOptionPane.showInputDialog("Zmien typ grupy");
	    	if(type1.equals("")||type1==null) return;
	    	this.currentGroup.setType(type1);
	    	this.fieldGroupType.setText(type1);
	      }
	      
	      if (source == this.menuAuthor) {
	        JOptionPane.showMessageDialog(this, "£ukasz Szumilas (236068)\nW04 Informatyka\nLaboratorium 3");
	      }
	}
	    catch (MyExceptions err)
	    {
	      JOptionPane.showMessageDialog(this, err.getMessage(), "Blad", 0);
	    }
	  }
    }
	

