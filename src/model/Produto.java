package model;

public class Produto {
	
	private long codigo;
	
	private String nome;
	
	private double preco;
	
	private CategoriaProduto categoria;
	
	private boolean fracionavel;
	
	public boolean isFracionavel() {
		return fracionavel;
	}


	public void setFracionavel(boolean fracionavel) {
		this.fracionavel = fracionavel;
	}


	public long getCodigo() {
		return codigo;
	}


	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public double getPreco() {
		return preco;
	}


	public void setPreco(double preco) {
		this.preco = preco;
	}


	public CategoriaProduto getCategoria() {
		return categoria;
	}


	public void setCategoria(CategoriaProduto categoria) {
		this.categoria = categoria;
	}

	public Produto (long codigo, String nome, double preco, CategoriaProduto categoria, boolean fracionavel) {
		
		this.codigo=codigo;
		this.nome=nome;
		this.preco=preco;
		this.categoria=categoria;
		this.fracionavel = fracionavel;
	}
	

	@Override
	    public String toString() {
	        return "Produto{" +
	                "codigo=" + codigo +
	                ", nome='" + nome + '\'' +
	                ", preco=" + preco +
	                ", categoria=" + categoria +
	                '}';
	    }
}
