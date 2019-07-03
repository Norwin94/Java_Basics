/** 
 £ukasz Szumilas W4 Inf (236068)   Data: 05.12.2018 r 
 Aplikacja okienkowa Producent - Konsument
 wraz z graficznym interfejsem i tekstowym przebiegiem symulacji
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GraphicTest extends JFrame implements ActionListener
{
	private boolean started;

	private static final long serialVersionUID = 1;
	
    JPanel panel = new JPanel();
	
	  private List<Producer> producer = new ArrayList<Producer>();
	  private List<Consumer> consumer = new ArrayList<Consumer>();
	  private Buffer bufer;

	private JMenu[] menu = {new JMenu("Plik"), 
							new JMenu("Info")};
	
	private JMenuItem[] options =	{	new JMenuItem("Restart"),
										new JMenuItem("Informacje"),
										new JMenuItem("Zamknij"), 
										};
	
	JLabel buforr = new JLabel("Rozmiar bufora: ");
	JLabel prodd = new JLabel("Ilosc producentow: ");
	JLabel conss = new JLabel("Ilosc konsumentow: ");
	JLabel timeMaxProd = new JLabel("Maksymalny czas produkcji");
	JLabel timeMinProd = new JLabel("Minimalny czas produkcji");
	JLabel timeMaxCons = new JLabel("Maksymalny czas konsumpcji");
	JLabel timeMinCons = new JLabel("Minimalny czas konsumpcji");
	
	JComboBox <Integer>bufor = new JComboBox<Integer>();
	JComboBox <Integer>prod = new JComboBox<Integer>();
	JComboBox <Integer>cons = new JComboBox<Integer>();
	JComboBox <Integer>maxTimeChooserC = new JComboBox<Integer>();
	JComboBox <Integer>minTimeChooserC = new JComboBox<Integer>();
	JComboBox <Integer>maxTimeChooserP = new JComboBox<Integer>();
	JComboBox <Integer>minTimeChooserP = new JComboBox<Integer>();
	
	JTextArea showSimulation = new JTextArea();

	
	private JButton start = new JButton("Start");
	private JButton stop = new JButton("Stop");

	
	public GraphicTest()
    { super ("Producent - Konsument");
      setSize(500,450);
      setResizable(false);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     
      options[0].addActionListener(this);
      options[1].addActionListener(this);
      options[2].addActionListener(this);
      
      menu[0].add(options[0]);
      menu[1].add(options[1]);
      menu[0].add(options[2]);
      JMenuBar menubar = new JMenuBar();
      menubar.add(menu[0]); menubar.add(menu[1]);
      setJMenuBar(menubar);

      this.showSimulation.setEditable(false);
      this.showSimulation.setColumns(30);
      this.showSimulation.setRows(15);
     
      JScrollPane scrollPane = new JScrollPane(showSimulation);
      //scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    // scrollPane.setvi
      
      //panel.add(scrollPane, BorderLayout.CENTER);
      for (int i = 1; i < 9; i++) 
      {
          this.bufor.addItem(Integer.valueOf(i));
      }
      for (int i = 1; i < 6; i++) 
      {
          this.minTimeChooserP.addItem(Integer.valueOf(i)*100);
      }
      for (int i = 6; i < 11; i++) 
      {
          this.maxTimeChooserP.addItem(Integer.valueOf(i)*100);
      }
      for (int i = 1; i < 6; i++) 
      {
          this.minTimeChooserC.addItem(Integer.valueOf(i)*100);
      }
      for (int i = 6; i < 11; i++) 
      {
          this.maxTimeChooserC.addItem(Integer.valueOf(i)*100);
      }
      for (int i = 1; i < 8; i++)
      {
        this.prod.addItem(Integer.valueOf(i));
        this.cons.addItem(Integer.valueOf(i));
      }
      start.addActionListener(this);
      stop.addActionListener(this);
      
      bufor.addActionListener(this);
      prod.addActionListener(this);
      cons.addActionListener(this);
      minTimeChooserC.addActionListener(this);
      maxTimeChooserC.addActionListener(this);
      minTimeChooserP.addActionListener(this);
      maxTimeChooserP.addActionListener(this);

      panel.add(buforr);
      panel.add(bufor);
      
      panel.add(prodd);
      panel.add(prod);

      panel.add(conss);
      panel.add(cons);
      
      panel.add(timeMinProd);
      panel.add(minTimeChooserP);
      
      
      
      panel.add(timeMaxProd);
      panel.add(maxTimeChooserP);
      
      panel.add(timeMinCons);
      panel.add(minTimeChooserC);
      
      panel.add(timeMaxCons);
      panel.add(maxTimeChooserC);
      
      panel.add(start);
      panel.add(stop);
      
      
      panel.add(scrollPane);
      
     // panel.setLayout( new GridLayout(4, 3) );
      
      setContentPane(this.panel);
	  setVisible(true);
      
    }
	
	@Override
	public void actionPerformed(ActionEvent event) 
	{
		Object source = event.getSource();
		
		  if (source == this.start) {
		        if (!this.started)
		        {
		          this.bufer = new Buffer(this.showSimulation, ((Integer)this.bufor.getSelectedItem()).intValue(), ((Integer)this.cons.getSelectedItem()).intValue(), ((Integer)this.prod.getSelectedItem()).intValue());
		          for (int i = 0; i < ((Integer)this.prod.getSelectedItem()).intValue(); i++) 
		          {
		            this.producer.add(new Producer(this.bufer,i, (Integer)this.maxTimeChooserP.getSelectedItem(),(Integer)this.minTimeChooserP.getSelectedItem()));
		          }
		          for (int i = 0; i < ((Integer)this.cons.getSelectedItem()).intValue(); i++)
		          {
			            this.consumer.add(new Consumer(this.bufer,i,(Integer)this.maxTimeChooserC.getSelectedItem(),(Integer)this.minTimeChooserC.getSelectedItem()));
		          }
		          
		          Producer k;
		          for(Iterator<Producer> a = this.producer.iterator(); a.hasNext(); k.start())
		          {
		        	  k = (Producer) a.next();
		          }
		        
		          Consumer l;
		          for(Iterator<Consumer> b = this.consumer.iterator(); b.hasNext(); l.start())
		          {
		        	  l = (Consumer) b.next();
		          }
		          this.started = true;
		          this.bufor.setEnabled(false);
		          this.prod.setEnabled(false);
		          this.cons.setEnabled(false);
		          this.maxTimeChooserP.setEnabled(false);
		          this.maxTimeChooserC.setEnabled(false);
		          this.minTimeChooserP.setEnabled(false);
		          this.minTimeChooserC.setEnabled(false);
		          }
		        
		        else
		        {
		       	   Producer m;
			       for(Iterator<Producer> c = this.producer.iterator(); c.hasNext(); m.play())
			       {
			           m = (Producer) c.next();
			       }
			        
			       Consumer n;
			       for(Iterator<Consumer> d = this.consumer.iterator(); d.hasNext(); n.play())
			       {
			      	  n = (Consumer) d.next();
			       }
			    }
		
		      }
		    if (source == this.stop)
		    {

		    	Producer pr;
		          for(Iterator<Producer> e = this.producer.iterator(); e.hasNext(); pr.pause())
		          {
		        	  pr = (Producer) e.next();
		          }
		        
		          Consumer cons;
		          for(Iterator<Consumer> f = this.consumer.iterator(); f.hasNext(); cons.pause())
		          {
		        	  cons = (Consumer) f.next();
		          }
		    }
		   if (source == options[1])
		    {
			   JOptionPane.showMessageDialog(null, "£ukasz Szumilas\n236068 W4\nProducent-Konsument Lab");
		    }
		   if (source == options[2])
		    {
			   dispose(); 
		    }
		   if (source == options[0])
		    {
			   dispose(); new GraphicTest();
		    }
			
		}
	 public static void main(String[] args)
	    { 
		 	new GraphicTest();
	    }
	
}
