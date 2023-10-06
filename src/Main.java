import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main extends JPanel implements KeyListener {
    private int [][] c;
    private char [][] b;
    private int [][] c0;
    private char [][] b0;
    private String inputX;
    private String inputY;
    private  int m;
    private  int n;
    private Graphics g;
    private boolean mistaken = false;
    private boolean comparing = false;
    private String gameInstruction;
    private String resultMessage;
    private String resultLCS, resultLCS1;
    public static String toString(char[] a)
    {
        // Creating object of String class
        StringBuilder sb = new StringBuilder();

        // Creating a string using append() method
        for (int i = 0; i < a.length; i++) {
            sb.append(a[i]);
        }

        return sb.toString();
    }
    public void practice() {
        var recW = (int)(getWidth()/(n+1));//*0.1);//(m+1);
        var recH = (int)2*(getHeight()/(m+1))/3;//*0.1);//(n+1);
        g.setFont(new Font("TimesRoman",Font.BOLD,recW/6));

        String[] buttons = {"↑", "←"};

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if(c0[i][j]==0) {
                    g.setColor(Color.YELLOW);
                    g.fillRect((j) * recW + 10, recH * (i) + 10, recW - 20, recH - 20);

                    int matchCheck = JOptionPane.showConfirmDialog(null, "Matched or Unmatched?", "Matched or Unmatched?", JOptionPane.YES_NO_OPTION);
                    if (matchCheck == 0) b0[i][j] = '↖';
                    else {
                        int returnValue = JOptionPane.showOptionDialog(null, "Please Label accordingly", "Labeling",
                                JOptionPane.WARNING_MESSAGE, 0, null, buttons, buttons[0]);
                        switch (returnValue) {
                            case 0 -> b0[i][j] = '↑';
                            case 1 -> b0[i][j] = '←';
                            default -> System.exit(0);
                        }
                    }
                    c0[i][j] = Integer.parseInt(JOptionPane.showInputDialog("Enter Value for this cell"));
                    repaint();
                }
            }
        }

    }
    public void LCS_Length() {
        var x = inputX.toCharArray();
        var y = inputY.toCharArray();


        for (int i = 0; i < m+1; i++) {
            c[i][0] = 0;     //init 1st column all 0s
        }
        //init 1st row to be 0s
        for (int i = 0; i < n+1; i++) {
            c[0][i] = 0;
        }



        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (x[i - 1] == y[j - 1]) {  //matched
                    c[i][j] = c[i - 1][j - 1] + 1;  // take input oblige
                    b[i][j] = '↖';
                }
                else if (c[i - 1][j] >= c[i][ j - 1]) {  //if upper is high or equal
                    c[i][j] = c[i - 1][j];
                    b[i][j] = '↑';
                }
                else {
                    c[i][j] = c[i][j - 1]; // if left is higher
                    b[i][j] = '←';
                }
            }
        }
    }

    public void show_answer(){

        if (comparing) {
            comparing = false;
            resultMessage = "";
            mistaken = false;
        } else {
            resultLCS = resultLCS1 = "";
            comparing = true;
            LCS_Length();

            LOOP_MR:for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    if ((b[i][j] != b0[i][j]) || (c[i][j] != c0[i][j])) {
                        mistaken = true;
                        break LOOP_MR;
                    }
                }
            }
            print_LCS(inputX,m,n);
            if (!mistaken) {
                resultMessage = "Congratulations your answer is correct! Correct length: " + c[m][n] + " and Substr: " + resultLCS;
            } else
            {

                resultMessage = "Incorrect, check the highlighted cell for the correct numbers and arrows" + ". Correct length: " + c[m][n] + " and Substr: " + resultLCS;
            }

            }

    }
    public void print_LCS(String X1, int i, int j) {
        if (i == 0 || j == 0) return; //string length 0
        var X = X1.toCharArray();
        if (b[i][j] == '↖') {
            print_LCS(toString(X), i - 1, j - 1);
            resultLCS += X[i-1];
            //System.out.print(X[i - 1]);   //same as yi
        }
        else if (b[i][j] == '↑') {
            print_LCS(toString(X), i - 1, j);
        }
        else print_LCS(toString(X), i, j - 1);
    }
    public void print_LCS1(String X1, int i, int j) {
        if (i == 0 || j == 0) return; //string length 0
        var X = X1.toCharArray();
        if (b0[i][j] == '↖') {
            print_LCS1(toString(X), i - 1, j - 1);
            resultLCS1 += X[i-1];
            //System.out.print(X[i - 1]);   //same as yi
        }
        else if (b0[i][j] == '↑') {
            print_LCS1(toString(X), i - 1, j);
        }
        else print_LCS1(toString(X), i, j - 1);
    }
    Main(){
        reset();
        addKeyListener(this);
    }
    private void reset(){
        inputX = JOptionPane.showInputDialog("Input 1st String");
        inputY = JOptionPane.showInputDialog("Input 2nd String");
        m  = inputX.length();
        n = inputY.length();
        c0 = new int [m+1][n+1]; //array of pointers to first index of int array
        b0 = new char [m+1][n+1]; //array of pointers to first index of char aray
        c = new int [m+1][n+1]; //array of pointers to first index of int array
        b = new char [m+1][n+1]; //array of pointers to first index of char aray
        gameInstruction = "To start, hit Enter";
        resultMessage = "";
        resultLCS = resultLCS1 = "";
    }

    @Override
    public void paintComponent(Graphics ge){
        //LCS_Length(inputX,inputY,g);
        g = ge;
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());

        var x = inputX.toCharArray();
        var y = inputY.toCharArray();


        var recW = (getWidth()/(n+1));//*0.1);//(m+1);
        var recH = 2*(getHeight()/(m+1))/3;//*0.1);//(n+1);

        g.setFont(new Font("TimesRoman",Font.BOLD,recW/6));
        g.setColor(Color.blue);


        for (int i = 0; i < m+1; i++) {
            c0[i][0] = 0;     //init 1st column all 0s
            g.drawRect(0,i*(recH),recW,recH);
            if (i >= 1 && i< m+1) g.drawString(String.valueOf(x[i-1]),recW/2,i*(recH) + recH/2);
        }
        //init 1st row to be 0s
        for (int i = 0; i < n+1; i++) {
            c0[0][i] = 0;
            g.drawRect(i*recW,0,recW,recH);
            if (i >= 1 && i< n+1) g.drawString(String.valueOf(y[i-1]),i*recW + recW/2,recH/2);
        }


        //g.setFont(new Font("TimesRoman", Font.BOLD, recW/3));
        g.setColor(Color.BLACK);
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                g.drawRect((j)*recW,recH*(i),recW,recH);
            }
        }


        LOOP_M:for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int value;
                char ch;
                if (comparing) {
                    value = c[i][j];
                    ch = b[i][j];
                }else {
                    value = c0[i][j];
                    ch = b0[i][j];
                }

                if(value!=0) {

                    g.setColor(Color.LIGHT_GRAY);
                    if(comparing) {
                        if ((b[i][j] != b0[i][j]) || (c[i][j] != c0[i][j]))
                        {
                            g.setColor(Color.GREEN);
                            //mistaken = true;
                        }
                    }
                    g.fillRect((j) * recW + 10, recH * (i) + 10, recW - 20, recH - 20);
                    g.setColor(Color.BLACK);
                    if (ch=='↖') {
                     g.setColor(Color.RED);
                    }
                    g.drawString(String.valueOf(ch), (j) * recW + recW / 8, recH * (i) + recH / 2);
                    g.drawString(String.valueOf(value), (j) * recW + recW / 2, recH * (i) + recH / 2);
                } else {
                    g.setColor(Color.YELLOW);
                    g.fillRect((j) * recW + 10, recH * (i) + 10, recW - 20, recH - 20);
                    break LOOP_M;
                }
            }
        }
        g.setFont(new Font("TimesRoman",Font.BOLD,15));
        g.setColor(Color.blue);
        g.drawString(gameInstruction,getWidth()/20,getHeight()/20 + (m+1)*recH );
        g.drawString("Toggle V: Show answer",getWidth()/20,getHeight()/10 + (m+1)*recH );
        g.drawString(resultMessage,getWidth()/20,getHeight()/5 + (m+1)*recH );
        requestFocusInWindow();
    }

    public static void main(String[] args) {
        var window = new JFrame();
        window.setContentPane(new Main());
        window.setResizable(false);
        window.setSize(1000,1000);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int k = e.getKeyCode();

        if (k == KeyEvent.VK_ENTER) {
            gameInstruction = "Compare between row and column of the highlighted cell then answer pop-up messages...";
            repaint();
            practice();
            gameInstruction = "To Restart, Hit R";
            //repaint();
        } else if (k==KeyEvent.VK_R) {
            gameInstruction = "To start, Hit Enter";
            int playAgain = JOptionPane.showConfirmDialog(null,"New Input Y/N?","Reseting", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
            if (playAgain == 0) {
                reset();
            }
            c0 = new int [m+1][n+1]; //array of pointers to first index of int array
            b0 = new char [m+1][n+1]; //array of pointers to first index of char aray
        } else if (k == KeyEvent.VK_V) {
            show_answer();

        }
        repaint();
        //lol
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}