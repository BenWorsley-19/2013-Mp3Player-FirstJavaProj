/*	Author: Ben Worsley
	Purpose: Music player which plays Mp3 files
	Date: 08/12/2013
*/	

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.io.FileInputStream;
import javazoom.jl.player.advanced.*;

//This is the class which plays the music in a separate thread so to stop the program from freezing until the song finishes
class MyPlayer extends Thread //I inherit the built in class Thread. Where pla is called in the action listeners, it calls this class.
{
	AdvancedPlayer player; //I declare an advance player
	int StartF; // I declare this variable so that I can store the value of the startFrame variable and use it in the run method
	public MyPlayer(InputStream fis, int startFrame) //this is where the input stream and the startFrame is passed into
	{
		try
		{
			player = new AdvancedPlayer(fis); //I create the advanced player so that the FileInputStream can be played
			StartF = startFrame; //this is where I store the value of startFrame in StartF
			this.start(); //this makes the run method start
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
	public void run()
	{
		try
		{
			player.play(StartF, Integer.MAX_VALUE); //this is the piece of code that starts the music actually playing. The parameters tell the player where to start and end from
			System.out.println("hello");
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}	
}

class Mp3Player6 implements ActionListener
{
	//Below I declare reference variables for objects
	ImageIcon wave, play, stop, forward, rewind;
	JFrame f_main,f_amount,f_lastplayed,f_help;
	JPanel p_m_top, p_m_top_search, p_m_top_mostlast, p_m_top_help, p_m_middle, p_m_middle_song, p_m_middle_wave, p_m_middle_but, p_m_bottom, p_a_top, p_a_bottom, p_lp_top, p_lp_bottom, p_h_top, p_h_bottom;
	myPanel background1;
	myPanel2 background2;
	myPanel3 background3;
	myPanel4 background4;
	myPanel6 background6;
	myPanel7 background7;
	myPanel8 background8;
	myPanel9 background9;
	myPanel10 background10;
	JButton b_top_search, b_top_amount, b_top_last, b_top_help, b_middle_play, b_middle_stop, b_middle_forward, b_middle_rewind, b_back, b_back2, b_back3;
	JLabel l_songname, l_file, l_size, l_type, l_restrictions, l_location, l_amount, l_lastsongd, l_lastsong, l_help, l_wave; 
	JFileChooser fc_search;
	File file;
	FileInputStream fis;
	MyPlayer pla;
	int framePaused = 0; //This variable determines which frame the player starts from when paused, it increases or decreases to fast forward or rewinds songs
	String beenpaused = "";// This variable is used to determine whether the play/ pause button was paused or played, it is given a different string if pause is pressed
	int amountplayed = 0;// This variable's value increases when a song is played
	
	public Mp3Player6()
	{
		//images
		play = new ImageIcon("Play-Pause.png");
		stop = new ImageIcon("stop.png");
		forward = new ImageIcon("fastforward.png");
		rewind = new ImageIcon("rewind.png");
		wave = new ImageIcon("soundwave.jpg");
		
		//create the objects of the components declared above
		f_main = new JFrame("Main");
		f_amount = new JFrame("Most Played");
		f_lastplayed = new JFrame("Last Played");
		f_help = new JFrame("Help");
		//panels for main frame
		p_m_top = new JPanel(new BorderLayout());
			p_m_top.setOpaque(false);
		p_m_middle = new JPanel(new BorderLayout());
			p_m_middle.setOpaque(false);
		p_m_bottom = new JPanel(new GridLayout(0,1));
			p_m_bottom.setOpaque(false);
			p_m_bottom.setPreferredSize(new Dimension(400,150));
		p_m_top_search = new JPanel();
			p_m_top_search.setOpaque(false);
		p_m_top_mostlast = new JPanel();
			p_m_top_mostlast.setOpaque(false);
		p_m_top_help = new JPanel();
			p_m_top_help.setOpaque(false);
		p_m_middle_song = new JPanel();
			p_m_middle_song.setOpaque(false);
		p_m_middle_wave = new JPanel();
			p_m_middle_wave.setOpaque(false);
		p_m_middle_but = new JPanel();
			p_m_middle_but.setOpaque(false);
		//panels for most played frame
		p_a_top = new JPanel();
			p_a_top.setOpaque(false);
		p_a_bottom	 = new JPanel();
			p_a_bottom.setOpaque(false);
		//panels for last song played	
		p_lp_top = new JPanel(new BorderLayout());
			p_lp_top.setOpaque(false);
		p_lp_bottom = new JPanel();
			p_lp_bottom.setOpaque(false);
		//panels for help
		p_h_top = new JPanel();
			p_h_top.setOpaque(false);
		p_h_bottom = new JPanel();
			p_h_bottom.setOpaque(false);
		//background images	
		background1 = new myPanel();
			background1.setPreferredSize(new Dimension(600,210));
		background2 = new myPanel2();
			background2.setPreferredSize(new Dimension(600,310));
		background3 = new myPanel3();
			background3.setPreferredSize(new Dimension(600,180));
		background4 = new myPanel4();
		background6 = new myPanel6();
		background7 = new myPanel7();
		background8 = new myPanel8();
		background9 = new myPanel9();
		background10 = new myPanel10();
		//Buttons
		b_top_search = new JButton("Search Songs");
			b_top_search.setPreferredSize(new Dimension(120,40));
			b_top_search.setBackground(new Color(255,0,51));
			b_top_search.setForeground(new Color(255,255,255)); //these methods set the colour and size of the buttons
		b_top_amount = new JButton("Amount Played");
			b_top_amount.setPreferredSize(new Dimension(120,40));
			b_top_amount.setBackground(new Color(255,0,51));
			b_top_amount.setForeground(new Color(255,255,255));
		b_top_last = new JButton("Last Played");
			b_top_last.setPreferredSize(new Dimension(120,40));
			b_top_last.setBackground(new Color(255,0,51));
			b_top_last.setForeground(new Color(255,255,255));
		b_top_help = new JButton("Help");
			b_top_help.setPreferredSize(new Dimension(120,40));
			b_top_help.setBackground(new Color(255,0,51));
			b_top_help.setForeground(new Color(255,255,255));
		b_middle_play = new JButton(play);
			b_middle_play.setBackground(new Color(255,0,51));
		b_middle_stop = new JButton(stop);		
			b_middle_stop.setBackground(new Color(255,0,51));
		b_middle_forward = new JButton(forward);
			b_middle_forward.setBackground(new Color(255,0,51));
		b_middle_rewind = new JButton(rewind);	
			b_middle_rewind.setBackground(new Color(255,0,51));
		b_back = new JButton("Go Back");
			b_back.setPreferredSize(new Dimension(110,40));
			b_back.setBackground(new Color(255,0,51));
			b_back.setForeground(new Color(255,255,255));
		b_back2 = new JButton("Go Back");
			b_back2.setPreferredSize(new Dimension(110,40));
			b_back2.setBackground(new Color(255,0,51));
			b_back2.setForeground(new Color(255,255,255));
		b_back3 = new JButton("Go Back");
			b_back3.setPreferredSize(new Dimension(110,40));
			b_back3.setBackground(new Color(255,0,51));
			b_back3.setForeground(new Color(255,255,255));
		//labels
		l_songname = new JLabel("Choose a song using the Search Songs button");
			l_songname.setPreferredSize(new Dimension(500,30));
			l_songname.setOpaque(true);
			l_songname.setBackground(new Color(0,0,0));
			l_songname.setForeground(new Color(255,255,255));
			l_songname.setHorizontalAlignment( SwingConstants.CENTER );
		l_file = new JLabel("Your file information will be displayed here");
			l_file.setPreferredSize(new Dimension(100,30));
			l_file.setOpaque(true);
			l_file.setBackground(new Color(255,0,51));
			l_file.setForeground(new Color(255,255,255));
			l_file.setHorizontalAlignment( SwingConstants.CENTER); //this centres the text
		l_size = new JLabel();
			l_size.setPreferredSize(new Dimension(100,30));
			l_size.setOpaque(true);
			l_size.setBackground(new Color(255,0,51));
			l_size.setForeground(new Color(255,255,255));
			l_size.setHorizontalAlignment( SwingConstants.CENTER);
		l_type = new JLabel();
			l_type.setPreferredSize(new Dimension(100,30));
			l_type.setOpaque(true);
			l_type.setBackground(new Color(255,0,51));
			l_type.setForeground(new Color(255,255,255));
			l_type.setHorizontalAlignment( SwingConstants.CENTER);
		l_restrictions = new JLabel();
			l_restrictions.setPreferredSize(new Dimension(100,30));
			l_restrictions.setOpaque(true);
			l_restrictions.setBackground(new Color(255,0,51));
			l_restrictions.setForeground(new Color(255,255,255));
			l_restrictions.setHorizontalAlignment( SwingConstants.CENTER);
		l_location = new JLabel();
			l_location.setPreferredSize(new Dimension(100,30));
			l_location.setOpaque(true);
			l_location.setBackground(new Color(255,0,51));
			l_location.setForeground(new Color(255,255,255));
			l_location.setHorizontalAlignment( SwingConstants.CENTER );
		l_amount = new JLabel("You have not played any songs yet");
			l_amount.setPreferredSize(new Dimension(300,30));
			l_amount.setOpaque(true);
			l_amount.setBackground(new Color(0,0,0));
			l_amount.setForeground(new Color(255,255,255));
			l_amount.setHorizontalAlignment( SwingConstants.CENTER );
		l_lastsongd = new JLabel("The song you last played is:");
			l_lastsongd.setPreferredSize(new Dimension(400,30));
			l_lastsongd.setOpaque(true);
			l_lastsongd.setBackground(new Color(0,0,0));
			l_lastsongd.setForeground(new Color(255,255,255));
			l_lastsongd.setHorizontalAlignment( SwingConstants.CENTER );
		l_lastsong = new JLabel("You have not yet played a song");
			l_lastsong.setPreferredSize(new Dimension(400,30));
			l_lastsong.setOpaque(true);
			l_lastsong.setBackground(new Color(255,0,51));
			l_lastsong.setForeground(new Color(255,255,255));
			l_lastsong.setHorizontalAlignment( SwingConstants.CENTER );
		l_help = new JLabel();
			l_help.setPreferredSize(new Dimension(450,210));
			l_help.setOpaque(true);
			l_help.setBackground(new Color(255,0,51));
			l_help.setForeground(new Color(255,255,255));
			String instructions = "<p>Search Songs button - Use this to find the song you want to play, the song will begin playing when you open it.</p><p>Amount Played button- This will tell you how many songs you have played.</p><p>Last Played Button - this will tell you which song you last played.</p><p>Play/Pause button - this will pause the song and play it from where you paused it.</p><p>Stop button - this will stop the song and the stream of the song, meaning you need to choose another one.</p><p>Fast Forward button - it is best to pause the song, then click this button, and then press play again, but you can also fast forward and press pause, then play after you have fast forwarded.</p><p>Rewind button- the same applies to this button as the previous</p>";
			l_help.setText("<html>"+ instructions +"</html>"); //I have used html elements to create paragraphs for better readability
		l_wave = new JLabel(wave);
			
		//add components to individual panels
		p_m_top_search.add(b_top_search);
		p_m_top_mostlast.add(b_top_amount);
		p_m_top_mostlast.add(b_top_last);
		p_m_top_help.add(b_top_help);
		
		p_m_middle_song.add(l_songname);
		p_m_middle_wave.add(l_wave);
		p_m_middle_but.add(b_middle_play);
		p_m_middle_but.add(b_middle_stop);
		p_m_middle_but.add(b_middle_rewind);
		p_m_middle_but.add(b_middle_forward);
		p_m_bottom.add(l_file);
		p_m_bottom.add(l_size);
		p_m_bottom.add(l_type);
		p_m_bottom.add(l_restrictions);
		p_m_bottom.add(l_location);
		
		p_a_top.add(l_amount);
		p_a_bottom.add(b_back);
		
		p_lp_top.add(l_lastsongd, BorderLayout.CENTER);
		p_lp_top.add(l_lastsong, BorderLayout.SOUTH);
		p_lp_bottom.add(b_back2);
		
		p_h_top.add(l_help);
		p_h_bottom.add(b_back3);
		
		//the panels are set into different panels themselves. I add them here
		p_m_top.add(p_m_top_search,BorderLayout.NORTH);
		p_m_top.add(p_m_top_mostlast,BorderLayout.CENTER);
		p_m_top.add(p_m_top_help,BorderLayout.SOUTH);
		
		p_m_middle.add(p_m_middle_song,BorderLayout.NORTH);
		p_m_middle.add(p_m_middle_wave,BorderLayout.CENTER);
		p_m_middle.add(p_m_middle_but,BorderLayout.SOUTH);
		
		//I add the main panels to the panels with background images so to add background images to the panels
		background1.add(p_m_top);
		background2.add(p_m_middle);
		background3.add(p_m_bottom);
		
		background4.add(p_a_top);
		background6.add(p_a_bottom);
		
		background7.add(p_lp_top);
		background8.add(p_lp_bottom);
		
		background9.add(p_h_top);
		background10.add(p_h_bottom);
		
		//I add the background images which contain the main panels and there individual panels to the frame
		f_main.add(background1, BorderLayout.NORTH);
		f_main.add(background2, BorderLayout.CENTER);
		f_main.add(background3, BorderLayout.SOUTH);
		
		f_amount.add(background4, BorderLayout.CENTER);
		f_amount.add(background6, BorderLayout.SOUTH);
		
		f_lastplayed.add(background7,BorderLayout.CENTER);
		f_lastplayed.add(background8, BorderLayout.SOUTH);
		
		f_help.add(background9, BorderLayout.CENTER);
		f_help.add(background10, BorderLayout.SOUTH);
		
		//I add the action listeners
		b_top_search.addActionListener(this);
		b_middle_play.addActionListener(this);
		b_middle_stop.addActionListener(this);
		b_middle_forward.addActionListener(this);
		b_middle_rewind.addActionListener(this);
		b_top_help.addActionListener(this);
		b_top_amount.addActionListener(this);
		b_top_last.addActionListener(this);
		b_back.addActionListener(this);
		b_back2.addActionListener(this);
		b_back3.addActionListener(this);
		
		//I set features of the frames
		f_amount.setSize(600,150);
		f_lastplayed.setSize(600,200);
		f_help.setSize(600,350);
		f_main.setSize(600,700);
		f_main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //This is a short cut to close the program completely
		f_main.setVisible(true);
	}	
	
	//Here I handle the events of the buttons
	public void actionPerformed(ActionEvent x)
	{
		JButton eb;
		eb = (JButton)x.getSource(); //I create a JButton which holds the source of the event. This way I can compare that, that source is the same as whatever button was pressed so to tell the computer that, that particular button has been pressed
		
		if(eb == b_top_search) //this button provides a lot of functionality for the program
		{
			framePaused = 1; 
			fc_search = new JFileChooser(); 
			fc_search.setCurrentDirectory(new File("c:\\Users\\April19\\Music"));
			FileNameExtensionFilter mp3only = new FileNameExtensionFilter("Mp3 Files","mp3");
			fc_search.setFileFilter(mp3only); 
			int returnVal = fc_search.showOpenDialog(b_top_search); //here and above I create a JFileChooser that will automatically open in the music folder, only show mp3 files, and will return whether or not a file was chosen and opened, or cancelled.
			file = fc_search.getSelectedFile(); //this stores the file that was chosen into the variable file so I can use the variable to play whatever value it holds later on
			if(returnVal == JFileChooser.APPROVE_OPTION) //if a file is selected...
			{
				amountplayed++; //this means every time a song is selected and played this variables value will increase by one
				l_amount.setText("You have played " + amountplayed + " song(s)"); // the value in the variable will change when a song is selected
				l_songname.setText(file.getName());
				l_lastsong.setText(l_songname.getText());
				l_file.setText("File name: " + file.getName());
				l_size.setText("File size: " + file.length() + " Bytes");
				l_type.setText("File type: mp3"); 
				l_location.setText("Location: " + file.getPath());
				l_restrictions.setText("Restrictions: None"); // The code above gets information about the file selected and displays it in the JLabels at the bottom of the main frame
				
				//FileInputStream and player
				try{
					fis = new FileInputStream(file.getPath()); //This creates an input stream for the file selected. You need a player to make it play. The player is in a separate thread.
					
					if(pla==null) //This if statement determines whether a song is already playing. If a song is not playing then it sends a fileInputstream for the song selected, and also sends which frame it should start at (which we earlier declared was 1(meaning the start of the song)).
					{
						System.out.println("No Object yet");
						pla = new MyPlayer(fis,framePaused);
					}
					else //if a song is already playing, that song is stopped and a new fileInputStream and starting point is declared.
					{
						pla.player.close();
						System.out.println("exectued else");
						pla = new MyPlayer(fis,framePaused);
					}					
				}
				catch(Exception e){
					System.out.println(e);
				}
			}
			else if(returnVal == JFileChooser.CANCEL_OPTION) //if a song is not selected and is cancelled, the song is stopped playing and all labels are set to null apart from two where instructions are given
			{
				System.out.println("cancelled");
				l_lastsong.setText(l_songname.getText());
				try
				{
					pla.player.close();
					pla = null;
					l_songname.setText("Choose a song using the Search Songs button");
					l_file.setText("Your file information will be displayed here");
					l_size.setText("");
					l_type.setText(""); 
					l_location.setText("");
					l_restrictions.setText("");			
					
					System.out.println("stoppp");
				}			
				catch(Exception e){}
			}
			else if(returnVal == JFileChooser.ERROR_OPTION)
			{
				System.out.println("error occurred");
			}
		}
		if(eb == b_middle_play) //this is the play/pause button. It will go to pause first because a song will be playing from when you select a song
		{
			if(fis == null) //if a song is not playing a message will appear telling the user to choose a song
			{
				JOptionPane.showMessageDialog(f_main, "You need to choose a song to play");
			}
			//below is play
			try
			{
				if(pla.player != null && beenpaused.equals("yes"))
				{
					try
					{
						System.out.println(".........................the song you are playing is: " + file.getName());
						System.out.println("beenpaused == yes and song should play from pause");
						System.out.println("were on frame: " + framePaused);
						fis = new FileInputStream(file.getPath()); //Now when play is pressed a new FileInputStream is created and this is sent to the thread on the line below with the new starting frame stored in the int variable framePaused
						pla = new MyPlayer(fis, framePaused);
						beenpaused = "";
						System.out.println("it should be playing from where it paused");
					}
					catch(Exception e){
						System.out.println(e);
						e.printStackTrace();
					}
				}
				//below is pause
				else if(pla != null && beenpaused.equals(""))
				{
					System.out.println("pause and null");
					pla.player.setPlayBackListener(new PlaybackListener()
					{
						public void playbackFinished(PlaybackEvent evt)//this is notified when the pla.player.stop() line is run. When the stop method is called it stops the music and notifies the PlayBackListener. This then runs the method evt.getFrame() which stores what frame the song was paused on in the variable framePaused. By doing this I can send the framePaused variable to the thread so that the player knows where to start the song from.
						{
							framePaused = evt.getFrame();
							//.setFrame(int) could help solve problems
						}
					});
					try
					{
						System.out.println(framePaused);
						pla.player.stop();
						System.out.println(".........................the song you are playing is: " + file.getName());
						System.out.println(framePaused);
						beenpaused = "yes";
					}
					catch(Exception e)
					{
						System.out.println(e);
					}
				}
			}catch(Exception e){}
			System.out.println(framePaused);
		}
		
		if(eb == b_middle_stop)//this is the stop button
		{
			l_lastsong.setText(l_songname.getText()); //this sets the text of the l_lastsong JLabel to the song that was being played so that when stop is pressed and all labels are cleared, the user can check what song was just playing 
			if(fis == null)
			{
				JOptionPane.showMessageDialog(f_main, "You need to choose a song to play");
			}
			try
			{				
				pla.player.close();//this stops the song
				pla = null;//this empties the player
				l_songname.setText("Choose a song using the Search Songs button");
				l_file.setText("Your file information will be displayed here");
				l_size.setText("");
				l_type.setText(""); 
				l_location.setText("");
				l_restrictions.setText("");	//the above codes set the labels texts to nothing accept from the instructions 	
				
				System.out.println("stoppp");
			}			
			catch(Exception e){}
		}
		if(eb == b_middle_forward)
		{
			if(fis == null)
			{
				JOptionPane.showMessageDialog(f_main, "You need to choose a song to play");
			}
			try
			{
				framePaused = framePaused + 100; //this adds 100 to the framePaused variable so that the frame paused variable increases and so it starts from further on
				System.out.println(framePaused);
				System.out.println("forward");
			}
			catch(NullPointerException e)
			{
				JOptionPane.showMessageDialog(f_main, "You need to choose a song to play");
			}
			catch(Exception e){}
		}
		if(eb == b_middle_rewind)
		{
			if(fis == null)
			{
				JOptionPane.showMessageDialog(f_main, "You need to choose a song to play");
			}
			try
			{
				framePaused = framePaused - 100; //this takes 100 away from the framePaused variable so that you can control that it starts from further back
				System.out.println(framePaused);
				System.out.println("rewind");
			}
			catch(NullPointerException e)
			{
				JOptionPane.showMessageDialog(f_main, "You need to choose a song to play");
			}
			catch(Exception e){}
		}
		//the buttons below open or closes frames
		if(eb == b_top_help)
		{
			f_help.setVisible(true);
		}
		if(eb == b_top_amount)
		{
			f_amount.setVisible(true);
		}
		if(eb == b_top_last)
		{
			f_lastplayed.setVisible(true);
		}
		if(eb == b_back)
		{
			f_amount.setVisible(false);
		}
		if(eb == b_back2)
		{
			f_lastplayed.setVisible(false);
		}
		if(eb == b_back3)
		{
			f_help.setVisible(false);
		}
	}
	
	// These are panels which contain images that will be in the background
	class myPanel extends JPanel//here I inherit the built in class JPanel. I need to create a background image for each panel, even if it is the same image
	{
		Image bg = new ImageIcon("head2.jpg").getImage(); //This is where the background image is chosen
		public void paintComponent(Graphics g)
		{
			g.drawImage(bg, 0, 0, getWidth(), getHeight(), this); //Using this method allows the images to change size when the frame is resized
		}	
	}
	class myPanel2 extends JPanel
	{
		Image bg = new ImageIcon("black.jpg").getImage();
		public void paintComponent(Graphics g)
		{
			g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
		}	
	}
	class myPanel3 extends JPanel 
	{
		Image bg = new ImageIcon("black.jpg").getImage();
		public void paintComponent(Graphics g)
		{
			g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
		}	
	}
	class myPanel4 extends JPanel
	{
		Image bg = new ImageIcon("black.jpg").getImage();
		public void paintComponent(Graphics g)
		{
			g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
		}	
	}
	class myPanel6 extends JPanel
	{
		Image bg = new ImageIcon("black.jpg").getImage();
		public void paintComponent(Graphics g)
		{
			g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
		}	
	}
	class myPanel7 extends JPanel
	{
		Image bg = new ImageIcon("black.jpg").getImage();
		public void paintComponent(Graphics g)
		{
			g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
		}	
	}
	class myPanel8 extends JPanel
	{
		Image bg = new ImageIcon("black.jpg").getImage();
		public void paintComponent(Graphics g)
		{
			g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
		}	
	}
	class myPanel9 extends JPanel
	{
		Image bg = new ImageIcon("black.jpg").getImage();
		public void paintComponent(Graphics g)
		{
			g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
		}	
	}
	class myPanel10 extends JPanel
	{
		Image bg = new ImageIcon("black.jpg").getImage();
		public void paintComponent(Graphics g)
		{
			g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
		}	
	}
	
	public static void main(String k[])
	{
		 new Mp3Player6();
	}
}