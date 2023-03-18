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
    private JComboBox<String> Gerados;
    private JButton gerarButton;
    private JButton ValidarButton;
    private JButton ComandoGerar;
    private JButton ComandoValidar;
     
    public TelaCadastro() {
        setTitle("Gerador de CPF/CNPJ");
        setSize(300, 400);
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
        gerarButton.setBounds(50, 300, 200, 25);
        gerarButton.setVisible(true);
        add(gerarButton);    
        
        ValidarButton = new JButton("Validar");
        ValidarButton.setBounds(50, 300, 200, 25);
        ValidarButton.setVisible(true);
        add(ValidarButton);  
        
        List<String> lGerados = new ArrayList<String>();
        lGerados.add("Dados Gerados");
        String[] pGerados = lGerados.toArray(new String[lGerados.size()]);
        Gerados = new JComboBox<String>(pGerados);
        Gerados.setBounds(50, 250, 200, 25);
        add(Gerados);
        
        ValidarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // código a ser executado quando o botão Gerar for clicado
            	
            	VerdadeiroFalso.setForeground(Color.RED);
            	String dado =  cpfTextField.getText();
            	
            	int validacao = 1;
            	
            	try {
            		
            		if((dado.length()!= 11 && (Tipo.getSelectedItem().toString() == "CPF"))||(dado.length()!= 14 && (Tipo.getSelectedItem().toString() == "CNPJ")))
            		{
            			if((Tipo.getSelectedItem().toString() == "CPF") && !dado.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}"))
            				throw new Exception("O CPF não segue o formato com pontuação, sendo ela: nnn.nnn.nnn-nn, sendo o n um numero de 0 a 9");
            			if((Tipo.getSelectedItem().toString() == "CNPJ") && !dado.matches("\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}"))
                			throw new Exception("O CPF não segue o formato com pontuação, sendo ela: nn.nnn.nnn/nnnn-nn, sendo o n um numero de 0 a 9");
            		}
            		
            		
            		validacao = 0;
            		
            		dado = dado.replace(".", "").replace("-", "").replace("/", "");
            		
            		Double.parseDouble(dado);
            		
            		validacao = 1;
            		
            		if((dado.length()!=14) && (Tipo.getSelectedItem().toString() == "CNPJ"))
            			throw new Exception("Não deve possuir mais ou menos de 14 digitos núméricos");
            		
            		if((dado.length()!=11) && (Tipo.getSelectedItem().toString() == "CPF"))
            			throw new Exception("Não deve possuir mais ou menos de 11 digitos núméricos");
            		
            		char primeiro = dado.charAt(0);
            	    for (int i = 1; i < dado.length(); i++) {
            	        if (dado.charAt(i) != primeiro) {
            	        	validacao = 2;
            	        }
            	    }
            	    
            	    if(validacao == 1)
            	    	throw new Exception("Informação indicada possui todos os digitos iguais");
            	    
            	    validacao = 1;
            	    
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
        
        Gerados.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // código a ser executado quando o botão Gerar for clicado
            	
            	if(Gerados.getSelectedItem().toString() != "Dados Gerados")
            	{
	            	cpfTextField.setText(Gerados.getSelectedItem().toString());
	            	VerdadeiroFalso.setText("");
            	}
            }
        });
        
        gerarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // código a ser executado quando o botão Gerar for clicado
            	
            	if(Tipo.getSelectedItem().toString() == "CNPJ")
            	{
            		CNPJ info = new CNPJ(0);            		
            		saida(info, lGerados);
            	}
            	else {
            		CPF info = new CPF(0);
            		saida(info,lGerados);
				}
            	
            	
                
            }
        });
        
        ComandoGerar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            		gerarButton.setVisible(true);
            		ValidarButton.setVisible(false);
            		pontuacaoComboBox.setVisible(true);            		
            		VerdadeiroFalso.setVisible(false);
            		Gerados.setVisible(true);
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
            		Gerados.setVisible(true);            		
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
    
    private void saida(Comuns documento, List<String> lGerados) {
        boolean ponto = false;
        if(pontuacaoComboBox.getSelectedItem().toString() == "Sim")
            ponto = true;
        documento.setPonto(ponto);
        cpfTextField.setText(documento.Gerar());
        
        Gerados.addItem(cpfTextField.getText());
    }

}
