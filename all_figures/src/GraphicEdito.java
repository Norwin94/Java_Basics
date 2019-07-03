
/**
  Program EdytorGraficzny - aplikacja z graficznym interfejsem
  - obsluga zdarzen od klawiatury, myszki i innych elementow GUI.
 
   Autor: Pawel Rogalinski, ...
   Data: 1. 10, 2015 r.
   ---------------------------------------------------------------
   
   £ukasz Szumilas, 236068, W04 Inf           20.11.2017r 
   
   Program edytowany
   dodane: 
   - przyciski do tworzenia figur o nowych kszta³tach
   - uzupe³niony konstruktor klasy GraphicEditor o dodatkowe przyciski do panelu
   - obs³uga zdarzeñ od dodatkowych przycisków
   
*/





import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class GraphicEdito extends JFrame implements ActionListener
{


	private static final long serialVersionUID = 3727471814914970170L;


	private final String DESCRIPTION = "OPIS PROGRAMU\n\n" + "Aktywna klawisze:\n"
			+ "   strzalki ==> przesuwanie figur\n"
			+ "   SHIFT + strzalki ==> szybkie przesuwanie figur\n"
			+ "   +,-  ==> powiekszanie, pomniejszanie\n"
			+ "   DEL  ==> kasowanie figur\n"
			+ "   p  ==> dodanie nowego punktu\n"
			+ "   c  ==> dodanie nowego kola\n"
			+ "   t  ==> dodanie nowego trojkata\n"
			+ "   r  ==> dodanie nowego prostokata\n"
			+ "   a  ==> dodanie nowego trapezu\n"
			+ "   d  ==> dodanie nowego rombu\n"
			+ "\nOperacje myszka:\n" + "   klik ==> zaznaczanie figur\n"
			+ "   ALT + klik ==> zmiana zaznaczenia figur\n"
			+ "   przeciaganie ==> przesuwanie figur";


	protected Picture picture;

	private JMenu[] menu = { new JMenu("Figury"),
			                 new JMenu("Edytuj"),
			                 new JMenu("Info")};

	private JMenuItem[] items = { new JMenuItem("Punkt"),
			                      new JMenuItem("Kolo"),
			                      new JMenuItem("Trojkat"),
			                      new JMenuItem("Prostokat"),
			                      new JMenuItem("Trapez"),
			                      new JMenuItem("Romb"),
			                      new JMenuItem("Wypisz wszystkie"),
			                      new JMenuItem("Przesun w gore"),
			                      new JMenuItem("Przesun w dol"),
			                      new JMenuItem("Powieksz"),
			                      new JMenuItem("Pomniejsz"),
			                      new JMenuItem("Opis programu"),
			                      };

	private JButton buttonPoint = new JButton("Punkt");
	private JButton buttonCircle = new JButton("Kolo");
	private JButton buttonTriangle = new JButton("Trojkat");
	private JButton buttonRectangle = new JButton("Prostokat");
	private JButton buttonTrapeze = new JButton("Trapez");
	private JButton buttonDiamond = new JButton("Romb");

    public GraphicEdito()
    { super ("Edytor graficzny");
      setSize(400,400);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      for (int i = 0; i < items.length; i++)
      	items[i].addActionListener(this);

      // dodanie opcji do menu "Figury"
      menu[0].add(items[0]);
      menu[0].add(items[1]);
      menu[0].add(items[2]);
      menu[0].add(items[3]);
      menu[0].add(items[4]);
      menu[0].add(items[5]);
      menu[0].addSeparator();
      menu[0].add(items[6]);

      // dodanie opcji do menu "Edytuj"
      menu[1].add(items[7]);
      menu[1].add(items[8]);
      menu[1].addSeparator();
      menu[1].add(items[9]);
      menu[1].add(items[10]);
      menu[2].add(items[11]);

      // dodanie do okna paska menu
      JMenuBar menubar = new JMenuBar();
      for (int i = 0; i < menu.length; i++)
      	menubar.add(menu[i]);
      setJMenuBar(menubar);

      picture=new Picture();
      picture.addKeyListener(picture);
      picture.setFocusable(true);
      picture.addMouseListener(picture);
   //   picture.addMouseMotionListener(picture);
      picture.setLayout(new FlowLayout());

      
      
      buttonPoint.addActionListener(this);
      buttonCircle.addActionListener(this);
      buttonTriangle.addActionListener(this);
      buttonRectangle.addActionListener(this);
      buttonTrapeze.addActionListener(this);
      buttonDiamond.addActionListener(this);

      picture.add(buttonPoint);
      picture.add(buttonCircle);
      picture.add(buttonTriangle);
      picture.add(buttonRectangle);
      picture.add(buttonTrapeze);
      picture.add(buttonDiamond);

      setContentPane(picture);
      setVisible(true);
    }

	public void actionPerformed(ActionEvent evt) {
		Object zrodlo = evt.getSource();

		if (zrodlo == buttonPoint)
			picture.addFigure(new Point());
		if (zrodlo == buttonCircle)
			picture.addFigure(new Circle());
		if (zrodlo == buttonTriangle)
			picture.addFigure(new Triangle());
		if (zrodlo == buttonRectangle)
			picture.addFigure(new Rectangle());
		if (zrodlo == buttonTrapeze)
			picture.addFigure(new Trapeze());
		if (zrodlo == buttonDiamond)
			picture.addFigure(new Diamond());
		
		if (zrodlo == items[0])
			picture.addFigure(new Point());
		if (zrodlo == items[1])
			picture.addFigure(new Circle());
		if (zrodlo == items[2])
			picture.addFigure(new Triangle());
		if (zrodlo == items[3])
			picture.addFigure(new Rectangle());
		if (zrodlo == items[4])
			picture.addFigure(new Trapeze());
		if (zrodlo == items[5])
			picture.addFigure(new Diamond());
		if (zrodlo == items[6])
			JOptionPane.showMessageDialog(null, picture.toString());

		if (zrodlo == items[7])
			picture.moveAllFigures(0, -10);
		if (zrodlo == items[8])
			picture.moveAllFigures(0, 10);
		if (zrodlo == items[9])
			picture.scaleAllFigures(1.1f);
		if (zrodlo == items[10])
			picture.scaleAllFigures(0.9f);
		if (zrodlo == items[11])
			JOptionPane.showMessageDialog(null, DESCRIPTION);

		picture.requestFocus(); // przywrocenie ogniskowania w celu przywrocenia
								// obslugi zadarezñ pd klawiatury
		repaint();
	}

    public static void main(String[] args)
    { new GraphicEdito();
    }
}
