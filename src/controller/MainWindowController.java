package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;



public class MainWindowController {

	private Main main;   
	private Stage primaryStage;   
	
	@FXML private Label b1Lab, b2Lab, b3Lab;
	@FXML private Label licz1Lab, licz2Lab, licz3Lab;
	@FXML private TextArea oryginText, zakodText;
	
	
	private ArrayList<Integer> beben1 = new ArrayList();
	private ArrayList<Integer> beben2 = new ArrayList();
	private ArrayList<Integer> beben3 = new ArrayList();
	private ArrayList<Integer> bebenOdw = new ArrayList();

	private int licznik1, licznik2,licznik3;	//liczniki przekrecen bebnow
	
	public void setMain(Main main, Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		this.main = main;
		
		primaryStage.setTitle("PROJEKT PRZEJŒCIOWY-- Agnieszka Œwiderska");

		//inicjujemy listy
		for(int i=0; i<256; i++){
			beben1.add(0);
			beben2.add(0);
			beben3.add(0);
			//if(i<128)
				bebenOdw.add(0);
		}
					
		wczytajDaneKodujace();
		//System.out.println("beben1 styk 12 "+beben1.get(12)+" beben2 styk 12 "+beben2.get(12)+" beben3 styk 12 "+beben3.get(12));
		
		wczytajUstawienieBebnow();
		//System.out.println("beben1 styk 12 "+beben1.get(12)+" beben2 styk 12 "+beben2.get(12)+" beben3 styk 12 "+beben3.get(12));
	}
	
	
	public void wczytajDaneKodujace(){
		
		Scanner in=null;
		String linia;

		try{
				
			//in=new Scanner(Paths.get("model/rotors.txt"));
			in=new Scanner(Paths.get("D:\\Java\\workspace\\ProjektPrzejsciowy\\src\\model\\rotors.txt"));
	
			while(in.hasNextLine())
			{
				//wczytujemy dane z ignorowaniem pustych linni
				linia = in.nextLine();  
				while(linia.equals(""))
					linia = in.nextLine();	
							
				for (int i=0; i<256; i++)
					daneDoBebna(in.nextLine(),beben1);
				
				linia = in.nextLine();
				while(linia.equals(""))
						linia = in.nextLine();	

				for (int i=0; i<256; i++)
					daneDoBebna(in.nextLine(),beben2);
				
				linia = in.nextLine();
				while(linia.equals(""))
						linia = in.nextLine();	

				for (int i=0; i<256; i++)
					daneDoBebna(in.nextLine(),beben3);
				
				linia = in.nextLine();
				while(linia.equals(""))
						linia = in.nextLine();	

				for (int i=0; i<128; i++)
					daneDoBebnaOdw(in.nextLine(),bebenOdw);

				break;
			}
			
		}
		catch (IOException e)
		
		{
			e.printStackTrace();
		}
		finally
		{
			if (in!=null)
			{
				in.close();
			}
		}	
	}
	
	void daneDoBebna(String linia, ArrayList<Integer> beben){
				
		int i=linia.indexOf("-",0);
		int index = (int) Double.parseDouble(linia.substring(0, i));
		int wartosc = (int) Double.parseDouble(linia.substring(i+1, linia.length()));
		
		//System.out.println("index "+index+" wartosc "+wartosc);
		beben.remove(index);
		beben.add(index, wartosc);
	}
	
	void daneDoBebnaOdw(String linia, ArrayList<Integer> beben){
		
		int i=linia.indexOf("-",0);
		int index = (int) Double.parseDouble(linia.substring(0, i));
		int wartosc = (int) Double.parseDouble(linia.substring(i+1, linia.length()));
		
		//System.out.println("index "+index+" wartosc "+wartosc);
		beben.remove(index);
		beben.add(index, wartosc);
		beben.remove(wartosc);
		beben.add(wartosc, index);
	}
	
