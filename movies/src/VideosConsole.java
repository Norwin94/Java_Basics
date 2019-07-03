import java.util.Scanner;

public class VideosConsole 
{
	private static String menu = ("WYBIERZ DZIA£ANIE: \n1. Dodaj film\n2. Edytuj film\n"
			+ "3. Usun film\n4. Zapisz do pliku\n5. Zapisz do pliku binarnego\n"
			+ "6. Wczytaj z pliku\n7. Wczytaj z pliku binarnego\n8. Zakoncz");
	
	public static void main(String[] args) throws ClassNotFoundException, MyExceptions
	{
		VideosConsole application = new VideosConsole();
		application.runMainLoop();
	} 
	
	static Scanner Scann = new Scanner(System.in);
	
	public void runMainLoop() throws MyExceptions, ClassNotFoundException 
	{
		Videos A = new Videos();
		
		System.out.println(menu);
		while(true)
		{      
			
		if(A!=null) System.out.println(A.toString());
		try
		{
		int i = 0;
		i = Scann.nextInt();
		switch(i)
		{
			case 1: 
			
			A = addNewVideo();
			break;
			
			case 2: 
			editVideo(A); 
			break;
			
			case 3:  A = null; System.out.println("Film zostal usuniety"); A = new Videos();
			break;
			
			case 4:
			System.out.println("Podaj nazwe pliku, do ktorego chcesz zapisac film: ");
			Scanner saveFileN = new Scanner(System.in);
			String saveFileName = saveFileN.nextLine();
			Videos.saveMovie(A,saveFileName); 
			break;
			
			case 5: 
			System.out.println("Podaj nazwe pliku binarnego, do ktorego chcesz zapisac film: ");
			Scanner saveBinaryFileN = new Scanner(System.in);
			String saveBinaryFileName = saveBinaryFileN.nextLine();
			Videos.binarySaveMovie(A,saveBinaryFileName); 
			break;
		
			case 6: 
			System.out.println("Podaj nazwe pliku, z ktorego chcesz wczytac film: ");
			Scanner loadFileN = new Scanner(System.in);
			String loadFileName = loadFileN.nextLine();
			A = Videos.loadMovie(loadFileName);
			break;
			
			case 7: 
			System.out.println("Podaj nazwe pliku binarnego, z ktorego chcesz wczytac film: ");
			Scanner loadBinaryFileN = new Scanner(System.in);
			String loadBinaryFileName = loadBinaryFileN.nextLine();
			A = Videos.binaryLoadMovie(loadBinaryFileName);
			break;
			
			case 8:	System.out.println("Koniec programu!"); System.exit(0);
			
			default: System.out.println("Nie ta liczba!"); break;
		 }
		}
		catch (MyExceptions e) {System.out.println("Cos poszlo nie tak. "+ e);}
	  }
	}
		
	static Videos addNewVideo() throws MyExceptions
	{
		Videos A = new Videos();
		try
		{
		System.out.println("Podaj tytul: ");
		String title = Scann.nextLine();
		System.out.println("Podaj rok produkcji: ");
		int year = Scann.nextInt();
		System.out.println("Podaj dlugosc filmu: ");
		int length = Scann.nextInt();
		System.out.println("Podaj rodzaj filmu: ");
		Scann = new Scanner(System.in);
		String kind = Scann.nextLine();
		A.setTitle(title);
		A.setYear(year);
		A.setLength(length);
		A.setKind(kind);
		}
		catch (MyExceptions e) { System.out.println("Cos poszlo nie tak. "+ e);}
		return A;
	}
	
	
	static void editVideo(Videos A) throws MyExceptions
	{
		int k;
		try 
		{
		System.out.println("Co edytowac?\n1. Tytul\n2. Rok produkcji\n3. Rodzaj\n4. Dlugosc" );
		k = Scann.nextInt();
		switch(k)
		{
			case 1: String setNewTitle;
			System.out.println("Podaj nowy tytul: ");
			Scanner setNewT = new Scanner(System.in);
			setNewTitle = setNewT.nextLine();
			A.setTitle(setNewTitle);
			break;
	
			case 2: int setNewYear;
			System.out.println("Podaj inny rok produkcji: ");
			Scanner setNewY = new Scanner(System.in);
			setNewYear = setNewY.nextInt();
			A.setYear(setNewYear); 
			break;
	
			case 3: String setNewKind;
			System.out.println("Podaj inny rodzaj filmu: ");
			Scanner setNewK = new Scanner(System.in);
			setNewKind = setNewK.nextLine();
			A.setKind(setNewKind); 
			break;
	
			case 4: int setNewLength;
			System.out.println("Podaj inna dlugosc filmu: ");
			Scanner setNewL = new Scanner(System.in);
			setNewLength = setNewL.nextInt();
			A.setLength(setNewLength); 
			break;
	
			default: System.out.println("Nie ta liczba!"); return;
		}
		}
		catch (MyExceptions e) { System.out.println("Cos poszlo nie tak"+ e); }
		}
}
