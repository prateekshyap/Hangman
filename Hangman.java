import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.awt.print.*;
import java.sql.*;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.lang.String;
import oracle.sql.*;
import java.awt.Desktop.*;
import java.net.*;
import javax.swing.table.*;

class MyFrame extends JFrame implements Runnable, ActionListener
{
	static Container c;
	static CardLayout cl;
	static JPanel main_panel, home_panel, inst_panel, game_panel, result_panel, high_score_panel;
	static JLabel backLabelHome, hangman, backLabelInst, defaultLabel, wrong1, wrong2, wrong3, wrong4, wrong5, wrong6, wrong7, resultBack, resultLabel, hintLabel, messageLabel, messageLabel2, highScoreBack, showScore;
	static Font f1, f2, f3, f4, f5, f6, f7, f8;
	static Cursor cur1;
	static Color col1, col2, col3, col4;
	Thread threadHangman;
	int x,y;
	static JButton play, exit, highScore, inst, loginAsAdmin, back, playInst, Q, W, E, R, T, Y, U, I, O, P, A, S, D, F, G, H, J, K, L, Z, X, C, V, B, N, M, next, hint, linkButton, playAgain, home, backToHome, submit;
	static JTextArea instText, wordLabel;
	static int counter=0, checker=0;
	static boolean suicide=false;
	static ImageIcon ii1, ii2, ii3, ii4, ii5, ii6, ii7, ii8, ii9, ii10, ii11, ii12, ii13;
	static Login l=null;
	static ChooseMode cm=null;
	static String wordGot, hintGot, linkGot;
	static char[] resultWord;
	static int valAET=5, valDHILNORS=10, valBCFGMPUW=15, valKVY=20, valJQXZ=25;
	static JTable highScoreTable,topTenTable;
	static Connection conn;
	static JTextField enterName;
	static boolean wonOnce=false;
	//static JScrollPane scroll;
	MyFrame()
	{
		//fonts, cursors and colours
		f1=new Font("Lucida Handwriting",Font.BOLD,80);
		f2=new Font("Lucida Handwriting",Font.BOLD,25);
		f3=new Font("Lucida Handwriting",Font.PLAIN,20);
		f4=new Font("Lucida Handwriting",Font.BOLD,45);
		f5=new Font("Lucida Handwriting",Font.BOLD,65);
		f6=new Font("Lucida Handwriting",Font.BOLD,30);
		f7=new Font("Lucida Handwriting",Font.PLAIN,16);
		f8=new Font("Lucida Handwriting",Font.BOLD,16);
		cur1=new Cursor(Cursor.HAND_CURSOR);
		col1=new Color(0,25,76);
		col2=new Color(72,0,2);
		col3=new Color(255,201,13);
		col4=new Color(0,0,0,0);

		//images
		ii1=new ImageIcon("HOME_BACK.png");
		ii2=new ImageIcon("INST_BACK.jpg");
		ii3=new ImageIcon("PLAY_BACK_1.png");
		ii4=new ImageIcon("PLAY_BACK_2.png");
		ii5=new ImageIcon("PLAY_BACK_3.png");
		ii6=new ImageIcon("PLAY_BACK_4.png");
		ii7=new ImageIcon("PLAY_BACK_5.png");
		ii8=new ImageIcon("PLAY_BACK_6.png");
		ii9=new ImageIcon("PLAY_BACK_7.png");
		ii10=new ImageIcon("PLAY_BACK_8.png");
		ii11=new ImageIcon("RESULT_BACK.png");
		ii12=new ImageIcon("HINT.png");
		Image img=ii12.getImage();
		Image newImg=img.getScaledInstance(50,50,java.awt.Image.SCALE_SMOOTH);
		ii12=new ImageIcon(newImg);
		ii13=new ImageIcon("HIGHSCORE_BACK.jpg");

		//container
		c=this.getContentPane();
		cl=new CardLayout();
		c.setLayout(null);

		//main panel holding all other panels
		main_panel=new JPanel();
		main_panel.setLayout(cl);
		main_panel.setBounds(0,0,1375,800);

		//panel which will be opened when the program is run
		home_panel=new JPanel();
		home_panel.setLayout(null);
		home_panel.setBounds(0,0,1375,735);

		//background for home_panel
		backLabelHome=new JLabel(ii1);
		backLabelHome.setBounds(-5,0,1375,735);

		//label for text hangman
		hangman=new JLabel("Hangman");
		hangman.setFont(f1);
		hangman.setForeground(col1);

		//to move the text
		threadHangman=new Thread(this);
		threadHangman.start();

		//button to enter into the play page
		play=new JButton("Play");
		play.setBounds(130,600,100,50);
		play.setFont(f2);
		play.setForeground(col2);
		play.setOpaque(false);
		play.setContentAreaFilled(false);
		play.setBorder(BorderFactory.createEmptyBorder());
		play.setCursor(cur1);
		play.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					playAction(ae);
				}
			});
		
		//button to exit the game
		exit=new JButton("Exit");
		exit.setFont(f2);
		exit.setForeground(col2);
		exit.setOpaque(false);
		exit.setContentAreaFilled(false);
		exit.setBorder(BorderFactory.createEmptyBorder());
		exit.setBounds(1100,600,100,50);
		exit.setCursor(cur1);
		exit.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					exitAction(ae);
				}
			});

		//button to check the high scores
		highScore=new JButton("High Score");
		highScore.setFont(f2);
		highScore.setForeground(col2);
		highScore.setOpaque(false);
		highScore.setContentAreaFilled(false);
		highScore.setBorder(BorderFactory.createEmptyBorder());
		highScore.setBounds(530,600,200,50);
		highScore.setCursor(cur1);
		highScore.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					highScoreAction(ae);
				}
			});

		//button for instructions
		inst=new JButton("Instructions");
		inst.setFont(f2);
		inst.setForeground(col2);
		inst.setOpaque(false);
		inst.setContentAreaFilled(false);
		inst.setBorder(BorderFactory.createEmptyBorder());
		inst.setBounds(280,600,200,50);
		inst.setCursor(cur1);
		inst.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					instAction(ae);
				}
			});

		//button for the admin
		loginAsAdmin=new JButton("Login as admin");
		loginAsAdmin.setFont(f2);
		loginAsAdmin.setForeground(col2);
		loginAsAdmin.setOpaque(false);
		loginAsAdmin.setContentAreaFilled(false);
		loginAsAdmin.setBorder(BorderFactory.createEmptyBorder());
		loginAsAdmin.setBounds(760,600,300,50);
		loginAsAdmin.setCursor(cur1);
		loginAsAdmin.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					loginAsAdminAction(ae);
				}
			});

		//instruction panel
		inst_panel=new JPanel();
		inst_panel.setLayout(null);
		inst_panel.setBounds(0,0,1375,735);

		//background for instruction panel
		backLabelInst=new JLabel(ii2);
		backLabelInst.setBounds(0,0,1375,735);

		//back button for instruction page
		back=new JButton("Back");
		back.setFont(f2);
		back.setForeground(Color.WHITE);
		back.setOpaque(false);
		back.setContentAreaFilled(false);
		back.setBorder(BorderFactory.createEmptyBorder());
		back.setBounds(1100,650,100,50);
		back.setCursor(cur1);
		back.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					backAction(ae);
				}
			});

		//play button in instruction page
		playInst=new JButton("Play");
		playInst.setBounds(1250,650,100,50);
		playInst.setFont(f2);
		playInst.setForeground(Color.WHITE);
		playInst.setOpaque(false);
		playInst.setContentAreaFilled(false);
		playInst.setBorder(BorderFactory.createEmptyBorder());
		playInst.setCursor(cur1);
		playInst.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					playInstAction(ae);
				}
			});

		//text area for instruction panel
		instText=new JTextArea("BASIC RULE\nYou will be given with a number of blank lines which forms a word and you have to guess the complete word letter by letter before the man is hanged. The man will be hanged after several incorrect attempts.\nThis game has three different modes.\nCLASSIC MODE\nIn this mode you can make multiple incorrect attempts till the man is alive. Once the man is hanged, you can't try more.\nSUICIDE MODE\nIn this mode the man hangs himself when you make a single incorrect attempt. You have to tap on the correct letters only.\nMULTIPLAYER MODE\nIn this mode two players can play at a time. Each time one player has to choose the secret word and the other has to guess it. The players can also choose secret phrases. Both can compare the scores.\nHave fun!");
		instText.setOpaque(false);
		instText.setFont(f3);
		instText.setBounds(25,25,1310,610);
		instText.setEditable(false);
		instText.setLineWrap(true);
		instText.setWrapStyleWord(true);
		instText.setRows(25);
		instText.setColumns(25);
		instText.setForeground(Color.WHITE);

		//panel on which the game will be played
		game_panel=new JPanel();
		game_panel.setLayout(null);
		game_panel.setBounds(0,0,1375,735);

		//buttons for the alphabets
		Q=new JButton("Q");
		Q.setBounds(100+550,475,50,50);
		Q.setFont(f2);
		Q.setForeground(Color.WHITE);
		Q.setOpaque(false);
		Q.setContentAreaFilled(false);
		Q.setBorder(BorderFactory.createEmptyBorder());
		Q.setCursor(cur1);
		Q.addActionListener(this);

		W=new JButton("W");
		W.setBounds(160+550,475,50,50);
		W.setFont(f2);
		W.setForeground(Color.WHITE);
		W.setOpaque(false);
		W.setContentAreaFilled(false);
		W.setBorder(BorderFactory.createEmptyBorder());
		W.setCursor(cur1);
		W.addActionListener(this);

		E=new JButton("E");
		E.setBounds(220+550,475,50,50);
		E.setFont(f2);
		E.setForeground(Color.WHITE);
		E.setOpaque(false);
		E.setContentAreaFilled(false);
		E.setBorder(BorderFactory.createEmptyBorder());
		E.setCursor(cur1);
		E.addActionListener(this);

		R=new JButton("R");
		R.setBounds(280+550,475,50,50);
		R.setFont(f2);
		R.setForeground(Color.WHITE);
		R.setOpaque(false);
		R.setContentAreaFilled(false);
		R.setBorder(BorderFactory.createEmptyBorder());
		R.setCursor(cur1);
		R.addActionListener(this);

		T=new JButton("T");
		T.setBounds(340+550,475,50,50);
		T.setFont(f2);
		T.setForeground(Color.WHITE);
		T.setOpaque(false);
		T.setContentAreaFilled(false);
		T.setBorder(BorderFactory.createEmptyBorder());
		T.setCursor(cur1);
		T.addActionListener(this);

		Y=new JButton("Y");
		Y.setBounds(400+550,475,50,50);
		Y.setFont(f2);
		Y.setForeground(Color.WHITE);
		Y.setOpaque(false);
		Y.setContentAreaFilled(false);
		Y.setBorder(BorderFactory.createEmptyBorder());
		Y.setCursor(cur1);
		Y.addActionListener(this);

		U=new JButton("U");
		U.setBounds(460+550,475,50,50);
		U.setFont(f2);
		U.setForeground(Color.WHITE);
		U.setOpaque(false);
		U.setContentAreaFilled(false);
		U.setBorder(BorderFactory.createEmptyBorder());
		U.setCursor(cur1);
		U.addActionListener(this);

		I=new JButton("I");
		I.setBounds(520+550,475,50,50);
		I.setFont(f2);
		I.setForeground(Color.WHITE);
		I.setOpaque(false);
		I.setContentAreaFilled(false);
		I.setBorder(BorderFactory.createEmptyBorder());
		I.setCursor(cur1);
		I.addActionListener(this);

		O=new JButton("O");
		O.setBounds(580+550,475,50,50);
		O.setFont(f2);
		O.setForeground(Color.WHITE);
		O.setOpaque(false);
		O.setContentAreaFilled(false);
		O.setBorder(BorderFactory.createEmptyBorder());
		O.setCursor(cur1);
		O.addActionListener(this);

		P=new JButton("P");
		P.setBounds(640+550,475,50,50);
		P.setFont(f2);
		P.setForeground(Color.WHITE);
		P.setOpaque(false);
		P.setContentAreaFilled(false);
		P.setBorder(BorderFactory.createEmptyBorder());
		P.setCursor(cur1);
		P.addActionListener(this);

		A=new JButton("A");
		A.setBounds(125+550,535,50,50);
		A.setFont(f2);
		A.setForeground(Color.WHITE);
		A.setOpaque(false);
		A.setContentAreaFilled(false);
		A.setBorder(BorderFactory.createEmptyBorder());
		A.setCursor(cur1);
		A.addActionListener(this);

		S=new JButton("S");
		S.setBounds(185+550,535,50,50);
		S.setFont(f2);
		S.setForeground(Color.WHITE);
		S.setOpaque(false);
		S.setContentAreaFilled(false);
		S.setBorder(BorderFactory.createEmptyBorder());
		S.setCursor(cur1);
		S.addActionListener(this);

		D=new JButton("D");
		D.setBounds(245+550,535,50,50);
		D.setFont(f2);
		D.setForeground(Color.WHITE);
		D.setOpaque(false);
		D.setContentAreaFilled(false);
		D.setBorder(BorderFactory.createEmptyBorder());
		D.setCursor(cur1);
		D.addActionListener(this);

		F=new JButton("F");
		F.setBounds(305+550,535,50,50);
		F.setFont(f2);
		F.setForeground(Color.WHITE);
		F.setOpaque(false);
		F.setContentAreaFilled(false);
		F.setBorder(BorderFactory.createEmptyBorder());
		F.setCursor(cur1);
		F.addActionListener(this);

		G=new JButton("G");
		G.setBounds(365+550,535,50,50);
		G.setFont(f2);
		G.setForeground(Color.WHITE);
		G.setOpaque(false);
		G.setContentAreaFilled(false);
		G.setBorder(BorderFactory.createEmptyBorder());
		G.setCursor(cur1);
		G.addActionListener(this);

		H=new JButton("H");
		H.setBounds(425+550,535,50,50);
		H.setFont(f2);
		H.setForeground(Color.WHITE);
		H.setOpaque(false);
		H.setContentAreaFilled(false);
		H.setBorder(BorderFactory.createEmptyBorder());
		H.setCursor(cur1);
		H.addActionListener(this);

		J=new JButton("J");
		J.setBounds(485+550,535,50,50);
		J.setFont(f2);
		J.setForeground(Color.WHITE);
		J.setOpaque(false);
		J.setContentAreaFilled(false);
		J.setBorder(BorderFactory.createEmptyBorder());
		J.setCursor(cur1);
		J.addActionListener(this);
	
		K=new JButton("K");
		K.setBounds(545+550,535,50,50);
		K.setFont(f2);
		K.setForeground(Color.WHITE);
		K.setOpaque(false);
		K.setContentAreaFilled(false);
		K.setBorder(BorderFactory.createEmptyBorder());
		K.setCursor(cur1);
		K.addActionListener(this);

		L=new JButton("L");
		L.setBounds(605+550,535,50,50);
		L.setFont(f2);
		L.setForeground(Color.WHITE);
		L.setOpaque(false);
		L.setContentAreaFilled(false);
		L.setBorder(BorderFactory.createEmptyBorder());
		L.setCursor(cur1);
		L.addActionListener(this);

		Z=new JButton("Z");
		Z.setBounds(185+550,595,50,50);
		Z.setFont(f2);
		Z.setForeground(Color.WHITE);
		Z.setOpaque(false);
		Z.setContentAreaFilled(false);
		Z.setBorder(BorderFactory.createEmptyBorder());
		Z.setCursor(cur1);
		Z.addActionListener(this);

		X=new JButton("X");
		X.setBounds(245+550,595,50,50);
		X.setFont(f2);
		X.setForeground(Color.WHITE);
		X.setOpaque(false);
		X.setContentAreaFilled(false);
		X.setBorder(BorderFactory.createEmptyBorder());
		X.setCursor(cur1);
		X.addActionListener(this);

		C=new JButton("C");
		C.setBounds(305+550,595,50,50);
		C.setFont(f2);
		C.setForeground(Color.WHITE);
		C.setOpaque(false);
		C.setContentAreaFilled(false);
		C.setBorder(BorderFactory.createEmptyBorder());
		C.setCursor(cur1);
		C.addActionListener(this);

		V=new JButton("V");
		V.setBounds(365+550,595,50,50);
		V.setFont(f2);
		V.setForeground(Color.WHITE);
		V.setOpaque(false);
		V.setContentAreaFilled(false);
		V.setBorder(BorderFactory.createEmptyBorder());
		V.setCursor(cur1);
		V.addActionListener(this);

		B=new JButton("B");
		B.setBounds(425+550,595,50,50);
		B.setFont(f2);
		B.setForeground(Color.WHITE);
		B.setOpaque(false);
		B.setContentAreaFilled(false);
		B.setBorder(BorderFactory.createEmptyBorder());
		B.setCursor(cur1);
		B.addActionListener(this);

		N=new JButton("N");
		N.setBounds(485+550,595,50,50);
		N.setFont(f2);
		N.setForeground(Color.WHITE);
		N.setOpaque(false);
		N.setContentAreaFilled(false);
		N.setBorder(BorderFactory.createEmptyBorder());
		N.setCursor(cur1);
		N.addActionListener(this);

		M=new JButton("M");
		M.setBounds(545+550,595,50,50);
		M.setFont(f2);
		M.setForeground(Color.WHITE);
		M.setOpaque(false);
		M.setContentAreaFilled(false);
		M.setBorder(BorderFactory.createEmptyBorder());
		M.setCursor(cur1);
		M.addActionListener(this);

		//label for the secret word
		wordLabel=new JTextArea();
		wordLabel.setBounds(650,150,1240-600,300);
		wordLabel.setFont(f4);
		wordLabel.setOpaque(false);
		wordLabel.setEditable(false);
		wordLabel.setLineWrap(true);
		wordLabel.setWrapStyleWord(true);
		wordLabel.setRows(25);
		wordLabel.setColumns(25);
		wordLabel.setForeground(Color.WHITE);

		//next button to go to the result page after winning or losing a game
		next=new JButton();
		next.setBounds(1200,650,100,50);
		next.setFont(f2);
		next.setForeground(Color.WHITE);
		next.setOpaque(false);
		next.setContentAreaFilled(false);
		next.setBorder(BorderFactory.createEmptyBorder());
		next.setCursor(cur1);
		next.setEnabled(false);
		next.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					nextAction(ae);
				}
			});

		//hint button
		hint=new JButton(ii12);
		hint.setBounds(20,630,ii12.getIconWidth(),ii12.getIconHeight());
		hint.setOpaque(false);
		hint.setContentAreaFilled(false);
		hint.setBorder(BorderFactory.createEmptyBorder());
		hint.setCursor(cur1);
		//hint.setEnabled(false);
		hint.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent me)
			{
				hintEntered(me);
			}
			public void mouseExited(MouseEvent me)
			{
				hintExited(me);
			}
		});

		//label to display the hint
		hintLabel=new JLabel();
		hintLabel.setBounds(70,640,1000,30);
		hintLabel.setFont(f3);
		hintLabel.setForeground(col3);

		//backgrounds for the game panel
		defaultLabel=new JLabel(ii3);
		defaultLabel.setBounds(0,0,1375,735);

		wrong1=new JLabel(ii4);
		wrong1.setBounds(0,0,1375,735);
		
		wrong2=new JLabel(ii5);
		wrong2.setBounds(0,0,1375,735);
		
		wrong3=new JLabel(ii6);
		wrong3.setBounds(0,0,1375,735);
		
		wrong4=new JLabel(ii7);
		wrong4.setBounds(0,0,1375,735);
		
		wrong5=new JLabel(ii8);
		wrong5.setBounds(0,0,1375,735);
		
		wrong6=new JLabel(ii9);
		wrong6.setBounds(0,0,1375,735);
		
		wrong7=new JLabel(ii10);
		wrong7.setBounds(0,0,1375,735);

		//panel for the result
		result_panel=new JPanel();
		result_panel.setLayout(null);
		result_panel.setBounds(0,0,1375,735);

		//label for background of result panel
		resultBack=new JLabel(ii11);
		resultBack.setBounds(0,0,1375,735);

		//label for the result
		resultLabel=new JLabel();
		resultLabel.setFont(f1);
		resultLabel.setForeground(Color.BLACK);
		resultLabel.setBounds(240,485,200,100);

		//button to move to the browser
		linkButton=new JButton();
		linkButton.setBounds(525,125,700,100);
		linkButton.setFont(f3);
		linkButton.setForeground(Color.WHITE);
		linkButton.setOpaque(false);
		linkButton.setContentAreaFilled(false);
		linkButton.setBorder(BorderFactory.createEmptyBorder());
		linkButton.setCursor(cur1);
		linkButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				linkAction(ae);
			}
		});

		//button to play again
		playAgain=new JButton("Play Again");
		playAgain.setFont(f2);
		playAgain.setForeground(col3);
		playAgain.setOpaque(false);
		playAgain.setContentAreaFilled(false);
		playAgain.setBorder(BorderFactory.createEmptyBorder());
		playAgain.setCursor(cur1);
		playAgain.setBounds(1150,620,200,50);
		playAgain.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					playAgainAction(ae);
				}
			});

		//button to go to home
		home=new JButton("Home");
		home.setFont(f2);
		home.setForeground(col3);
		home.setOpaque(false);
		home.setContentAreaFilled(false);
		home.setBorder(BorderFactory.createEmptyBorder());
		home.setCursor(cur1);
		home.setBounds(1000,620,100,50);
		home.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					homeAction(ae);
				}
			});

		//textbox to get the name
		enterName=new JTextField();
		enterName.setForeground(Color.BLACK);
		enterName.setOpaque(false);
		enterName.setFont(f8);
		enterName.setBorder(BorderFactory.createEmptyBorder());
		enterName.setEnabled(false);
		enterName.setEditable(false);
		enterName.setBounds(700,540,250,50);
		enterName.addFocusListener(new FocusListener()
			{
				public void focusGained(FocusEvent fe)
				{
					enterNameFocusGained(fe);
				}
				public void focusLost(FocusEvent fe)
				{
					enterNameFocusLost(fe);
				}
			});

		//submit button to save the score in the table
		submit=new JButton();
		submit.setBounds(800,620,150,50);
		submit.setFont(f2);
		submit.setForeground(col3);
		submit.setOpaque(false);
		submit.setContentAreaFilled(false);
		submit.setBorder(BorderFactory.createEmptyBorder());
		submit.setCursor(cur1);
		submit.setEnabled(false);
		submit.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					submitAction(ae);
				}
			});

		//label for showing the score
		showScore=new JLabel();
		showScore.setBounds(1000,540,250,50);
		showScore.setForeground(Color.WHITE);
		showScore.setFont(f8);

		//message label for the result_panel
		messageLabel=new JLabel();
		messageLabel.setFont(f6);
		messageLabel.setForeground(Color.WHITE);
		messageLabel.setBounds(650,25,700,50);

		messageLabel2=new JLabel();
		messageLabel2.setFont(f6);
		messageLabel2.setForeground(Color.WHITE);
		messageLabel2.setBounds(650,75,700,50);

		//panel for high scores
		high_score_panel=new JPanel();
		high_score_panel.setLayout(null);
		high_score_panel.setBounds(0,0,1375,735);

		topTenTable=new JTable();

		//label for background of the high score panel
		highScoreBack=new JLabel(ii13);
		highScoreBack.setBounds(0,0,1375,735);

		//back button to go to the home page
		backToHome=new JButton("Back To Home");
		backToHome.setFont(f2);
		backToHome.setForeground(col3);
		backToHome.setOpaque(false);
		backToHome.setContentAreaFilled(false);
		backToHome.setBorder(BorderFactory.createEmptyBorder());
		backToHome.setCursor(cur1);
		backToHome.setBounds(1100,620,225,50);
		backToHome.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					backToHomeAction(ae);
				}
			});

		//components of home_panel
		home_panel.add(play);
		home_panel.add(exit);
		home_panel.add(highScore);
		home_panel.add(inst);
		home_panel.add(loginAsAdmin);
		home_panel.add(backLabelHome);

		//components of instruction panel
		inst_panel.add(back);
		inst_panel.add(playInst);
		inst_panel.add(instText);
		inst_panel.add(backLabelInst);

		//components of game_panel
		game_panel.add(Q);
		game_panel.add(W);
		game_panel.add(E);
		game_panel.add(R);
		game_panel.add(T);
		game_panel.add(Y);
		game_panel.add(U);
		game_panel.add(I);
		game_panel.add(O);
		game_panel.add(P);
		game_panel.add(A);
		game_panel.add(S);
		game_panel.add(D);
		game_panel.add(F);
		game_panel.add(G);
		game_panel.add(H);
		game_panel.add(J);
		game_panel.add(K);
		game_panel.add(L);
		game_panel.add(Z);
		game_panel.add(X);
		game_panel.add(C);
		game_panel.add(V);
		game_panel.add(B);
		game_panel.add(N);
		game_panel.add(M);
		game_panel.add(wordLabel);
		game_panel.add(next);
		game_panel.add(hint);
		game_panel.add(hintLabel);
		game_panel.add(defaultLabel);

		//components of result_panel
		result_panel.add(resultLabel);
		result_panel.add(linkButton);
		result_panel.add(messageLabel);
		result_panel.add(messageLabel2);
		result_panel.add(home);
		result_panel.add(submit);
		result_panel.add(playAgain);
		result_panel.add(topTenTable);
		result_panel.add(enterName);
		result_panel.add(showScore);
		result_panel.add(resultBack);

		//components of high score panel
		high_score_panel.add(backToHome);
		high_score_panel.add(highScoreBack);

		//adding the panels to the main panel
		main_panel.add(home_panel,"First");
		main_panel.add(inst_panel,"Second");
		main_panel.add(game_panel,"Third");
		main_panel.add(result_panel,"Fourth");
		main_panel.add(high_score_panel,"Fifth");

		//adding the main panel to the container
		c.add(main_panel);

		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","prateekshyap","Soni1999");
		}
		catch(Exception ee)
		{
			ee.printStackTrace();
		}
	}

	//run method for thread
	public void run()
	{
		for(y=1380;y>-40;y-=10)
		{
			hangman.setBounds(450,y,700,200);
			home_panel.add(hangman);
			home_panel.add(backLabelHome);
			try
			{
				threadHangman.sleep(10);
			}
			catch(InterruptedException ie)
			{}
		}
	}

	public void stop()
	{
		threadHangman.stop();
	}

	//actions for the alphabet buttons
	public void actionPerformed(ActionEvent ae)
	{
		JButton but=(JButton)ae.getSource();
		String buttonPressed=but.getText();
		if(buttonPressed.equals("Q"))
		{
			Q.setText("");
			Q.setEnabled(false);
		}
		else if(buttonPressed.equals("W"))
		{
			W.setText("");
			W.setEnabled(false);
		}
		else if(buttonPressed.equals("E"))
		{
			E.setText("");
			E.setEnabled(false);
		}
		else if(buttonPressed.equals("R"))
		{
			R.setText("");
			R.setEnabled(false);
		}
		else if(buttonPressed.equals("T"))
		{
			T.setText("");
			T.setEnabled(false);
		}
		else if(buttonPressed.equals("Y"))
		{
			Y.setText("");
			Y.setEnabled(false);
		}
		else if(buttonPressed.equals("U"))
		{
			U.setText("");
			U.setEnabled(false);
		}
		else if(buttonPressed.equals("I"))
		{
			I.setText("");
			I.setEnabled(false);
		}
		else if(buttonPressed.equals("O"))
		{
			O.setText("");
			O.setEnabled(false);
		}
		else if(buttonPressed.equals("P"))
		{
			P.setText("");
			P.setEnabled(false);
		}
		else if(buttonPressed.equals("A"))
		{
			A.setText("");
			A.setEnabled(false);
		}
		else if(buttonPressed.equals("S"))
		{
			S.setText("");
			S.setEnabled(false);
		}
		else if(buttonPressed.equals("D"))
		{
			D.setText("");
			D.setEnabled(false);
		}
		else if(buttonPressed.equals("F"))
		{
			F.setText("");
			F.setEnabled(false);
		}
		else if(buttonPressed.equals("G"))
		{
			G.setText("");
			G.setEnabled(false);
		}
		else if(buttonPressed.equals("H"))
		{
			H.setText("");
			H.setEnabled(false);
		}
		else if(buttonPressed.equals("J"))
		{
			J.setText("");
			J.setEnabled(false);
		}
		else if(buttonPressed.equals("K"))
		{
			K.setText("");
			K.setEnabled(false);
		}
		else if(buttonPressed.equals("L"))
		{
			L.setText("");
			L.setEnabled(false);
		}
		else if(buttonPressed.equals("Z"))
		{
			Z.setText("");
			Z.setEnabled(false);
		}
		else if(buttonPressed.equals("X"))
		{
			X.setText("");
			X.setEnabled(false);
		}
		else if(buttonPressed.equals("C"))
		{
			C.setText("");
			C.setEnabled(false);
		}
		else if(buttonPressed.equals("V"))
		{
			V.setText("");
			V.setEnabled(false);
		}
		else if(buttonPressed.equals("B"))
		{
			B.setText("");
			B.setEnabled(false);
		}
		else if(buttonPressed.equals("N"))
		{
			N.setText("");
			N.setEnabled(false);
		}
		else if(buttonPressed.equals("M"))
		{
			M.setText("");
			M.setEnabled(false);
		}
		else {}
		char buttonChar=buttonPressed.charAt(0);
		int len=wordGot.length();
		int correctCounter=0;
		char[] secretWord=wordGot.toCharArray();
		for(int i=0;i<len;i++)
		{
			if(secretWord[i]==buttonChar)
			{
				resultWord[i]=buttonChar;
				correctCounter++;
			}
		}
		wordLabel.setText("");
		for(int i=0;i<len;i++)
			wordLabel.append(Character.toString(resultWord[i]));
		int flag=0;
		for(int i=0;i<len;i++)
		{
			if(resultWord[i]=='_')
			{
				flag++;
				break;
			}
		}
		if(flag==0)
		{
			counter=0;
			Q.setText("");
			Q.setEnabled(false);
			W.setText("");
			W.setEnabled(false);
			E.setText("");
			E.setEnabled(false);
			R.setText("");
			R.setEnabled(false);
			T.setText("");
			T.setEnabled(false);
			Y.setText("");
			Y.setEnabled(false);
			U.setText("");
			U.setEnabled(false);
			I.setText("");
			I.setEnabled(false);
			O.setText("");
			O.setEnabled(false);
			P.setText("");
			P.setEnabled(false);
			A.setText("");
			A.setEnabled(false);
			S.setText("");
			S.setEnabled(false);
			D.setText("");
			D.setEnabled(false);
			F.setText("");
			F.setEnabled(false);
			G.setText("");
			G.setEnabled(false);
			H.setText("");
			H.setEnabled(false);
			J.setText("");
			J.setEnabled(false);
			K.setText("");
			K.setEnabled(false);
			L.setText("");
			L.setEnabled(false);
			Z.setText("");
			Z.setEnabled(false);
			X.setText("");
			X.setEnabled(false);
			C.setText("");
			C.setEnabled(false);
			V.setText("");
			V.setEnabled(false);
			B.setText("");
			B.setEnabled(false);
			N.setText("");
			N.setEnabled(false);
			M.setText("");
			M.setEnabled(false);
			next.setText("Next");
			next.setEnabled(true);
		}
		else
		{}
		if(correctCounter==0)
		{
			if(counter==0)
			{
				game_panel.remove(defaultLabel);
				game_panel.add(wrong1);
				cl.previous(main_panel);
				cl.next(main_panel);
				counter++;
				checker=counter;
			}
			else if(counter==1)
			{
				game_panel.remove(wrong1);
				game_panel.add(wrong2);
				cl.previous(main_panel);
				cl.next(main_panel);
				counter++;
				checker=counter;
			}
			else if(counter==2)
			{
				game_panel.remove(wrong2);
				game_panel.add(wrong3);
				cl.previous(main_panel);
				cl.next(main_panel);
				counter++;
				checker=counter;
			}
			else if(counter==3)
			{
				game_panel.remove(wrong3);
				game_panel.add(wrong4);
				cl.previous(main_panel);
				cl.next(main_panel);
				counter++;
				checker=counter;
			}
			else if(counter==4)
			{
				game_panel.remove(wrong4);
				game_panel.add(wrong5);
				cl.previous(main_panel);
				cl.next(main_panel);
				counter++;
				checker=counter;
			}
			else if(counter==5)
			{
				game_panel.remove(wrong5);
				game_panel.add(wrong6);
				cl.previous(main_panel);
				cl.next(main_panel);
				counter++;
				checker=counter;
			}
			else if(counter==6)
			{
				game_panel.remove(wrong1);
				game_panel.remove(wrong2);
				game_panel.remove(wrong3);
				game_panel.remove(wrong4);
				game_panel.remove(wrong5);
				game_panel.remove(wrong6);
				game_panel.remove(defaultLabel);
				game_panel.add(wrong7);
				cl.previous(main_panel);
				cl.next(main_panel);
				Q.setText("");
				Q.setEnabled(false);
				W.setText("");
				W.setEnabled(false);
				E.setText("");
				E.setEnabled(false);
				R.setText("");
				R.setEnabled(false);
				T.setText("");
				T.setEnabled(false);
				Y.setText("");
				Y.setEnabled(false);
				U.setText("");
				U.setEnabled(false);
				I.setText("");
				I.setEnabled(false);
				O.setText("");
				O.setEnabled(false);
				P.setText("");
				P.setEnabled(false);
				A.setText("");
				A.setEnabled(false);
				S.setText("");
				S.setEnabled(false);
				D.setText("");
				D.setEnabled(false);
				F.setText("");
				F.setEnabled(false);
				G.setText("");
				G.setEnabled(false);
				H.setText("");
				H.setEnabled(false);
				J.setText("");
				J.setEnabled(false);
				K.setText("");
				K.setEnabled(false);
				L.setText("");
				L.setEnabled(false);
				Z.setText("");
				Z.setEnabled(false);
				X.setText("");
				X.setEnabled(false);
				C.setText("");
				C.setEnabled(false);
				V.setText("");
				V.setEnabled(false);
				B.setText("");
				B.setEnabled(false);
				N.setText("");
				N.setEnabled(false);
				M.setText("");
				M.setEnabled(false);
				next.setText("Next");
				next.setEnabled(true);
			}
		}
		else
		{}
	}

	//actions to be performed on click of the play button on the home page
	static void playAction(ActionEvent ae)
	{
		play.setEnabled(false);
		inst.setEnabled(false);
		loginAsAdmin.setEnabled(false);
		highScore.setEnabled(false);
		counter=0;
		Q.setText("Q");
		Q.setEnabled(true);
		W.setText("W");
		W.setEnabled(true);
		E.setText("E");
		E.setEnabled(true);
		R.setText("R");
		R.setEnabled(true);
		T.setText("T");
		T.setEnabled(true);
		Y.setText("Y");
		Y.setEnabled(true);
		U.setText("U");
		U.setEnabled(true);
		I.setText("I");
		I.setEnabled(true);
		O.setText("O");
		O.setEnabled(true);
		P.setText("P");
		P.setEnabled(true);
		A.setText("A");
		A.setEnabled(true);
		S.setText("S");
		S.setEnabled(true);
		D.setText("D");
		D.setEnabled(true);
		F.setText("F");
		F.setEnabled(true);
		G.setText("G");
		G.setEnabled(true);
		H.setText("H");
		H.setEnabled(true);
		J.setText("J");
		J.setEnabled(true);
		K.setText("K");
		K.setEnabled(true);
		L.setText("L");
		L.setEnabled(true);
		Z.setText("Z");
		Z.setEnabled(true);
		X.setText("X");
		X.setEnabled(true);
		C.setText("C");
		C.setEnabled(true);
		V.setText("V");
		V.setEnabled(true);
		B.setText("B");
		B.setEnabled(true);
		N.setText("N");
		N.setEnabled(true);
		M.setText("M");
		M.setEnabled(true);
		next.setText("");
		next.setEnabled(false);
		if(l!=null)
		{
			l.setVisible(false);
			l.dispose();
			l=null;
		}
		if(cm!=null)
		{
			cm.setVisible(false);
			cm.dispose();
			cm=null;
		}      
		ChooseMode cm=new ChooseMode();
		cm.setVisible(true);
		cm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cm.setBounds(350,100,800,500);
		cm.setResizable(false);
	}

	//actions for play again button
	public void playAgainAction(ActionEvent ae)
	{	
		if(wonOnce)
		{
			topTenTable.getColumnModel().getColumn(0).setMinWidth(0);
			topTenTable.getColumnModel().getColumn(1).setMinWidth(0);
			topTenTable.getColumnModel().getColumn(0).setMaxWidth(0);
			topTenTable.getColumnModel().getColumn(1).setMaxWidth(0);
		}
		counter=0;
		Q.setText("Q");
		Q.setEnabled(true);
		W.setText("W");
		W.setEnabled(true);
		E.setText("E");
		E.setEnabled(true);
		R.setText("R");
		R.setEnabled(true);
		T.setText("T");
		T.setEnabled(true);
		Y.setText("Y");
		Y.setEnabled(true);
		U.setText("U");
		U.setEnabled(true);
		I.setText("I");
		I.setEnabled(true);
		O.setText("O");
		O.setEnabled(true);
		P.setText("P");
		P.setEnabled(true);
		A.setText("A");
		A.setEnabled(true);
		S.setText("S");
		S.setEnabled(true);
		D.setText("D");
		D.setEnabled(true);
		F.setText("F");
		F.setEnabled(true);
		G.setText("G");
		G.setEnabled(true);
		H.setText("H");
		H.setEnabled(true);
		J.setText("J");
		J.setEnabled(true);
		K.setText("K");
		K.setEnabled(true);
		L.setText("L");
		L.setEnabled(true);
		Z.setText("Z");
		Z.setEnabled(true);
		X.setText("X");
		X.setEnabled(true);
		C.setText("C");
		C.setEnabled(true);
		V.setText("V");
		V.setEnabled(true);
		B.setText("B");
		B.setEnabled(true);
		N.setText("N");
		N.setEnabled(true);
		M.setText("M");
		M.setEnabled(true);
		next.setText("");
		next.setEnabled(false);
		cl.first(main_panel);
		if(l!=null)
		{
			l.setVisible(false);
			l.dispose();
			l=null;
		}
		if(cm!=null)
		{
			cm.setVisible(false);
			cm.dispose();
			cm=null;
		}      
		ChooseMode cm=new ChooseMode();
		cm.setVisible(true);
		cm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cm.setBounds(350,100,800,500);
		cm.setResizable(false);
	}

	//actions for home button
	public void homeAction(ActionEvent ae)
	{	
		if(wonOnce)
		{
			topTenTable.getColumnModel().getColumn(0).setMinWidth(0);
			topTenTable.getColumnModel().getColumn(1).setMinWidth(0);
			topTenTable.getColumnModel().getColumn(0).setMaxWidth(0);
			topTenTable.getColumnModel().getColumn(1).setMaxWidth(0);
		}
		cl.first(main_panel);
		play.setEnabled(true);
		inst.setEnabled(true);
		loginAsAdmin.setEnabled(true);
		highScore.setEnabled(true);
	}

	//action for the exit button
	static void exitAction(ActionEvent ae)
	{
		System.exit(0);
	}

	//actions to be performed on click of the high score button on the home page
	static void highScoreAction(ActionEvent ae)
	{
		//table to display the top ten scores
		String[] column={"NAME","SCORE","RANK"};
		String[][] data=new String[10][3];
		try
		{
			PreparedStatement ps=conn.prepareStatement("select * from hangman_top_ten order by rank asc");
			ResultSet rs=ps.executeQuery();
			int i=0;
			while(rs.next())
			{
				data[i][0]=rs.getString(1);
				data[i][1]=Integer.toString(rs.getInt(2));
				data[i][2]=Integer.toString(rs.getInt(3));
				i++;
			}
		}
		catch(Exception ee)
		{
			ee.printStackTrace();
		}

		highScoreTable=new JTable(data,column);
		highScoreTable.setBounds(400,150,600,400);
		//JScrollPane sp=new JScrollPane(highScoreTable);
		highScoreTable.setFont(f2);
		highScoreTable.setForeground(col3);
		highScoreTable.setBackground(col4);/*
		highScoreTable.setOpaque(false);*/
		highScoreTable.setShowGrid(false);
		/*((TableCellRenderer)highScoreTable.getDefaultRenderer(Object.class)).setOpaque(false);*/
		highScoreTable.setRowHeight(40);
		highScoreTable.setEnabled(false);

		high_score_panel.add(highScoreTable);
		high_score_panel.add(highScoreBack);

		cl.next(main_panel);
		cl.next(main_panel);
		cl.next(main_panel);
		cl.next(main_panel);
	}

	//actions to be performed on the click of the next button on the game page
	public void nextAction(ActionEvent ae)
	{
		String resWord=wordLabel.getText();
		int flag=0;
		for(int i=0;i<resWord.length();i++)
		{
			if(resWord.charAt(i)=='_')
			{
				flag++;
				break;
			}
		}
		int scoreCalculator=0;
		for(int i=0;i<resWord.length();i++)
		{
			if(resWord.charAt(i)=='A'||resWord.charAt(i)=='E'||resWord.charAt(i)=='T')
				scoreCalculator=scoreCalculator+valAET;
			else if(resWord.charAt(i)=='D'||resWord.charAt(i)=='H'||resWord.charAt(i)=='I'||resWord.charAt(i)=='L'||resWord.charAt(i)=='N'||resWord.charAt(i)=='O'||resWord.charAt(i)=='R'||resWord.charAt(i)=='S')
				scoreCalculator=scoreCalculator+valDHILNORS;
			else if(resWord.charAt(i)=='B'||resWord.charAt(i)=='C'||resWord.charAt(i)=='F'||resWord.charAt(i)=='G'||resWord.charAt(i)=='M'||resWord.charAt(i)=='P'||resWord.charAt(i)=='U'||resWord.charAt(i)=='W')
				scoreCalculator=scoreCalculator+valBCFGMPUW;
			else if(resWord.charAt(i)=='K'||resWord.charAt(i)=='V'||resWord.charAt(i)=='Y')
				scoreCalculator=scoreCalculator+valKVY;
			else if(resWord.charAt(i)=='J'||resWord.charAt(i)=='Q'||resWord.charAt(i)=='X'||resWord.charAt(i)=='Z')
				scoreCalculator=scoreCalculator+valJQXZ;
			else {}
		}
		if(checker==6)
			scoreCalculator=scoreCalculator+10;
		else if(checker==5)
			scoreCalculator=scoreCalculator+20;
		else if(checker==4)
			scoreCalculator=scoreCalculator+30;
		else if(checker==3)
			scoreCalculator=scoreCalculator+40;
		else if(checker==2)
			scoreCalculator=scoreCalculator+50;
		else if(checker==1)
			scoreCalculator=scoreCalculator+60;
		else if(checker==0)
			scoreCalculator=scoreCalculator+70;
		else {}
		for(int i=0;i<resWord.length();i++)
		{
			if(resWord.charAt(i)=='_')
			{
				scoreCalculator=0;
				break;
			}
		}
		String scoreString;
		if(scoreCalculator==0)
		{
			scoreString="0";
			messageLabel.setText("Oops! You lost! The word");
			messageLabel2.setText("is "+wordGot);
			enterName.setText("");
			enterName.setEnabled(false);
			enterName.setEditable(false);
			result_panel.remove(topTenTable);
			submit.setText("");
			submit.setEnabled(false);
			showScore.setText("");
		}
		else
		{
			wonOnce=true;
			scoreString=Integer.toString(scoreCalculator);
			messageLabel.setText("You guessed it right!");
			messageLabel2.setText("");
			String[] column={"NAME","SCORE"};
			String[][] data=new String[10][2];
			try
			{
				PreparedStatement ps=conn.prepareStatement("select name,score from hangman_top_ten order by rank asc");
				ResultSet rs=ps.executeQuery();
				int i=0;
				while(rs.next())
				{	
					data[i][0]=rs.getString(1);
					data[i][1]=Integer.toString(rs.getInt(2));
					i++;
				}
			}
			catch(Exception ee)
			{
				ee.printStackTrace();
			}

			topTenTable=new JTable(data,column);
			topTenTable.setBounds(700,250,600,400);
			//JScrollPane sp=new JScrollPane(highScoreTable);
			topTenTable.setFont(f8);
			topTenTable.setForeground(col3);
			topTenTable.setBackground(col4);/*
			topTenTable.setOpaque(false);*/
			topTenTable.setShowGrid(false);
			/*((TableCellRenderer)highScoreTable.getDefaultRenderer(Object.class)).setOpaque(false);*/
			topTenTable.setRowHeight(30);
			topTenTable.setEnabled(false);

			result_panel.add(topTenTable);
			result_panel.add(resultBack);

			enterName.setText("Enter Your Name");
			enterName.setEnabled(true);
			enterName.setEditable(true);
			submit.setText("Submit");
			submit.setEnabled(true);
			showScore.setText(scoreString);
		}
		resultLabel.setText(scoreString);
		if(hint.isEnabled())
		{
			linkButton.setText("Click to know more about "+wordGot);
			linkButton.setEnabled(true);
		}
		else
		{
			linkButton.setText("");
			linkButton.setEnabled(false);
		}
		cl.next(main_panel);
	}//valAET=1, valDHILNORS=2, valBCFGMPUW=3, valKVY=4, valJQXZ=5

	//actions for the link button
	public void linkAction(ActionEvent ae)
	{
		Desktop dt=Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		if(dt != null && dt.isSupported(Desktop.Action.BROWSE))
		{
			try{
			dt.browse(new URI(linkGot));
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	//action for instructions button
	static void instAction(ActionEvent ae)
	{
		cl.next(main_panel);
	}

	//actions for login as admin button
	static void loginAsAdminAction(ActionEvent ae)
	{
		if(l!=null)
		{
			l.setVisible(false);
			l.dispose();
			l=null;
		}
		if(cm!=null)
		{
			cm.setVisible(false);
			cm.dispose();
			cm=null;
		}
		l=new Login();
		l.setVisible(true);
		l.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		l.setBounds(350,100,800,500);
		l.setResizable(false);
	}

	//actions to be performed on click of the back button on the instructions page
	static void backAction(ActionEvent ae)
	{	
		cl.first(main_panel);
	}

	//actions for the play button on the instructions page
	static void playInstAction(ActionEvent ae)
	{
		counter=0;
		Q.setText("Q");
		Q.setEnabled(true);
		W.setText("W");
		W.setEnabled(true);
		E.setText("E");
		E.setEnabled(true);
		R.setText("R");
		R.setEnabled(true);
		T.setText("T");
		T.setEnabled(true);
		Y.setText("Y");
		Y.setEnabled(true);
		U.setText("U");
		U.setEnabled(true);
		I.setText("I");
		I.setEnabled(true);
		O.setText("O");
		O.setEnabled(true);
		P.setText("P");
		P.setEnabled(true);
		A.setText("A");
		A.setEnabled(true);
		S.setText("S");
		S.setEnabled(true);
		D.setText("D");
		D.setEnabled(true);
		F.setText("F");
		F.setEnabled(true);
		G.setText("G");
		G.setEnabled(true);
		H.setText("H");
		H.setEnabled(true);
		J.setText("J");
		J.setEnabled(true);
		K.setText("K");
		K.setEnabled(true);
		L.setText("L");
		L.setEnabled(true);
		Z.setText("Z");
		Z.setEnabled(true);
		X.setText("X");
		X.setEnabled(true);
		C.setText("C");
		C.setEnabled(true);
		V.setText("V");
		V.setEnabled(true);
		B.setText("B");
		B.setEnabled(true);
		N.setText("N");
		N.setEnabled(true);
		M.setText("M");
		M.setEnabled(true);
		next.setText("");
		next.setEnabled(false);
		cl.first(main_panel);
		if(l!=null)
		{
			l.setVisible(false);
			l.dispose();
			l=null;
		}
		if(cm!=null)
		{
			cm.setVisible(false);
			cm.dispose();
			cm=null;
		}
		cm=new ChooseMode();
		cm.setVisible(true);
		cm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cm.setBounds(350,100,800,500);
		cm.setResizable(false);
	}

	//actions to be performed after closing the ChooseMode window
	static void chooseModeAction(String w, String h, String l)
	{
		game_panel.remove(wrong1);
		game_panel.remove(wrong2);
		game_panel.remove(wrong3);
		game_panel.remove(wrong4);
		game_panel.remove(wrong5);
		game_panel.remove(wrong6);
		game_panel.remove(wrong7);
		cl.next(main_panel);
		cl.next(main_panel);
		game_panel.add(defaultLabel);
		wordGot=w.toUpperCase();
		int len=wordGot.length();
		resultWord=new char[len];
		for(int i=0;i<len;i++)
		{
			if(wordGot.charAt(i)==' ')
				resultWord[i]='\n';
			else
				resultWord[i]='_';
		}
		hintGot=h;
		linkGot=l;
		int length=wordGot.length();
		wordLabel.setText("");
		for(int i=0;i<length;i++)
		{
			if(wordGot.charAt(i)==' ')
				wordLabel.append("\n");
			else
				wordLabel.append("_");
		}
	}

	public void hintEntered(MouseEvent me)
	{
		hintLabel.setText(hintGot);
	}

	public void hintExited(MouseEvent me)
	{
		hintLabel.setText("");
	}

	public void backToHomeAction(ActionEvent ae)
	{
		highScoreTable.getColumnModel().getColumn(0).setMinWidth(0);
		highScoreTable.getColumnModel().getColumn(1).setMinWidth(0);
		highScoreTable.getColumnModel().getColumn(2).setMinWidth(0);
		highScoreTable.getColumnModel().getColumn(0).setMaxWidth(0);
		highScoreTable.getColumnModel().getColumn(1).setMaxWidth(0);
		highScoreTable.getColumnModel().getColumn(2).setMaxWidth(0);
		cl.first(main_panel);
	}

	//focus listeners for the enter name field in result panel
	public void enterNameFocusGained(FocusEvent fe)
	{
		if(enterName.getText().equals("Enter Your Name"))
		{
			enterName.setText("");
			enterName.setForeground(Color.WHITE);
		}
	}

	public void enterNameFocusLost(FocusEvent fe)
	{
		if(enterName.getText().equals(""))
		{
			enterName.setText("Enter Your Name");
			enterName.setForeground(Color.BLACK);
		}
	}

	//actions for the submit button
	public void submitAction(ActionEvent ae)
	{
		String nameGot=enterName.getText();
		if(nameGot.equals("")||nameGot.equals("Enter Your Name"))
			nameGot="Anonymous";
		int scoreGot=Integer.parseInt(resultLabel.getText());
		String[] column={"NAME","SCORE"};
		String[][] data=new String[10][2];
		try
		{
			CallableStatement cs=conn.prepareCall("{call insert_into_hangman_highscores(?,?)}");
			cs.setString(1,nameGot);
			cs.setInt(2,scoreGot);
			cs.execute();
			cs.close();

			PreparedStatement ps=conn.prepareStatement("select name,score from hangman_top_ten order by rank asc");
			ResultSet rs=ps.executeQuery();
			int i=0;
			while(rs.next())
			{	
				data[i][0]=rs.getString(1);
				data[i][1]=Integer.toString(rs.getInt(2));
				i++;
			}
		}
		catch(Exception ee)
		{
			ee.printStackTrace();
		}
		topTenTable.getColumnModel().getColumn(0).setMinWidth(0);
		topTenTable.getColumnModel().getColumn(1).setMinWidth(0);
		topTenTable.getColumnModel().getColumn(0).setMaxWidth(0);
		topTenTable.getColumnModel().getColumn(1).setMaxWidth(0);

		topTenTable=new JTable(data,column);
		topTenTable.setBounds(700,250,600,400);
		//JScrollPane sp=new JScrollPane(highScoreTable);
		topTenTable.setFont(f8);
		topTenTable.setForeground(col3);
		topTenTable.setBackground(col4);/*
		topTenTable.setOpaque(false);*/
		topTenTable.setShowGrid(false);
		/*((TableCellRenderer)highScoreTable.getDefaultRenderer(Object.class)).setOpaque(false);*/
		topTenTable.setRowHeight(30);
		topTenTable.setEnabled(false);
		result_panel.add(topTenTable);
		result_panel.add(resultBack);
		cl.next(main_panel);
		cl.previous(main_panel);
		enterName.setEditable(false);
		enterName.setEnabled(false);
		enterName.setText("");
		showScore.setText("");
		submit.setEnabled(false);
		submit.setText("");
	}
}

class ChooseMode extends JFrame
{
	static Container c;
	static JPanel main_panel;
	static Font f1,f2, f3;
	static Cursor cur1;
	static JLabel backLabel, modeLabel;
	static ImageIcon ii1;
	static JButton classic, suicide, multi, play;
	static JTextField word;
	ChooseMode()
	{
		//container
		c=this.getContentPane();
		c.setLayout(null);

		//images
		ii1=new ImageIcon("INST_BACK.jpg");

		//fonts,cursors and colours
		f1=new Font("Lucida Handwriting",Font.BOLD,30);
		f2=new Font("Lucida Handwriting",Font.BOLD,25);
		f3=new Font("Lucida Handwriting",Font.PLAIN,20);
		cur1=new Cursor(Cursor.HAND_CURSOR);

		//main panel
		main_panel=new JPanel();
		main_panel.setBounds(0,0,800,500);
		main_panel.setLayout(null);

		//label for the background image
		backLabel=new JLabel(ii1);
		backLabel.setBounds(0,0,800,600);

		//head label
		modeLabel=new JLabel("Choose one");
		modeLabel.setBounds(300,75,300,50);
		modeLabel.setFont(f1);
		modeLabel.setForeground(Color.WHITE);

		//button to enter into classic mode
		classic=new JButton("Classic");
		classic.setBounds(50,150,125,50);
		classic.setFont(f2);
		classic.setForeground(Color.WHITE);
		classic.setOpaque(false);
		classic.setContentAreaFilled(false);
		classic.setBorder(BorderFactory.createEmptyBorder());
		classic.setCursor(cur1);
		classic.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					classicAction(ae);
				}
			});

		//button to enter into suicide mode
		suicide=new JButton("Suicide");
		suicide.setBounds(330,150,125,50);
		suicide.setFont(f2);
		suicide.setForeground(Color.WHITE);
		suicide.setOpaque(false);
		suicide.setContentAreaFilled(false);
		suicide.setBorder(BorderFactory.createEmptyBorder());
		suicide.setCursor(cur1);
		suicide.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					suicideAction(ae);
				}
			});

		//button to enter into multiplayer mode
		multi=new JButton("Multiplayer");
		multi.setBounds(550,150,200,50);
		multi.setFont(f2);
		multi.setForeground(Color.WHITE);
		multi.setOpaque(false);
		multi.setContentAreaFilled(false);
		multi.setBorder(BorderFactory.createEmptyBorder());
		multi.setCursor(cur1);
		multi.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					multiAction(ae);
				}
			});

		//adding the components to the main panel
		main_panel.add(classic);
		main_panel.add(suicide);
		main_panel.add(multi);
		main_panel.add(modeLabel);
		main_panel.add(backLabel);

		//adding the main panel to the container
		c.add(main_panel);
	}

	public void classicAction(ActionEvent ae)
	{
		MyFrame.counter=0;
		MyFrame.suicide=false;
		//MyFrame.result_panel.add(MyFrame.linkButton);
		MyFrame.hint.setEnabled(true);
		setVisible(false);
		dispose();
		ChooseCategory cc=new ChooseCategory();
		cc.setVisible(true);
		cc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cc.setBounds(540,125,300,525);
		cc.setResizable(false);
	}

	public void suicideAction(ActionEvent ae)
	{
		MyFrame.counter=6;
		MyFrame.suicide=true;
		//MyFrame.result_panel.add(MyFrame.linkButton);
		MyFrame.hint.setEnabled(true);
		setVisible(false);
		dispose();
		ChooseCategory cc=new ChooseCategory();
		cc.setVisible(true);
		cc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cc.setBounds(540,125,300,525);
		cc.setResizable(false);
	}

	public void multiAction(ActionEvent ae)
	{
		MyFrame.counter=0;
		MyFrame.hint.setEnabled(false);
		setVisible(false);
		dispose();
		MultiPlayerInterface mpi=new MultiPlayerInterface();
		mpi.setVisible(true);
		mpi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mpi.setBounds(350,100,800,500);
		mpi.setResizable(false);
	}
}