	public void wczytajUstawienieBebnow(){
		
		Scanner in=null;
		String linia;
		
		try{
				
			//in=new Scanner(Paths.get("model/init.txt"));
			in=new Scanner(Paths.get("D:\\Java\\workspace\\ProjektPrzejsciowy\\src\\model\\init.txt"));

			
			if(in.hasNextLine())
			{
				linia = in.nextLine();
				licznik1 = (int) Double.parseDouble(linia);
				b1Lab.setText(linia);
				licz1Lab.setText(""+licznik1);
				przekrecBeben(licznik1,beben1);

				if(in.hasNextLine()){
					linia = in.nextLine();
					licznik2 = (int) Double.parseDouble(linia);
					b2Lab.setText(linia);
					licz2Lab.setText(""+licznik2);
					przekrecBeben(licznik2,beben2);

					if(in.hasNextLine()){
						linia = in.nextLine();
						licznik3 = (int) Double.parseDouble(linia);
						b3Lab.setText(linia);
						licz3Lab.setText(""+licznik3);
						przekrecBeben(licznik3,beben3);
					}
					else
						System.out.println("Bark linii 3");
				}
				else
					System.out.println("Brak linii 2");
			}
			else
				System.out.println("Plik pusty");
			
		}
		catch (IOException e)
		
		{
			e.printStackTrace();
		}
		finally
		{
			if (in!=null)
			{
				in.close();
			}
		}	
	}
	
	
	public void przekrecBeben(int ustawienie, ArrayList<Integer> beben) {
		
		int wartosc;
		for(int i=0; i<ustawienie; i++){
			wartosc = beben.get(0);
			beben.remove(0);
			beben.add(wartosc);
		}
	
	}


	@FXML
	public void wczytajButton(){
		
		Scanner in=null;
		//errorLabel.setText("");
		String lineEnd = System.getProperty("line.separator"); //konieczne dla Windowsa pod UNIX wystarczy dodac na koncu \n

		try{
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Otwórz z pliku");
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Pliki tekstowe","*.txt"));
			File selectedFile = fileChooser.showOpenDialog(primaryStage);
			
			if(selectedFile != null)
			{
				in = new Scanner(selectedFile);
				oryginText.clear();

				while(in.hasNextLine())
				{
					oryginText.appendText(in.nextLine());
					oryginText.appendText(lineEnd);   //konieczne dla windows			
				}
			}					
		}
		catch (IOException e)		
		{
			e.printStackTrace();
		}
		finally
		{
			if (in!=null)
			{
				in.close();
			}
		}
	}

	@FXML
	public void zakodujButton(){
		String text = oryginText.getText();
		int stykWe;
		int startAscii = 10;
		
		zakodText.clear();
		
		for(int i=0; i<text.length(); i++){
			stykWe = text.charAt(i)-startAscii;
			
			/*int beben1Wy = beben1.get(stykWe);
			int beben2Wy = beben2.get(beben1Wy);
			int beben3Wy = beben3.get(beben2Wy);
			int bebenOdwWy = bebenOdw.get(beben3Wy);
			//powrót
			int beben3Powr = beben3.indexOf(bebenOdwWy);
			int beben2Powr = beben2.indexOf(beben3Powr);
			int beben1Powr = beben1.indexOf(beben2Powr);
			
			int stykWy = beben1Powr;*/
			
			int bebenOdwWy = bebenOdw.get(beben3.get(beben2.get(beben1.get(stykWe))));
			int stykWy = beben1.indexOf(beben2.indexOf(beben3.indexOf(bebenOdwWy)));
			
			char znak = (char)(stykWy + startAscii);
			
			zakodText.appendText(""+znak);
			przekrecamy();		
		}
	}
	
	public void przekrecamy(){
		
		przekrecBeben(1,beben1);
		if(++licznik1>255){
			licznik1=0;
			przekrecBeben(1,beben2);
			if(++licznik2>255){
				licznik2=0;
				przekrecBeben(1,beben3);
				if(++licznik3>255)
					licznik3=0;
				licz3Lab.setText(""+licznik3);
			}
			licz2Lab.setText(""+licznik2);
		}
		licz1Lab.setText(""+licznik1);
	}
	
}
