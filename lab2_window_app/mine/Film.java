import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/*
 * Program contains :
 * - class Film representing films with their Title, Release year, Director, Genre
 * - getters and setters for this class and its variables
 * - enum type containing genres
 * - methods to save and load films
 * - extended class Error with specific exceptions
 * 
 * Author: Lukasz Szumilas 236068
 * Date: October 2018
 */



////////////////////////////////////////////////////////////////////////  Enum
enum Genre  
{
	None, Comedy, Drama, Thriller, Horror, Animation;
}

//////////////////////////////////////////////////////////////////////// Errors
class Error extends Exception
{

	private static final long serialVersionUID = 1L;
	public Error(String text) 
	{
		super(text);
	}
}

//////////////////////////////////////////////////////////////////////// main Class Film
class Film 
{
		//object
	private String title;
	private int year;
	private String director;
	private Genre genre;

		//default constructor
	public Film() throws Error
	{
		this.setYear(1900);
		this.setTitle("Empty");
		this.setDirector("None");
		this.setGenre(Genre.None);
	}
	
	//////////////////////////////////////////////////////////////////////// constructor
	public Film(String t, int y, String d, Genre g) throws Error
	{
		this.setYear(y);
		this.setTitle(t);
		this.setDirector(d);
		this.setGenre(g);
	}
	
	public Film(String t, int y, String d, String gS) throws Error
	{
		this.setYear(y);
		this.setTitle(t);
		this.setDirector(d);
		this.setGenre(gS);
	}

	////////////////////////////////////////////////////////////////////////// getters
	public int getYear () 
	{
		return year;
	}
	
	public String getTitle () 
	{
		return title;
	}

	public String getDirector ()
	{
		return director;
	}

	public Genre getGenre () 
	{
		return genre;
	}
	
	public String getGenresStr()
	{
		String allGenres = "";
		for(Genre genre : Genre.values())
		{
			allGenres += "(" + genre.toString() + ") ";
		}
		return allGenres;
	}


    ///////////////////////////////////////////////////////////////////////  setters
	public void setYear (int y) throws Error 
	{
		if(y < 1900 || y > 2018) {
			throw new Error("Year of releasing date must be between 1900 and 2018");
		}
		this.year = y;
	}
	
	public void setYear (String y) throws Error, NumberFormatException
	{
		Scanner scanner = new Scanner(y);
		if(!scanner.hasNextInt())
		{
			scanner.close();
			throw new Error("Value must be an integer!");
		}
		int yearInInt = Integer.parseInt(y);
		if(yearInInt < 1900 || yearInInt > 2018)
		{
			scanner.close();
			throw new Error("Year of releasing date must be between 1900 and 2018");	
		}
		scanner.close();
		this.year = yearInInt;
	}

	public void setTitle (String s) throws Error 
	{
		if(s == null || s.equals(""))
		{
			throw new Error("Complete the title!");
		}
		this.title = s;
	}

	public void setDirector (String d) throws Error 
	{
		if(d == null || d.equals("")) 
		{
			throw new Error("Complete the name!");
		}
		this.director = d;
	}

	public void setGenre (Genre g) throws Error
	{
		for(Genre genre : Genre.values())
		{
			if(g  == genre) {this.genre = g; return;}
		}
		throw new Error ("This genre <" + g.name() + "> does not exists");
	}
	
	public void setGenre (String gS) throws Error 
	{
		for(Genre genre : Genre.values())
		{
			if(gS.equals(genre.toString())) {this.genre = Genre.valueOf(gS); return;}
		}
		throw new Error ("This genre <" + gS + "> does not exists!");
	}
	
	///////////////////////////////////////////////////////////////////////  overload method
	public String toString() 
	{
		String temp = "Film: " + this.title + "| Released: " + this.year + "| Director: " + this.director + "| Genre: " + this.genre.toString();
		return temp;
	}
		


	//////////////////////////////////////////////////////////////////////////  save file
	public void saveFile(String name, Film film) throws Error 
	{
		name += ".txt";
		try 
		{
			PrintWriter newFile = new PrintWriter(name);
			newFile.print(film.title + "\r\n" + film.year + "\r\n" + film.director + "\r\n" + film.genre.toString());
			newFile.close();
			System.out.println("Film saved");
		} 
		catch (FileNotFoundException e) 
		{
			throw new Error("Something went wrong");
		}
	}
	
	//////////////////////////////////////////////////////////////////////////  load file
	public static Film loadFile(String name) throws IOException, Error 
	{
		name += ".txt";
		Film film = new Film();
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(new File(name)));
			
			film.setTitle(reader.readLine());
			film.setYear(reader.readLine());
			film.setDirector(reader.readLine());
			film.setGenre(reader.readLine());
			reader.close();
			System.out.println("Film loaded");
		} 
		catch (FileNotFoundException e) 
		{
			throw new Error("File not found (" + e + ")");
		}
		return film;
	}

}