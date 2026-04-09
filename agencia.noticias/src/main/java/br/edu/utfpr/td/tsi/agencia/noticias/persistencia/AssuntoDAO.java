package br.edu.utfpr.td.tsi.agencia.noticias.persistencia;

import java.util.List;

import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Assunto;

public interface AssuntoDAO {

	public void gravar(Assunto assunto);

	public void remover(String id);

	public List<Assunto> listarTodos();

	public Assunto procurar(String id);

	public void atualizar(String id, Assunto autorAtualizado);
	
	
	
}
