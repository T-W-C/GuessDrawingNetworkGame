package game.networking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WordBar extends JPanel {
    private String currentWord;
    private int lengthOfCurrentWord;
    private int numOfRevealedLetters;
    private String[] wordLetters;
    private Timer timer;
    private int timeRemaining;

    private static int height;
    private static int width;

    public WordBar(String word, int w, int h) throws Exception {
        this.currentWord = word;
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

        JLabel word = new JLabel(sb.toString(), SwingConstants.CENTER);
        JLabel time = new JLabel("Time: 60", SwingConstants.CENTER);
        word.setFont(new Font("Trebuchet MS", Font.BOLD, 28));
        time.setFont(new Font("Trebuchet MS", Font.BOLD, 28));
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
                        wordOutput[numOfRevealedLetters] = wordLetters[numOfRevealedLetters]; // make the element at 'index' in output array equal to the element at 'index' in the word
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
}
