package gerador;

import java.awt.Color;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;



import javax.swing.*;

public class TelaCadastro extends JFrame {

    private JTextField cpfTextField;
    private JTextField VerdadeiroFalso;
    private JComboBox<String> pontuacaoComboBox;    
    private JComboBox<String> Tipo;
    private JButton gerarButton;
    private JButton ValidarButton;
    private JButton ComandoGerar;
    private JButton ComandoValidar;
    
    public TelaCadastro() {
        setTitle("Gerador de CPF/CNPJ");
        setSize(300, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        ComandoGerar = new JButton("Gerar");
        ComandoGerar.setBounds(50, 50, 100, 25);
        ComandoGerar.setVisible(true);
        add(ComandoGerar);
        
        ComandoValidar = new JButton("Validar");
        ComandoValidar.setBounds(150, 50, 100, 25);
        ComandoValidar.setVisible(true);
        add(ComandoValidar);
        
        
        List<String> ltipo = new ArrayList<String>();
        ltipo.add("CPF");
        ltipo.add("CNPJ");        
        
        String[] ptipo = ltipo.toArray(new String[ltipo.size()]);
        
        Tipo = new JComboBox<String>(ptipo);
        Tipo.setBounds(50, 100, 200, 25);
        add(Tipo);
        
        cpfTextField = new JTextField();
        cpfTextField.setBounds(50, 150, 200, 25);
        cpfTextField.setEditable(false);
        cpfTextField.setVisible(true);
        add(cpfTextField);
        
        
        VerdadeiroFalso = new JTextField();
        VerdadeiroFalso.setBounds(50, 200, 200, 25);
        VerdadeiroFalso.setEditable(false);
        VerdadeiroFalso.setVisible(false);
        VerdadeiroFalso.setForeground(Color.RED);
        add(VerdadeiroFalso);
        
        List<String> lpontuacao = new ArrayList<String>();
        lpontuacao.add("Não");
        lpontuacao.add("Sim");        
        
        String[] pontuacao = lpontuacao.toArray(new String[lpontuacao.size()]);        
        pontuacaoComboBox = new JComboBox<String>(pontuacao);
        pontuacaoComboBox.setBounds(50, 200, 200, 25);
        add(pontuacaoComboBox);
        
        gerarButton = new JButton("Gerar");
        gerarButton.setBounds(50, 250, 200, 25);
        gerarButton.setVisible(true);
        add(gerarButton);    
        
        ValidarButton = new JButton("Validar");
        ValidarButton.setBounds(50, 250, 200, 25);
        ValidarButton.setVisible(true);
        add(ValidarButton);    
        
        
        ValidarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // código a ser executado quando o botão Gerar for clicado
            	
            	VerdadeiroFalso.setForeground(Color.RED);
            	String dado =  cpfTextField.getText();
            	dado = dado.replace(".", "").replace("-", "").replace("/", "");
            	int validacao = 0;
            	
            	try {
            		
            		
            		Double.parseDouble(dado);
            		
            		validacao = 1;
            		
            		if((dado.length()!=14) && (Tipo.getSelectedItem().toString() == "CNPJ"))
            			throw new Exception("Não deve possuir mais ou menos de 14 digitos");
            		
            		if((dado.length()!=11) && (Tipo.getSelectedItem().toString() == "CPF"))
            			throw new Exception("Não deve possuir mais ou menos de 11 digitos");
            		
            		if(Tipo.getSelectedItem().toString() == "CPF")
            		{
            			CPF pCPF = new CPF(0);
            			validar(pCPF,dado);
            		}
            		else
            		{
            			CNPJ pCNPJ = new CNPJ(0);
            			validar(pCNPJ,dado);
            		}
            		           		
                	
            	}
            	catch (Exception ex) {
            		if(validacao == 1)
            			VerdadeiroFalso.setText("Erro: " + ex.getMessage());
            		else
            			VerdadeiroFalso.setText("Erro: Sequência digitada possui caracteres não numéricos");
				}
            }
        });
        
        gerarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // código a ser executado quando o botão Gerar for clicado
            	
            	if(Tipo.getSelectedItem().toString() == "CNPJ")
            	{
            		CNPJ info = new CNPJ(0);            		
            		saida(info);
            	}
            	else {
            		CPF info = new CPF(0);
            		saida(info);
				}
            }
        });
        
        ComandoGerar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            		gerarButton.setVisible(true);
            		ValidarButton.setVisible(false);
            		pontuacaoComboBox.setVisible(true);            		
            		VerdadeiroFalso.setVisible(false);  
            		cpfTextField.setEditable(false);
            		VerdadeiroFalso.setText("");
            }
        });
        
        ComandoValidar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            		gerarButton.setVisible(false);
            		ValidarButton.setVisible(true);
            		pontuacaoComboBox.setVisible(false);            		
            		VerdadeiroFalso.setVisible(true);
            		cpfTextField.setEditable(true);
            		VerdadeiroFalso.setText("");
            }
        });
        
    }
    
    
    private void validar(CNPJ pCNPJ, String pdado)
    {
    	validar(pCNPJ.Validar(pdado));
    }
    
    private void validar(CPF CPF, String pdado)
    {
    	validar(CPF.Validar(pdado));
    }
    
    private void validar(boolean pStatus)
    {
    	if(pStatus) {
    		VerdadeiroFalso.setText("Verdadeiro");
    		VerdadeiroFalso.setForeground(new Color(0, 100, 0));
    	}
    	else
    		VerdadeiroFalso.setText("Falso");
    }
    
    private void saida(Comuns documento) {
        boolean ponto = false;
        if(pontuacaoComboBox.getSelectedItem().toString() == "Sim")
            ponto = true;
        documento.setPonto(ponto);
        cpfTextField.setText(documento.Gerar());
    }

}
