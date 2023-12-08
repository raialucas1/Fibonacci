
// Figura 23.25: Fibonacci Numbers.java
// Usando SwingWorker para realizar um cálculo longo com
// resultados exibidos em uma GUI.
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.concurrent.ExecutionException;

public class FibonacciNumbers extends JFrame
{
    // Componentes para calcular o número de Fibonacci inserido pelo usuário
    private final JPanel workerJPanel = 
        new JPanel(new GridLayout(2,2,5,5));
    private final JTextField numberJTextField = new JTextField();
    private final JButton goJButton = new JButton("Go");
    private final JLabel fibonacciJLabel = new JLabel();

    // Componentes e variáveis para obter o próximo número de Fibonacci
    private final JPanel eventThreadJPanel = 
        new JPanel(new GridLayout(2, 2, 5, 5));
    private long n1 = 0; // inicializa com o primeiro número de Fibonacci
    private long n2 = 1; // inicializa com o segundo número de Fibonacci
    private int count = 1; // número de Fibonacci atual para exibir
    private final JLabel nJLabel = new JLabel("Fibonacci of 1: ");
    private final JLabel nFibonacciJLabel = 
        new JLabel (String.valueOf(n2));
    private final JButton nextNumberJButton = new JButton ("Next Number");

    // construtor
    public FibonacciNumbers()
    {
        super("Fibonacci Numbers");
        setLayout(new GridLayout (2, 1, 10, 10));

        // adiciona componentes GUI ao painel SwingWorker
        workerJPanel.setBorder(new TitledBorder(
            new LineBorder(Color.BLACK), "With SwingWorker"));
        workerJPanel.add(new JLabel("Get Fibonacci of:"));
        workerJPanel.add(numberJTextField);
        goJButton.addActionListener(
            new ActionListener()
            {
                public void actionPerformed(ActionEvent event)
                {
                    int n;

                    try
                    {
                        // recupera a entrada do usuário como um inteiro
                        n = Integer.parseInt(numberJTextField.getText());
                    }
                    catch(NumberFormatException ex)
                    {
                        // exibe uma mensagem de erro se o usuário não inseriu
                        // um número inteiro
                        fibonacciJLabel.setText("Enter an integer.");
                        return;
                    }

                    // indica que o cálculo começou
                    fibonacciJLabel.setText("Calculating...");

                    // cria uma tarefa para realizar o cálculo em segundo plano
                    BackgroundCalculator task =
                        new BackgroundCalculator(n, fibonacciJLabel);
                    task.execute(); // executa a tarefa
                }
            } // fim da classe interna anônima
        ); // fim da chamada para addActionListener
        workerJPanel.add (goJButton);
        workerJPanel.add(fibonacciJLabel);

        // adiciona componentes GUI ao painel da thread de despacho de eventos
        eventThreadJPanel.setBorder(new TitledBorder(
            new LineBorder (Color.YELLOW), "Without SwingWorker"));
       
        eventThreadJPanel.add(nJLabel);
        eventThreadJPanel.add(nFibonacciJLabel);
        nextNumberJButton.addActionListener(
            new ActionListener()
            {
                public void actionPerformed(ActionEvent event)
                {
                    // calcula o número de Fibonacci após n2
                    long temp = n1 + n2;
                    n1 = n2;
                    n2 = temp;
                    ++count;

                    // exibe o próximo número de Fibonacci
                    nJLabel.setText("Fibonacci of " + count + ": ");
                    nFibonacciJLabel.setText(String.valueOf(n2));
                }
            } // fim da classe interna anônima
        ); // fim da chamada para addActionListener
        eventThreadJPanel.add(nextNumberJButton);

        add(workerJPanel);
        add(eventThreadJPanel);
        setSize(475, 400);
        setVisible(true);
    } // fim do construtor

    // método main inicia a execução de programa
    public static void main(String[] args)
    {
        FibonacciNumbers application = new FibonacciNumbers();
        application.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
} // fim da classe Fibonacci Numbers