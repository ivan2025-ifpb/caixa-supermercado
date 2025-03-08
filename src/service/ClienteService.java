package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ClienteService {
	public static boolean verificarFidelidade(String caminhoArquivo, String separador, String cpfCliente) {
		String linha;
		boolean temFidelidade = false;
		try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
			br.readLine();
			while ((linha = br.readLine()) != null) {
				String[] dados = linha.split(separador);
				String cpf = dados[0].trim();
				if(cpf.equals(cpfCliente)) {
					temFidelidade = true;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temFidelidade;
	}

	public static void cadastrarCliente(String caminhoArquivo, String separador, String cpfCliente, String nomeCliente) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo, true))) {
			bw.newLine();
			String linha = cpfCliente + separador + nomeCliente;
			bw.write(linha);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
