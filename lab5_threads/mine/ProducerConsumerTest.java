
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JTextArea;

/** 
 *  Problem producenta i konsumenta
 *
 *  Autor: Pawe³ Rogaliñski
 *   Data: 1 listopada 2017 r.
 *   
 *   ----------------------------
 *   Rozbudowane klasy Producer, Consumer, Buffer
 *   
 *   Edycja: £ukasz Szumilas W4 Inf (236068)     Data: 05.12.2018 r 
 */ 


abstract class  Worker extends Thread {
	
	// Metoda usypia w¹tek na podany czas w milisekundach
	public static void sleep(int millis){
		try {
			Thread.sleep(millis);
			} catch (InterruptedException e) { }
	}
	
	// Metoda usypia w¹tek na losowo dobrany czas z przedzia³u [min, max) milsekund
	public static void sleep(int min_millis, int max_milis){
		sleep(ThreadLocalRandom.current().nextInt(min_millis, max_milis));
	}
	
	// Unikalny identyfikator przedmiotu wyprodukowanego
	// przez producenta i zu¿ytego przez konsumenta
	// Ten identyfikator jest wspólny dla wszystkich producentów
	// i bêdzie zwiêkszany przy produkcji ka¿dego nowego przedmiotu
	static int itemID = 0;
	
	// Minimalny i maksymalny czas produkcji przedmiotu
	public static int MIN_PRODUCER_TIME = 100;
	public static int MAX_PRODUCER_TIME = 1000;
	
	// Minimalny i maksymalny czas konsumpcji (zu¿ycia) przedmiotu
	public static int MIN_CONSUMER_TIME = 100;
	public static int MAX_CONSUMER_TIME = 1000;
	
	
	String name;
	Buffer buffer;
	
	@Override
	public abstract void run();
}


class Producer extends Worker
{
	private boolean pause;
	private Buffer buff;
	private int k;
	private char name = 'A';
	int minTimeP = 100;
	int maxTimeP = 1000;

	
	public void play()
	{
	  this.pause = false;
	}
	
	public void pause()
	{
	  this.pause = true;
	}
	
	public void setMinTimeP(int a)
	{
		this.minTimeP = a;
	}
	
	public void setMaxTimeP(int b)
	{
		this.maxTimeP = b;
	}
	
	public int getMinTimeP()
	{
		return this.minTimeP;
	}
	
	public int getMaxTimeP()
	{
		return this.maxTimeP;
	}
	
	public Producer(Buffer b, int i, int max, int min)
	{
	  this.buff = b;
	  this.k = i;
	  this.minTimeP = min;
	  this.maxTimeP = max;
	}
	
	public int getK()
	{
		return this.k;
	}
	
	public void run()
	{
	  this.buff.put(this.k, this.name++);
	  sleep(minTimeP, maxTimeP);
	  while (this.pause)
	  {
	    try
	    {
	      sleep(5L);
	    }
	    catch (InterruptedException e) {}
	  }
	}
}

class Consumer extends Worker
{
	private Buffer buf;
	private int l;
	private boolean pause;
	private int minTimeC = 100;
	private int maxTimeC = 1000;
	
	public void play()
	{
	  this.pause = false;
	}
	
	public void pause()
	{
	  this.pause = true;
	}
	
	public void setMinTimeC(int a)
	{
		this.minTimeC = a;
	}
	
	public void setMaxTimeC(int b)
	{
		this.maxTimeC = b;
	}
	
	public int getMinTimeC()
	{
		return this.minTimeC;
	}
	
	public int getMaxTimeC()
	{
		return this.maxTimeC;
	}
	
	public Consumer(Buffer a, int i, int max, int min)
	{
	  this.buf = a;
	  this.l = i;
	  this.minTimeC = min;
	  this.maxTimeC = max;
	}
	
	public void run()
	{
	  this.buf.get(this.l);
	  sleep(minTimeC, maxTimeC);
	  while (this.pause) {
	    try
	    {
	      sleep(5L);
	    }
	    catch (InterruptedException e) {}
	  }
	}
}


class Buffer 
{
	  private BlockingQueue<Integer> content;
	  private int customers;
	  private int producers;
	  private int nr;
	  private JTextArea message;
	  
	 public Buffer (JTextArea a, int l, int c, int p)
	  {
	    this.message = a;
	    this.nr = l;
	    this.customers = c;
	    this.producers = p;
	    this.content = new ArrayBlockingQueue<Integer>(l, true);
	  }

	public synchronized void get(int i)
	{
		 this.message.append("Konsument nr " + i + " potrzebuje produktu\n");
		while (this.content.isEmpty())
		{
			this.message.append("Konsument nr " + i + ": Nic dla mnie nie ma\n");
			try 
			{ 
		      Thread.sleep(50L);
			} catch (InterruptedException e) { }
		
		try
	      {
	        wait();
	      }
	      catch (InterruptedException e) {}
		}
		
		   try
		    {
		      Thread.sleep(150L);
		      char take = (char)((Integer)this.content.take()).intValue();
		      this.message.append("Konsument nr " + i + " wybiera " + take + "\n");
		    }
		    catch (InterruptedException e) {}
		    this.message.append("Konsument nr " + i + " wlasnie konsumuje\n");
		    notifyAll();
	}

	  public synchronized void put(int i, char th)
	  {
	    this.message.append("Producent nr " + i + " chce sprzedac " + th + "\n");
	    while (this.content.size() == this.nr)
	    {
	      this.message.append("Producent nr " + i + " nie ma miejsca na produkt\n");
	      try
	      {
	        Thread.sleep(50L);
	        wait();
	      }
	      catch (InterruptedException e) {}
	    }
	    try
	    {
	      this.content.put(Integer.valueOf(th));
	    }
	    catch (InterruptedException e) {}
	    try
	    {
	      Thread.sleep(150L);
	    }
	    catch (InterruptedException e) {}
	    this.message.append("Producent nr " + i + " oddaje " + th + "\n");
	    this.message.append("Producent nr " + i + " produkuje\n");
	    notifyAll();
	  }

	public int getCustomers() {
		return customers;
	}

	public void setCustomers(int customers) {
		this.customers = customers;
	}

	public int getProducers() {
		return producers;
	}

	public void setProducers(int producers) {
		this.producers = producers;
	}
	
} 


public class ProducerConsumerTest {}

