import java.io.IOException;
import java.util.Scanner;

/*
 * Program contains :
 * - special class with main to test every method from Film class in console application 
 * 
 * Author: Lukasz Szumilas 236068
 * Date: October 2018
 */

class Functions 
{
	private String menu = "\nChoose option and press the key: \n"
			+ "1. Set the film\n"
			+ "2. Edit\n"
			+ "3. Delete\n"
			+ "4. Save to file\n"
			+ "5. Load from file\n"
			+ "6. End\n";
	
	private String editMenu = "\nChoose option to edit: \n"
			+ "1. Title\n"
			+ "2. Year\n"
			+ "3. Director\n"
			+ "4. Genre\n"
			+ "5. Exit\n";
	
	private Film film;
	
	private Scanner readString;
	
	public void openScannerStr()
	{
		readString = new Scanner(System.in);
	}
	
	public void closeScannerStr()
	{
		readString.close();;
	}

	public Scanner getScannerStr()
	{
		return readString;
	}
	
	public void setFilm() throws Error
	{
		try
		{
		film = new Film();
		System.out.println("Set title: \n");
		film.setTitle(readString.nextLine());
		System.out.println("Set year of release: \n");
		film.setYear(readString.nextLine());
		System.out.println("Set director: \n");
		film.setDirector(readString.nextLine());
		System.out.println("Set genre: " + film.getGenresStr() + "\n");
		film.setGenre(readString.nextLine());
		}
		catch (Error e)
		{
			film = null;
			System.err.println(e + "|| Data restarted");
		}
	}
	
	public void editFilm() throws Error
	{
		String alba1 = readString.nextLine();
		int choices1 = Integer.parseInt(alba1);
		switch (choices1)
		{
			case 1:
			{
				System.out.println("Write new title: ");
				film.setTitle(readString.nextLine());
				break;
			}
			case 2:
			{
				System.out.println("Write new year: ");
				film.setYear(readString.nextLine());
				break;
			}
			case 3:
			{
				System.out.println("Write new director: ");
				film.setDirector(readString.nextLine());
				break;
			}
			case 4:
			{
				System.out.println("Write new genre: " + film.getGenresStr() + "\n");
				film.setGenre(readString.nextLine());
				break;
			}
			case 5:
			{
				break;
			}
		}
	}
	
	
/////////////////////////////////////////////////////////////////////////////main
	
	public static void main(String[] args) throws Error, IOException
	{
		Functions function = new Functions();
		function.openScannerStr();

		while(true)
		{
		if(function.film != null) System.out.println(function.film.toString());
		System.out.println(function.menu);
		String alba = function.getScannerStr().nextLine();
		int choices = Integer.parseInt(alba);
		try
		{
		switch(choices)
			{
				case 1:
				{
					function.setFilm();
					break;
				}
				case 2:
				{
					if(function.film != null)
					{
					System.out.println(function.editMenu);
					function.editFilm();
					}
					else { System.out.println("No data");}
					break;
				}
				case 3:
				{
					function.film = null;
					System.out.println("Film deleted");
					break;
				}	
				case 4:
				{
					System.out.println("Choose name of the file to write: ");
					function.film.saveFile(function.getScannerStr().nextLine(), function.film);
					break;
				}
				case 5:
				{
					System.out.println("Choose name of the file to load: ");
					function.film = Film.loadFile(function.getScannerStr().nextLine());
					break;
				}
				case 6:
				{
					function.closeScannerStr();
					System.exit(0);
				}		
			}
		}
		catch (Error e)
		{
			System.err.println(e.getMessage());
		}
		}
	}

	
}
