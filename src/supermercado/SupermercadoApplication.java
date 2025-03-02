package supermercado;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import exception.CategoriaProdutoInvalidaException;
import model.Carrinho;
import model.CategoriaProduto;
import model.ItemCompra;
import model.Produto;
import service.ProdutoService;

public class SupermercadoApplication {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int opcao;

		do {
			System.out.println("Menu:");
			System.out.println("1) Passar Compra");
			System.out.println("2) Listar Produtos");
			System.out.println("3) Adicionar Produto");
			System.out.print("Opção: ");

			opcao = scanner.nextInt();
			scanner.nextLine();

			switch (opcao) {
			case 1:
				passarCompra(scanner);
				break;
			case 2:
				listarProdutos();
				break;
			case 3:
				cadastrarProduto(scanner);
				break;
			case 0:
				System.out.println("Saindo...");
				break;
			default:
				System.out.println("Opção inválida. Tente novamente.");
			}

		} while (opcao != 0);

		scanner.close();
	}

	private static void passarCompra(Scanner scanner) {
	    List<Produto> produtos = ProdutoService.getProdutosArquivoCsv("produtos.csv", ",");
	    List<ItemCompra> itemCompras = new ArrayList<ItemCompra>();

	    while (true) {
	    	System.out.println("Produtos Disponíveis:");
	    	listarProdutos();
	        System.out.print("Insira o nome do produto (ou 0 para finalizar a compra): ");
	        String nome = scanner.nextLine().trim().toLowerCase();
	        
	        if (nome.equals("0")) {
	            break;
	        }

	        Produto produto = null;
	        for (Produto p : produtos) {
	            if (p.getNome().trim().toLowerCase().equals(nome)) {
	                produto = p;
	                break;
	            }
	        }

	        if (produto == null) {
	            System.out.println("Produto não encontrado. Tente novamente.");
	            continue;
	        }

	        System.out.print("Insira a quantidade (em unidades ou Kg): ");
	        double quantidade = scanner.nextDouble();
	        scanner.nextLine();
	        ItemCompra item = new ItemCompra(produto, quantidade);
	        itemCompras.add(item);
	        System.out.println("Produto adicionado: " + produto.getNome() + " - Quantidade: " + quantidade);
	    }
	    Carrinho carrinho = new Carrinho(itemCompras);
	    System.out.println("Compra finalizada! Produtos comprados:");
	    
	    for(ItemCompra item : itemCompras) {
	    	System.out.println("Nome: " + item.getProduto().getNome());
	    	System.out.println("Valor: " + item.getProduto().getPreco());
	    	System.out.println("Quantidade: " + item.getQuantidade());
	    }
	    System.out.println("Valor do carrinho: " + carrinho.getValorTotal());
	    
	    // Perguntar forma de pagamento, e cliente no inicio. criar objeto FinalizarCompra
	    
	}


	private static void cadastrarProduto(Scanner scanner) {
		System.out.println("Adicione as informações do produto: ");
		System.out.println("Código do Produto: ");
		long codigo = scanner.nextLong();
		scanner.nextLine();

		System.out.println("Nome do Produto: ");
		String nome = scanner.nextLine();

		System.out.println("Preço do Produto: ");
		double preco = scanner.nextDouble();
		scanner.nextLine();

		System.out.println("Produto é fracionável? (0 = Não, 1 = Sim): ");
		boolean fracionavel = scanner.nextInt() == 1;
		scanner.nextLine();

		System.out.println("Categoria do Produto (ex: Frio, Fruta, Carne): ");
		String categoria = scanner.nextLine();
		CategoriaProduto categoriaProduto;
		try {
			categoriaProduto = ProdutoService.parseCategoria(categoria);
			Produto produto = new Produto(codigo, nome, preco, categoriaProduto, fracionavel);
			List<Produto> produtos = new ArrayList<>();
			produtos.add(produto);
			ProdutoService.adicionarProdutosArquivoCsv(produtos, "produtos.csv", ",");
			System.out.println("Produto \"" + nome + "\" adicionado com sucesso!");
		} catch (CategoriaProdutoInvalidaException e) {
			e.printStackTrace();
		}

	}

	private static void listarProdutos() {
		List<Produto> produtos = ProdutoService.getProdutosArquivoCsv("produtos.csv", ",");
		for (Produto produto : produtos) {
			System.out.println(produto.toString());
		}
	}

}
