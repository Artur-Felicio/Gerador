package gerador;

public class CPF extends aleatorio implements Comuns{

	private boolean ponto = false; //falso sem pontuação, verdadeiro com pontuação
	
	public CPF(int pFim) {
		super(pFim);
		this.ponto = false;		
	}
	
	public void setPonto(boolean pPonto)
	{
		this.ponto = pPonto;
	}

	
	@Override
	public String Gerar() {
		String InfoSaida="";		
		this.setInicio(0);
		
		//Realizando o calculo do CPF
		this.setFim(1000000000);
		//Ajustando a saida para que seja gerados numeros aleatórios mas com no minimo 9 digitos
		InfoSaida = String.format("%09d", this.gerarAleatoriedade());
		//Realizando o calculo do CPF
		InfoSaida = calcularCPF(InfoSaida);
		//regularizando, caso exista a pontuação 
		if(ponto)
			InfoSaida = InfoSaida.substring(0, 3) + "." + InfoSaida.substring(3, 6) + "." + InfoSaida.substring(6, 9) + "-" + InfoSaida.substring(9);
		
		return InfoSaida;
	}

	@Override
	public boolean Validar(String pDado) {
		return pDado.equals(calcularCPF(pDado.substring(0,9)));
	}
	
	//metodos CPF
	
	private String calcularCPF(String cpfSemDV) {
		// Transformando o CPF em o digito verificador, ou seja, sem os dois ultimos digitos em um array de inteiros para facilitar os calculos
	    int[] cpfSemDVArray = new int[9];
	    for (int i = 0; i < 9; i++) {
	        cpfSemDVArray[i] = Character.getNumericValue(cpfSemDV.charAt(i));
	    }
	    
	    //Calculando o primeiro digito verificador 
	    int dv1 = calcularPrimeiroDigitoVerificador(cpfSemDVArray);
	    
	    // Criando novo vetor de dados com para carregar os novos digitos verificadores 
	    int[] cpfComDVArray = new int[11];
	    for (int i = 0; i < 9; i++) {
	        cpfComDVArray[i] = cpfSemDVArray[i];
	    }
	    //Carregabdo o digito verificador 1 (calculado antes)
	    cpfComDVArray[9] = dv1;
	    //Calculando o segundo digito verificador
	    int dv2 = calcularSegundoDigitoVerificador(cpfComDVArray);
	    //Carregabdo o segundo digito verificador
	    cpfComDVArray[10] = dv2;
	    //criando a nova string de saida
	    String cpfComDV = "";
	    //Carregando os dados calculados na saida
	    for (int i = 0; i < 11; i++) {
	        cpfComDV += cpfComDVArray[i];
	    }
	    //Retornando string com os dados calculados 
	    return cpfComDV;
	}

	private int calcularPrimeiroDigitoVerificador(int[] cpfSemDV) {
	    int soma = 0;
	    //Calculo inicial do CPF, seguindo a sequencia dig1 * 10 + dig2 * 9 até o fim  
	    for (int i = 0; i < 9; i++) {
	        soma += cpfSemDV[i] * (10 - i);
	    }
	    //encontrando o resto da divisão por 11
	    int resto = soma % 11;
	    //validação final do digito (resultados possiveis 0 - 9)
	    if (resto < 2) {
	        return 0;
	    } else {
	        return 11 - resto;
	    }
	}

	private int calcularSegundoDigitoVerificador(int[] cpfComDV) {
	    int soma = 0;
	    //Calculando o segundo digito verificador seguindo a sequencia dig 1 * 11 + dig2 * 10 até o fim
	    for (int i = 0; i < 10; i++) {
	        soma += cpfComDV[i] * (11 - i);
	    }
	    //encontrando o resto da divisão
	    int resto = soma % 11;
	    //assim como no verificador 1 temos uma saida de 0 - 9 
	    if (resto < 2) {
	        return 0;
	    } else {
	        return 11 - resto;
	    }
	}
	
}
