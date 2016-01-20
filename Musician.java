/*Subclass of person class with extra fields and methods*/

class Musician extends Person{
	
	//Declare instance fields and types associated 
	public String instrument;
	public String genre;
	public String bandName;

	//Overload constructor initializes instance fields with a field from a person class 
	//Accessed  through super
	public Musician(String n, String i, String g, String b){
		super(n);
		instrument = i;
		genre = g;
		bandName = b;
	}
	
	//Overload constructor initializing musician without an instrument
	public Musician(String n, String g, String b){
		super(n);
		genre = g;
		bandName = b;
	}
	
	//Overload constructor initializing musician without an instrument and a bandName
	public Musician(String n, String g){
		super(n);
		genre = g;
	}
	
	//Method used to access the instrument of the musician 
	public String getInstrument(){
		
		return instrument;
	}

	//Method to access genre of the musician 	
	public String getGenre(){
		
		return genre;
	}
	
	//Methid to access the name of the band 
	public String getBandName(){
		
		return bandName;
	}
	
	public String showMusician1(){
		return display() + " " + "musician plays " + instrument + " " + genre + " " + "music in " + bandName;
	}
	public String showMusician2(){
		return display() + " " + "musician is a vocalist of a " + genre + " " + "music";
	}
	public String showMusician3(){
		return display() + " " + "musician is a vocalist of a " + genre + " " + "music group " + bandName;
	}
}
 
