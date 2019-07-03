/*
 * Ksiazka telefoniczna
 * £ukasz Szumilas (236068) 03.01.2019
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

 class MyExceptions extends Exception
 {
	private static final long serialVersionUID = 1L;
	public MyExceptions(String inf)
	{
		super(inf);
	}
	 
 }
	
 class PhoneBook
 {
	 String name;
	 int number;
	 ConcurrentHashMap<String, String> list;	 
	 
	 PhoneBook()
	 {
		 this.list = new ConcurrentHashMap<String,String>();
	 }
	 String load(String file_name) throws MyExceptions, FileNotFoundException
	 {
		 if(!file_name.endsWith(".txt")) throw new MyExceptions("Zly format pliku!");
		 File file = new File(file_name);
		 Scanner read = new Scanner (file);
			this.list.clear();
		 while(read.hasNextLine())
		 {
			 String[] separate = read.nextLine().split("=");
			 
			this.list.put(separate[0], separate[1]);	 
		  } read.close();
		 return ("Plik wczytany");
	 }
	 String save(String file_name) throws MyExceptions, FileNotFoundException
	 {
		 if(!file_name.endsWith(".txt")) throw new MyExceptions("Zly format pliku!");
		 PrintWriter saving = new PrintWriter(file_name);
		 String a = list.toString().substring(1,(list.toString().length()-1));
		 String[] editText = a.split(", "); 
		 for(int i =0; i<editText.length; i++)
		 {
		 saving.println(editText[i]);
		 }
		 
		 saving.close();
		 return ("Plik zapisany");
	 }
	 String getNumber(String name) throws MyExceptions
	 {
		 if(!this.list.containsKey(name)) throw new MyExceptions("Brak podanego imienia!");
		  String nr = this.list.get(name);
		 return ("Nr telefonu pobrany: "+nr);
	 }
	 String put(String name, String number) throws MyExceptions
	 {
		 if(!number.matches("[0-9]+")) {throw new MyExceptions("Nr telefonu musi zawierac same cyfry!");}
		 if(name=="" || name.equals(null) || number=="" || number.equals(null))
			 throw new MyExceptions("Podaj prawidlowe dane!");
         this.list.put(name, number);
		 
		 return ("Nr telefonu zapisany");
	 }
	 String replace(String name, String number) throws MyExceptions
	 {
		 if(!number.matches("[0-9]+")) {throw new MyExceptions("Nr telefonu musi zawierac same cyfry!");}
		 if(!this.list.containsKey(name)) throw new MyExceptions("Brak podanego imienia!");
		 this.list.replace(name, number);
		 return ("Zmiana przebiegla pomyslnie");
	 }
	 String delete(String name) throws MyExceptions
	 {
		 if(!this.list.containsKey(name)) throw new MyExceptions("Brak podanego imienia!");
		 this.list.remove(name);
		 return ("Usuniecie przebieglo pomyslnie");
	 }
	 String list()
	 {
		 if(this.list.isEmpty()) return "Lista jest pusta";
		 else
		 {
		 String all = "";
	     for ( String key : this.list.keySet())
		 {
	     all += "\n";
		 all += key;
		 }
		
		 return ("Przesylam liste: "+ all);
		 }
	 }
 }
 public class PhoneBookServer {}
