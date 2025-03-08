package supermercado;

import java.util.ArrayList;

import java.util.List;
import java.util.Scanner;

import exception.CategoriaProdutoInvalidaException;
import model.Carrinho;
import model.CategoriaProduto;
import model.ItemCompra;
import model.Pagamento;
import model.Produto;
import model.TipoPagamento;
import service.ClienteService;
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
			System.out.println("4) Cadastrar Cliente");
			System.out.println("5) Sair");
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
			case 4:
				cadastrarCliente(scanner);
				break;	
			case 5:
				System.out.println("Saindo...");
				break;
			default:
				System.out.println("Opção inválida. Tente novamente.");
			}

		} while (opcao != 5);

		scanner.close();
	}

	private static void cadastrarCliente(Scanner scanner) {
		System.out.println("Inserir nome do cliente: ");
		String nome = scanner.nextLine();
		System.out.println("Inserir CPF do cliente: ");
		String cpf = scanner.nextLine();
		ClienteService.cadastrarCliente("cadastro.csv", ",", cpf, nome);
		System.out.println("Cliente cadastrado");
		
	}

	private static void passarCompra(Scanner scanner) {
		List<Produto> produtos = ProdutoService.getProdutosArquivoCsv("produtos.csv", ",");
		List<ItemCompra> itemCompras = new ArrayList<ItemCompra>();
		System.out.println("Inserir CPF do cliente: ");
		String cpf = scanner.nextLine();
		boolean temFidelidade = ClienteService.verificarFidelidade("cadastro.csv", ",", cpf);
		if(temFidelidade) {
			System.out.println("Cliente com fidelidade. Será aplicado um desconto de 7%");
		}
		
		TipoPagamento tipoPagamento;
		while (true) {
			System.out.println("Inserir forma de pagamento: (Credito,Debito,Pix,Dinheiro)");
			String tipoPagamentoStr = scanner.nextLine().trim().toUpperCase();
			try {
				tipoPagamento = TipoPagamento.valueOf(tipoPagamentoStr);
			} catch (IllegalArgumentException e) {
				System.out.println("Tipo de pagamento inválido, tente novamente");
				continue;
			}
			break;
		}

		while (true) {
			System.out.println("Produtos Disponíveis:");
			listarProdutos();
			System.out.print("Insira o nome do produto (ou 0 para finalizar a compra): ");
			String nome = scanner.nextLine().trim().toLowerCase();

			if (nome.equals("0")) {
				if(itemCompras.size() > 0) {
					break;
				} else {
					System.out.println("Insira alguma compra");
				}	
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
		Pagamento pagamento = new Pagamento(carrinho.getValorTotal(), tipoPagamento);
		System.out.println("Compra finalizada! Produtos comprados:");

		for (int i = 0; i < itemCompras.size(); i++) {
			ItemCompra item = itemCompras.get(i);
			System.out.printf("%d) Nome: %s | Codigo: %d | Valor: %.2f | Quantidade: %.2f\n",
					i + 1, 
					item.getProduto().getNome(),  
					item.getProduto().getCodigo(), 
					item.getProduto().getPreco(),
					item.getQuantidade());
		}
		double valorAPagar = pagamento.getValor();
		if(temFidelidade) {
			valorAPagar *= 0.93;
		}
		System.out.printf("Valor total a pagar: %.2f\n", valorAPagar);
		if(tipoPagamento == TipoPagamento.DINHEIRO) {
			while(true) {
				System.out.println("Insira o valor recebido para calcular o troco");
				double valorRecebido = scanner.nextDouble();
				scanner.nextLine();
				double troco = valorRecebido - valorAPagar;
				if(troco > 0) {
					System.out.printf("O troco a ser devolvido é %.2f\n", troco);
					break;
				}
			}		
		}

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
