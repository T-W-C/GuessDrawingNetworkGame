package game.networking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class WordBar extends JPanel {
    private String currentWord;
    private int lengthOfCurrentWord;
    private int numOfRevealedLetters;
    private String[] wordLetters;
    private Timer timer;
    private int timeRemaining;

    private static int height;
    private static int width;

    public WordBar(String currentWord, int w, int h) throws Exception {
        this.currentWord = currentWord;
        this.lengthOfCurrentWord = currentWord.length();
        this.numOfRevealedLetters = 0;
        this.wordLetters = currentWord.split("");
        this.timer = null;
        this.timeRemaining = 60;
        width = w;
        height = h;

        this.setSize(width, height);
        this.setLayout(new FlowLayout());
        this.add(mainPanel());

    }

    private JPanel mainPanel() {
        JPanel mainInnerPanel = new JPanel();

        String[] wordOutput = new String[lengthOfCurrentWord];
        for(int i=0; i<wordOutput.length; i++) {
            wordOutput[i] = "_";
        }
        StringBuffer sb = new StringBuffer();
        sb.append(String.join(" ", wordOutput)); // join the array into a string and append it to the string buffer

        Set<Integer> usedIndex = new HashSet<>(); // set to hold the used integers for the word reveal below

        JLabel word = new JLabel(sb.toString(), SwingConstants.CENTER);
        JLabel time = new JLabel("Time: 60", SwingConstants.CENTER);
        word.setFont(new Font("SansSerif", 1, 15));
        time.setFont(new Font("SansSerif", 1, 15));
        mainInnerPanel.add(time, BorderLayout.LINE_START);
        mainInnerPanel.add(word, BorderLayout.CENTER);


        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(timeRemaining == 0) { // when timer is 0
                    timer.stop(); // stop the timer
                    word.setText(currentWord); // reveal the word
                }
                if(timeRemaining == 40|| timeRemaining == 30 || timeRemaining == 20 || timeRemaining == 10 || timeRemaining == 5) { // every ten seconds
                    if (numOfRevealedLetters < lengthOfCurrentWord-1) { // if we haven't revealed all but last letter yet
                        int index = new Random().nextInt(lengthOfCurrentWord); // random integer 0, 1, ..... , lengthOfCurrentWord-1
                        if(numOfRevealedLetters == 0) { usedIndex.add(index); } // first index so add it to the set
                        System.out.println("first: " + index);
                        while(usedIndex.contains(index) && numOfRevealedLetters != 0) { // if we get an index that has already been used then try again, skip this check for the first index
                            index = new Random().nextInt(lengthOfCurrentWord); // random integer 0, 1, ..... , lengthOfCurrentWord-1
                            System.out.println("After redoing: " + index);
                        }
                        usedIndex.add(index); // since it is not a used index, add it to the set of used indexes
                        wordOutput[index] = wordLetters[index]; // make the element at 'index' in output array equal to the element at 'index' in the word
                        sb.setLength(0);
                        sb.append(String.join(" ", wordOutput));
                        word.setText(sb.toString());
                        numOfRevealedLetters++;
                    }
                }
                time.setText("Time: " + timeRemaining);
                timeRemaining--;
            }
        });
        timer.start();

        return mainInnerPanel;
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setSize(700,500);
        f.setVisible(true);//making the frame visible
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel bar = new JPanel();
        JPanel box = new JPanel();
        bar.setSize(700,200);
        bar.setBackground(Color.pink);
        box.setSize(700,300);
        box.setBackground(Color.BLUE);

        JLabel label1 = new JLabel("Label 1");
        JLabel label2 = new JLabel("Label 2");

        bar.add(label1, BorderLayout.LINE_START); // why doesn't this work?
        bar.add(label2, BorderLayout.CENTER);

        f.add(bar, BorderLayout.PAGE_START);
        f.add(box, BorderLayout.CENTER);
    }
}