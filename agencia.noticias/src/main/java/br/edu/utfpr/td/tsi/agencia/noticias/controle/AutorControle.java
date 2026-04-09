package br.edu.utfpr.td.tsi.agencia.noticias.controle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Autor;
import br.edu.utfpr.td.tsi.agencia.noticias.persistencia.AutorDAO;

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

	@GetMapping(value = "/listarAutor")
	public String exibirPaginaListarAutor(Model model) {
	    List<Autor> autores = dao.listarTodos();
	    model.addAttribute("autores", autores);
	    return "listarAutor";
	}
	
	@GetMapping(value = "/editarAutor")
	public String mostrarPaginaEditarAutor(@RequestParam String idAutor, Model model) {
	    Autor autor = dao.procurar(idAutor);
	    model.addAttribute("autor", autor);
	    return "editarAutor";
	}

	@PostMapping(value = "/editarAutor")
	public String editarAutor(@RequestParam String idAutor, Autor autor) {
	    dao.remover(idAutor);
	    autor.setId(idAutor);
	    dao.gravar(autor);
	    return "redirect:listarAutor";
	}

	@GetMapping(value = "/removerAutor")
	public String removerAutor(@RequestParam String idAutor) {
	    System.out.println("Removendo autor de id " + idAutor);
	    dao.remover(idAutor);
	    return "index";
	}
	
}
