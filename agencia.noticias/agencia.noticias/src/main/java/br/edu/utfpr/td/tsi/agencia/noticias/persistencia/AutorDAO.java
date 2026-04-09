package br.edu.utfpr.td.tsi.agencia.noticias.persistencia;

import java.util.List;

import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Autor;


public interface AutorDAO {
	
	public void gravar(Autor autor);
	
	public void remover(String id);
	
	public List<Autor> listarTodos();
	
	public Autor procurar(String id);

}
