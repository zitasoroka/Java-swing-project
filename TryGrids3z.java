/*
-- This is a java swing program that creates a GUI;
-- Initially it displays 5 buttons with a picture of a musician on it;
-- When one of these buttons is pressed a musician object is displayed; 
-- And two additional buttons at the bottom of the container - "Add Musician" and "Delete Musician" that allow to add a musician or delete one respectively;

*/

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.lang.*;
import javax.imageio.ImageIO;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;

public class TryGrids3z extends JFrame implements ActionListener{
		
		//Declaring fields of a class 
		 JButton button1;  
		 JButton button2;
		 JButton button3;
		 JButton button4;
		 JButton button5;
		 JButton extr;
		 JButton extr1;
		 JPanel buttonPanel;
		 BufferedImage img;
		 JButton extr3;
		 File file;
		 ArrayList<Musician> list = new ArrayList<Musician>();
		 ArrayList<JButton> buttons = new ArrayList<JButton>();
		 
		 //Constructor that builds the GUI and initializes it
    public TryGrids3z() {
		
		 JPanel panel0 = new JPanel(new BorderLayout());
		 JPanel mainPanel = new JPanel(new BorderLayout());
		 JPanel panel1 = new JPanel();

		 
		 buttonPanel = new JPanel(new GridLayout(0, 3, 3, 3));
		 
         ImageIcon icon1 = new ImageIcon(this.getClass().getResource("roscoe.jpg"));
         ImageIcon icon2 = new ImageIcon(this.getClass().getResource("butler.jpg"));
         ImageIcon icon3 = new ImageIcon(this.getClass().getResource("janet.jpg"));
		 ImageIcon icon4 = new ImageIcon(this.getClass().getResource("simovic.jpg"));
		 ImageIcon icon5 = new ImageIcon(this.getClass().getResource("moore.jpg"));
         
		 button1 = new JButton(icon1);
		 button2 = new JButton(icon2);
		 button3 = new JButton(icon3);
		 button4 = new JButton(icon4);
		 button5 = new JButton(icon5);
		 
		 extr = new JButton("Add musician");
		 extr1 = new JButton("Delete musician");
		 
		 panel1.add(extr);
		 extr.addActionListener(this);
		 panel1.add(extr1);
		 extr1.addActionListener(this);
		 
		 button1.setPreferredSize(new Dimension(150, 150));
		 button2.setPreferredSize(new Dimension(150, 150));
		 button3.setPreferredSize(new Dimension(150, 150));
		 button4.setPreferredSize(new Dimension(150, 150));
		 button5.setPreferredSize(new Dimension(150, 150));
		 
		 
		 buttonPanel.add(button1);
		 buttonPanel.add(button2);
		 buttonPanel.add(button3);
		 buttonPanel.add(button4);
		 buttonPanel.add(button5);
		 
		 
		 button1.addActionListener(this);
		 button2.addActionListener(this);
		 button3.addActionListener(this);
		 button4.addActionListener(this);
		 button5.addActionListener(this);
		 
		 buttons.add(button1);
		 buttons.add(button2);
		 buttons.add(button3);
         buttons.add(button4);
		 buttons.add(button5);
		 
		 mainPanel.add(buttonPanel, BorderLayout.WEST);
		 
		 
		 panel0.add(mainPanel, BorderLayout.NORTH);
		 panel0.add(panel1, BorderLayout.SOUTH);
		 add(panel0);
		 
		 buttonPanel.revalidate();
		 buttonPanel.repaint();
		 
	}
		//Method used to read musicians data line by line from a file in a working directory 
		//It splits the line on comma, declares each part as a string with a coresponding name and pops it into tempObj2 creating a musician object 
		//Finally places each of the musician object into an arraylist
		public void getDataFromFile(){
		
		//arralist method used to clear the arraylist before placing musicians 
		list.clear();
		
		try{
			BufferedReader br = new BufferedReader(new FileReader("musicians.txt"));
			String fileRead = br.readLine();
			
			while (fileRead != null){
	
				String[] spLine = fileRead.split(",");
				String name = spLine[0];
				String instrument = spLine[1];
				String genre = spLine[2];
				String band = spLine[3];
				
				Musician tempObj = new Musician(name, instrument, genre, band);
				list.add(tempObj);
				fileRead = br.readLine();
			}
			br.close();
		}
		catch(FileNotFoundException fnfe){
			System.out.println("File not found");
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
		}
		
		//Method used to create a new button 
		//Opens JFileChooser and displays current working directory, allowing user to browse for an image and place it onto the button
		public void createButton(){
			
			    JButton button = new JButton();
				button.addActionListener(this);
				button.setPreferredSize(new Dimension(150, 150));
				buttonPanel.add(button);
				
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("choosertitle");
				int returnVal = chooser.showOpenDialog(null);
					if(returnVal == JFileChooser.APPROVE_OPTION){
					chooser.getCurrentDirectory();
					file = chooser.getSelectedFile();
					}
					try{
						img=ImageIO.read(file);
						ImageIcon icon = new ImageIcon(img);
						button.setIcon(icon);
						//cleanning off the pannel to allow for the new button to show up
						buttonPanel.revalidate();
						buttonPanel.repaint();	
					}
					catch(IOException ioe2){
						ioe2.printStackTrace();
					}
				buttons.add(button);
		}
		
		//Method creates a new musician object from a user input
		//And uses another method to append musician to an existing file
		public void createMusician(){
				
				String name = JOptionPane.showInputDialog("Enter musician name");
				String instrument = JOptionPane.showInputDialog("Enter instrument");
				if(instrument.length() == 0){
					instrument = null;
				}
				String genre = JOptionPane.showInputDialog("Enter genre");
				String band = JOptionPane.showInputDialog("Enter band name");
				if(band.length() == 0){
					band = null;
				}
			
				StringJoiner joiner = new StringJoiner(",");
				joiner.add(name).add(instrument).add(genre).add(band);
				String joinedString = joiner.toString(); 		
				Musician tempObj2 = new Musician(name, instrument, genre, band);
				list.add(tempObj2);	
				//This method appends musician object to a file 
				this.appendToFile(joinedString);
		
		}
		//Takes string name of a musician as a parameter value
		//Reads the file with musicians line after line looking for the match to the paramether value 
		//Pops each read line onto temporary file except the one containing parameter value
		//Replaces the data in original file with data from temporary
		public void removeDataFromFile(String mName){
			
			String currentLine;
			String toRemove = mName;
			final Path inputFile = Paths.get("musicians.txt").toAbsolutePath();
			final Path tempFile = inputFile.resolveSibling("musicians.txt.new");
	
			try{
				final BufferedReader br = Files.newBufferedReader(inputFile, StandardCharsets.UTF_8);
				final BufferedWriter wr = Files.newBufferedWriter(tempFile, StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW);
				while ((currentLine = br.readLine()) != null){
				
					String trimmedLine = currentLine.trim();
					if(trimmedLine.contains(toRemove))
						continue;
					try{
						wr.write(currentLine);
						wr.newLine();
					}
					catch(IOException e){
						e.printStackTrace();
					}
				}
				br.close();
				wr.close();
				Files.move(tempFile, inputFile, StandardCopyOption.REPLACE_EXISTING);
			}
			catch(FileNotFoundException e2){
				e2.printStackTrace();
			}
			catch(IOException e1){
				e1.printStackTrace();
			}
		}
		
		//Method that creates responses to the actionlistener when vrious buttons are pressed
		public void actionPerformed(ActionEvent e){
			
			//Button "add musician" when pressed runs two methods one creating a new button and another new musician 
			if(e.getSource() == extr){
				this.createButton();
				this.createMusician();
			}
			
			//Button "delete musician" when pressed deletes musician from a file with a corresponding button
			if(e.getSource() == extr1){
				//Asks the user to input the name of the musician to be deleted
				String mName = JOptionPane.showInputDialog("Enter name of musician you want to delete");
				
				//Reads data from file 
				this.getDataFromFile();
				//Loops through the arraylist with musicians
				for(int j=0; j<list.size(); j++){
					//Conversts name of the musician to string object
					String someName = list.get(j).getName().toString();
					//Compares user input with the musician name and if maches, runs removeDataFromFile method deleting musician object and a corresponding 
					//Button from a buttonPanel
					if(someName.equals(mName)){
					
						this.removeDataFromFile(mName);
						buttonPanel.remove(buttons.get(j));
						buttons.remove(j);
						buttonPanel.revalidate();
						buttonPanel.repaint();
					}
				}
			}
			//Loops through the button arraylist checking for which button has been pressed 
			for(int i=0; i<buttons.size(); i++){
				
				if(e.getSource() == buttons.get(i)){
					
					this.getDataFromFile();
					//Looks for the corresponding musician in the musician arraylist and displays the musician
					//Running corresponding method from the Musician class
					if(list.get(i).getInstrument() == null && list.get(i).getBandName() == null){
								JOptionPane.showMessageDialog(null, list.get(i).showMusician2());
					}	
			
					else if(list.get(i).getInstrument() == null){
						JOptionPane.showMessageDialog(null, list.get(i).showMusician3());
					}
					else{
						JOptionPane.showMessageDialog(null, list.get(i).showMusician1());
					}
				}
			}
		}
		//Method takes string (which represents musician object) as a parameter value
		//And write is to an existing file with musicians 
		public void appendToFile(String s){
				
			try{
				BufferedWriter bw = new BufferedWriter(new FileWriter("musicians.txt", true));    
				bw.write(s);
				bw.newLine();
				bw.flush();
				bw.close(); 
				}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
	
	//Main method which creates new instance of a JFrame initializing GUI 
	public static void main(String[] args) {
	
        TryGrids3z frame = new TryGrids3z();
        frame.pack();
        frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(470,600);
    }
}