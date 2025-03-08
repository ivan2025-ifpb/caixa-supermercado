package model;

public class ItemCompra {

	private long codigo;
	private Produto produto;
	private double quantidade;
	
	public ItemCompra(Produto produto, double quantidade) {
		this.produto = produto;
		this.quantidade = quantidade;
	}
	
	public long getCodigo() {
		return codigo;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public double getValor() {
		return this.produto.isFracionavel() ? produto.getPreco() * quantidade : produto.getPreco() * (int) quantidade;
	};

}