class ChooseCategory extends JFrame
{
	static Container c;
	static JPanel main_panel;
	static JLabel backLabel, chooseCat;
	static JButton country, animal, bird, car, city, river, scientist, nobelist, mathematician, cs, olympics, cricket;
	static Font f1, f2;
	static Cursor cur1;
	static ImageIcon ii1;
	static Connection conn;
	int index;
	ChooseCategory()
	{
		//container
		c=this.getContentPane();
		c.setLayout(null);

		//images
		ii1=new ImageIcon("CAT_BACK.jpg");

		//fonts, cursors and colours
		f1=new Font("Lucida Handwriting",Font.BOLD,25);
		f2=new Font("Lucida Handwriting",Font.PLAIN,18);
		cur1=new Cursor(Cursor.HAND_CURSOR);

		//label for background
		backLabel=new JLabel(ii1);
		backLabel.setBounds(0,0,300,700);

		//label of category
		chooseCat=new JLabel("Choose category");
		chooseCat.setBounds(20,10,400,50);
		chooseCat.setFont(f1);
		chooseCat.setForeground(Color.WHITE);

		//country button
		country=new JButton("Countries");
		country.setBounds(50,45,150,50);
		country.setFont(f2);
		country.setForeground(Color.WHITE);
		country.setOpaque(false);
		country.setContentAreaFilled(false);
		country.setBorder(BorderFactory.createEmptyBorder());
		country.setCursor(cur1);
		country.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					countryAction(ae);
				}
			});

		//animal button
		animal=new JButton("Animals");
		animal.setBounds(50,80,150,50);
		animal.setFont(f2);
		animal.setForeground(Color.WHITE);
		animal.setOpaque(false);
		animal.setContentAreaFilled(false);
		animal.setBorder(BorderFactory.createEmptyBorder());
		animal.setCursor(cur1);
		animal.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					animalAction(ae);
				}
			});

		//bird button
		bird=new JButton("Birds");
		bird.setBounds(50,115,150,50);
		bird.setFont(f2);
		bird.setForeground(Color.WHITE);
		bird.setOpaque(false);
		bird.setContentAreaFilled(false);
		bird.setBorder(BorderFactory.createEmptyBorder());
		bird.setCursor(cur1);
		bird.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					birdAction(ae);
				}
			});

		//car button
		car=new JButton("Cars");
		car.setBounds(50,150,150,50);
		car.setFont(f2);
		car.setForeground(Color.WHITE);
		car.setOpaque(false);
		car.setContentAreaFilled(false);
		car.setBorder(BorderFactory.createEmptyBorder());
		car.setCursor(cur1);
		car.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					carAction(ae);
				}
			});

		//city button
		city=new JButton("Cities");
		city.setBounds(50,185,150,50);
		city.setFont(f2);
		city.setForeground(Color.WHITE);
		city.setOpaque(false);
		city.setContentAreaFilled(false);
		city.setBorder(BorderFactory.createEmptyBorder());
		city.setCursor(cur1);
		city.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					cityAction(ae);
				}
			});

		//river button
		river=new JButton("Rivers");
		river.setBounds(50,220,150,50);
		river.setFont(f2);
		river.setForeground(Color.WHITE);
		river.setOpaque(false);
		river.setContentAreaFilled(false);
		river.setBorder(BorderFactory.createEmptyBorder());
		river.setCursor(cur1);
		river.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					riverAction(ae);
				}
			});

		//scientist button
		scientist=new JButton("Scientists");
		scientist.setBounds(50,255,150,50);
		scientist.setFont(f2);
		scientist.setForeground(Color.WHITE);
		scientist.setOpaque(false);
		scientist.setContentAreaFilled(false);
		scientist.setBorder(BorderFactory.createEmptyBorder());
		scientist.setCursor(cur1);
		scientist.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					scientistAction(ae);
				}
			});

		//nobelist button
		nobelist=new JButton("Nobelists");
		nobelist.setBounds(50,290,150,50);
		nobelist.setFont(f2);
		nobelist.setForeground(Color.WHITE);
		nobelist.setOpaque(false);
		nobelist.setContentAreaFilled(false);
		nobelist.setBorder(BorderFactory.createEmptyBorder());
		nobelist.setCursor(cur1);
		nobelist.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					nobelistAction(ae);
				}
			});

		//mathematician button
		mathematician=new JButton("Mathematicians");
		mathematician.setBounds(40,325,200,50);
		mathematician.setFont(f2);
		mathematician.setForeground(Color.WHITE);
		mathematician.setOpaque(false);
		mathematician.setContentAreaFilled(false);
		mathematician.setBorder(BorderFactory.createEmptyBorder());
		mathematician.setCursor(cur1);
		mathematician.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					mathematicianAction(ae);
				}
			});

		//cs button
		cs=new JButton("Computer Science");
		cs.setBounds(40,360,200,50);
		cs.setFont(f2);
		cs.setForeground(Color.WHITE);
		cs.setOpaque(false);
		cs.setContentAreaFilled(false);
		cs.setBorder(BorderFactory.createEmptyBorder());
		cs.setCursor(cur1);
		cs.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					csAction(ae);
				}
			});

		//olympics button
		olympics=new JButton("Olympics");
		olympics.setBounds(50,395,150,50);
		olympics.setFont(f2);
		olympics.setForeground(Color.WHITE);
		olympics.setOpaque(false);
		olympics.setContentAreaFilled(false);
		olympics.setBorder(BorderFactory.createEmptyBorder());
		olympics.setCursor(cur1);
		olympics.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					olympicsAction(ae);
				}
			});

		//cricket button
		cricket=new JButton("Cricket");
		cricket.setBounds(50,430,150,50);
		cricket.setFont(f2);
		cricket.setForeground(Color.WHITE);
		cricket.setOpaque(false);
		cricket.setContentAreaFilled(false);
		cricket.setBorder(BorderFactory.createEmptyBorder());
		cricket.setCursor(cur1);
		cricket.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					cricketAction(ae);
				}
			});

		//main panel
		main_panel=new JPanel();
		main_panel.setLayout(null);
		main_panel.setBounds(0,0,800,500);

		//components of the main panel
		main_panel.add(chooseCat);
		main_panel.add(country);
		main_panel.add(animal);
		main_panel.add(bird);
		main_panel.add(car);
		main_panel.add(city);
		main_panel.add(river);
		main_panel.add(scientist);
		main_panel.add(nobelist);
		main_panel.add(mathematician);
		main_panel.add(cs);
		main_panel.add(olympics);
		main_panel.add(cricket);
		main_panel.add(backLabel);

		//adding the main panel to the container
		c.add(main_panel);

		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","prateekshyap","Soni1999");
		}
		catch(Exception ee)
		{
			ee.printStackTrace();
		}
	}

	public void countryAction(ActionEvent ae)
	{
		try{
		int max=-1000;
		Statement s=conn.createStatement();
		ResultSet rs=s.executeQuery("select max(cat_serial) from hangman_game where cat_code='CO'");
		while(rs.next())
		{
			max=max+rs.getInt(1);
		}
		int num=(int)(Math.random()*((max-1)+1))+1;
		index=num+1000;
		PreparedStatement ps=conn.prepareStatement("select name,hint,link from hangman_game where cat_serial=?");
		ps.setInt(1,index);
		rs=ps.executeQuery();
		String wordRet="", hintRet="", linkRet="";
		while(rs.next())
		{
			wordRet=rs.getString(1);
			hintRet=rs.getString(2);
			linkRet=rs.getString(3);
		}
		setVisible(false);
		dispose();
		MyFrame.chooseModeAction(wordRet,hintRet,linkRet);
		}catch(Exception ee)
		{
			ee.printStackTrace();
		}
	}

	public void animalAction(ActionEvent ae)
	{
		try{
		int max=-2000;
		Statement s=conn.createStatement();
		ResultSet rs=s.executeQuery("select max(cat_serial) from hangman_game where cat_code='AN'");
		while(rs.next())
		{
			max=max+rs.getInt(1);
		}
		int num=(int)(Math.random()*((max-1)+1))+1;
		index=num+2000;
		PreparedStatement ps=conn.prepareStatement("select name,hint,link from hangman_game where cat_serial=?");
		ps.setInt(1,index);
		rs=ps.executeQuery();
		String wordRet="", hintRet="", linkRet="";
		while(rs.next())
		{
			wordRet=rs.getString(1);
			hintRet=rs.getString(2);
			linkRet=rs.getString(3);
		}
		setVisible(false);
		dispose();
		MyFrame.chooseModeAction(wordRet,hintRet,linkRet);
		}catch(Exception ee)
		{
			ee.printStackTrace();
		}
	}

	public void birdAction(ActionEvent ae)
	{
		try{
		int max=-3000;
		Statement s=conn.createStatement();
		ResultSet rs=s.executeQuery("select max(cat_serial) from hangman_game where cat_code='BI'");
		while(rs.next())
		{
			max=max+rs.getInt(1);
		}
		int num=(int)(Math.random()*((max-1)+1))+1;
		index=num+3000;
		PreparedStatement ps=conn.prepareStatement("select name,hint,link from hangman_game where cat_serial=?");
		ps.setInt(1,index);
		rs=ps.executeQuery();
		String wordRet="", hintRet="", linkRet="";
		while(rs.next())
		{
			wordRet=rs.getString(1);
			hintRet=rs.getString(2);
			linkRet=rs.getString(3);
		}
		setVisible(false);
		dispose();
		MyFrame.chooseModeAction(wordRet,hintRet,linkRet);
		}catch(Exception ee)
		{
			ee.printStackTrace();
		}
	}

	public void carAction(ActionEvent ae)
	{
		try{
		int max=-4000;
		Statement s=conn.createStatement();
		ResultSet rs=s.executeQuery("select max(cat_serial) from hangman_game where cat_code='CA'");
		while(rs.next())
		{
			max=max+rs.getInt(1);
		}
		int num=(int)(Math.random()*((max-1)+1))+1;
		index=num+4000;
		PreparedStatement ps=conn.prepareStatement("select name,hint,link from hangman_game where cat_serial=?");
		ps.setInt(1,index);
		rs=ps.executeQuery();
		String wordRet="", hintRet="", linkRet="";
		while(rs.next())
		{
			wordRet=rs.getString(1);
			hintRet=rs.getString(2);
			linkRet=rs.getString(3);
		}
		setVisible(false);
		dispose();
		MyFrame.chooseModeAction(wordRet,hintRet,linkRet);
		}catch(Exception ee)
		{
			ee.printStackTrace();
		}
	}

	public void cityAction(ActionEvent ae)
	{
		try{
		int max=-5000;
		Statement s=conn.createStatement();
		ResultSet rs=s.executeQuery("select max(cat_serial) from hangman_game where cat_code='CI'");
		while(rs.next())
		{
			max=max+rs.getInt(1);
		}
		int num=(int)(Math.random()*((max-1)+1))+1;
		index=num+5000;
		PreparedStatement ps=conn.prepareStatement("select name,hint,link from hangman_game where cat_serial=?");
		ps.setInt(1,index);
		rs=ps.executeQuery();
		String wordRet="", hintRet="", linkRet="";
		while(rs.next())
		{
			wordRet=rs.getString(1);
			hintRet=rs.getString(2);
			linkRet=rs.getString(3);
		}
		setVisible(false);
		dispose();
		MyFrame.chooseModeAction(wordRet,hintRet,linkRet);
		}catch(Exception ee)
		{
			ee.printStackTrace();
		}
	}

	public void riverAction(ActionEvent ae)
	{
		try{
		int max=-6000;
		Statement s=conn.createStatement();
		ResultSet rs=s.executeQuery("select max(cat_serial) from hangman_game where cat_code='RI'");
		while(rs.next())
		{
			max=max+rs.getInt(1);
		}
		int num=(int)(Math.random()*((max-1)+1))+1;
		index=num+6000;
		PreparedStatement ps=conn.prepareStatement("select name,hint,link from hangman_game where cat_serial=?");
		ps.setInt(1,index);
		rs=ps.executeQuery();
		String wordRet="", hintRet="", linkRet="";
		while(rs.next())
		{
			wordRet=rs.getString(1);
			hintRet=rs.getString(2);
			linkRet=rs.getString(3);
		}
		setVisible(false);
		dispose();
		MyFrame.chooseModeAction(wordRet,hintRet,linkRet);
		}catch(Exception ee)
		{
			ee.printStackTrace();
		}
	}

	public void scientistAction(ActionEvent ae)
	{
		try{
		int max=-7000;
		Statement s=conn.createStatement();
		ResultSet rs=s.executeQuery("select max(cat_serial) from hangman_game where cat_code='SC'");
		while(rs.next())
		{
			max=max+rs.getInt(1);
		}
		int num=(int)(Math.random()*((max-1)+1))+1;
		index=num+7000;
		PreparedStatement ps=conn.prepareStatement("select name,hint,link from hangman_game where cat_serial=?");
		ps.setInt(1,index);
		rs=ps.executeQuery();
		String wordRet="", hintRet="", linkRet="";
		while(rs.next())
		{
			wordRet=rs.getString(1);
			hintRet=rs.getString(2);
			linkRet=rs.getString(3);
		}
		setVisible(false);
		dispose();
		MyFrame.chooseModeAction(wordRet,hintRet,linkRet);
		}catch(Exception ee)
		{
			ee.printStackTrace();
		}
	}

	public void nobelistAction(ActionEvent ae)
	{
		try{
		int max=-8000;
		Statement s=conn.createStatement();
		ResultSet rs=s.executeQuery("select max(cat_serial) from hangman_game where cat_code='NO'");
		while(rs.next())
		{
			max=max+rs.getInt(1);
		}
		int num=(int)(Math.random()*((max-1)+1))+1;
		index=num+8000;
		PreparedStatement ps=conn.prepareStatement("select name,hint,link from hangman_game where cat_serial=?");
		ps.setInt(1,index);
		rs=ps.executeQuery();
		String wordRet="", hintRet="", linkRet="";
		while(rs.next())
		{
			wordRet=rs.getString(1);
			hintRet=rs.getString(2);
			linkRet=rs.getString(3);
		}
		setVisible(false);
		dispose();
		MyFrame.chooseModeAction(wordRet,hintRet,linkRet);
		}catch(Exception ee)
		{
			ee.printStackTrace();
		}
	}

	public void mathematicianAction(ActionEvent ae)
	{
		try{
		int max=-9000;
		Statement s=conn.createStatement();
		ResultSet rs=s.executeQuery("select max(cat_serial) from hangman_game where cat_code='MA'");
		while(rs.next())
		{
			max=max+rs.getInt(1);
		}
		int num=(int)(Math.random()*((max-1)+1))+1;
		index=num+9000;
		PreparedStatement ps=conn.prepareStatement("select name,hint,link from hangman_game where cat_serial=?");
		ps.setInt(1,index);
		rs=ps.executeQuery();
		String wordRet="", hintRet="", linkRet="";
		while(rs.next())
		{
			wordRet=rs.getString(1);
			hintRet=rs.getString(2);
			linkRet=rs.getString(3);
		}
		setVisible(false);
		dispose();
		MyFrame.chooseModeAction(wordRet,hintRet,linkRet);
		}catch(Exception ee)
		{
			ee.printStackTrace();
		}
	}

	public void csAction(ActionEvent ae)
	{
		try{
		int max=-10000;
		Statement s=conn.createStatement();
		ResultSet rs=s.executeQuery("select max(cat_serial) from hangman_game where cat_code='CS'");
		while(rs.next())
		{
			max=max+rs.getInt(1);
		}
		int num=(int)(Math.random()*((max-1)+1))+1;
		index=num+10000;
		PreparedStatement ps=conn.prepareStatement("select name,hint,link from hangman_game where cat_serial=?");
		ps.setInt(1,index);
		rs=ps.executeQuery();
		String wordRet="", hintRet="", linkRet="";
		while(rs.next())
		{
			wordRet=rs.getString(1);
			hintRet=rs.getString(2);
			linkRet=rs.getString(3);
		}
		setVisible(false);
		dispose();
		MyFrame.chooseModeAction(wordRet,hintRet,linkRet);
		}catch(Exception ee)
		{
			ee.printStackTrace();
		}
	}

	public void olympicsAction(ActionEvent ae)
	{
		try{
		int max=-11000;
		Statement s=conn.createStatement();
		ResultSet rs=s.executeQuery("select max(cat_serial) from hangman_game where cat_code='OL'");
		while(rs.next())
		{
			max=max+rs.getInt(1);
		}
		int num=(int)(Math.random()*((max-1)+1))+1;
		index=num+11000;
		PreparedStatement ps=conn.prepareStatement("select name,hint,link from hangman_game where cat_serial=?");
		ps.setInt(1,index);
		rs=ps.executeQuery();
		String wordRet="", hintRet="", linkRet="";
		while(rs.next())
		{
			wordRet=rs.getString(1);
			hintRet=rs.getString(2);
			linkRet=rs.getString(3);
		}
		setVisible(false);
		dispose();
		MyFrame.chooseModeAction(wordRet,hintRet,linkRet);
		}catch(Exception ee)
		{
			ee.printStackTrace();
		}
	}

	public void cricketAction(ActionEvent ae)
	{
		try{
		int max=-12000;
		Statement s=conn.createStatement();
		ResultSet rs=s.executeQuery("select max(cat_serial) from hangman_game where cat_code='CR'");
		while(rs.next())
		{
			max=max+rs.getInt(1);
		}
		int num=(int)(Math.random()*((max-1)+1))+1;
		index=num+12000;
		PreparedStatement ps=conn.prepareStatement("select name,hint,link from hangman_game where cat_serial=?");
		ps.setInt(1,index);
		rs=ps.executeQuery();
		String wordRet="", hintRet="", linkRet="";
		while(rs.next())
		{
			wordRet=rs.getString(1);
			hintRet=rs.getString(2);
			linkRet=rs.getString(3);
		}
		setVisible(false);
		dispose();
		MyFrame.chooseModeAction(wordRet,hintRet,linkRet);
		}catch(Exception ee)
		{
			ee.printStackTrace();
		}
	}
}

