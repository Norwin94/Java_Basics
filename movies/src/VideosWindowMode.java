import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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
import javax.swing.UIManager;

/*
 * £ukasz Szumilas (236068) W04 
 * 
 * 25.10.2017
 * 
 * Aplikacja okienkowa z edycja i tworzeniem nowych filmow
 * 
 * 
 */

public class VideosWindowMode extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static final String ProgramInfo = "£ukasz Szumilas (236068)\nW04 Informatyka\nLaboratorium 2";

	public static void main(String[] args) {
		new VideosWindowMode();
	}

	private Videos NewVideo;

	Font font = new Font("MonoSpaced", Font.BOLD, 12);

	JMenuBar MyMenu;
	JMenu File, Info, Ex, SaveO, LoadO;
	JMenuItem New, Load, BinaryLoad, Save, BinarySave, Edit, Delete, Information, Exit;

	JLabel titleLabel = new JLabel("        Tytul: ");
	JLabel yearLabel = new JLabel("Rok produkcji: ");
	JLabel lengthLabel = new JLabel("Dlugosc filmu: ");
	JLabel kindLabel = new JLabel("       Rodzaj: ");

	JTextField titleField = new JTextField(10);
	JTextField yearField = new JTextField(10);
	JTextField lengthField = new JTextField(10);
	JTextField kindField = new JTextField(10);

	JButton newButton = new JButton("Nowy film");
	JButton editButton = new JButton("Zmieñ dane");
	JButton saveButton = new JButton("Zapisz do pliku");
	JButton loadButton = new JButton("Wczytaj z pliku");
	JButton binarySaveButton = new JButton("Zapisz do pliku binarnego");
	JButton binaryLoadButton = new JButton("Wczytaj z pliku binarnego");
	JButton deleteButton = new JButton("Usuñ film");
	JButton infoButton = new JButton("O programie");
	JButton exitButton = new JButton("Zakoñcz");

	public VideosWindowMode() {
		setTitle("VideosWindowMode");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(270, 360);
		setResizable(false);
		setLocationRelativeTo(null);

		MyMenu = new JMenuBar();
		File = new JMenu("Plik");
		SaveO = new JMenu("Zapisz w pliku");
		LoadO = new JMenu("Wczytaj z pliku");
		Info = new JMenu("Info");
		Ex = new JMenu("Wyjscie");

		setJMenuBar(MyMenu);

		MyMenu.add(File);
		MyMenu.add(Info);
		MyMenu.add(Ex);

		New = new JMenuItem("Nowy");
		Save = new JMenuItem("tekstowym");
		BinarySave = new JMenuItem("binarnym");
		Edit = new JMenuItem("Edytuj");
		Delete = new JMenuItem("Usun");
		BinaryLoad = new JMenuItem("binarnego");
		Load = new JMenuItem("tekstowego");
		Information = new JMenuItem("O programie");
		Exit = new JMenuItem("Zakoncz program");

		File.add(New);
		File.add(Edit);
		File.add(Delete);
		File.add(SaveO);
		File.add(LoadO);
		LoadO.add(Load);
		LoadO.add(BinaryLoad);
		SaveO.add(Save);
		SaveO.add(BinarySave);
		Ex.add(Exit);
		Info.add(Information);

		titleField.setEditable(false);
		yearField.setEditable(false);
		lengthField.setEditable(false);
		kindField.setEditable(false);

		newButton.addActionListener(this);
		editButton.addActionListener(this);
		saveButton.addActionListener(this);
		loadButton.addActionListener(this);
		binarySaveButton.addActionListener(this);
		binaryLoadButton.addActionListener(this);
		deleteButton.addActionListener(this);
		infoButton.addActionListener(this);
		exitButton.addActionListener(this);

		New.addActionListener(this);
		Edit.addActionListener(this);
		Delete.addActionListener(this);
		SaveO.addActionListener(this);
		LoadO.addActionListener(this);
		BinarySave.addActionListener(this);
		Save.addActionListener(this);
		BinaryLoad.addActionListener(this);
		Load.addActionListener(this);
		Exit.addActionListener(this);
		Information.addActionListener(this);

		JPanel panel = new JPanel();

		panel.add(titleLabel);
		panel.add(titleField);

		panel.add(yearLabel);
		panel.add(yearField);

		panel.add(lengthLabel);
		panel.add(lengthField);

		panel.add(kindLabel);
		panel.add(kindField);

		panel.add(newButton);
		panel.add(deleteButton);
		panel.add(saveButton);
		panel.add(loadButton);
		panel.add(binarySaveButton);
		panel.add(binaryLoadButton);
		panel.add(editButton);
		panel.add(infoButton);
		panel.add(exitButton);
		UIManager.put("OptionPane.cancelButtonText", "Anuluj");

		showVideo();

		setVisible(true);
	}

	void showVideo() {
		if (NewVideo == null) {
			titleField.setText("");
			yearField.setText("");
			lengthField.setText("");
			kindField.setText("");
		} else {
			titleField.setText(NewVideo.getTitle());
			yearField.setText("" + NewVideo.getYear());
			lengthField.setText("" + NewVideo.getLength());
			kindField.setText(NewVideo.getKind());
		}
	}

	public void actionPerformed(ActionEvent e) {
		Object eSource = e.getSource();

		try {
			if ((eSource == newButton) || (eSource == New)) {
				Videos A = NewVideo;
				NewVideo = VideosDialogue.createNewVideo(this);
				if (NewVideo == null) {
					NewVideo = A;
				}
			}
			if ((eSource == deleteButton) || (eSource == Delete)) {
				Object[] options = { "Tak", "Nie" };
				int n = JOptionPane.showOptionDialog(deleteButton, "Czy aby na pewno?", "Potwierdzenie",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				if ((NewVideo == null) && (n == 0))
					throw new MyExceptions("Nie ma czego usuwac!");
				if (n == 0)
					NewVideo = null;
			}
			if ((eSource == saveButton) || (eSource == Save)) {
				if (NewVideo == null)
					throw new MyExceptions("Nie ma nic do zapisania!");
				JFileChooser saveChooser = new JFileChooser();
				if (saveChooser.showSaveDialog(saveButton) == JFileChooser.APPROVE_OPTION) {
					File saveFile = saveChooser.getSelectedFile();
					Videos.saveMovie(NewVideo, saveFile.getPath());
					JOptionPane.showMessageDialog(saveButton, "Dane zostaly zapisane", "Wiadomosc", 1);
				}
			}
			if ((eSource == loadButton) || (eSource == Load)) {
				JFileChooser loadChooser = new JFileChooser();
				if (loadChooser.showOpenDialog(loadButton) == JFileChooser.APPROVE_OPTION) {
					File loadFile = loadChooser.getSelectedFile();
					NewVideo = Videos.loadMovie(loadFile.getPath());
				}
			}
			if ((eSource == binarySaveButton) || (eSource == BinarySave)) {
				if (NewVideo == null)
					throw new MyExceptions("Nie ma nic do zapisania!");
				JFileChooser saveBinaryChooser = new JFileChooser();
				if (saveBinaryChooser.showSaveDialog(saveButton) == JFileChooser.APPROVE_OPTION) {
					File saveBinaryFile = saveBinaryChooser.getSelectedFile();
					Videos.binarySaveMovie(NewVideo, saveBinaryFile.getPath());
					JOptionPane.showMessageDialog(saveButton, "Dane zostaly zapisane", "Wiadomosc", 1);
				}
			}
			if ((eSource == binaryLoadButton) || (eSource == BinaryLoad)) {

				JFileChooser loadBinaryChooser = new JFileChooser();
				if (loadBinaryChooser.showOpenDialog(loadButton) == JFileChooser.APPROVE_OPTION) {
					File loadBinaryFile = loadBinaryChooser.getSelectedFile();
					NewVideo = Videos.binaryLoadMovie(loadBinaryFile.getPath());
				}
			}
			if ((eSource == editButton) || (eSource == Edit)) {
				if (NewVideo == null)
					throw new MyExceptions("Nie ma zadnego filmu!");
				VideosDialogue.changeVideo(this, NewVideo);
			}
			if ((eSource == infoButton) || (eSource == Information)) {
				JOptionPane.showMessageDialog(this, ProgramInfo);
			}
			if ((eSource == exitButton) || (eSource == Exit)) {
				System.exit(0);
			}
		} catch (MyExceptions | ClassNotFoundException er) {
			JOptionPane.showMessageDialog(this, er.getMessage(), "Cos poszlo nie tak", JOptionPane.ERROR_MESSAGE);
		}
		showVideo();
	}

}
