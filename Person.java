public class Person{
	
	//Declare instance field
	String name;
	
	//Constructor initializes instance field
	public Person(String n){
		
		name = n;	
	}
	
	//Method to access instance 
	public String getName(){
		return name;
	}
	
	//Display method to output the name
	public String display(){
		
		return "Name of the artist is " + name;
	}
}
 