class MultiPlayerInterface extends JFrame
{
	static Container c;
	static JPanel main_panel;
	static ImageIcon ii1;
	static Font f1, f2, f3, f4;
	static Cursor cur1;
	static JLabel backLabel, wordLabel, nameLabel, errorLabel, hintLabel;
	static JTextField word, name;
	static JButton play, view;
	static JComboBox wordList;
	static Connection conn;
	MultiPlayerInterface()
	{
		//container
		c=this.getContentPane();
		c.setLayout(null);

		//images
		ii1=new ImageIcon("INST_BACK.jpg");

		//fonts,cursors and colours
		f1=new Font("Lucida Handwriting",Font.BOLD,30);
		f2=new Font("Lucida Handwriting",Font.BOLD,25);
		f3=new Font("Lucida Handwriting",Font.PLAIN,20);
		f4=new Font("Lucida Handwriting",Font.PLAIN,14);
		cur1=new Cursor(Cursor.HAND_CURSOR);

		//background label
		backLabel=new JLabel(ii1);
		backLabel.setBounds(0,0,800,600);

		//label to display warning messages
		errorLabel=new JLabel();
		errorLabel.setBounds(200,400,500,50);
		errorLabel.setFont(f3);
		errorLabel.setForeground(Color.WHITE);

		//label to display view message
		hintLabel=new JLabel();
		hintLabel.setBounds(200,300,500,50);
		hintLabel.setFont(f3);
		hintLabel.setForeground(Color.WHITE);

		//label for the word
		wordLabel=new JLabel("Enter the word");
		wordLabel.setBounds(150,50,250,50);
		wordLabel.setFont(f2);
		wordLabel.setForeground(Color.WHITE);

		//textfield to enter the secret word or phrase
		word=new JTextField();
		word.setForeground(Color.BLACK);
		word.setFont(f3);
		word.setBounds(450,50,200,35);
		word.setBorder(BorderFactory.createEmptyBorder());

		//label for the name
		nameLabel=new JLabel("Enter your name");
		nameLabel.setBounds(150,150,260,50);
		nameLabel.setFont(f2);
		nameLabel.setForeground(Color.WHITE);

		//textfield to enter the secret word or phrase
		name=new JTextField();
		name.setFont(f3);
		name.setBounds(450,150,200,35);
		name.setBorder(BorderFactory.createEmptyBorder());

		//button to view the saved words
		view=new JButton("View");
		view.setBounds(200,250,100,50);
		view.setFont(f2);
		view.setForeground(Color.WHITE);
		view.setOpaque(false);
		view.setContentAreaFilled(false);
		view.setBorder(BorderFactory.createEmptyBorder());
		view.setCursor(cur1);
		view.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					viewAction(ae);
				}
			});
		view.addMouseListener(new MouseAdapter()
			{
				public void mouseEntered(MouseEvent me)
				{
					viewEntered(me);
				}
				public void mouseExited(MouseEvent me)
				{
					viewExited(me);
				}
			});

		//list of words
		wordList=new JComboBox();
		wordList.setBounds(450,250,200,35);
		wordList.addItem("Select Word");
		wordList.setFont(f4);
		wordList.setForeground(Color.BLACK);

		//button to play in multiplayer mode
		play=new JButton("Play");
		play.setBounds(350,350,100,50);
		play.setFont(f2);
		play.setForeground(Color.WHITE);
		play.setOpaque(false);
		play.setContentAreaFilled(false);
		play.setBorder(BorderFactory.createEtchedBorder());
		play.setCursor(cur1);
		play.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					playAction(ae);
				}
			});

		//main panel
		main_panel=new JPanel();
		main_panel.setLayout(null);
		main_panel.setBounds(0,0,800,500);

		//components of main panel
		main_panel.add(wordLabel);
		main_panel.add(nameLabel);
		main_panel.add(word);
		main_panel.add(play);
		main_panel.add(view);
		main_panel.add(name);
		main_panel.add(errorLabel);
		main_panel.add(hintLabel);
		main_panel.add(wordList);
		main_panel.add(backLabel);

		//adding the main panel to the container
		c.add(main_panel);

		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","prateekshyap","Soni1999");
		}
		catch(Exception ee)
		{
			ee.printStackTrace();
		}
	}

	public void playAction(ActionEvent ae)
	{
		String itemGot=(String)wordList.getSelectedItem();
		String wordRet="";
		if(itemGot.equals("Select Word"))
			wordRet=word.getText();
		else
			wordRet=itemGot;
		if(wordRet.length()>20)
			errorLabel.setText("Too large word!");
		else if(wordRet.equals(""))
			errorLabel.setText("Please enter a valid word!");
		else
		{
			try
			{
				Statement s=conn.createStatement();
				ResultSet rs=s.executeQuery("select hseqmulti.nextval from dual");
				int slno=0;
				while(rs.next())
				{
					slno=rs.getInt(1);
				}
				if(name.getText().equals(""))
					errorLabel.setText("Please enter your name!");
				else
				{
					String nameGot=name.getText();
					s=conn.createStatement();
					rs=s.executeQuery("select word from multiplayer_data where name='"+nameGot+"'");
					int flag=0;
					while(rs.next())
					{
						String wordGot2=rs.getString(1);
						if(wordRet.equals(wordGot2))
						{
							flag++;
							break;
						}
					}
					if(flag==0)
					{
						PreparedStatement ps=conn.prepareStatement("insert into multiplayer_data values(?,?,?)");
						ps.setInt(1,slno);
						ps.setString(2,nameGot);
						ps.setString(3,wordRet);
						int status=ps.executeUpdate();
						if(status>0)
						{
							MyFrame.counter=0;
							MyFrame.chooseModeAction(wordRet,"","");
							setVisible(false);
							dispose();
						}
						else
							errorLabel.setText("Error!");
					}
					else
					{
						MyFrame.counter=0;
						MyFrame.chooseModeAction(wordRet,"","");
						setVisible(false);
						dispose();
					}
				}
			}
			catch(Exception ee)
			{
				ee.printStackTrace();
			}
		}	
	}

	public void viewAction(ActionEvent ae)
	{
		if(name.getText().equals(""))
			errorLabel.setText("Please enter your name to see the words!");
		else
		{
			try
			{
				String nt=name.getText();
				PreparedStatement ps=conn.prepareStatement("select word from multiplayer_data where name=?");
				ps.setString(1,nt);
				ResultSet rs=ps.executeQuery();
				wordList.removeAllItems();
				wordList.addItem("Select Word");
				int count=0;
				while(rs.next())
				{
					wordList.addItem(rs.getString(1));
					count++;
				}
				if(count>0)
					errorLabel.setText("");
				else if(count==0)
					errorLabel.setText("There is no word saved in this name!");
			}
			catch(Exception ee)
			{
				ee.printStackTrace();
			}
		}
	}

	public void viewEntered(MouseEvent me)
	{
		hintLabel.setText("Click to view the words you have saved");
	}

	public void viewExited(MouseEvent me)
	{
		hintLabel.setText("");
	}
}

