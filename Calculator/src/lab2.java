
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author rcdelatorre
 */
public class lab2 {

    /**
     * @param args the command line arguments
     */
    static Scanner input = new Scanner(System.in);
    static JFrame frame  = new JFrame("Calculator with GUI (Dela_Torre)");
    static JTextArea textArea = new JTextArea(1,1);
    static JTextField textField = new JTextField();
    static String fieldValue="";
    static String finalAns="";
    public static void addButtons() {
        Font font = new Font("Rockwell", Font.BOLD, 20);
        JLabel copyright = new JLabel();
        copyright.setBounds(465,385,100,50);
        copyright.setText("© Dela Torre");
        copyright.setForeground(Color.white);
        frame.add(copyright);
        int xForNum=100;
        int yForNum=100;
        int xForOperators = 450;
        int yForOperators = 100;
        JButton[] numbers = new JButton[11];
        JButton[] operators = new JButton[4];
        JLabel holderForNumButtons = new JLabel();
        JLabel holderForOperatorButtons = new JLabel();
        holderForNumButtons.setSize(1027,768);
        holderForOperatorButtons.setSize(1027,768);
        
        //numbers
        
        for(int i=0; i<numbers.length;i++) {
            String numbersAndSpecialCharArray[] = {"7","8","9","4","5","6","1","2","3","0","."};
            numbers[i] = new JButton();
            numbers[i].setFont(font);
            numbers[i].setText(numbersAndSpecialCharArray[i]);
            numbers[i].setBackground(Color.cyan);
            if (i >= 3 && i % 3 == 0) {
                yForNum += 55;
                xForNum = 100;
            }
            numbers[i].setBounds(xForNum, yForNum, 100, 50);
            xForNum += 105;
            String tempHolderForNumbers =numbers[i].getText();
            final String toBePassedNumbers = tempHolderForNumbers;
            numbers[i].addActionListener(new ActionListener() {
                
                @Override
                public void actionPerformed(ActionEvent e) {
                    getNumAsString(toBePassedNumbers);
                   
                }
        });   
            holderForNumButtons.add(numbers[i]);
            frame.add(holderForNumButtons);   
        }
        
        //operators
        
        for(int j=0; j<operators.length;j++) {
            String operatorsArray[] = {"+","-","*","/"};
            operators[j] = new JButton();
            operators[j].setFont(font);
            operators[j].setText(operatorsArray[j]);
            operators[j].setBackground(Color.red);
            if (j >= 1 && j % 1 == 0) {
                yForOperators += 55;
                xForOperators = 450;
            }
            String tempHolderForOperators = operators[j].getText();
            final String toBePassedOperators = tempHolderForOperators;
            operators[j].setBounds(xForOperators,yForOperators, 100, 50);
            xForOperators += 100;
            operators[j].addActionListener(new ActionListener() {
                
                @Override
                public void actionPerformed(ActionEvent e) {
                    getOper(toBePassedOperators);
                }
        });  
           holderForOperatorButtons.add(operators[j]);
           frame.add(holderForOperatorButtons);
            
        }
        
        //computation button
        
        JLabel forEqualButton = new JLabel();
        forEqualButton.setSize(1027,768);
        JButton equal = new JButton();
        equal.setText("=");
        equal.setFont(font);
        equal.setBackground(Color.green);
        equal.setBounds(450,320,100,50);
        equal.addActionListener(new ActionListener() {
                
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                 //process
                    
                String answerText = fieldValue;
                try {
                do {
                String arrayComp[] = answerText.split("-|\\+|\\/|\\*");       
                CharSequence temp = answerText;
                for(String a:arrayComp) {
                    System.out.println(a);
                }
                System.out.println("");
                ArrayList <Character> operators = new ArrayList();
                ArrayList <Double> operands = new ArrayList();
                for(int i=0;i<temp.length();i++) {
                    if(isOperator(temp.charAt(i))) {
                        operators.add(temp.charAt(i));
                    }
                }
                for(int i=0;i<operators.size();i++) {
                    System.out.println(operators.get(i));            
                }
                for(int i=0;i<arrayComp.length;i++) {
                    operands.add(Double.parseDouble(arrayComp[i]));
                }
                int index = getPrecedence(temp);
                double answer=0;

                switch(getOperator(temp,index)) {
                    case '+': {
                        answer = Double.parseDouble(arrayComp[index])+Double.parseDouble(arrayComp[index+1]);
                    }
                    break;
                    case '-': {
                        answer = Double.parseDouble(arrayComp[index])-Double.parseDouble(arrayComp[index+1]);
                    }
                    break;
                    case '/': {
                        answer = Double.parseDouble(arrayComp[index])/Double.parseDouble(arrayComp[index+1]);
                    }
                    break;
                    case '*': {
                        answer = Double.parseDouble(arrayComp[index])*Double.parseDouble(arrayComp[index+1]);
                    }
                    break;
                }    
                operators.remove(index);
                operands.remove(index);
                operands.remove(index);
                operands.add(index,answer);

                answerText = answer+" ";
                for(int i=0;i<operators.size();i++) {
                    if(i==0) {
                        answerText = "";
                    }            
                    answerText+=operands.get(i);
                    answerText+=operators.get(i);            
                }
                if(!operators.isEmpty()) {
                    answerText+=operands.get(operands.size()-1);
                }
                System.out.println("");

            }
            while(hasOperator(answerText));
            finalAns = answerText;
            displayValue();
                }catch (Exception ex) {
                //do nothing
                }
        }
        });  
        forEqualButton.add(equal);
        frame.add(forEqualButton);
        
