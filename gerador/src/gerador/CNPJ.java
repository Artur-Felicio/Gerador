package gerador;

public class CNPJ extends aleatorio implements Comuns{

	private boolean ponto = false; //falso sem pontuação, verdadeiro com pontuação
	 
	public CNPJ(int pFim) {
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
		
		//caso CNPJ setamos o limite do valor de retorno do CNPJ base de 99999999, ou seja, 8 digitos
		this.setFim(100000000); 
		
		//fazemos a geração e formatamos para que o CNPJ base possua a quantidade de caracteres corretos, ou seja 8
		InfoSaida = String.format("%08d", this.gerarAleatoriedade());
		
		//o cnpj é composto pelo CNPJ base mais o adicional, que trata-se normalmente da instituição complementar vinculada a base, estamos gerando esse valor também
		this.setFim(10000);
		
		//Concatenando todos os dados CNPJ base mais a instituição
		InfoSaida = InfoSaida + String.format("%04d", this.gerarAleatoriedade());
		
		//Realizando o calculo de CNPJ, ou seja, os digitos verificadores
		InfoSaida  += calcularDigitosVerificadores(InfoSaida);
		
		//Validando se existe pontuação na requisição, caso sim, retorne a pontuação
		if(ponto)
			InfoSaida = InfoSaida.substring(0, 2) + "." + InfoSaida.substring(2, 5) + "." + InfoSaida.substring(5, 8) + "/" + InfoSaida.substring(8,12) + "-" + InfoSaida.substring(12);
		
		
		return InfoSaida;
	}

	@Override
	public boolean Validar(String pDado) {		
		return pDado.equals(pDado.substring(0,12) + calcularDigitosVerificadores(pDado.substring(0,12)));
	}
	
	
	private String calcularDigitosVerificadores(String cnpj) {
		
		int[] digitos = new int[12];
	    for (int i = 0; i < 12; i++) {
	    	digitos[i] = Character.getNumericValue(cnpj.charAt(i));
	    }
	 // Cálculo do primeiro dígito verificador
	    int soma = 0;
	    soma += digitos[0] * 5;
	    soma += digitos[1] * 4;
	    soma += digitos[2] * 3;
	    soma += digitos[3] * 2;
	    soma += digitos[4] * 9;
	    soma += digitos[5] * 8;
	    soma += digitos[6] * 7;
	    soma += digitos[7] * 6;
	    soma += digitos[8] * 5;
	    soma += digitos[9] * 4;
	    soma += digitos[10] * 3;
	    soma += digitos[11] * 2;
	    int primeiroDigito = 11 - (soma % 11);
	    if (primeiroDigito >= 10) {
	      primeiroDigito = 0;
	    }

	    // Cálculo do segundo dígito verificador
	    soma = 0;
	    soma += digitos[0] * 6;
	    soma += digitos[1] * 5;
	    soma += digitos[2] * 4;
	    soma += digitos[3] * 3;
	    soma += digitos[4] * 2;
	    soma += digitos[5] * 9;
	    soma += digitos[6] * 8;
	    soma += digitos[7] * 7;
	    soma += digitos[8] * 6;
	    soma += digitos[9] * 5;
	    soma += digitos[10] * 4;
	    soma += digitos[11] * 3;
	    soma += primeiroDigito * 2;
	    int segundoDigito = 11 - (soma % 11);
	    if (segundoDigito >= 10) {
	      segundoDigito = 0;
	    }
		
	    return String.format("%d%d", primeiroDigito, segundoDigito);
	  }

}
