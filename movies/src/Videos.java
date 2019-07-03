
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/*
 * £ukasz Szumilas (236068) W04 
 * 
 * 25.10.2017
 * 
 * Klasa ze zmienna Filmy
 * 
 * 
 */

enum Kind
{
	EMPTY("Pusty"),
	COMEDY("Komedia"),
	DRAMA("Dramat"),
	THRILLER("Thriller"),
	HORROR("Horror"),
	FANTASY("Fantasy"),
	ANIME("Anime"),
	MUSICAL("Musical");
	
	String name;
	Kind(String name)
	{
		this.name = name;
	}
	public String getKind()
	{
		return this.name;
	}

}

class MyExceptions extends Exception
{
	private static final long serialVersionUID = 1L;
	public MyExceptions(String inf) 
	{
		super(inf);
	}
}

public class Videos
{
	private String title;
	private int year;
	private int length;
	private Kind kind;
	
	public Videos(String title, int year, int length, Kind kind) throws MyExceptions
	{
		setTitle(title);
		setLength(length);
		setYear(year);
		setKind(kind);
    }
	public Videos(String title, int year, int length, String kind) throws MyExceptions
	{
		setTitle(title);
		setLength(length);
		setYear(year);
		setKind(kind);
    }
	public Videos(String title, String year, String length, Kind kind) throws MyExceptions
	{
		setTitle(title);
		setLength(length);
		setYear(year);
		setKind(kind);
    }
	public Videos(String title, String year, String length, String kind) throws MyExceptions
	{
		setTitle(title);
		setLength(length);
		setYear(year);
		setKind(kind);
    }
	public Videos() throws MyExceptions
	{
		setTitle("Pusty");
		setLength(1);
		setYear(1900);
		setKind("Pusty");
    }
	public void setTitle(String title) throws MyExceptions
	{
		if ((title == null) || title.equals("")) throw new MyExceptions("Wypelnij pole 'Tytul'!");
		this.title = title;
	}
	public void setYear(int year) throws MyExceptions
	{
		if (year > 2017 || year < 1900) throw new MyExceptions("Zly przedzial daty produkcji!");
		this.year = year;
	}
	public void setYear(String syear) throws MyExceptions
	{
		if (syear == null || syear.equals("")) throw new MyExceptions("Podaj prawidlowa date produkcji!");
		try 
		{ 
		  setYear(Integer.parseInt(syear));
		} catch (NumberFormatException e) 
		{
		  throw new MyExceptions("Data produkcji ma byc liczba calkowita!");
		}
	}
	public void setLength(int length) throws MyExceptions
	{
		if ((length < 1) || length > 300) throw new MyExceptions("Zly przedzial dlugosci filmu!");
		this.length = length;
	}
	public void setLength(String slength) throws MyExceptions
	{
		if ((slength == null) || slength.equals("")) throw new MyExceptions("Podaj prawidlowa dlugosc filmu!");
		try 
		{ 
		  setLength(Integer.parseInt(slength));
		} catch (NumberFormatException e) 
		{
		  throw new MyExceptions("Dlugosc filmu ma byc liczba calkowita!");
		}
	}
	public void setKind(Kind kind)
	{
		this.kind = kind;
	}
	public void setKind(String kind)
	{
		for(Kind kn : Kind.values())
			if (kn.name.equals(kind)) 
				this.kind = kn;
	}
	public String getTitle()
	{
		return this.title;
	}
	public int getYear()
	{
		return this.year;
	}
	public int getLength()
	{
		return this.length;
	}

	public String getKind()
	{
		return kind.getKind();
	}
	public void showVideo(Videos ShowVideo) throws MyExceptions
	{
		if(ShowVideo != null)
		{String message = "Tytul: " + getTitle() +"  Rok produkcji: " + getYear() + "   Dlugosc (min): " + 
		 getLength() + "   Rodzaj: " + getKind(); System.out.println(message);}
		else throw new MyExceptions("Nie ma czego pokazywac!");
	}
	public String toString()
	{
		
		return "Tytul: " + getTitle() +"  Rok produkcji: " + getYear() + "   Dlugosc (min): " + 
		       getLength() + "   Rodzaj: " + getKind();
	}
	
