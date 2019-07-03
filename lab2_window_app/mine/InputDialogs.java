import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * Program contains :
 * - class created for dialogue with user, 'child' window for WindowApp class
 * 
 * Author: Lukasz Szumilas 236068
 * Date: October 2018
 */

 class InputDialogs extends JDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;

	private Film film;
	private JPanel panel;
	Object eventSource;
	JLabel labelTitle = new JLabel("Title:");
	JTextMy title = new JTextMy("Film", true);
	JLabel labelYear = new JLabel("Year:");
	JTextMy year = new JTextMy("Year", true);
	JLabel labelDirector = new JLabel("Director:");
	JTextMy director = new JTextMy("Director", true);
	JLabel labelGenre = new JLabel("Genre:");
	JComboBox<Genre> genres = new JComboBox<Genre>(Genre.values());
	JButton ok = new JButton("OK");
	JButton cancel = new JButton("Cancel");

	public InputDialogs(Film film, WindowApp window) throws Error
	{
		super(window, Dialog.ModalityType.DOCUMENT_MODAL);
		film = new Film();
		this.film = film;
		setTitle("Setting");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(200, 200);
		setResizable(false);
		panel = new JPanel();
		title.setText(film.getTitle());
		year.setText(Integer.toString(film.getYear()));
		director.setText(film.getDirector());
		panel.add(labelTitle); 
		panel.add(title);
		panel.add(labelYear);	
		panel.add(year);
		panel.add(labelDirector);
		panel.add(director);
		panel.add(labelGenre);
		panel.add(genres);
		panel.add(ok);
		panel.add(cancel);
		panel.setLayout( new GridLayout(5, 2) );
		
		 title.addActionListener(this);
		 year.addActionListener(this);
		 director.addActionListener(this);
		 genres.addActionListener(this);
		 ok.addActionListener(this);
		 cancel.addActionListener(this);

		setContentPane(panel);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent event) 
	{
		 eventSource = event.getSource();
		try 
		{

			if (eventSource == ok)
			{
				film.setTitle(title.getText());
				film.setYear(year.getText());
				film.setDirector(director.getText());
				film.setGenre(genres.getSelectedItem().toString());
				setVisible(false);

			}
			if (eventSource == cancel)
			{
				setVisible(false);
			}
		}
		catch (Error e)
		{
			JOptionPane.showMessageDialog(panel, e, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public Film getFilm(Film film)
	{
		film = this.film;
		return film;
	}
	
}
