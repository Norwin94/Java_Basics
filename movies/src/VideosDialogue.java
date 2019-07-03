import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


/*
 * £ukasz Szumilas (236068) W04 
 * 
 * 25.10.2017
 * 
 * Okno dialogowe do edycji i tworzenia nowego filmu
 * 
 * 
 */
 
public class VideosDialogue extends JDialog implements ActionListener {
	
	private static final long serialVersionUID = 1L;

	
	private Videos Video;

	
	
	Font font = new Font("MonoSpaced", Font.BOLD, 12);
	

	JLabel titleLabel = new JLabel("        Tytul: ");
	JLabel yearLabel  = new JLabel("Rok produkcji: ");
	JLabel lengthLabel      = new JLabel("Dlugosc filmu: ");
	JLabel kindLabel       = new JLabel("       Rodzaj: ");

	JTextField titleField = new JTextField(10);
	JTextField yearField    = new JTextField(10);
	JTextField lengthField    = new JTextField(10);
	JTextField kindField     = new JTextField(10);
	JComboBox<Kind> kindBox = new JComboBox<Kind>(Kind.values());

	 JButton OKButton = new JButton("  OK  ");
	 JButton CancelButton = new JButton("Anuluj");
	
	

	private VideosDialogue(Window parent, Videos Video) {
		
		super(parent, Dialog.ModalityType.DOCUMENT_MODAL);
		
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(250, 250);
		setLocationRelativeTo(parent);
		
		
		this.Video = Video;
		
		
		if (Video==null){
			setTitle("Nowy film");
		} else{
			setTitle(Video.toString());
			titleField.setText(Video.getTitle());
			yearField.setText(""+Video.getYear());
			lengthField.setText(""+Video.getLength());
			kindBox.setSelectedItem(Video.getKind());
		}
		
		
		OKButton.addActionListener( this );
		CancelButton.addActionListener( this );
		
		
		JPanel panel = new JPanel();
		
		
		panel.setBackground(Color.yellow);

		
		panel.add(titleLabel);
		panel.add(titleField);
		
		panel.add(yearLabel);
		panel.add(yearField);
		
		panel.add(lengthLabel);
		panel.add(lengthField);
		
		panel.add(kindLabel);
		panel.add(kindBox);
		
		panel.add(OKButton);
		panel.add(CancelButton);
		
		
		setContentPane(panel);
		
		
		setVisible(true);
	}
	
	
	
	
	public void actionPerformed(ActionEvent event)
	{
		
		Object source = event.getSource();
		
		if (source == OKButton) 
		{
				if (Video == null) 
				{
					try
					{
					String A = "Pusty";
					Video = new Videos(titleField.getText(), yearField.getText(), lengthField.getText(), A);
					Video.setKind((Kind) kindBox.getSelectedItem());
					dispose();
					}
					catch(MyExceptions err)
					{
						JOptionPane.showMessageDialog(this, err.getMessage(), "B³¹d", JOptionPane.ERROR_MESSAGE);
					}
					
				} 
				else 
				{ 
					try
					{
					Video.setTitle(titleField.getText());
					Video.setYear(yearField.getText());
					Video.setLength(lengthField.getText());
					Video.setKind((Kind) kindBox.getSelectedItem());
					dispose();
					}
					
				catch(MyExceptions e)
				{
					JOptionPane.showMessageDialog(this, e.getMessage(), "B³¹d", JOptionPane.ERROR_MESSAGE);
				}

		}
		}

		if (source == CancelButton)
		{
			dispose();
		}
	}
	
	
	public static Videos createNewVideo(Window parent)
	{
		VideosDialogue dialog = new VideosDialogue(parent, null);
		return dialog.Video;
	}


	public static void changeVideo(Window parent, Videos Video)
	{
		new VideosDialogue(parent, Video);
	}

}

