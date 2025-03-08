package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import exception.CategoriaProdutoInvalidaException;
import model.CategoriaProduto;
import model.Produto;

public class ProdutoService {

	public static List<Produto> getProdutosArquivoCsv(String caminhoArquivo, String separador) {
		List<Produto> produtos = new ArrayList<>();
		String linha;
		try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
			br.readLine();
			while ((linha = br.readLine()) != null) {
				String[] dados = linha.split(separador);
				int codigo = Integer.parseInt(dados[0].trim());
				String nome = dados[1].trim();
				double preco = Double.parseDouble(dados[2].trim());
				CategoriaProduto categoria = parseCategoria(dados[3].trim());
				boolean fracionavel = dados[4].trim().equals("1");
				Produto produto = new Produto(codigo, nome, preco, categoria, fracionavel);
				produtos.add(produto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return produtos;
	}
	
	public static void adicionarProdutosArquivoCsv(List<Produto> produtos, String caminhoArquivo, String separador) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo,true))) {
            for (Produto produto : produtos) {
            	bw.newLine();
                String linha = produto.getCodigo() + separador +
                               produto.getNome() + separador +
                               produto.getPreco() + separador +
                               (produto.isFracionavel() ? "1" : "0") + separador +
                               produto.getCategoria().name();
                bw.write(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public static CategoriaProduto parseCategoria(String categoriaStr) throws CategoriaProdutoInvalidaException {
		try {
			return CategoriaProduto.valueOf(categoriaStr);
		} catch (IllegalArgumentException e) {
			throw new CategoriaProdutoInvalidaException("Categoria inválida: " + categoriaStr);
		}
	}
}
