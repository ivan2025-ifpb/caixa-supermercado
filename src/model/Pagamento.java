package model;

public class Pagamento {
	
	private double valor;
	private TipoPagamento tipoPagamento;
	
	public double getValor() {
		if(this.tipoPagamento.equals(TipoPagamento.CREDITO)) {
			return valor * 1.05;
		}
		return valor;
	}
	
}