class Login extends JFrame
{
	static Container c;
	static JPanel main_panel;
	static Color col1, col2;
	static ImageIcon ii1, ii2;
	static JLabel userIcon, signUpLabel, forgotLabel, wrongLabel;
	static JButton login, signup, forgot, view;
	static JTextField userName, passwordField;
	static JPasswordField password;
	static Font f1, f2;
	static Cursor cur1;
	static Connection conn;
	static String ud="";
	static String pd="";
	Login()
	{
		//container
		c=this.getContentPane();
		c.setLayout(null);

		//fonts,cursors and colors
		f1=new Font("Calibri",Font.PLAIN,15);
		f2=new Font("Calibri",Font.PLAIN,18);
		cur1=new Cursor(Cursor.HAND_CURSOR);
		col1=new Color(0,0,64);
		col2=new Color(112,146,190);

		//image
		ii1=new ImageIcon("USER.png");
		ii2=new ImageIcon("VIEW_PASSWORD.jpeg");

		//label for the user icon image
		userIcon=new JLabel(ii1);
		userIcon.setBounds(425,100,ii1.getIconWidth(),ii1.getIconHeight());

		//textField for user name
		userName=new JTextField("User Name");
		userName.setBounds(175,130,200,35);
		userName.setBackground(col2);
		userName.setForeground(col1);
		userName.setFont(f1);
		userName.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		userName.addFocusListener(new FocusListener()
			{
				public void focusGained(FocusEvent fe)
				{
					userNameFocusGained(fe);
				}
				public void focusLost(FocusEvent fe)
				{
					userNameFocusLost(fe);
				}
			});

		//password field
		password=new JPasswordField("Password");
		password.setEchoChar('*');
		password.setBounds(175,210,200,35);
		password.setBackground(col2);
		password.setForeground(col1);
		password.setFont(f1);
		password.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		password.addFocusListener(new FocusListener()
			{
				public void focusGained(FocusEvent fe)
				{
					passwordFocusGained(fe);
				}
				public void focusLost(FocusEvent fe)
				{
					passwordFocusLost(fe);
				}
			});

		//login button
		login=new JButton("LOG IN");
		login.setBounds(175,290,200,35);
		login.setFont(f2);
		login.setForeground(col1);
		login.setBackground(col2);
		login.setBorder(BorderFactory.createEmptyBorder());
		login.setCursor(cur1);
		login.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					loginAction(ae);
				}
			});

		//label for displaying about signing up
		signUpLabel=new JLabel("Not an existing user?");
		signUpLabel.setFont(f1);
		signUpLabel.setForeground(col2);
		signUpLabel.setBounds(310,370,175,35);

		//button to enter into the signing up section
		signup=new JButton("Sign up here.");
		signup.setBounds(335,375,300,25);
		signup.setFont(f1);
		signup.setForeground(col2);
		signup.setOpaque(false);
		signup.setContentAreaFilled(false);
		signup.setBorder(BorderFactory.createEmptyBorder());
		signup.setCursor(cur1);
		signup.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					signupAction(ae);
				}
			});

		//label for forgot password
		forgotLabel=new JLabel("Forgot password?");
		forgotLabel.setFont(f1);
		forgotLabel.setForeground(col2);
		forgotLabel.setBounds(320,400,150,35);

		//label for displaying wrong match
		wrongLabel=new JLabel();
		wrongLabel.setFont(f1);
		wrongLabel.setForeground(Color.WHITE);
		wrongLabel.setBounds(330,430,200,35);

		//button to recover password
		forgot=new JButton("Recover here.");
		forgot.setBounds(325,405,300,25);
		forgot.setFont(f1);
		forgot.setForeground(col2);
		forgot.setOpaque(false);
		forgot.setContentAreaFilled(false);
		forgot.setBorder(BorderFactory.createEmptyBorder());
		forgot.setCursor(cur1);
		forgot.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					forgotAction(ae);
				}
			});

		//password view button
		view=new JButton(ii2);
		view.setBounds(380,222,ii2.getIconWidth(),ii2.getIconHeight());
		view.setBorder(BorderFactory.createEmptyBorder());
		view.addMouseListener(new MouseAdapter()
			{
				public void mousePressed(MouseEvent me)
				{
					viewPressed(me);
				}
				public void mouseReleased(MouseEvent me)
				{
					viewReleased(me);
				}
			});

		//main panel
		main_panel=new JPanel();
		main_panel.setLayout(null);
		main_panel.setBounds(0,0,800,500);
		main_panel.setBackground(col1);

		//components of main panel
		main_panel.add(userIcon);
		main_panel.add(userName);
		main_panel.add(password);
		main_panel.add(login);
		main_panel.add(signUpLabel);
		main_panel.add(signup);
		main_panel.add(forgot);
		main_panel.add(forgotLabel);
		main_panel.add(wrongLabel);
		main_panel.add(view);

		//adding the main panel to the container
		c.add(main_panel);

		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","prateekshyap","Soni1999");
		}
		catch(Exception ee)
		{
			ee.printStackTrace();
		}
	}

	public void userNameFocusGained(FocusEvent fe)
	{
		if(userName.getText().equals("User Name"))
		{
			userName.setText("");
		}
	}

	public void userNameFocusLost(FocusEvent fe)
	{
		if(userName.getText().equals(""))
		{
			userName.setText("User Name");
		}
	}

	public void passwordFocusGained(FocusEvent fe)
	{
		if(password.getText().equals("Password"))
		{
			password.setText("");
		}
	}

	public void passwordFocusLost(FocusEvent fe)
	{
		if(password.getText().equals(""))
		{
			password.setText("Password");
		}
	}

	public void loginAction(ActionEvent ae)
	{
		try{
		String ut=userName.getText();
		String pt=password.getText();
		Statement s=conn.createStatement();
		ResultSet rs=s.executeQuery("select user_id,password from hangman_admin");
		int flag=0;
		while(rs.next())
		{
			ud=rs.getString(1);
			pd=rs.getString(2);
			if(ut.equals(ud)&&pt.equals(pd))
			{
				flag++;
				break;
			}
		}
		if(flag==1)
		{
			DataEntry de=new DataEntry(ud);
			de.setVisible(true);
			de.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			de.setBounds(200,50,1100,650);
			de.setResizable(false);
			setVisible(false);
			dispose();
		}
		else
		{
			wrongLabel.setText("Wrong user ID or password!");
		}
		}catch(Exception ee)
		{
			wrongLabel.setText("Error!");
		}
	}

	public void signupAction(ActionEvent ae)
	{
		SignUp su=new SignUp();
		su.setVisible(true);
		su.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		su.setBounds(200,50,1100,650);
		su.setResizable(false);
		setVisible(false);
		dispose();
	}

	public void forgotAction(ActionEvent ae)
	{
		Recover r=new Recover();
		r.setVisible(true);
		r.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		r.setBounds(350,100,800,500);
		r.setResizable(false);
		setVisible(false);
		dispose();
	}

	public void viewPressed(MouseEvent ae)
	{
		password.setEchoChar((char)0);
	}

	public void viewReleased(MouseEvent me)
	{
		password.setEchoChar('*');
	}
}

