package br.edu.utfpr.td.tsi.agencia.noticias.persistencia;

import java.util.List;

import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Autor;


public class MysqlAutorDAO implements AutorDAO {

	@Override
	public void gravar(Autor autor) {
		System.out.println("faz de conta que gravou no mysql");
		
	}

	@Override
	public void remover(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Autor> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Autor procurar(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void atualizar(String id, Autor autorAtualizado) {
		// TODO Auto-generated method stub
		
	}

}
