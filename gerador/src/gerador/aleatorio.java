package gerador;

import java.util.Random;

public class aleatorio {
	private int Fim;	
	private int Inicio;
	
	protected aleatorio(int pFim)
	{
		this.Fim = pFim; 		 
	}
	
	protected long gerarAleatoriedade()
	{
		Random gerador = new Random();
		return gerador.nextLong(this.Fim)+Inicio; 
	}
	
	protected void setFim(int pFim)
	{
		this.Fim = pFim;
	}
	
	protected void setInicio(int pInicio)
	{
		this.Inicio = pInicio;
	}
}
