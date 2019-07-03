/*
 * Serwer
 * £ukasz Szumilas (236068) 03.01.2019
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
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

public class ServerClient extends JFrame implements ActionListener, Runnable
{ 
	private static final long serialVersionUID = 1L;
	private static String Help = "Put nazwa nrtelefonu - Zapis w ksiedze\n" + 
			"Get nazwa - Pobiera numer o podanej nazwie\n" + 
			"Replace nazwa nrtelefonu - zmienia numer w podanej nazwie\n" + 
			"Save plik.txt - zapisuje liste do pliku\n" + 
			"Load plik.txt - wczytuje liste z pliku\n" + 
			"Delete nazwa - usuwa dana nazwe i przypisany do niej numer\n" + 
			"List - spis nazw\n";
	String temp="";
	PhoneBook phoneBook = new PhoneBook(); 
	DataInputStream scann;
	DataOutputStream print;
	
	JPanel panel = new JPanel();

	
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

	public ServerClient()
	{
		super("Serwer");
		setSize (270, 500);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		textArea.setEditable(false);
		listArea.setEditable(false);

		menu[0].add(items[0]);
		menu[1].add(items[2]);
		menu[2].add(items[1]);
		
		menu[1].addActionListener(this);
		menu[2].addActionListener(this);
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
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			String ex;
			
			Object event = e.getSource();
			
			if(event == items[1]) { System.exit(0);}
			
			if(event == message)
			{
		         try {
		        	
				 ex = message.getText(); 
		        print.writeUTF(ex+temp);
		        textArea.append("<<< " + ex + " " + temp + "\n");				
					message.setText("");
				} catch (IOException | NullPointerException e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
		         
			}
			if(event == items[2])
			{
				JOptionPane.showMessageDialog(null, Help);
			}
			if(event == items[0])
			{
				String text = JOptionPane.showInputDialog("Podaj nazwe i numer");
				try 
				{
					String n[] = text.split(" ");
					phoneBook.put(n[0], n[1]);
					
				} 
				catch (MyExceptions | ArrayIndexOutOfBoundsException | NullPointerException e1) 
				{
		        	 JOptionPane.showMessageDialog(null, e1);
				}
			}
		
			repaint();
		}
		
	

		public static void main(String[] args) throws MyExceptions
		{
			new ServerClient();	
		}
		
		@Override
		public void run()
		{
			try 
			{
			int portNumber = 12312;
			String []number;
			String ex;
			String tempr ="";
			ServerSocket serverSocket = new ServerSocket(portNumber);
			Socket socket = serverSocket.accept();
			scann = new DataInputStream(socket.getInputStream());
			print = new DataOutputStream(socket.getOutputStream());
				while(tempr!="Bye")
				{
				ex = scann.readUTF();
				textArea.append(">>> " + ex + "\n");
				number = ex.split(" ");
		        	switch(number[0])
		        	{
			        case ("Put"): {temp = phoneBook.put(number[1], number[2]) ; break;}
			        case ("Get"): {temp = phoneBook.getNumber(number[1]); break; }
			        case ("Replace"): { temp = phoneBook.replace(number[1],number[2]);break;}
			        case ("Save"): {temp = phoneBook.save(number[1]);break; }
			        case ("Load"): {temp = phoneBook.load(number[1]);break; }
			        case ("Delete"): { temp = phoneBook.delete(number[1]);break;}
			        case ("List"): {temp = phoneBook.list(); break;}
			        case ("Bye"): {temp = "exit"; dispose(); }
			        default:{temp = ""; break;}
		        	}
			        if(temp!="") 
			        {
			        	print.writeUTF(temp); 
			        	textArea.append("<<< " + temp + "\n");	temp = "";
			        }
			    	if(phoneBook.list.isEmpty())
					{
			    		listArea.setText("");
					}
			    	else 
			    	{
				    	listArea.setText("");
						for (Map.Entry<String, String> entry : phoneBook.list.entrySet()) 
						{
							String key = entry.toString();
							listArea.append(key + "\n");
						}
			    	}
		        
				}
			//	serverSocket.close();
			}
	        catch(MyExceptions | IOException e)
	        {
				JOptionPane.showMessageDialog(null, e); 
	        }
	        repaint();
 
		}		

	}
		
		
	


