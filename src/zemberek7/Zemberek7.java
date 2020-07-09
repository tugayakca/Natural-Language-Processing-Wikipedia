package zemberek7;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import zemberek.morphology.analysis.WordAnalysis;
import zemberek.morphology.TurkishMorphology;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import zemberek.morphology.analysis.SentenceAnalysis;
import zemberek.morphology.analysis.SingleAnalysis;
import zemberek.tokenization.TurkishTokenizer;

public class Zemberek7 {
     static JTextField  questionText;
     static JLabel label;
     static String sentence = "";

       Zemberek7(){
    
    f=new JFrame();
    JButton b=new JButton("click");//creating instance of JButton  
    b.setBounds(130,200,100,100); 
    JTextField  questionText=new JTextField();
     questionText.setBounds(170, 50, 100, 40);
      JLabel questionlabel=new JLabel("Question:");
      questionlabel.setBounds(100,50,100,40);
     
    f.add(questionlabel);
    f.add(b);//adding button in JFrame  
    f.add(questionText);
      b.addActionListener(new ActionListener(){  
        public void actionPerformed(ActionEvent e){  
        String qtext=questionText.getText();
        Question x = new Question();
        x.getQuestion(qtext);
        
        }  
    });  
    f.setSize(400,500);//400 width and 500 height  
   
    f.setLayout(null);//using no layout managers  
    f.setVisible(true);//making the frame visible  
    }
   JFrame f;
   
 
    public static void main(String[] args) throws IOException {
         new Zemberek7();
         
        
    }
  
    
    
    

}