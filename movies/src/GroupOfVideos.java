import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.Vector;

/*
 * £ukasz Szumilas (236068) W04 
 * 
 * 07.11.2017
 * 
 * Klasa przechowujaca grupe Filmow
 * 
 * 
 */


public class GroupOfVideos implements Iterable<Videos>, Serializable
{
	private static final long serialVersionUID = 1L;

	public Collection<Videos> setGroupType(String type)	throws MyExceptions
		 {
			    switch (type)
			    {
			    case "Vector":    return new Vector <Videos>();
			    case "ArrayList":     return new ArrayList <Videos>();
			    case "HashSet":  return new HashSet <Videos>();
			    case "LinkedList":    return new LinkedList <Videos>();
			    case "TreeSet":   return new TreeSet <Videos>();
			    default: throw new MyExceptions("Zly typ kolekcji!");

			    }
		 }
	
	
    Collection<Videos> collection;
    private String name;
    private String type;
    
	GroupOfVideos(String type, String name) throws MyExceptions
	{
		try
		{
	    this.type = type;
		this.collection = this.setGroupType(type);
		this.name = name;
		}
		catch (IllegalArgumentException e) 
		{
		throw new MyExceptions ("Nie ma takiego typu kolekcji lub zla nazwa!");
		}
		
	}
	GroupOfVideos(Collection<Videos> type, String name) throws MyExceptions
	{
		try
		{
		this.collection =type;
		this.name = name;
		}
		catch (IllegalArgumentException e) 
		{
		throw new MyExceptions ("Nie ma takiego typu kolekcji!");
		}
	}
	
	
	public String getName()
	{
		return this.name;
	}
	
	public Collection<Videos> getType()
	{
		return this.collection;
	}
	public String getType1()
	{
		return this.type;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public String getCollection()
	{
		return this.collection.toString();
	}
	
	public void add(Videos A) 
	{
		this.collection.add(A);
	}
	
	public void edit(Videos A) throws MyExceptions
	{
		VideosConsole.editVideo(A);
	}
	
	public void delete(Videos A)
	{
		A = null;
	}
	
	public static void saveToFile(String filename, GroupOfVideos A) throws MyExceptions
	{
		if(!filename.substring((filename.length()-4),filename.length()).equals(".txt")) throw new MyExceptions("Zly rodzaj pliku!");
		try
	    {
		PrintWriter saving = new PrintWriter(filename);
		saving.println(A.name);
		saving.println(A.type);
			for (Videos B : A.collection) 
			{
				saving.println(B.getTitle()+"-"+B.getYear()+"-"+B.getLength()+"-"+B.getKind());	
				
			}saving.close();
		}
		 catch (IOException e) 
			{
			throw new MyExceptions("Blad przy zapisie danych");
			}
	}
	

	
	public static GroupOfVideos loadMovie(String filename) throws MyExceptions 
	{
		if(!filename.substring((filename.length()-4),filename.length()).equals(".txt")) throw new MyExceptions("Zly rodzaj pliku!");
		try
		{
			File file = new File(filename);
			Scanner read = new Scanner (file); 
			String name = read.nextLine();
			String type = read.nextLine();
			GroupOfVideos g = new GroupOfVideos(name, type);
		      while (read.nextLine() != null)
		      {
		    	  String scanned = read.nextLine();
		    	  String[] sepText = scanned.split("-");
		    	  Videos A1 = new Videos(sepText[0], sepText[1], sepText[2], sepText[3]);
		        g.collection.add(A1);
		      }read.close();
		      return g;
		}
			catch (IOException e) 
			{
			throw new MyExceptions("Blad przy odczycie danych");
			}
	}
	
	  public void sortTitle()
			    throws MyExceptions
			  {
			    if ((this.type == "HashSet" || (this.type == "TreeSet")))
			    {
			      throw new MyExceptions("Kolekcje typu SET nie moga byc sortowane.");
			    }
			    Collections.sort((List<Videos>)this.collection, new Comparator<Videos>()
			    {
			      public int compare(Videos o1, Videos o2)
			      {
			        return o1.getTitle().compareTo(o2.getTitle());
			      }
				 });
			  }
			  
	public void sortYear()
			    throws MyExceptions
			  {
				 if ((this.type == "HashSet" || (this.type == "TreeSet")))
			    {
			      throw new MyExceptions("Kolekcje typu SET nie moga byc sortowane.");
			    }
			    Collections.sort((List<Videos>)this.collection, new Comparator<Videos>()
			    {
			      public int compare(Videos o1, Videos o2)
			      {
			        if (o1.getYear() < o2.getYear()) {
			          return -1;
			        }
			        if (o1.getYear() > o2.getYear()) {
			          return 1;
			        }
			        return 0;
			      }
			    });
			  }
	public void sortLength()
		    throws MyExceptions
		  {
			 if ((this.type == "HashSet" || (this.type == "TreeSet")))
		    {
		      throw new MyExceptions("Kolekcje typu SET nie moga byc sortowane.");
		    }
		    Collections.sort((List<Videos>)this.collection, new Comparator<Videos>()
		    {
		      public int compare(Videos o1, Videos o2)
		      {
		        if (o1.getLength() < o2.getLength()) {
		          return -1;
		        }
		        if (o1.getLength() > o2.getLength()) {
		          return 1;
		        }
		        return 0;
		      }
		    });
		  }
			  
			  public void sortJob()
			    throws MyExceptions
			  {
				  if ((this.type == "HashSet" || (this.type == "TreeSet")))
				  {
			      throw new MyExceptions("Kolekcje typu SET nie moga byc sortowane.");
			      }
			    Collections.sort((List<Videos>)this.collection, new Comparator<Videos>()
			    {
			      public int compare(Videos o1, Videos o2)
			      {
			        return o1.getKind().toString().compareTo(o2.getKind().toString());
			      }
				 });
			  }
	
			  
			  
	@Override
	public Iterator<Videos> iterator() {
		return this.collection.iterator();
	}
	
}

