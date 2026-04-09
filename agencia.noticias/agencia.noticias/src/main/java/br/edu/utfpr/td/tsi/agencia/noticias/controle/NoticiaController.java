package br.edu.utfpr.td.tsi.agencia.noticias.controle;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Autor;
import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Noticia;
import br.edu.utfpr.td.tsi.agencia.noticias.persistencia.AutorDAO;
import br.edu.utfpr.td.tsi.agencia.noticias.persistencia.NoticiaDAO;

@Controller
public class NoticiaController {

	@Autowired
	private NoticiaDAO noticiaDAO;
	
	@Autowired
	private AutorDAO autorDAO;

	@GetMapping(value = "/cadastrarNoticia")
	public String exibirPaginaCadastrarNoticia(Model model) {
		List<Autor> listaAutores = autorDAO.listarTodos();
		model.addAttribute("autores", listaAutores);
		
		return "cadastrarNoticia";
	}

	@PostMapping(value = "/cadastrarNoticia")
	public String cadastrarDocumento(Noticia noticia ) {
		noticia.setDataCriacao(LocalDate.now());
		noticiaDAO.cadastrar(noticia);
		return "index";
	}

	@GetMapping(value = "/listarNoticias")
	public String exibirPaginaListarNoticias(Model model) {
		List<Noticia> noticias = noticiaDAO.listarTodas();
		for (Noticia noticia : noticias) {
			Autor autor = autorDAO.procurar(noticia.getAutor().getId());
			noticia.setAutor(autor);
		}
		model.addAttribute("noticias", noticias);
		return "listarNoticias";
	}

	@GetMapping(value = "/removerNoticia")
	public String removerDocumentos(@RequestParam String idNoticia) {
		System.out.println("removendo noticia de id " + idNoticia);
		noticiaDAO.remover(idNoticia);
		return "index";
	}
	
	@GetMapping(value = "/editarNoticia")
	public String mostrarpaginaEditaNoticia(@RequestParam String idNoticia,  Model model) {
		Noticia noticia = noticiaDAO.consultar(idNoticia);
		model.addAttribute("noticia", noticia);
		return "editarNoticia";
	}
	
	@PostMapping(value = "/editarNoticia")
	public String editaNoticia(@RequestParam String idNoticia, Noticia noticia) {
		noticiaDAO.remover(idNoticia);
		noticia.setId(idNoticia);
		noticiaDAO.cadastrar(noticia);
		return "redirect:listarNoticias";
	}
}