	public static void saveMovie(Videos film, String filename) throws MyExceptions
	{
		if(!filename.substring((filename.length()-4),filename.length()).equals(".txt")) throw new MyExceptions("Zly rodzaj pliku!");
		try
		{
		PrintWriter saving = new PrintWriter(filename);
		saving.println("Film: "+film.title+"-"+film.year+"-"+film.length+"-"+film.getKind());	
		saving.close();
		}
	 catch (IOException e) 
		{
		throw new MyExceptions("Blad przy zapisie danych");
		}
	}
	public static void binarySaveMovie(Videos Film, String filename) throws MyExceptions
	{
		if(!filename.substring((filename.length()-4),filename.length()).equals(".bin")) throw new MyExceptions("Zly rodzaj pliku!");
		try
		{
			ObjectOutputStream binarySaving = new ObjectOutputStream(new FileOutputStream(filename));
			binarySaving.writeObject(Film.title+"-"+Film.year+"-"+Film.length+"-"+Film.getKind());	
			binarySaving.close();
		}
	 catch (IOException e) 
		{
		throw new MyExceptions("Blad przy zapisie danych binarnych");
		}
	}
	public static Videos loadMovie(String filename) throws MyExceptions 
	{
		if(!filename.substring((filename.length()-4),filename.length()).equals(".txt")) throw new MyExceptions("Zly rodzaj pliku!");
		try
		{
			File file = new File(filename);
			Scanner read = new Scanner (file); 
			String scanned = read.nextLine();
			String[] sepText = scanned.split("-");
			String B = sepText[0].substring(0, 5);
			read.close();
			if(!B.equals("Film:")) {throw new MyExceptions("Wybierz plik z filmem!");}
			sepText[0] = sepText[0].substring(6, sepText[0].length());
			Videos Film = new Videos(sepText[0], sepText[1], sepText[2], sepText[3]);
			
			return Film;
		}
			catch (IOException e) 
			{
			throw new MyExceptions("Blad przy odczycie danych");
			}
	}
	public static Videos binaryLoadMovie(String filename) throws MyExceptions, ClassNotFoundException
	{
		if(!filename.substring((filename.length()-4),filename.length()).equals(".bin")) throw new MyExceptions("Zly rodzaj pliku!");
		try
		{
		ObjectInputStream binaryLoad = new ObjectInputStream(new FileInputStream(filename));
		Object bString = binaryLoad.readObject();	
		String[] sepText = ((String) bString).split("-"); 
		Videos Film = new Videos(sepText[0], sepText[1], sepText[2], sepText[3]);
		binaryLoad.close();
		return Film;
		}
	 catch (IOException e) 
		{
		throw new MyExceptions("Blad przy odczycie danych binarnych");
		}
	}
	public int hashCode()
	{
	  int prime = 31;
	  int result = 1;
	  result = prime * result + (this.title == null ? 0 : this.title.hashCode());
	  result = prime * result + (this.kind == null ? 0 : this.kind.hashCode());
	  result = prime * result + this.getYear();
	  result = prime * result + this.getLength();
	  return result;
	}
	public boolean equals(Videos A)
	{
		if (this == A) return true;
		if(this.getTitle()==A.getTitle())
		{
			if(this.getYear()==A.getYear())
			{
				if(this.getLength()==A.getLength())
				{
					if(this.getKind()==A.getKind())
						return true;
				}
			}
		}
		return false;
	}
	public int compareTo(Videos A)
	{
		int a = this.getTitle().compareTo(A.getTitle());
		if(a==0)
		{
			a = this.getYear()-A.getYear();
			if(a==0)
			{
				a = this.getLength()-A.getLength();
				if(a==0)
				{
					a = this.getKind().compareTo(A.getKind());
				}
			}
		}
		if(a<0) a=-a;
	 return a;
	}
	
}

	

	
	