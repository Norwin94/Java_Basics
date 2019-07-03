/*
 * Klient
 * £ukasz Szumilas (236068) 03.01.2019
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ClientServer extends JFrame implements ActionListener, Runnable
{
	
	private static final long serialVersionUID = 1L;
	private static String Help = "Put nazwa nrtelefonu - Zapis w ksiedze\n" + 
			"Get nazwa - Pobiera numer o podanej nazwie\n" + 
			"Replace nazwa nrtelefonu - zmienia numer w podanej nazwie\n" + 
			"Save plik.txt - zapisuje liste do pliku\n" + 
			"Load plik.txt - wczytuje liste z pliku\n" + 
			"Delete nazwa - usuwa dana nazwe i przypisany do niej numer\n" + 
			"List - spis nazw\n" ;
	
	String serverHost = "localhost";
	String myHost = "";
    int portNumber = 12312;
	 Socket socket;
	 DataOutputStream print;
	 DataInputStream scann;
	
	JMenu menu[] = {new JMenu ("Menu"), new JMenu ("Info"), new JMenu ("Zamknij")};
	JMenuItem items[] = {new JMenuItem ("Put"),new JMenuItem("Wyjscie"),
			new JMenuItem("Pomoc")};
	JTextField message = new JTextField(20);
	JTextArea textArea = new JTextArea(15,18);
	JTextArea listArea = new JTextArea(7,15);

	JLabel write = new JLabel("Napisz");
	JLabel text = new JLabel("Tekst: ");
	JLabel listLabel = new JLabel("Lista: ");

    JScrollPane scrollPane = new JScrollPane(textArea);
    JScrollPane scrollPaneList = new JScrollPane(listArea);

    
	
	public ClientServer(String name)
	{
		super(name);
		this.myHost = name;
		setSize (270, 500);
		setResizable(false);
		
		textArea.setEditable(false);
		listArea.setEditable(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		menu[1].addActionListener(this);
		menu[2].addActionListener(this);
		menu[0].add(items[0]);
		menu[2].add(items[1]);
		menu[1].add(items[2]);
		items[0].addActionListener(this);
		items[1].addActionListener(this);
		items[2].addActionListener(this);
		message.addActionListener(this);
		
        panel.add(write);
        panel.add(message);
        panel.add(text);
        panel.add(scrollPane);
        panel.add(listLabel);
        panel.add(scrollPaneList);
		
		JMenuBar bar = new JMenuBar();
		bar.add(menu[0]);
		bar.add(menu[1]);
		bar.add(menu[2]);
		setJMenuBar(bar);
		


		setContentPane(panel);
	  	setVisible(true);
	  	new Thread(this).start();  
	  	
	}
	public static void main(String[] args) throws MyExceptions
	{
		new ClientServer("Adam");

	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String number;
		Object event = e.getSource();
		
		if(event == items[1]) { System.exit(0);}
		
		if(event == message)
		{
	         try {
				
		            number = message.getText(); 
		        	textArea.append("<<< " + number + "\n");
					print.writeUTF(number);
					
					message.setText("");
			} catch (IOException | NullPointerException e1) {
				JOptionPane.showMessageDialog(null, e1);
			}
		}
		if(event == items[2])
		{
		JOptionPane.showMessageDialog(null, Help);
		}
		repaint();
	}
	@Override
	public void run() {
			try
			{
				socket = new Socket("192.168.1.17",portNumber);
				print = new DataOutputStream(socket.getOutputStream());
				scann = new DataInputStream(socket.getInputStream());
				
				while(true)
				{
					String temp = scann.readUTF();
					
					textArea.append(">>> " + temp + "\n");
					if(temp.contains("Przesylam liste: "))
					{
			    		listArea.setText("");
						listArea.append(temp);
					}
				}
			}
			catch (IOException e) 
			{
				JOptionPane.showMessageDialog(null, e);
			} 
		}
		

	
}