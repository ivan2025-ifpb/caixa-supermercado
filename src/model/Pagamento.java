package model;

public class Pagamento {
	
	private double valor;
	private TipoPagamento tipoPagamento;
	
	public Pagamento(double valor , TipoPagamento tipoPagamento) {
		super();
		this.valor = valor;
		this.tipoPagamento = tipoPagamento;
	}
	
	public double getValor() {
		if(this.tipoPagamento.equals(TipoPagamento.CREDITO)) {
			return valor * 1.05;
		}
		return valor;
	}
	
}
