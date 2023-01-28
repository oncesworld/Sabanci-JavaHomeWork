package edu.sabanciuniv.it524.screens;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

import edu.sabanciuniv.it524.parsers.CSVParser;
import edu.sabanciuniv.it524.parsers.TSVParser;
import edu.sabanciuniv.it524.parsers.TextFileParser;

public class MainScreen extends JFrame {
	
	private JButton fileReadButton;
	private JTextArea fileContentArea;
	private JTextField fileNameField;
	private JButton fileSaveButton;
	private JButton clearAllButton;
	private boolean writeChecker = false; // To tell the user that doc is saved
	
	public MainScreen()
	{
		this.setTitle("IT524 Homework 2");
		this.getContentPane().setLayout(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(600, 600);
		this.setLocationRelativeTo(null);
		
		//Buton ekliyoruz. Read File butonu
		this.fileReadButton = new  JButton("Read File ...");
		this.fileReadButton.setLocation(450, 20);
		this.fileReadButton.setSize(150, 25);
		this.getContentPane().add(fileReadButton);
		
		//Seçilen dosya isminin görüneceği textfield ekliyoruz Read file solunda
		this.fileNameField = new JTextField();
		this.fileNameField.setLocation(20, 20);
		this.fileNameField.setSize(400, 25);
		this.getContentPane().add(fileNameField);
		
		
		//Dosyanın içeriğinin görüntüleneceği textArea yı ekliyoruz. Beyaz alan
		this.fileContentArea = new JTextArea();
		this.fileContentArea.setLocation(23, 60);
		this.fileContentArea.setSize(550, 450);
		this.getContentPane().add(fileContentArea);
		
		//Bulunan herşeyi temizleme butonunu ekliyorum
		this.clearAllButton = new JButton("Clear All");
		this.clearAllButton.setLocation(350,520);
		this.clearAllButton.setSize(100, 25);
		this.getContentPane().add(clearAllButton);
		
		
		//Buton a kliklendiğinde JFileChooser (Dosya seçme ekranı) açıyoruz
		//Bunun için button un mouseClicked eventini override etmemiz gerekiyor
		this.fileReadButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				 JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			        jfc.setPreferredSize(new Dimension(400, 400));
			        int returnValue = jfc.showOpenDialog(null);
			        if (returnValue == JFileChooser.APPROVE_OPTION) {
			            File selectedFile = jfc.getSelectedFile();
			            fileNameField.setText(selectedFile.getAbsolutePath());
			            
			            String stringFilePath = selectedFile.getAbsolutePath(); //To find file path as string
			            String substr = stringFilePath.substring(stringFilePath.length() - 3); // To get type of doc.
			            
			            if(substr.equals("csv")) {
			            	TextFileParser parser = new CSVParser();
			            	fileContentArea.setText(parser.readFile(stringFilePath)); // To show in content area white area
			            	writeChecker = true;
			            }
			            
			            else if(substr.equals("tsv")) {
			            	TextFileParser parser = new TSVParser();
			            	fileContentArea.setText(parser.readFile(stringFilePath)); // To show in content area white area
			            	writeChecker = true;
			            	
			            }
			            else {
				           	fileContentArea.setText("This app is just for tsv and csv docs");
				           	writeChecker = false; // Önceden doğru uzantı seçilmişse sonra değiştirilmişse kaydetmemeli
				           
			            }
	         
			        }
			}
		});
		
		
		//Dosyayı kaydetme butonunu ekliyoruz
		this.fileSaveButton = new  JButton("Save File");
		this.fileSaveButton.setLocation(160, 520);
		this.fileSaveButton.setSize(100, 25);
		this.getContentPane().add(fileSaveButton);
		
		this.fileSaveButton.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				 {
		            File file1 = new File("output.txt");
		            try {
			            if(writeChecker == true) {  					// If doc successfully wrote to output.txt then this block will run
							FileWriter fileWriter = new FileWriter(file1);
							fileWriter.write(fileContentArea.getText()); // To write content area to txt.
							fileWriter.close(); // Need to use pipe :)
			            	fileContentArea.setText("İçerik output.txt dosyasına başarıyla kaydedilmiştir");
			            }
			            else {
			            	fileContentArea.setText("Lütfen csv veya tsv uzantılı bir dosya seçiniz");
			            }
 							
					} catch (IOException e1) {
						System.out.println("Yetki yok");
					}
		            writeChecker = false;  // Saveledikten sonra false olmalı ki usulsüz birşey kaydetmesin

		          
				}
			}
		});
		
		
		this.clearAllButton.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				fileNameField.setText("");
				fileContentArea.setText("");
				writeChecker = false;  // Ekran temizlendiğinde dosyanın kaydedilmemesi gerekiyor
				
			}
		});
		
		
	}
}