class DataEntry extends JFrame implements ActionListener
{
	static Container c;
	static JPanel main_panel;
	static Font f1, f2, f3;
	static Color col1, col2;
	static Cursor cur1;
	static String adminID="";
	static JLabel chooseCat, errorLabel;
	static JButton country, animal, bird, car, city, river, scientist, nobelist, mathematician, cs, olympics, cricket, save;
	static JTextField word, hint, link;
	static Connection conn;
	static int sl;
	static int catSl;
	static String name="";
	static String catName="";
	static String catCode="";
	static String hintStr="";
	static String linkStr="";
	DataEntry(String adminID)
	{
		this.adminID=adminID;

		//container
		c=this.getContentPane();
		c.setLayout(null);

		//fonts,cursors and colours
		f1=new Font("Calibri",Font.PLAIN,15);
		f2=new Font("Calibri",Font.PLAIN,18);
		f3=new Font("Calibri",Font.BOLD,20);
		cur1=new Cursor(Cursor.HAND_CURSOR);
		col1=new Color(0,0,64);
		col2=new Color(197,210,228);

		//label for category
		chooseCat=new JLabel("Choose Category");
		chooseCat.setBounds(470,25,200,50);
		chooseCat.setFont(f3);
		chooseCat.setForeground(col1);

		//country button
		country=new JButton("Countries");
		country.setBounds(75,75,200,50);
		country.setFont(f2);
		country.setForeground(col1);
		country.setOpaque(false);
		country.setContentAreaFilled(false);
		country.setBorder(BorderFactory.createEmptyBorder());
		country.setCursor(cur1);
		country.addActionListener(this);

		//animal button
		animal=new JButton("Animals");
		animal.setBounds(325,75,200,50);
		animal.setFont(f2);
		animal.setForeground(col1);
		animal.setOpaque(false);
		animal.setContentAreaFilled(false);
		animal.setBorder(BorderFactory.createEmptyBorder());
		animal.setCursor(cur1);
		animal.addActionListener(this);

		//bird button
		bird=new JButton("Birds");
		bird.setBounds(575,75,200,50);
		bird.setFont(f2);
		bird.setForeground(col1);
		bird.setOpaque(false);
		bird.setContentAreaFilled(false);
		bird.setBorder(BorderFactory.createEmptyBorder());
		bird.setCursor(cur1);
		bird.addActionListener(this);

		//car button
		car=new JButton("Cars");
		car.setBounds(825,75,200,50);
		car.setFont(f2);
		car.setForeground(col1);
		car.setOpaque(false);
		car.setContentAreaFilled(false);
		car.setBorder(BorderFactory.createEmptyBorder());
		car.setCursor(cur1);
		car.addActionListener(this);

		//city button
		city=new JButton("Cities");
		city.setBounds(75,125,200,50);
		city.setFont(f2);
		city.setForeground(col1);
		city.setOpaque(false);
		city.setContentAreaFilled(false);
		city.setBorder(BorderFactory.createEmptyBorder());
		city.setCursor(cur1);
		city.addActionListener(this);

		//river button
		river=new JButton("Rivers");
		river.setBounds(325,125,200,50);
		river.setFont(f2);
		river.setForeground(col1);
		river.setOpaque(false);
		river.setContentAreaFilled(false);
		river.setBorder(BorderFactory.createEmptyBorder());
		river.setCursor(cur1);
		river.addActionListener(this);

		//scientist button
		scientist=new JButton("Scientists");
		scientist.setBounds(575,125,200,50);
		scientist.setFont(f2);
		scientist.setForeground(col1);
		scientist.setOpaque(false);
		scientist.setContentAreaFilled(false);
		scientist.setBorder(BorderFactory.createEmptyBorder());
		scientist.setCursor(cur1);
		scientist.addActionListener(this);

		//nobelist button
		nobelist=new JButton("Nobelists");
		nobelist.setBounds(825,125,200,50);
		nobelist.setFont(f2);
		nobelist.setForeground(col1);
		nobelist.setOpaque(false);
		nobelist.setContentAreaFilled(false);
		nobelist.setBorder(BorderFactory.createEmptyBorder());
		nobelist.setCursor(cur1);
		nobelist.addActionListener(this);

		//mathematician button
		mathematician=new JButton("Mathematicians");
		mathematician.setBounds(75,175,200,50);
		mathematician.setFont(f2);
		mathematician.setForeground(col1);
		mathematician.setOpaque(false);
		mathematician.setContentAreaFilled(false);
		mathematician.setBorder(BorderFactory.createEmptyBorder());
		mathematician.setCursor(cur1);
		mathematician.addActionListener(this);

		//cs button
		cs=new JButton("Computer Science");
		cs.setBounds(325,175,200,50);
		cs.setFont(f2);
		cs.setForeground(col1);
		cs.setOpaque(false);
		cs.setContentAreaFilled(false);
		cs.setBorder(BorderFactory.createEmptyBorder());
		cs.setCursor(cur1);
		cs.addActionListener(this);

		//olympics button
		olympics=new JButton("Olympics");
		olympics.setBounds(575,175,200,50);
		olympics.setFont(f2);
		olympics.setForeground(col1);
		olympics.setOpaque(false);
		olympics.setContentAreaFilled(false);
		olympics.setBorder(BorderFactory.createEmptyBorder());
		olympics.setCursor(cur1);
		olympics.addActionListener(this);

		//cricket button
		cricket=new JButton("Cricket");
		cricket.setBounds(825,175,200,50);
		cricket.setFont(f2);
		cricket.setForeground(col1);
		cricket.setOpaque(false);
		cricket.setContentAreaFilled(false);
		cricket.setBorder(BorderFactory.createEmptyBorder());
		cricket.setCursor(cur1);
		cricket.addActionListener(this);

		//textfield for word
		word=new JTextField();
		word.setBounds(400,275,300,35);
		word.setFont(f1);
		word.setOpaque(false);
		word.setBorder(BorderFactory.createEmptyBorder());
		word.setEditable(false);
		word.addFocusListener(new FocusListener()
			{
				public void focusGained(FocusEvent fe)
				{
					wordFocusGained(fe);
				}
				public void focusLost(FocusEvent fe)
				{
					wordFocusLost(fe);
				}
			});

		//textfield for hint
		hint=new JTextField();
		hint.setBounds(400,350,300,35);
		hint.setFont(f1);
		hint.setOpaque(false);
		hint.setBorder(BorderFactory.createEmptyBorder());
		hint.setEditable(false);
		hint.addFocusListener(new FocusListener()
			{
				public void focusGained(FocusEvent fe)
				{
					hintFocusGained(fe);
				}
				public void focusLost(FocusEvent fe)
				{
					hintFocusLost(fe);
				}
			});

		//textfield for link
		link=new JTextField();
		link.setBounds(400,425,300,35);
		link.setFont(f1);
		link.setOpaque(false);
		link.setBorder(BorderFactory.createEmptyBorder());
		link.setEditable(false);
		link.addFocusListener(new FocusListener()
			{
				public void focusGained(FocusEvent fe)
				{
					linkFocusGained(fe);
				}
				public void focusLost(FocusEvent fe)
				{
					linkFocusLost(fe);
				}
			});

		//save button
		save=new JButton();
		save.setBounds(500,525,100,40);
		save.setFont(f2);
		save.setForeground(col2);
		save.setBorder(BorderFactory.createEmptyBorder());
		save.setCursor(cur1);
		save.setBackground(col2);
		save.setEnabled(false);
		save.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					saveAction(ae);
				}
			});

		//label for error
		errorLabel=new JLabel();
		errorLabel.setBounds(400,575,300,50);
		errorLabel.setFont(f1);
		errorLabel.setForeground(Color.BLACK);

		//main panel
		main_panel=new JPanel();
		main_panel.setLayout(null);
		main_panel.setBounds(0,0,1100,650);
		main_panel.setBackground(col2);

		//components of the main panel
		main_panel.add(chooseCat);
		main_panel.add(country);
		main_panel.add(animal);
		main_panel.add(bird);
		main_panel.add(car);
		main_panel.add(city);
		main_panel.add(river);
		main_panel.add(scientist);
		main_panel.add(nobelist);
		main_panel.add(mathematician);
		main_panel.add(cs);
		main_panel.add(olympics);
		main_panel.add(cricket);
		main_panel.add(word);
		main_panel.add(hint);
		main_panel.add(link);
		main_panel.add(save);
		main_panel.add(errorLabel);

		//adding the main panel to the container
		c.add(main_panel);

		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","prateekshyap","Soni1999");
		}
		catch(Exception ee)
		{
			ee.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent ae)
	{
		try{
		if(ae.getSource()==country)
		{
			Statement s=conn.createStatement();
			ResultSet rs=s.executeQuery("select hseqmain.nextval from dual");
			while(rs.next())
			{
				sl=rs.getInt(1);
			}
			s=conn.createStatement();
			rs=s.executeQuery("select hseqco.nextval from dual");
			while(rs.next())
			{
				catSl=rs.getInt(1);
			}
			catName="Countries";
			catCode="CO";
			word.setEditable(true);
			word.setText("Enter word");
			hint.setEditable(true);
			hint.setText("Enter a hint for the word");
			link.setEditable(true);
			link.setText("Provide a website for the word");
			save.setEnabled(true);
			save.setText("Save");
			save.setBackground(col1);
		}
		else if(ae.getSource()==animal)
		{
			Statement s=conn.createStatement();
			ResultSet rs=s.executeQuery("select hseqmain.nextval from dual");
			while(rs.next())
			{
				sl=rs.getInt(1);
			}
			s=conn.createStatement();
			rs=s.executeQuery("select hseqan.nextval from dual");
			while(rs.next())
			{
				catSl=rs.getInt(1);
			}
			catName="Animals";
			catCode="AN";
			word.setEditable(true);
			word.setText("Enter word");
			hint.setEditable(true);
			hint.setText("Enter a hint for the word");
			link.setEditable(true);
			link.setText("Provide a website for the word");
			save.setEnabled(true);
			save.setText("Save");
			save.setBackground(col1);
		}
		else if(ae.getSource()==bird)
		{
			Statement s=conn.createStatement();
			ResultSet rs=s.executeQuery("select hseqmain.nextval from dual");
			while(rs.next())
			{
				sl=rs.getInt(1);
			}
			s=conn.createStatement();
			rs=s.executeQuery("select hseqbi.nextval from dual");
			while(rs.next())
			{
				catSl=rs.getInt(1);
			}
			catName="Birds";
			catCode="BI";
			word.setEditable(true);
			word.setText("Enter word");
			hint.setEditable(true);
			hint.setText("Enter a hint for the word");
			link.setEditable(true);
			link.setText("Provide a website for the word");
			save.setEnabled(true);
			save.setText("Save");
			save.setBackground(col1);
		}
		else if(ae.getSource()==car)
		{
			Statement s=conn.createStatement();
			ResultSet rs=s.executeQuery("select hseqmain.nextval from dual");
			while(rs.next())
			{
				sl=rs.getInt(1);
			}
			s=conn.createStatement();
			rs=s.executeQuery("select hseqca.nextval from dual");
			while(rs.next())
			{
				catSl=rs.getInt(1);
			}
			catName="Cars";
			catCode="CA";
			word.setEditable(true);
			word.setText("Enter word");
			hint.setEditable(true);
			hint.setText("Enter a hint for the word");
			link.setEditable(true);
			link.setText("Provide a website for the word");
			save.setEnabled(true);
			save.setText("Save");
			save.setBackground(col1);
		}
		else if(ae.getSource()==city)
		{
			Statement s=conn.createStatement();
			ResultSet rs=s.executeQuery("select hseqmain.nextval from dual");
			while(rs.next())
			{
				sl=rs.getInt(1);
			}
			s=conn.createStatement();
			rs=s.executeQuery("select hseqci.nextval from dual");
			while(rs.next())
			{
				catSl=rs.getInt(1);
			}
			catName="Cities";
			catCode="CI";
			word.setEditable(true);
			word.setText("Enter word");
			hint.setEditable(true);
			hint.setText("Enter a hint for the word");
			link.setEditable(true);
			link.setText("Provide a website for the word");
			save.setEnabled(true);
			save.setText("Save");
			save.setBackground(col1);
		}
		else if(ae.getSource()==river)
		{
			Statement s=conn.createStatement();
			ResultSet rs=s.executeQuery("select hseqmain.nextval from dual");
			while(rs.next())
			{
				sl=rs.getInt(1);
			}
			s=conn.createStatement();
			rs=s.executeQuery("select hseqri.nextval from dual");
			while(rs.next())
			{
				catSl=rs.getInt(1);
			}
			catName="Rivers";
			catCode="RI";
			word.setEditable(true);
			word.setText("Enter word");
			hint.setEditable(true);
			hint.setText("Enter a hint for the word");
			link.setEditable(true);
			link.setText("Provide a website for the word");
			save.setEnabled(true);
			save.setText("Save");
			save.setBackground(col1);
		}
		else if(ae.getSource()==scientist)
		{
			Statement s=conn.createStatement();
			ResultSet rs=s.executeQuery("select hseqmain.nextval from dual");
			while(rs.next())
			{
				sl=rs.getInt(1);
			}
			s=conn.createStatement();
			rs=s.executeQuery("select hseqsc.nextval from dual");
			while(rs.next())
			{
				catSl=rs.getInt(1);
			}
			catName="Scientists";
			catCode="SC";
			word.setEditable(true);
			word.setText("Enter word");
			hint.setEditable(true);
			hint.setText("Enter a hint for the word");
			link.setEditable(true);
			link.setText("Provide a website for the word");
			save.setEnabled(true);
			save.setText("Save");
			save.setBackground(col1);
		}
		else if(ae.getSource()==nobelist)
		{
			Statement s=conn.createStatement();
			ResultSet rs=s.executeQuery("select hseqmain.nextval from dual");
			while(rs.next())
			{
				sl=rs.getInt(1);
			}
			s=conn.createStatement();
			rs=s.executeQuery("select hseqno.nextval from dual");
			while(rs.next())
			{
				catSl=rs.getInt(1);
			}
			catName="Nobelists";
			catCode="NO";
			word.setEditable(true);
			word.setText("Enter word");
			hint.setEditable(true);
			hint.setText("Enter a hint for the word");
			link.setEditable(true);
			link.setText("Provide a website for the word");
			save.setEnabled(true);
			save.setText("Save");
			save.setBackground(col1);
		}
		else if(ae.getSource()==mathematician)
		{
			Statement s=conn.createStatement();
			ResultSet rs=s.executeQuery("select hseqmain.nextval from dual");
			while(rs.next())
			{
				sl=rs.getInt(1);
			}
			s=conn.createStatement();
			rs=s.executeQuery("select hseqma.nextval from dual");
			while(rs.next())
			{
				catSl=rs.getInt(1);
			}
			catName="Mathematicians";
			catCode="MA";
			word.setEditable(true);
			word.setText("Enter word");
			hint.setEditable(true);
			hint.setText("Enter a hint for the word");
			link.setEditable(true);
			link.setText("Provide a website for the word");
			save.setEnabled(true);
			save.setText("Save");
			save.setBackground(col1);
		}
		else if(ae.getSource()==cs)
		{
			Statement s=conn.createStatement();
			ResultSet rs=s.executeQuery("select hseqmain.nextval from dual");
			while(rs.next())
			{
				sl=rs.getInt(1);
			}
			s=conn.createStatement();
			rs=s.executeQuery("select hseqcs.nextval from dual");
			while(rs.next())
			{
				catSl=rs.getInt(1);
			}
			catName="Computer Science";
			catCode="CS";
			word.setEditable(true);
			word.setText("Enter word");
			hint.setEditable(true);
			hint.setText("Enter a hint for the word");
			link.setEditable(true);
			link.setText("Provide a website for the word");
			save.setEnabled(true);
			save.setText("Save");
			save.setBackground(col1);
		}
		else if(ae.getSource()==olympics)
		{
			Statement s=conn.createStatement();
			ResultSet rs=s.executeQuery("select hseqmain.nextval from dual");
			while(rs.next())
			{
				sl=rs.getInt(1);
			}
			s=conn.createStatement();
			rs=s.executeQuery("select hseqol.nextval from dual");
			while(rs.next())
			{
				catSl=rs.getInt(1);
			}
			catName="Olympics";
			catCode="OL";
			word.setEditable(true);
			word.setText("Enter word");
			hint.setEditable(true);
			hint.setText("Enter a hint for the word");
			link.setEditable(true);
			link.setText("Provide a website for the word");
			save.setEnabled(true);
			save.setText("Save");
			save.setBackground(col1);
		}
		else if(ae.getSource()==cricket)
		{
			Statement s=conn.createStatement();
			ResultSet rs=s.executeQuery("select hseqmain.nextval from dual");
			while(rs.next())
			{
				sl=rs.getInt(1);
			}
			s=conn.createStatement();
			rs=s.executeQuery("select hseqcr.nextval from dual");
			while(rs.next())
			{
				catSl=rs.getInt(1);
			}
			catName="Cricket";
			catCode="CR";
			word.setEditable(true);
			word.setText("Enter word");
			hint.setEditable(true);
			hint.setText("Enter a hint for the word");
			link.setEditable(true);
			link.setText("Provide a website for the word");
			save.setEnabled(true);
			save.setText("Save");
			save.setBackground(col1);
		}
		else 
		{
			errorLabel.setText("Error!");
		}
		}catch(Exception ee)
		{
			ee.printStackTrace();
		}
	}

	public void saveAction(ActionEvent ae)
	{
		try{
		name=word.getText();
		hintStr=hint.getText();
		linkStr=link.getText();
		char[] linkArr=linkStr.toCharArray();
		int flag=0;
		String splitter="[./:]";
		String[] st=linkStr.split(splitter,0);
		for(int i=0;i<st.length;i++)
		{
			if(st[i].equals("co")||st[i].equals("in")||st[i].equals("com")||st[i].equals("edu")||st[i].equals("gov")||st[i].equals("org")||st[i].equals("info")||st[i].equals("biz")||st[i].equals("pro")||st[i].equals("ca")||st[i].equals("us")||st[i].equals("uk")||st[i].equals("eu")||st[i].equals("nl")||st[i].equals("en"))
				flag++;
			if(st[i].equals("http")||st[i].equals("https"))
				flag++;
			if(st[i].equals("www"))
				flag++;
			if(st[i].equals("wiki"))
				flag++;
		}
		if(flag>=3)
		{
			PreparedStatement ps=conn.prepareStatement("insert into hangman_game values(?,?,?,?,?,?,?,?)");
			ps.setInt(1,sl);
			ps.setInt(2,catSl);
			ps.setString(3,name);
			ps.setString(4,catName);
			ps.setString(5,catCode);
			ps.setString(6,hintStr);
			ps.setString(7,linkStr);
			ps.setString(8,adminID);
			int status=ps.executeUpdate();
			if(status>0)
			{
				errorLabel.setText("Saved successfully!");
				word.setText("");
				word.setEditable(false);
				hint.setText("");
				hint.setEditable(false);
				link.setText("");
				link.setEditable(false);
				save.setBackground(col2);
				save.setText("");
				save.setEnabled(false);
			}
			else
			{
				errorLabel.setText("Error!");
			}
		}
		else
		{
			errorLabel.setText("Not a valid website!");
		}
		}catch(Exception ee)
		{
			ee.printStackTrace();
		}
	}

	public void wordFocusGained(FocusEvent fe)
	{
		if(word.getText().equals("Enter word"))
			word.setText("");
	}

	public void wordFocusLost(FocusEvent fe)
	{
		if(word.getText().equals(""))
			word.setText("Enter word");
	}

	public void hintFocusGained(FocusEvent fe)
	{
		if(hint.getText().equals("Enter a hint for the word"))
			hint.setText("");
	}

	public void hintFocusLost(FocusEvent fe)
	{
		if(hint.getText().equals(""))
			hint.setText("Enter a hint for the word");
	}

	public void linkFocusGained(FocusEvent fe)
	{
		if(link.getText().equals("Provide a website for the word"))
			link.setText("");
	}

	public void linkFocusLost(FocusEvent fe)
	{
		if(link.getText().equals(""))
			link.setText("Provide a website for the word");
	}
}

