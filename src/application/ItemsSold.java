package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Products;

public class ItemsSold{

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		List<Products> list = new ArrayList<>();
		
		System.out.print("Enter the full path of the source file and its extension: ");
		String strFileSrc = sc.nextLine();
		
		File fileSrc = new File(strFileSrc);
		String strFolderSrc = fileSrc.getParent();
		
		System.out.print("Enter the name of the folder to be created: ");
		String newFolder = sc.nextLine();
		
		boolean mkFolder = new File(strFolderSrc + "/" + newFolder).mkdir();
		if(mkFolder) {
			System.out.println("Folder created successfully.");
		}else {
			System.out.println("The folder was not created.");
		}
		
		System.out.print("Enter the output file name and its extension: ");
		String newFile = sc.nextLine();
		
		String strDestinationFile = strFolderSrc + "/" + newFolder + "/" + newFile;

		try(BufferedReader readFile = new BufferedReader(new FileReader(strFileSrc))){
			String itemCsv = readFile.readLine();
			
			while(itemCsv != null) {
				String[] field = itemCsv.split(",");
				String name = field[0];
				double value = Double.parseDouble(field[1]);
				int quantity = Integer.parseInt(field[2]);
				list.add(new Products(name, value, quantity));
				itemCsv = readFile.readLine();
			}
			
			try(BufferedWriter writeFile = new BufferedWriter(new FileWriter(strDestinationFile))){
				
				for(Products item : list) {
					writeFile.write(item.getName() + "," + String.format("%.2f", item.totalValue()));
					writeFile.newLine();
				}
				System.out.println(strDestinationFile + ": Created!");
			}
			catch(IOException e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
		catch(IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		finally {
			sc.close();
		}
	}

}