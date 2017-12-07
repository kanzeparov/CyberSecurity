import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Created by Franck on 14.11.2017.
 */

/**
 * Creates a new {@link Main} using the specified block cipher {@code algorithmName}.  Per this
 * class's JavaDoc, this constructor also sets the following defaults:
 * To present UI SWING
 */

public class Main {

    static JLabel textBob;
    static JTextField textFieldMessage;
    static JButton sendButton;
    static JLabel textSpy;
    static JLabel textAlise;
    public static void main(String[] args) throws InterruptedException {

        initComponents();

        sendButton.addActionListener(new MyButtonActionListener());


    }

    public static void initComponents() throws InterruptedException{
        JFrame frame = new JFrame("Deffie-Hellman");
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridLayout(2, 3));

        sendButton = new JButton();
        sendButton.setText("Send");
        textFieldMessage= new JTextField(30);

        JLabel labelAlise = new JLabel("Alise");
        Font font = new Font("Veranda", Font.ITALIC, 25);
        JProgressBar progressBar = new JProgressBar();
        progressBar.setStringPainted(false);
        progressBar.setIndeterminate(true);
        progressBar.setMinimum(0);
        progressBar.setMaximum(1000);
        for(int i = progressBar.getMinimum(); i <= progressBar.getMaximum(); i++) {
            Thread.sleep(5);
        }
        labelAlise.setFont(font);
        JLabel labelBob = new JLabel("Bob");
        JLabel labelSpy = new JLabel("Spy");
        labelSpy.setFont(font);
        labelBob.setFont(font);


        textBob = new JLabel("Bob text:");
        textSpy = new JLabel("Spy text:");
        textAlise = new JLabel("Alise text:");


        JPanel panelAlise = new JPanel();
        panelAlise.setLayout(new GridLayout(2,1));
        panelAlise.add(labelAlise);
        panelAlise.setBackground(Color.WHITE);
        panelAlise.add(textAlise);
        JPanel panelBob = new JPanel();
        panelBob.setLayout(new GridLayout(2,1));
        panelBob.add(labelBob);
        panelBob.setBackground(Color.WHITE);
        panelBob.add(textBob);
        JPanel panelSpy = new JPanel();
        panelSpy.setLayout(new GridLayout(3,1));
        panelSpy.add(labelSpy);
        panelSpy.add(progressBar);
        panelSpy.setBackground(Color.WHITE);
        panelSpy.add(textSpy);
        JPanel panelTextMessage = new JPanel();
        JPanel panelRunButton = new JPanel();
        JPanel panelEmplty = new JPanel();
        panelTextMessage.setLayout(new BorderLayout());
        panelTextMessage.add(textFieldMessage, BorderLayout.PAGE_START);
        panelTextMessage.add(sendButton, BorderLayout.CENTER);
        panelAlise.setVisible(true);
        panelBob.setVisible(true);
        panelSpy.setVisible(true);
        panelTextMessage.setVisible(true);
        panelRunButton.setVisible(true);
        panelEmplty.setVisible(true);


        frame.add(panelAlise);
        frame.add(panelSpy);
        frame.add(panelBob);
        frame.add(panelTextMessage);
        frame.add(panelRunButton);
        frame.add(panelEmplty);

        frame.setVisible(true);




    }
        public static class MyButtonActionListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {
            textAlise.setText(textFieldMessage.getText());

            SideA sideA = new SideA();
            SideB sideB = new SideB();
            Spy spy = new Spy();

            sideA.generateSecretKey(sideB, spy);


            System.out.println("SideA generates parameters a, p, g and A and sends them to sideB");
            System.out.println("Spy intercepts parameters a, p, g and A from sideA and generates privat parameter c");
            System.out.println("SideB recieves parameters a, p, g and A from sideA and generates privat parameter b");

            System.out.println();

            System.out.println("SideB generates key based on parameters p, g, b and A");

            System.out.println("SideB send public key B to side A");
            System.out.println("Spy intercepts public key B from sideB which oriented for sideA");

            System.out.println();

            System.out.println("SideA recieves public key B from sideA and generates sercet key based on parameters p, g, a and B");
            System.out.println("Spy generates private key as it exchanged sideB base on parameters p, g, c and A");
            System.out.println("Spy generates private key as it exchanged sideA base on parameters p, g, c and B");

            System.out.println();

            System.out.println("Have a look at generated private keys of each participents:");
            System.out.print("sideA:                     ");
            sideA.showHashSecretKey();
            System.out.print("sideB:                     ");
            sideB.showHashSecretKey();
            System.out.print("spy as it exchanges sideB: ");
            spy.showHashSecretKey();

            System.out.println();

            String testMessage = textFieldMessage.getText();

            System.out.println("SideA enciphers message " + testMessage);

            String encipheredMessage = sideA.encipherMessage(testMessage);

            textAlise.setText(testMessage);

            System.out.println();
            System.out.println("Message was enchiphered and now looks like: " + encipheredMessage);
            System.out.println();
            String decipheredMessage = sideB.decipherMessage(encipheredMessage);
            System.out.println("SideB deciphers message: " + decipheredMessage);
            System.out.println();

            textBob.setText(decipheredMessage);

            String decipheredMessageBySpy = spy.decipherMessage(encipheredMessage);
            System.out.println("Spy deciphers message as it exchanges sideB: " + decipheredMessageBySpy);

            textSpy.setText(encipheredMessage);
        }
    }
}
