import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * Program contains :
 * - test methods from Film class in window application
 * - extended class to help create new JTextFields
 * Author: Lukasz Szumilas 236068
 * Date: October 2018
 */

class JTextMy extends JTextField{
	private static final long serialVersionUID = 1L;
	
	public JTextMy(String str1, boolean bool)
	{
		    super(str1);
		    super.setEditable(bool);
	}
}

 class WindowApp extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private Film film;
	private JPanel panel;
	private InputDialogs input;
	JButton setFilm = new JButton("Set film");
	JButton editFilm = new JButton("Edit");
	JButton deleteFilm = new JButton("Delete");
	JButton saveFilm = new JButton("Save");
	JButton loadFilm = new JButton("Load");
	JButton saveFilmB = new JButton("Binary Save");
	JButton loadFilmB = new JButton("Binary Load");
	JButton exit = new JButton("Exit");
	JLabel labelTitle = new JLabel("Title:");
	JLabel labelYear = new JLabel("Year:");
	JLabel labelDirector = new JLabel("Director:");
	JLabel labelGenre = new JLabel("Genre:");
	JTextMy title = new JTextMy("Film", false);
	JTextMy year = new JTextMy("Year", false);
	JTextMy director = new JTextMy("Director", false);
	JTextMy genre = new JTextMy("Genre", false);
	JMenuBar bar = new JMenuBar();
	JMenu menuBar = new JMenu("   Menu   ");
	JMenuItem itemSet = new JMenuItem("Set Film");
	JMenuItem itemEdit = new JMenuItem("Edit");
	JMenuItem itemDelete = new JMenuItem("Delete");
	JMenuItem itemSave = new JMenuItem("Save");
	JMenuItem itemLoad = new JMenuItem("Load");
	JMenuItem itemSaveB = new JMenuItem("Binary Save");
	JMenuItem itemLoadB = new JMenuItem("Binary Load");
	JMenuItem itemExit = new JMenuItem("Exit");
	
	/////////////////////////////////////////////////////constructor
	public WindowApp()
	{
		setTitle("Films");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(300, 300);
		setResizable(false);
		panel = new JPanel();
		panel.add(setFilm);
		panel.add(editFilm);
		panel.add(saveFilm);
		panel.add(loadFilm);
		panel.add(saveFilmB);
		panel.add(loadFilmB);
		panel.add(deleteFilm);
		panel.add(exit);
		panel.add(labelTitle); 
		panel.add(title);
		panel.add(labelYear);	
		panel.add(year);
		panel.add(labelDirector);
		panel.add(director);
		panel.add(labelGenre);
		panel.add(genre);
		
		panel.setLayout( new GridLayout(8, 2) );
		
		panel.add(bar);
		setJMenuBar(bar);
		bar.add(menuBar);
		
		menuBar.add(itemSet);
		menuBar.add(itemEdit);
		menuBar.add(itemDelete);
		menuBar.add(itemSave);
		menuBar.add(itemLoad);
		menuBar.add(itemSaveB);
		menuBar.add(itemLoadB);
		menuBar.add(itemExit);
		
		 setFilm.addActionListener(this);
		 editFilm.addActionListener(this);
		 deleteFilm.addActionListener(this);
		 saveFilm.addActionListener(this);
		 loadFilm.addActionListener(this);
		 exit.addActionListener(this);
		 
		itemSet.addActionListener(this);
		itemEdit.addActionListener(this);;
		itemDelete.addActionListener(this);
		itemSave.addActionListener(this);
		itemLoad.addActionListener(this);;
		itemSaveB.addActionListener(this);
		itemLoadB.addActionListener(this);
		itemExit.addActionListener(this);
		
		setContentPane(panel);
	}
 
	public static void main (String[] args)
	{
		
		WindowApp newWindow = new WindowApp();
		newWindow.setVisible(true);
		//newWindow.
	}
	
	public void actionPerformed(ActionEvent event)
	{
		
		Object eventSource = event.getSource();
		try {
			if (eventSource == setFilm || eventSource == itemSet)
			{
				if(film == null)
					{film = new Film();}
					//String m = JOptionPane.showInputDialog("Set title: ");
					//film.setTitle(m);
				//	title.setText(m);
				    input = new InputDialogs(film, this);
					this.film = input.getFilm(film);
					setFilm(film);
					//System.out.println(film.getTitle());
			}
			if (eventSource == editFilm  || eventSource == itemEdit)
			{
				try
				{
				input.setVisible(true);
				
				this.film = input.getFilm(film);
				setFilm(film);
				}
				catch (NullPointerException e)
				{
					throw new Error("Null Pointer " + e);
				}
			}
			
			if (eventSource == deleteFilm  || eventSource == itemDelete)
			{
				film = null;
				film = new Film();
				setFilm(film);
			}
			
			if (eventSource == saveFilm  || eventSource == itemSave)
			{
				if(film == null)
				{
				    JOptionPane.showMessageDialog(panel, "Empty file!", "Error", JOptionPane.ERROR_MESSAGE);
				    return;
				}
				JFileChooser chooser = new JFileChooser();
				chooser.showSaveDialog(panel);
				try {
				film.saveFile(chooser.getSelectedFile().getName(), film);
				}
				catch (NullPointerException e)
				{
					throw new Error("No name!");
				}
			}
			
			if (eventSource == loadFilm  || eventSource == itemLoad)
			{
				String getFileName = JOptionPane.showInputDialog("Write file name to load");
				film = Film.loadFile(getFileName);
				setFilm(film);
			}
			
			if (eventSource == exit  || eventSource == itemExit)
			{
				System.exit(0);
			}
			
			}
		catch (Error | IOException e)
			{
		    JOptionPane.showMessageDialog(panel, e, "Error", JOptionPane.ERROR_MESSAGE);
		    return;
			}
	}
	
	
	public void setFilm(Film film)
	{
		title.setText(this.film.getTitle());
		year.setText(Integer.toString(film.getYear()));
		director.setText(this.film.getDirector());
		genre.setText(this.film.getGenre().toString());;
	}

	
}
