package br.edu.utfpr.td.tsi.agencia.noticias.persistencia;

import java.util.List;

import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Noticia;

public interface NoticiaDAO {
	
	public void cadastrar(Noticia noticia);
	
	public List<Noticia>  listarTodas();
	
	public void remover(String id);
	
	public void atualizar(String id, Noticia noticiaAtualizada);
	
	public Noticia consultar(String id);

}
