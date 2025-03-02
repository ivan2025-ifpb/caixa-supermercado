package model;
import java.util.List;

public class Carrinho {
   
	List<ItemCompra> itemCompras;
	
	public Carrinho(List<ItemCompra> itemCompras) {
		super();
		this.itemCompras = itemCompras;
	}
	public List<ItemCompra> getProdutos() {
		return itemCompras;
	}
	public void setProdutos(List<ItemCompra> produtos) {
		this.itemCompras = produtos;
	}
	public double getValorTotal() {
		double total = 0;
		for(ItemCompra item : this.itemCompras) {
			total += item.getValor();
		}
		return total;
	}
	
	public void adicionarItem(ItemCompra item) {
		this.itemCompras.add(item);
	}
	
	public void removerItem(long codigoItem) {
		for(ItemCompra item: this.itemCompras) {
			if(item.getCodigo() == codigoItem) {
				this.itemCompras.remove(item);
			}
		}
	}

	
}