class Recover extends JFrame
{
	static Container c;
	static JPanel main_panel;
	static Font f1, f2;
	static Color col1, col2;
	static Cursor cur1;
	static JButton submit, save, ok;
	static JPasswordField password1, password2;
	static JComboBox question;
	static JTextField answer, userid;
	static JLabel warning;
	static Connection conn;
	static String ud="";
	static String qd="";
	Recover()
	{
		//container
		c=this.getContentPane();
		c.setLayout(null);

		//fonts,cursors and colours
		f1=new Font("Calibri",Font.PLAIN,15);
		f2=new Font("Calibri",Font.PLAIN,18);
		cur1=new Cursor(Cursor.HAND_CURSOR);
		col1=new Color(0,0,64);
		col2=new Color(112,146,190);

		//combobox for security question
		question=new JComboBox();
		question.setBounds(50,50,300,35);
		question.addItem("Select security question");
		question.addItem("Where were you born?");
		question.addItem("What is your first phone number?");
		question.addItem("Which is your favourite web browser?");
		question.addItem("What is the last name of your favourite teacher?");
		question.addItem("What is your grandmother's name?");
		question.setEnabled(false);
		question.setFont(f1);

		//textfield for answer
		answer=new JTextField("Answer");
		answer.setFont(f1);
		answer.setBounds(400,50,150,35);
		answer.setForeground(col1);
		answer.setBackground(col2);
		answer.setEditable(false);
		answer.setBorder(BorderFactory.createEmptyBorder());
		answer.addFocusListener(new FocusListener()
			{
				public void focusGained(FocusEvent fe)
				{
					answerFocusGained(fe);
				}
				public void focusLost(FocusEvent fe)
				{
					answerFocusLost(fe);
				}
			});

		//textfield for userid
		userid=new JTextField("User ID");
		userid.setFont(f1);
		userid.setBounds(600,50,115,35);
		userid.setForeground(col1);
		userid.setBackground(col2);
		userid.setBorder(BorderFactory.createEmptyBorder());
		userid.addFocusListener(new FocusListener()
			{
				public void focusGained(FocusEvent fe)
				{
					useridFocusGained(fe);
				}
				public void focusLost(FocusEvent fe)
				{
					useridFocusLost(fe);
				}
			});

		//submit button to recover the password
		submit=new JButton("Submit");
		submit.setBounds(350,150,100,40);
		submit.setFont(f2);
		submit.setForeground(col1);
		submit.setBackground(col2);
		submit.setBorder(BorderFactory.createEmptyBorder());
		submit.setCursor(cur1);
		submit.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					submitAction(ae);
				}
			});

		//label for warning message
		warning=new JLabel("Enter your user ID and click OK");
		warning.setFont(f1);
		warning.setForeground(Color.WHITE);
		warning.setBounds(300,200,300,35);

		//password fields for new 
		password1=new JPasswordField();
		password1.setEchoChar((char)0);
		password1.setFont(f1);
		password1.setBounds(260,275,125,30);
		password1.setForeground(col2);
		password1.setOpaque(false);
		password1.setBorder(BorderFactory.createEmptyBorder());
		password1.setEditable(false);
		password1.addFocusListener(new FocusListener()
			{
				public void focusGained(FocusEvent fe)
				{
					password1FocusGained(fe);
				}
				public void focusLost(FocusEvent fe)
				{
					password1FocusLost(fe);
				}
			});

		password2=new JPasswordField();
		password2.setEchoChar((char)0);
		password2.setFont(f1);
		password2.setBounds(410,275,125,30);
		password2.setForeground(col2);
		password2.setOpaque(false);
		password2.setBorder(BorderFactory.createEmptyBorder());
		password2.setEditable(false);
		password2.addFocusListener(new FocusListener()
			{
				public void focusGained(FocusEvent fe)
				{
					password2FocusGained(fe);
				}
				public void focusLost(FocusEvent fe)
				{
					password2FocusLost(fe);
				}
			});

		//button to save the password
		save=new JButton();
		save.setBounds(350,375,100,40);
		save.setFont(f2);
		save.setForeground(col1);
		save.setBackground(col1);
		save.setBorder(BorderFactory.createEmptyBorder());
		save.setCursor(cur1);
		save.setEnabled(false);
		save.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					saveAction(ae);
				}
			});

		//button for the userID
		//button to save the password
		ok=new JButton("OK");
		ok.setBounds(720,50,30,35);
		ok.setFont(f1);
		ok.setForeground(col1);
		ok.setBackground(col2);
		ok.setBorder(BorderFactory.createEmptyBorder());
		ok.setCursor(cur1);
		ok.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					okAction(ae);
				}
			});

		//main panel
		main_panel=new JPanel();
		main_panel.setLayout(null);
		main_panel.setBounds(0,0,800,500);
		main_panel.setBackground(col1);

		//components of main panel
		main_panel.add(question);
		main_panel.add(answer);
		main_panel.add(submit);
		main_panel.add(userid);
		main_panel.add(warning);
		main_panel.add(password1);
		main_panel.add(password2);
		main_panel.add(save);
		main_panel.add(ok);

		//adding the main panel to the container
		c.add(main_panel);

		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","prateekshyap","Soni1999");
		}
		catch(Exception ee)
		{
			ee.printStackTrace();
		}
	}

	public void answerFocusGained(FocusEvent fe)
	{
		if(answer.getText().equals("Answer"))
			answer.setText("");
	}

	public void answerFocusLost(FocusEvent fe)
	{
		if(answer.getText().equals(""))
			answer.setText("Answer");
	}

	public void okAction(ActionEvent ae)
	{
		try{
		String ut=userid.getText();//hif
		Statement s=conn.createStatement();	
		ResultSet rs=s.executeQuery("select user_id from hangman_admin");
		int flag=0;
		while(rs.next())
		{
			ud=rs.getString(1);
			if(ud.equals(ut))
			{
				flag++;
				break;
			}
		}
		if(flag==1)
		{
			userid.setEditable(false);
			ok.setEnabled(false);
			answer.setEditable(true);
			PreparedStatement ps=conn.prepareStatement("select question from hangman_admin where user_id=?");
			ps.setString(1,ud);
			ResultSet rs2=ps.executeQuery();
			while(rs2.next())
			{
				qd=rs2.getString(1);
				question.setSelectedItem(qd);
				warning.setText("Enter the answer and click Submit.");
			}
		}
		else
		{
			warning.setText("User ID does not exist!");
		}
		}catch(Exception ee)
		{
			warning.setText("Error!");
		}
	}

	public void submitAction(ActionEvent ae)
	{
		try
		{
			int flag=0;
			String at=answer.getText();//8280175296
			String ad="";
			PreparedStatement ps=conn.prepareStatement("select answer from hangman_admin where user_id=?");
			ps.setString(1,ud);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				ad=rs.getString(1);
				if(at.equals(ad))
				{
					flag++;
					break;
				}
			}
			if(flag==1)
			{
				warning.setText("");
				save.setEnabled(true);
				save.setBackground(col2);
				save.setText("Save");
				password1.setEditable(true);
				password1.setText("New Password");
				password2.setEditable(true);
				password2.setText("Re-type Password");
				answer.setEditable(false);
			}
			else
			{
				warning.setText("Question and answer do not match!");
			}
		}
		catch(Exception ee)
		{
			warning.setText("Error!");
		}
	}

	public void saveAction(ActionEvent ae)
	{
		try{
		String pw1t=password1.getText();
		String pw2t=password2.getText();
		String pwd="";
		if(pw1t.equals(pw2t)==false)
		{
			warning.setText("Passwords do not match!");
		}
		else
		{
			String ut=userid.getText();
			int flag=0;
			PreparedStatement ps=conn.prepareStatement("select password from hangman_admin where user_id=?");
			ps.setString(1,ut);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				pwd=rs.getString(1);
				if(pwd.equals(pw1t))
				{
					flag++;
					break;
				}
			}
			if(flag==1)
			{
				warning.setText("Too similar with the old password!");
			}
			else
			{
				warning.setText("");
				ps=conn.prepareStatement("update hangman_admin set password=? where user_id=?");
				ps.setString(1,pw1t);
				ps.setString(2,ud);
				int status=ps.executeUpdate();
				if(status>0)
				{
					Login lg=new Login();
					lg.setVisible(true);
					lg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					lg.setBounds(350,100,800,500);
					lg.setResizable(false);
					setVisible(false);
					dispose();
				}
				else
				{
					warning.setText("Error!");
				}
			}
		}
		}catch(Exception ee)
		{
			ee.printStackTrace();
		}
	}

	public void useridFocusGained(FocusEvent fe)
	{
		if(userid.getText().equals("User ID"))
			userid.setText("");
	}

	public void useridFocusLost(FocusEvent fe)
	{
		if(userid.getText().equals(""))
			userid.setText("User ID");
	}

	public void password1FocusGained(FocusEvent fe)
	{
		if(password1.getText().equals("New Password"))
		{
			password1.setText("");
			password1.setEchoChar('*');
		}
	}

	public void password1FocusLost(FocusEvent fe)
	{
		if(password1.getText().equals(""))
		{
			password1.setText("New Password");
			password1.setEchoChar((char)0);
		}
	}

	public void password2FocusGained(FocusEvent fe)
	{
		if(password2.getText().equals("Re-type Password"))
		{
			password2.setText("");
			password2.setEchoChar('*');
		}
	}

	public void password2FocusLost(FocusEvent fe)
	{
		if(password2.getText().equals(""))
		{
			password2.setText("Re-type Password");
			password2.setEchoChar((char)0);
		}
	}
}

