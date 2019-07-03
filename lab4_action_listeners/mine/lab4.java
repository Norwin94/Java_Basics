package lab4;


/*
   £ukasz Szumilas, 236068, W04 Inf           26.11.2018r 
   
   Interfejs graficzny
   
*/





import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class lab4 extends JFrame implements ActionListener
{


	private static final long serialVersionUID = 1;

	protected Picture picture;

	private JMenu[] menu = { new JMenu("Figury")};

	private JMenuItem[] items = { new JMenuItem("Punkt"),
			                      new JMenuItem("Kolo"),
			                      new JMenuItem("Linia"),
			                      new JMenuItem("Przesun w gore"),
			                      new JMenuItem("Przesun w dol"),
			                      };

	private JButton buttonCircle = new JButton("Kolo");
	private JButton buttonLine = new JButton("Linia");

    public lab4()
    { super ("lab4");
      setSize(400,400);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      for (int i = 0; i < items.length; i++)
      	items[i].addActionListener(this);

      menu[0].add(items[0]);
      menu[0].add(items[1]);
      menu[0].add(items[2]);
      menu[0].add(items[3]);
      menu[0].add(items[4]);
      menu[0].addSeparator();

      JMenuBar menubar = new JMenuBar();
      	menubar.add(menu[0]);
      setJMenuBar(menubar);

      picture=new Picture();
      picture.addKeyListener(picture);
      picture.setFocusable(true);
      picture.addMouseListener(picture);
      picture.addMouseMotionListener(picture);
      picture.setLayout(new FlowLayout());

      
      
      buttonCircle.addActionListener(this);
      buttonLine.addActionListener(this);

      picture.add(buttonCircle);
      picture.add(buttonLine);

      Circle k = new Circle(100,200);
      Circle l = new Circle(300,400);
      picture.addFigure(k); 
      picture.addFigure(l);
      ;
	  
      setContentPane(picture);
      setVisible(true);
    }

	public void actionPerformed(ActionEvent evt) {
		Object zrodlo = evt.getSource();
		if (zrodlo == buttonCircle)
			picture.addFigure(new Circle());
		if (zrodlo == buttonLine)
			picture.addFigure(new Line());
		if (zrodlo == items[0])
			picture.addFigure(new Point());
		if (zrodlo == items[1])
			picture.addFigure(new Circle());
		if (zrodlo == items[6])
			JOptionPane.showMessageDialog(null, picture.toString());
		
		if (zrodlo == items[7])
			picture.moveAllFigures(0, -10);
		if (zrodlo == items[8])
			picture.moveAllFigures(0, 10);

		picture.requestFocus(); 
		repaint();
	}
	
    public static void main(String[] args)
    { 
    	new lab4();
    }
}
