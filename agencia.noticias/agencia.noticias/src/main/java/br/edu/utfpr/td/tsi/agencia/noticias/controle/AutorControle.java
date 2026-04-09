package br.edu.utfpr.td.tsi.agencia.noticias.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Autor;
import br.edu.utfpr.td.tsi.agencia.noticias.persistencia.AutorDAO;
import br.edu.utfpr.td.tsi.agencia.noticias.persistencia.MongoDbAutorDAO;
import br.edu.utfpr.td.tsi.agencia.noticias.persistencia.MysqlAutorDAO;

@Controller
public class AutorControle {
	
	@Autowired
	private AutorDAO dao;
	
	@GetMapping(value = "/cadastrarAutor")
	public String mostrarPaginaCadastroAutor() {
		return "cadastrarAutor";
	}
	
	@PostMapping(value = "/cadastrarAutor")
	public String receberDadosFormulario(Autor autor) {
		dao.gravar(autor);		
		return "redirect:/";
	}

}