class SignUp extends JFrame
{
	static Container c;
	static JPanel main_panel;
	static Font f1, f2;
	static Color col1, col2;
	static Cursor cur1;
	static JTextField firstName, middleName, lastName, email, phone, userid, dob, answer;
	static JPasswordField password1, password2;
	static JComboBox profession, question;
	static JRadioButton male, female, other;
	static JButton signup, login;
	static JLabel wrongID, wrongEmail, wrongPhone, wrongPassword, wrongDOB;
	static Connection conn;
	SignUp()
	{
		//container
		c=this.getContentPane();
		c.setLayout(null);

		//fonts, cursors and colours
		f1=new Font("Calibri",Font.PLAIN,15);
		f2=new Font("Calibri",Font.PLAIN,18);
		cur1=new Cursor(Cursor.HAND_CURSOR);
		col1=new Color(0,0,64);
		col2=new Color(112,146,190);

		ButtonGroup gender=new ButtonGroup();

		//textfield for name
		firstName=new JTextField("First Name");
		firstName.setFont(f1);
		firstName.setBounds(200,100,85,30);
		firstName.setForeground(col2);
		firstName.setOpaque(false);
		firstName.setBorder(BorderFactory.createEmptyBorder());
		firstName.addFocusListener(new FocusListener()
			{
				public void focusGained(FocusEvent fe)
				{
					firstNameFocusGained(fe);
				}
				public void focusLost(FocusEvent fe)
				{
					firstNameFocusLost(fe);
				}
			});
		firstName.addKeyListener(new KeyAdapter()
			{
				public void keyTyped(KeyEvent ke)
				{
					firstNameTyped(ke);
				}
			});
		middleName=new JTextField("Middle Name");
		middleName.setFont(f1);
		middleName.setBounds(325,100,85,30);
		middleName.setForeground(col2);
		middleName.setOpaque(false);
		middleName.setBorder(BorderFactory.createEmptyBorder());
		middleName.addFocusListener(new FocusListener()
			{
				public void focusGained(FocusEvent fe)
				{
					middleNameFocusGained(fe);
				}
				public void focusLost(FocusEvent fe)
				{
					middleNameFocusLost(fe);
				}
			});
		middleName.addKeyListener(new KeyAdapter()
			{
				public void keyTyped(KeyEvent ke)
				{
					middleNameTyped(ke);
				}
			});
		lastName=new JTextField("Last Name");
		lastName.setFont(f1);
		lastName.setBounds(450,100,85,30);
		lastName.setForeground(col2);
		lastName.setOpaque(false);
		lastName.setBorder(BorderFactory.createEmptyBorder());
		lastName.addFocusListener(new FocusListener()
			{
				public void focusGained(FocusEvent fe)
				{
					lastNameFocusGained(fe);
				}
				public void focusLost(FocusEvent fe)
				{
					lastNameFocusLost(fe);
				}
			});
		lastName.addKeyListener(new KeyAdapter()
			{
				public void keyTyped(KeyEvent ke)
				{
					lastNameTyped(ke);
				}
			});

		//text field for email address
		email=new JTextField("Email address");
		email.setFont(f1);
		email.setBounds(200,200,300,30);
		email.setForeground(col2);
		email.setOpaque(false);
		email.setBorder(BorderFactory.createEmptyBorder());
		email.addFocusListener(new FocusListener()
			{
				public void focusGained(FocusEvent fe)
				{
					emailFocusGained(fe);
				}
				public void focusLost(FocusEvent fe)
				{
					emailFocusLost(fe);
				}
			});

		//label for wrong email id warning
		wrongEmail=new JLabel();
		wrongEmail.setBounds(200,235,300,30);
		wrongEmail.setFont(f1);
		wrongEmail.setForeground(Color.WHITE);

		//password fields for password
		password1=new JPasswordField("Password");
		password1.setEchoChar((char)0);
		password1.setFont(f1);
		password1.setBounds(200,300,125,30);
		password1.setForeground(col2);
		password1.setOpaque(false);
		password1.setBorder(BorderFactory.createEmptyBorder());
		password1.addFocusListener(new FocusListener()
			{
				public void focusGained(FocusEvent fe)
				{
					password1FocusGained(fe);
				}
				public void focusLost(FocusEvent fe)
				{
					password1FocusLost(fe);
				}
			});

		password2=new JPasswordField("Re-type Password");
		password2.setEchoChar((char)0);
		password2.setFont(f1);
		password2.setBounds(375,300,125,30);
		password2.setForeground(col2);
		password2.setOpaque(false);
		password2.setBorder(BorderFactory.createEmptyBorder());
		password2.addFocusListener(new FocusListener()
			{
				public void focusGained(FocusEvent fe)
				{
					password2FocusGained(fe);
				}
				public void focusLost(FocusEvent fe)
				{
					password2FocusLost(fe);
				}
			});

		//label for wrong password warning
		wrongPassword=new JLabel();
		wrongPassword.setBounds(200,335,300,30);
		wrongPassword.setFont(f1);
		wrongPassword.setForeground(Color.WHITE);

		//textfield for phone
		phone=new JTextField("Phone");
		phone.setFont(f1);
		phone.setBounds(200,400,125,30);
		phone.setForeground(col2);
		phone.setOpaque(false);
		phone.setBorder(BorderFactory.createEmptyBorder());
		phone.addFocusListener(new FocusListener()
			{
				public void focusGained(FocusEvent fe)
				{
					phoneFocusGained(fe);
				}
				public void focusLost(FocusEvent fe)
				{
					phoneFocusLost(fe);
				}
			});

		//label for wrong phone warning
		wrongPhone=new JLabel();
		wrongPhone.setBounds(200,435,300,30);
		wrongPhone.setFont(f1);
		wrongPhone.setForeground(Color.WHITE);

		//textfield for userid
		userid=new JTextField("Choose unique ID");
		userid.setFont(f1);
		userid.setBounds(375,400,125,30);
		userid.setForeground(col2);
		userid.setOpaque(false);
		userid.setBorder(BorderFactory.createEmptyBorder());
		userid.addFocusListener(new FocusListener()
			{
				public void focusGained(FocusEvent fe)
				{
					useridFocusGained(fe);
				}
				public void focusLost(FocusEvent fe)
				{
					useridFocusLost(fe);
				}
			});

		//label for wrong user id warning
		wrongID=new JLabel();
		wrongID.setBounds(375,435,300,30);
		wrongID.setFont(f1);
		wrongID.setForeground(Color.WHITE);

		//textfield for dob
		dob=new JTextField("Date of birth (dd/mm/yyyy)");
		dob.setFont(f1);
		dob.setBounds(600,100,300,30);
		dob.setForeground(col2);
		dob.setOpaque(false);
		dob.setBorder(BorderFactory.createEmptyBorder());
		dob.addFocusListener(new FocusListener()
			{
				public void focusGained(FocusEvent fe)
				{
					dobFocusGained(fe);
				}
				public void focusLost(FocusEvent fe)
				{
					dobFocusLost(fe);
				}
			});

		//label for wrong dob warning
		wrongDOB=new JLabel();
		wrongDOB.setBounds(600,135,300,30);
		wrongDOB.setFont(f1);
		wrongDOB.setForeground(Color.WHITE);

		//radio buttons for gender
		//male
		male=new JRadioButton("Male");
		male.setBounds(600,200,58,25);
		male.setOpaque(false);
		male.setForeground(col2);
		male.setFont(f1);

		//female
		female=new JRadioButton("Female");
		female.setBounds(655,200,73,25);
		female.setOpaque(false);
		female.setForeground(col2);
		female.setFont(f1);

		//other
		other=new JRadioButton("Other");
		other.setBounds(725,200,65,25);
		other.setOpaque(false);
		other.setForeground(col2);
		other.setFont(f1);

		//combobox for profession
		profession=new JComboBox();
		profession.setBounds(800,200,100,35);
		profession.addItem("Education");
		profession.addItem("Matriculate");
		profession.addItem("Undergraduate");
		profession.addItem("Graduate");
		profession.addItem("Postgraduate");
		profession.addItem("Doctorate");
		profession.setFont(f1);

		//combobox for security question
		question=new JComboBox();
		question.setBounds(600,300,300,35);
		question.addItem("Security question");
		question.addItem("Where were you born?");
		question.addItem("What is your first phone number?");
		question.addItem("Which is your favourite web browser?");
		question.addItem("What is the last name of your favourite teacher?");
		question.addItem("What is your grandmother's name?");
		question.setFont(f1);

		//textfield for answer
		answer=new JTextField("Answer");
		answer.setFont(f1);
		answer.setBounds(600,400,300,30);
		answer.setForeground(col2);
		answer.setOpaque(false);
		answer.setBorder(BorderFactory.createEmptyBorder());
		answer.addFocusListener(new FocusListener()
			{
				public void focusGained(FocusEvent fe)
				{
					answerFocusGained(fe);
				}
				public void focusLost(FocusEvent fe)
				{
					answerFocusLost(fe);
				}
			});

		//button to signup
		signup=new JButton("SIGN UP");
		signup.setBounds(450,500,100,50);
		signup.setFont(f2);
		signup.setForeground(col1);
		signup.setBackground(col2);
		signup.setBorder(BorderFactory.createEmptyBorder());
		signup.setCursor(cur1);
		signup.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					signupAction(ae);
				}
			});

		//button to login
		login=new JButton();
		login.setBounds(600,500,325,50);
		login.setFont(f2);
		login.setForeground(col2);
		login.setOpaque(false);
		login.setContentAreaFilled(false);
		login.setBorder(BorderFactory.createEmptyBorder());
		login.setCursor(cur1);
		login.setEnabled(false);
		login.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					loginAction(ae);
				}
			});

		//main panel
		main_panel=new JPanel();
		main_panel.setLayout(null);
		main_panel.setBounds(0,0,1100,650);
		main_panel.setBackground(col1);

		//adding radio buttons to the buttongroup
		gender.add(male);
		gender.add(female);
		gender.add(other);

		//components of main panel
		main_panel.add(firstName);
		main_panel.add(middleName);
		main_panel.add(lastName);
		main_panel.add(email);
		main_panel.add(password1);
		main_panel.add(password2);
		main_panel.add(userid);
		main_panel.add(phone);
		main_panel.add(male);
		main_panel.add(female);
		main_panel.add(other);
		main_panel.add(dob);
		main_panel.add(profession);
		main_panel.add(question);
		main_panel.add(answer);
		main_panel.add(login);
		main_panel.add(signup);
		main_panel.add(wrongID);
		main_panel.add(wrongPassword);
		main_panel.add(wrongDOB);
		main_panel.add(wrongPhone);
		main_panel.add(wrongEmail);

		//adding the main panel to the container
		c.add(main_panel);

		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","prateekshyap","Soni1999");
		}
		catch(Exception ee)
		{
			ee.printStackTrace();
		}
	}

	public void firstNameFocusGained(FocusEvent fe)
	{
		if(firstName.getText().equals("First Name"))
			firstName.setText("");
	}

	public void firstNameFocusLost(FocusEvent fe)
	{
		if(firstName.getText().equals(""))
			firstName.setText("First Name");
	}

	public void firstNameTyped(KeyEvent ke)
	{
		if(firstName.getText().equals(""))
		{
			char kc=ke.getKeyChar();
			if(Character.isLowerCase(kc))
				ke.setKeyChar(Character.toUpperCase(kc));
		}
	}

	public void middleNameFocusGained(FocusEvent fe)
	{
		if(middleName.getText().equals("Middle Name"))
			middleName.setText("");
	}

	public void middleNameFocusLost(FocusEvent fe)
	{
		if(middleName.getText().equals(""))
			middleName.setText("Middle Name");
	}

	public void middleNameTyped(KeyEvent ke)
	{
		if(middleName.getText().equals(""))
		{
			char kc=ke.getKeyChar();
			if(Character.isLowerCase(kc))
				ke.setKeyChar(Character.toUpperCase(kc));
		}
	}

	public void lastNameFocusGained(FocusEvent fe)
	{
		if(lastName.getText().equals("Last Name"))
			lastName.setText("");
	}

	public void lastNameFocusLost(FocusEvent fe)
	{
		if(lastName.getText().equals(""))
			lastName.setText("Last Name");
	}

	public void lastNameTyped(KeyEvent ke)
	{
		if(lastName.getText().equals(""))
		{
			char kc=ke.getKeyChar();
			if(Character.isLowerCase(kc))
				ke.setKeyChar(Character.toUpperCase(kc));
		}
	}

	public void emailFocusGained(FocusEvent fe)
	{
		if(email.getText().equals("Email address"))
			email.setText("");
	}

	public void emailFocusLost(FocusEvent fe)
	{
		if(email.getText().equals(""))
			email.setText("Email address");
	}

	public void password1FocusGained(FocusEvent fe)
	{
		if(password1.getText().equals("Password"))
		{
			password1.setText("");
			password1.setEchoChar('*');
		}
	}

	public void password1FocusLost(FocusEvent fe)
	{
		if(password1.getText().equals(""))
		{
			password1.setText("Password");
			password1.setEchoChar((char)0);
		}
	}

	public void password2FocusGained(FocusEvent fe)
	{
		if(password2.getText().equals("Re-type Password"))
		{
			password2.setText("");
			password2.setEchoChar('*');
		}
	}

	public void password2FocusLost(FocusEvent fe)
	{
		if(password2.getText().equals(""))
		{
			password2.setText("Re-type Password");
			password2.setEchoChar((char)0);
		}
	}

	public void phoneFocusGained(FocusEvent fe)
	{
		if(phone.getText().equals("Phone"))
			phone.setText("");
	}

	public void phoneFocusLost(FocusEvent fe)
	{
		if(phone.getText().equals(""))
			phone.setText("Phone");
	}

	public void useridFocusGained(FocusEvent fe)
	{
		if(userid.getText().equals("Choose unique ID"))
			userid.setText("");
	}

	public void useridFocusLost(FocusEvent fe)
	{
		if(userid.getText().equals(""))
			userid.setText("Choose unique ID");
	}

	public void dobFocusGained(FocusEvent fe)
	{
		if(dob.getText().equals("Date of birth (dd/mm/yyyy)"))
			dob.setText("");
	}

	public void dobFocusLost(FocusEvent fe)
	{
		if(dob.getText().equals(""))
			dob.setText("Date of birth (dd/mm/yyyy)");
	}

	public void answerFocusGained(FocusEvent fe)
	{
		if(answer.getText().equals("Answer"))
			answer.setText("");
	}

	public void answerFocusLost(FocusEvent fe)
	{
		if(answer.getText().equals(""))
			answer.setText("Answer");
	}

	public void loginAction(ActionEvent ae)
	{
		Login l=new Login();
		l.setVisible(true);
		l.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		l.setBounds(350,100,800,500);
		l.setResizable(false);
		setVisible(false);
		dispose();
	}

	public void signupAction(ActionEvent ae)
	{
		try{
		String fn=firstName.getText();
		String mn=middleName.getText();
		String ln=lastName.getText();
		if(fn.equals("First Name"))
			fn="";
		if(mn.equals("Middle Name"))
			mn="";
		if(ln.equals("Last Name"))
			ln="";
		String name=fn+" "+mn+" "+ln;
		String e=email.getText();
		if(e.equals("Email address"))
			e="";
		int flag=0;
		boolean eStr=false;
		for(int i=0;i<e.length();i++)
		{
			char ch=e.charAt(i);
			if(ch=='@')
			{
				flag++;
				break;
			}
		}
		if(flag==1)
			eStr=true;
		else
			eStr=false;
		String pw1=password1.getText();
		String pw2=password2.getText();
		boolean phStr=true;
		long pho=0;
		try{
		pho=Long.parseLong(phone.getText());
		}catch(Exception ee)
		{
			phStr=false;
		}
		String ph=Long.toString(pho);
		if(ph.length()>10||ph.length()<10)
			phStr=false;
		else
		{
			for(int i=0;i<ph.length();i++)
			{
				char ch=ph.charAt(i);
				if(ch=='0'||ch=='1'||ch=='2'||ch=='3'||ch=='4'||ch=='5'||ch=='6'||ch=='7'||ch=='8'||ch=='9')
					phStr=true;
				else
					phStr=false;
			}
		}
		String id=userid.getText();
		if(id.equals("Choose unique ID"))
			id="";
		String d=dob.getText();
		if(d.equals("Date of birth (dd/mm/yyyy)"))
			d="";
		boolean dobStr=true;
		if(d.length()>10||d.length()<10)
			dobStr=false;
		else
		{
			String y=d.substring(6,10);
			int iy=Integer.parseInt(y);
			String m=d.substring(3,5);
			String dt=d.substring(0,2);
			int i=iy%4;
			if(i==0)
			{
				if((m.equals("02")&&dt.equals("31"))||(m.equals("02")&&dt.equals("30")))
					dobStr=false;
				if((m.equals("04")&&dt.equals("31"))||(m.equals("06")&&dt.equals("31"))||(m.equals("09")&&dt.equals("31"))||(m.equals("11")&&dt.equals("31")))
					dobStr=false;
			}
			else
			{
				if((m.equals("02")&&dt.equals("31"))||(m.equals("02")&&dt.equals("30"))||(m.equals("02")&&dt.equals("29")))
					dobStr=false;
				if((m.equals("04")&&dt.equals("31"))||(m.equals("06")&&dt.equals("31"))||(m.equals("09")&&dt.equals("31"))||(m.equals("11")&&dt.equals("31")))
					dobStr=false;
			}
		}
		String gen="";
		if(male.isSelected())
			gen="M";
		else if(female.isSelected())
			gen="F";
		else if(other.isSelected())
			gen="O";
		String edu=(String)profession.getSelectedItem();
		if(edu.equals("Education"))
			edu="";
		String ques=(String)question.getSelectedItem();
		if(ques.equals("Security question"))
			ques="";
		String ans=answer.getText();
		if(ans.equals("Answer"))
			ans="";
		if(name.equals("")||e.equals("")||pw1.equals("")||pw2.equals("")||ph.equals("")||id.equals("")||d.equals("")||gen.equals("")||edu.equals("")||ques.equals("")||ans.equals(""))
		{
			login.setText("Information is not complete!");
			wrongDOB.setText("");
			wrongEmail.setText("");
			wrongPhone.setText("");
			wrongPassword.setText("");
			login.setEnabled(false);
		}
		else if(pw1.equals(pw2)==false)
		{
			login.setText("");
			wrongPassword.setText("Passwords are not matching!");
			wrongDOB.setText("");
			wrongEmail.setText("");
			wrongPhone.setText("");
			login.setEnabled(false);
		}
		else if(eStr==false)
		{
			login.setText("");
			wrongPassword.setText("");
			wrongEmail.setText("Please enter a valid email address!");
			wrongDOB.setText("");
			wrongPhone.setText("");
			login.setEnabled(false);
		}
		else if(phStr==false)
		{
			login.setText("");
			wrongPassword.setText("");
			wrongEmail.setText("");
			wrongPhone.setText("Please enter a valid phone number!");
			wrongDOB.setText("");
			login.setEnabled(false);
		}
		else if(dobStr==false)
		{
			login.setText("");
			wrongPassword.setText("");
			wrongEmail.setText("");
			wrongPhone.setText("");
			wrongDOB.setText("Please enter a valid date!");
			login.setEnabled(false);
		}
		else
		{
			Statement s=conn.createStatement();
			ResultSet rs=s.executeQuery("select user_id from hangman_admin");
			int flg=0;
			while(rs.next())
			{
				String ss=rs.getString(1);
				if(ss.equals(id))
				{
					login.setEnabled(false);
					wrongID.setText("This ID is already taken!");
					flg++;
					break;
				}
			}
			if(flg==0)
			{
				int sl=0;
				s=conn.createStatement();
				rs=s.executeQuery("select hseqadm.nextval from dual");
				while(rs.next())
					sl=rs.getInt(1);
				PreparedStatement ps=conn.prepareStatement("insert into hangman_admin values(?,?,?,?,?,?,?,?,?,?,?)");
				ps.setInt(1,sl);
				ps.setString(2,name);
				ps.setString(3,e);
				ps.setLong(4,pho);
				ps.setString(5,pw1);
				SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
				java.util.Date ud=sdf.parse(d);
				java.sql.Date sqd=new java.sql.Date(ud.getTime());
				ps.setDate(6,sqd);
				ps.setString(7,gen);
				ps.setString(8,edu);
				ps.setString(9,id);
				ps.setString(10,ques);
				ps.setString(11,ans);
				int status=ps.executeUpdate();
				if(status>0)
				{
					login.setText("Signed up successfully. Click here to login.");
					login.setEnabled(true);
				}
				else
				{
					login.setEnabled(false);
					login.setText("Error!");
				}
			}
		}
		}catch(Exception ee)
		{
			login.setText("Error!");
		}
	}
}

public class Hangman
{
	public static void main(String[] args)
	{
		MyFrame mf=new MyFrame();
		mf.setVisible(true);
		mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mf.setBounds(0,0,1375,735);
	}
}