        //reset button
        
        JButton reset = new JButton();
        JLabel forResetButton = new JLabel();
        forResetButton.setSize(1027,768);
        reset.setText("AC");
        reset.setFont(font);
        reset.setBounds(310,265,100,50);
        reset.setBackground(Color.yellow);
        reset.addActionListener(new ActionListener() {
                
                @Override
                public void actionPerformed(ActionEvent e) {
                    fieldValue="";
                    finalAns="";
                    textField.setText("");
                    textArea.setText("");
                }
        }); 
        forResetButton.add(reset);
        frame.add(forResetButton);
        
        //negative sign button
        
        JButton negativeSign = new JButton();
        JLabel forNegativeSignButton = new JLabel();
        forNegativeSignButton.setSize(1027,768);
        negativeSign.setText("(-)");
        negativeSign.setFont(font);
        negativeSign.setBounds(100,320,100,50);
        negativeSign.setBackground(new Color(0,139,139));
        negativeSign.addActionListener(new ActionListener() {
                
                @Override
                public void actionPerformed(ActionEvent e) {
                
                }
        }); 
        forNegativeSignButton.add(negativeSign);
        frame.add(forNegativeSignButton);
        
        //delete individual char button
        
        JButton delete = new JButton();
        JLabel forDeleteButton = new JLabel();
        forDeleteButton.setSize(1027,768);
        delete.setText("DEL");
        delete.setFont(font);
        delete.setBounds(310,320,100,50);
        delete.setBackground(new Color(0,139,139));
        delete.addActionListener(new ActionListener() {
                
                @Override
                public void actionPerformed(ActionEvent e) {

                }
        }); 
        forDeleteButton.add(delete);
        frame.add(forDeleteButton);
    }
    public static void addTextArea() {
        JScrollPane scroll;
        Font font = new Font("Rockwell", Font.BOLD, 20);
        textArea.setBounds(100,390,310,40);
        textArea.setEditable(false);
        textArea.setFont(font);
        scroll = new JScrollPane(textArea);
        scroll.setBounds(100,390,310,40);
        frame.add(scroll);
    }
    public static void addTextField() {
        JScrollPane scroll;
        Font font = new Font("Rockwell", Font.BOLD, 18);
        textField.setBounds(100,30,450,50);
        textField.setEditable(false);
        textField.setFont(font);
        scroll = new JScrollPane(textField);
        scroll.setBounds(100,30,450,50);
        frame.add(scroll);
    }
    public static void getNumAsString(String val) {
        fieldValue += val;
        displayValue();
    }
    public static void getOper(String operators) {
        try {
        CharSequence checker = fieldValue;
        if(isOperator(checker.charAt(checker.length()-1))) {
            checker = checker.subSequence(0,checker.length()-2);
            fieldValue = checker.toString();
            fieldValue +=operators;
            System.out.println("ha");
        }
        else {
            fieldValue+=operators;
        }
        }
        catch (Exception ex) {
        //do nothing
      }
    }
    public static void displayValue() {
        if(fieldValue.contains("E")) {
            DecimalFormat df = new DecimalFormat("#");
            df.setMaximumFractionDigits(8);
            System.out.println(df.format(Double.parseDouble(fieldValue)));            
            fieldValue = df.format(Double.parseDouble(fieldValue))+"";
            textArea.setText(fieldValue+ "");
        }
        else
        textField.setText(finalAns);
        textArea.setText(fieldValue);
    }
    public static boolean isOperator(char toTest) {
        switch(toTest) {
            case '+': {
                return true;
            }
            case '-': {
                return true;                
            }
            case '/': {
                return true;  
            }
            case '*': {
                return true;  
            }
            default: {
                return false;  
            }
        }       
       
    }
    public static char getOperator(CharSequence seq,int index) {
        int j=-1;
        for(int i=0;i<seq.length();i++) {
            if(isOperator(seq.charAt(i))) {
                j++;
                if(j==index) {
                    return seq.charAt(i);
                }
            }
        }
        return '?';
    }
     public static boolean hasOperator(String toCheck) {
        CharSequence temp = toCheck;
        for(int i=0;i<temp.length();i++) {
            if(isOperator(temp.charAt(i))) {
                return true;
            }
        }
        return false;
    }
    public static int getPrecedence(CharSequence value) {
        int returnIndex = -1;
        int temp=0;
        int precedence = -1;
        for(int i=0;i<value.length();i++) {
            if(isOperator(value.charAt(i))) {
                temp++;
            }
            if(value.charAt(i)=='+'&&precedence<0) {
                returnIndex+=temp;
                precedence = 0;
                temp=0;
            }
            else if(value.charAt(i)=='-'&&precedence<0) {
                returnIndex+=temp;
                precedence = 0;
                temp=0;
            }           
            else if(value.charAt(i)=='*'&&precedence<1) {
                returnIndex+=temp;
                precedence = 1;
                temp=0;
            }
            else if(value.charAt(i)=='/'&&precedence<1) {
                 returnIndex+=temp;
                precedence = 1;
                temp=0;
            }
        }
        return returnIndex;
    }
    public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        addTextArea();
        addTextField();
        addButtons();
        frame.setSize(660, 500);
        frame.getContentPane().setBackground(Color.DARK_GRAY);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
        
      }
    }); 
    }
}

