import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/* 
 *  Program: Prosty edytor grafu
 *     Plik: GraphEditor.java
 *           
 *  Klasa GraphEditor implementuje okno g��wne
 *  dla prostego graficznego edytora grafu.  
 *            
 *    Autor: Pawel Rogalinski
 *     Data:  listopad 2018 r.
 */

public class GraphEditor extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;

	private static final String APP_AUTHOR = "Autor: Pawe� Rogali�ski\n  Data: listopad 2018";
	private static final String APP_TITLE = "Prosty edytor graf�w";
	
	private static final String APP_INSTRUCTION =
			"                  O P I S   P R O G R A M U \n\n" + 
	        "Aktywna klawisze:\n" +
			"   strza�ki ==> przesuwanie wszystkich k�\n" +
			"   SHIFT + strza�ki ==> szybkie przesuwanie wszystkich k�\n\n" +
			"ponadto gdy kursor wskazuje ko�o:\n" +
			"   DEL   ==> kasowanie ko�a\n" +
			"   +, -   ==> powi�kszanie, pomniejszanie ko�a\n" +
			"   r,g,b ==> zmiana koloru ko�a\n\n" +
			"Operacje myszka:\n" +
			"   przeci�ganie ==> przesuwanie wszystkich k�\n" +
			"   PPM ==> tworzenie nowego ko�a w niejscu kursora\n" +
	        "ponadto gdy kursor wskazuje ko�o:\n" +
	        "   przeci�ganie ==> przesuwanie ko�a\n" +
			"   PPM ==> zmiana koloru ko�a\n" +
	        "                   lub usuwanie ko�a\n";
	
	
	public static void main(String[] args) {
		new GraphEditor();
	}

	
	// private GraphBase graph;
	private JMenuBar menuBar = new JMenuBar();
	private JMenu menuGraph = new JMenu("Graph");
	private JMenu menuHelp = new JMenu("Help");
	private JMenuItem menuNew = new JMenuItem("New", KeyEvent.VK_N);
	private JMenuItem menuShowExample = new JMenuItem("Example", KeyEvent.VK_X);
	private JMenuItem menuExit = new JMenuItem("Exit", KeyEvent.VK_E);
	private JMenuItem menuListOfNodes = new JMenuItem("List of Nodes", KeyEvent.VK_N);
	private JMenuItem menuAuthor = new JMenuItem("Author", KeyEvent.VK_A);
	private JMenuItem menuInstruction = new JMenuItem("Instruction", KeyEvent.VK_I);
	
	private GraphPanel panel = new GraphPanel();
	
	
	public GraphEditor() {
		super(APP_TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400,400);
		setLocationRelativeTo(null);
		setContentPane(panel);
		createMenu();
		showBuildinExample();
		setVisible(true);
	}

	private void showListOfNodes(Graph graph) {
		Node array[] = graph.getNodes();
		int i = 0;
		StringBuilder message = new StringBuilder("Liczba w�z��w: " + array.length + "\n");
		for (Node node : array) {
			message.append(node + "    ");
			if (++i % 5 == 0)
				message.append("\n");
		}
		JOptionPane.showMessageDialog(this, message, APP_TITLE + " - Lista w�z��w", JOptionPane.PLAIN_MESSAGE);
	}

	private void createMenu() {
		menuNew.addActionListener(this);
		menuShowExample.addActionListener(this);
		menuExit.addActionListener(this);
		menuListOfNodes.addActionListener(this);
		menuAuthor.addActionListener(this);
		menuInstruction.addActionListener(this);
		
		menuGraph.setMnemonic(KeyEvent.VK_G);
		menuGraph.add(menuNew);
		menuGraph.add(menuShowExample);
		menuGraph.addSeparator();
		menuGraph.add(menuListOfNodes);
		menuGraph.addSeparator();
		menuGraph.add(menuExit);
		
		menuHelp.setMnemonic(KeyEvent.VK_H);
		menuHelp.add(menuInstruction);
		menuHelp.add(menuAuthor);
		
		menuBar.add(menuGraph);
		menuBar.add(menuHelp);
		setJMenuBar(menuBar);
	}


	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source == menuNew) {
			panel.setGraph(new Graph());
		}
		if (source == menuShowExample) {
			showBuildinExample();
		}
		if (source == menuListOfNodes) {
			showListOfNodes(panel.getGraph());
		}
		if (source == menuAuthor) {
			JOptionPane.showMessageDialog(this, APP_AUTHOR, APP_TITLE, JOptionPane.INFORMATION_MESSAGE);
		}
		if (source == menuInstruction) {
			JOptionPane.showMessageDialog(this, APP_INSTRUCTION, APP_TITLE, JOptionPane.PLAIN_MESSAGE);
		}
		if (source == menuExit) {
			System.exit(0);
		}
	}

	private void showBuildinExample() {
		Graph graph = new Graph();

		Node n1 = new Node(100, 100);
		Node n2 = new Node(100, 200);
		n2.setColor(Color.CYAN);
		Node n3 = new Node(200, 100);
		n3.setR(20);
		Node n4 = new Node(200, 250);
		n4.setColor(Color.GREEN);
		n4.setR(30);

		graph.addNode(n1);
		graph.addNode(n2);
		graph.addNode(n3);
		graph.addNode(n4);
		panel.setGraph(graph);
	}
}
