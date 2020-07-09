/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zemberek7;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import zemberek.morphology.TurkishMorphology;
import zemberek.morphology.analysis.SentenceAnalysis;
import zemberek.morphology.analysis.SingleAnalysis;
import zemberek.morphology.analysis.WordAnalysis;
import zemberek.tokenization.TurkishTokenizer;
import static zemberek7.Zemberek7.label;

import static zemberek7.Zemberek7.sentence;


public class Question {
     JFrame f;
     String d="";
    Question(){
      
    }
    
        public void questionResult() {
       
        try {
            String sorgu = "";
            String cümle = "";
            String cümle2 = "";
            System.out.println();
            ArrayList<String> mylist = new ArrayList<String>();
            ArrayList<String> mylist2 = new ArrayList<String>();
            ArrayList<String> mylist3 = new ArrayList<String>();
            String arr[] = null, totalarr[] = null;
            String deneme = "";
            //kelimenin köküne inme
            TurkishMorphology morphology = TurkishMorphology.createWithDefaults();
            System.out.println("Sentence: " + sentence);
            List<WordAnalysis> analysis = morphology.analyzeSentence(sentence);
            System.out.println("\n***Before disambiguation.***");
            for (WordAnalysis entry : analysis) {
                System.out.println("Word = " + entry.getInput());
                for (SingleAnalysis single : entry) {
                    System.out.println(single.formatLong());
                }
            }   System.out.println("\n***After disambiguation.***");
            SentenceAnalysis after = morphology.disambiguate(sentence, analysis);
            after.bestAnalysis().forEach(s -> System.out.println(s.formatLong()));
            System.out.println();
            after.bestAnalysis().forEach(s -> mylist2.add(s.getDictionaryItem().root));
            //soru tipini belirleme
            QuestionAnalyzer questionAnalyzer = new QuestionAnalyzer();
            System.out.println("Sentence: " + sentence);
            TurkishTokenizer tokenizer = TurkishTokenizer.ALL;
            List<zemberek.tokenization.Token> tokens = tokenizer.tokenize(sentence);
            for (zemberek.tokenization.Token token : tokens) {
                if (questionAnalyzer.questions.contains(token.getText())) {
                    System.out.println("Content = " + token.getText());
                    System.out.println("Start = " + token.getStart());
                    System.out.println("Stop = " + token.getEnd());
                    System.out.println("Type = " + "Question");
                }
                else
                {
                    sorgu = token.getText();
                    System.out.println("Content = " + token.getText());
                    System.out.println("Start = " + token.getStart());
                    System.out.println("Stop = " + token.getEnd());
                    System.out.println("Type = " + token.getType());
                    cümle = cümle.concat(sorgu);
                    deneme = token.getType().toString();
                    if (deneme != "SpaceTab") {
                        mylist.add(sorgu);
                    }
                }
            }   System.out.println("\n Sorgu atıldıktan sonra cümle = " + cümle);
            System.out.println();
            for (int j = 0; j < mylist.size(); j++) {
                System.out.println(mylist.get(j));
            }   // soru kelimesini atıp yeni liste oluşturmak ve cümleyi oluşturmak
            for (int k = 0; k < mylist2.size(); k++) {
                if (!questionAnalyzer.questions.contains(mylist2.get(k))) {
                    cümle2 = cümle2 + mylist2.get(k) + " ";
                    mylist3.add(mylist2.get(k));
                }
            }   String url = "https://tr.wikipedia.org/wiki/Special:Search?search=" + cümle;
            System.out.print("\n Ekler ve sorgu atıldıktan sonra cümle = " + cümle2);
            System.out.println("\n ");
            for (int j = 0; j < mylist3.size(); j++) {
                
                System.out.println(mylist3.get(j));
            }   System.out.println();
            Document doc = Jsoup.connect(url).get();
            Elements links = doc.select("a[href]");
            Elements paragraphs = null, pp = null;
            Element e;
            String sonuc = "", total = "", c = "";// d = "";
            String urlLinks = "";
            Document ara;
            int linksayac = 0;
            int sayac = 0;
            for (Element link : links) {
                try {
                    urlLinks = link.attr("abs:href");
                    ara = Jsoup.connect(urlLinks).get();
                    sayac = 0;
                    if (linksayac < 11) {
                        linksayac++;
                        for (int j = 0; j < mylist3.size(); j++) {
                            paragraphs = ara.getElementsMatchingOwnText(mylist3.get(j)).select("mw-parser-output ,p");
                            sonuc = paragraphs.text();
                            arr = sonuc.split("[.]");
                        }
                        for (int k = 0; k < arr.length; k++) {
                            for (int t = 0; t < mylist3.size(); t++) {
                                if ((arr[k].contains(mylist3.get(t)) || arr[k].contains(mylist.get(t))) && ( arr[k].contains(mylist3.get(mylist3.size() - 1) ) || arr[k].contains(mylist3.get(mylist3.size()-1-t)  ) ) ) {
                                    c = arr[k];
                                    d = d.concat(c);
                                    sayac++;
                                }
                            }
                        }
                        System.out.println("url= " + urlLinks + "\t eşleşen kelime= " + sayac);
                    }
                    else
                    {
                        break;
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Question.class.getName()).log(Level.SEVERE, null, ex);
                }
            }   System.out.println(d);
        } catch (IOException ex) {
            Logger.getLogger(Question.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       f=new JFrame();  
 
     //   JLabel  questionText=new JLabel("<html>"+ d +"</html>");
            JTextArea questionText = new JTextArea(d);
            questionText.setLineWrap(true);
         questionText.setBounds(170, 50, 1280, d.length());
         
        f.add(questionText);
         
        f.setSize(1500,d.length());//400 width and 500 height  

        f.setLayout(null);//using no layout managers  
        f.setVisible(true);//making the frame visible  

    }
        
     void getQuestion(String questionText)
     {
         sentence=questionText;
         System.out.print(sentence);
         questionResult();
       
    
     }
  
    
}
