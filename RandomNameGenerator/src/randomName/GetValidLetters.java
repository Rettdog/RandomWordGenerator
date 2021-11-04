package randomName;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class GetValidLetters implements ActionListener, MouseListener {
	static ArrayList<ArrayList<String>> allowed2 = new ArrayList<ArrayList<String>>();
	static ArrayList<ArrayList<ArrayList<String>>> allowed3 = new ArrayList<ArrayList<ArrayList<String>>>();
	static ArrayList<String> letters = new ArrayList<String>();
	static ArrayList<String> begins = new ArrayList<String>();
	static Random randy = new Random();
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	JLabel alph = new JLabel("Alphabet:");
	static JTextField alphabet;
	JLabel list = new JLabel("Word List:");
	static JTextField words;
	JLabel length = new JLabel("Word Length:");
	static JTextField wordlength;
	JLabel wordCount = new JLabel("Number of words:");
	static JTextField wc;
	JButton goButton = new JButton("Generate");
	
	JLabel title = new JLabel("Random Word Generator:                        ");
	JLabel generated = new JLabel();
	static GetValidLetters wordMaker;
	JPanel wordPanel = new JPanel();
	ArrayList<JLabel> generatedArray = new ArrayList<JLabel>();
	
	static String[] defaults = {"alphabet.txt","uscities.txt","8","15"};
	public static void main(String[] args) {
		
		alphabet = new JTextField(defaults[0]);
		words = new JTextField(defaults[1]);
		wordlength = new JTextField(defaults[2]);
		wc = new JTextField(defaults[3]);
		
		wordMaker = new GetValidLetters();
		wordMaker.generate();
		wordMaker.makeFrame();
		
	}
	
	public void makeFrame() {
		
		
		alphabet.setPreferredSize(new Dimension(172,20));
		words.setPreferredSize(new Dimension(167,20));
		wordlength.setPreferredSize(new Dimension(148,20));
		wc.setPreferredSize(new Dimension(124,20));
		goButton.addActionListener(this);
		panel.setLayout(new FlowLayout());
		panel.add(title);
		panel.add(alph);
		panel.add(alphabet);
		panel.add(list);
		panel.add(words);
		panel.add(length);
		panel.add(wordlength);
		panel.add(wordCount);
		panel.add(wc);
		panel.add(goButton);
		panel.add(generated);
		wordPanel.setLayout(new FlowLayout());
		wordPanel.setMaximumSize(new Dimension(300,500));
		wordPanel.setPreferredSize(new Dimension(300,300));
		frame.getContentPane().add(panel,"Center");
		frame.getContentPane().add(wordPanel,"South");
		frame.setMinimumSize(new Dimension(300,200));
		frame.setMaximumSize(new Dimension(300,500));
		frame.setPreferredSize(new Dimension(300,500));
		frame.pack();
		frame.setVisible(true);
	}
	
	public void generate() {
		allowed2 = new ArrayList<ArrayList<String>>();
		allowed3 = new ArrayList<ArrayList<ArrayList<String>>>();
		letters = new ArrayList<String>();
		begins = new ArrayList<String>();
		try {
			BufferedReader alpha = new BufferedReader(new FileReader(new File("src/"+alphabet.getText())));
			String current = alpha.readLine();
			while(current != null) {
				letters.add(current);
				current = alpha.readLine();
			}
			alpha.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			wordPanel.add(new JLabel("ERROR: File not found"));
		}
		int numberOfChars = letters.size();
		for(int i = 0;i<numberOfChars;i++) {
			allowed2.add(new ArrayList<String>());
			allowed3.add(new ArrayList<ArrayList<String>>());
			for(int j = 0;j<numberOfChars;j++) {
				allowed3.get(i).add(new ArrayList<String>());
			}
		}
		//System.out.println(allowed2.size());
		
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("src/"+words.getText())));
			String current = br.readLine();
			
			while(current != null) {
				//System.out.println(current);
				if(current.length()>=3) {
					begins.add(current.substring(0,3).toLowerCase());
					for(int i = 0;i<current.length()-2;i++) {
						String now = current.substring(i,i+1).toLowerCase();
						String next = current.substring(i+1,i+2).toLowerCase();
						String nextnext = current.substring(i+2, i+3).toLowerCase();
						int index = letters.indexOf(now);
						int nextIndex = letters.indexOf(next);
						//int nextnextIndex = letters.indexOf(nextnext);
						allowed2.get(index).add(current.substring(i+1, i+2));
						//System.out.println(" now: "+now+" next: "+next+" nextnext: "+nextnext);
						allowed3.get(index).get(nextIndex).add(nextnext);
					}
				}
				current = br.readLine();
			}
			
			br.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			wordPanel.add(new JLabel("ERROR: File not found"));
		}
	}
	
	public static String getNext(String str) {
		int index = letters.indexOf(str);
		return allowed2.get(index).get(randy.nextInt(allowed2.get(index).size()));
	}
	
	public static String get2Next(String str) {
		int index1 = letters.indexOf(str.substring(0,1));
		int index2 = letters.indexOf(str.substring(1,2));
		//System.out.println(index1+index2);
		int size = allowed3.get(index1).get(index2).size();
		if(size==0) {
			return null;
		}
		return allowed3.get(index1).get(index2).get(randy.nextInt(allowed3.get(index1).get(index2).size()));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == goButton) {
			wordMaker.generate();
			// TODO Auto-generated method stub
			try {
				wordMaker.generated.setText("");
				int amount = Integer.parseInt(wc.getText());
				String out;
	//			for(int i = generatedArray.size()-1;i>=0;i--) {
	//				wordPanel.remove(i);
	//			}
	//			wordPanel.removeAll();
				generatedArray = new ArrayList<JLabel>();
				frame.remove(wordPanel);
				wordPanel = new JPanel();
				wordPanel.setLayout(new FlowLayout());
				wordPanel.setMaximumSize(new Dimension(200,500));
				wordPanel.setPreferredSize(new Dimension(200,300));
				frame.getContentPane().add(wordPanel,"South");
				for(int k = 0;k<amount;k++) {
					out = begins.get(randy.nextInt(begins.size()));
					int lengthOfWord = Integer.parseInt(wordlength.getText());
	//				lengthOfWord = 7;
					for(int l = 0;l<lengthOfWord-3;l++) {
						if(get2Next(out.substring(out.length()-2))==null) {
							break;
						}
						out += get2Next(out.substring(out.length()-2));
					}
					//System.out.println(out);
					JLabel temp = new JLabel(out+" ");
					temp.setFont(new Font("Serif", Font.PLAIN, 20));
					temp.addMouseListener(this);
					generatedArray.add(temp);
				}
				for(int i = 0;i<generatedArray.size();i++) {
					wordPanel.add(generatedArray.get(i));
				}
				wordMaker.frame.pack();
			}catch(NumberFormatException e1) {
				wordPanel.add(new JLabel("ERROR: Please use numbers"));
			}
			wordMaker.formatFrame();
		}else {
			System.out.println(((JLabel) e.getSource()).getText());
		}
	}
	
	public void formatFrame() {
		frame.pack();
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println(((JLabel) e.getSource()).getText());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
