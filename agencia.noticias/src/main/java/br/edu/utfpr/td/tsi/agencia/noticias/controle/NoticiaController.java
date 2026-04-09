package br.edu.utfpr.td.tsi.agencia.noticias.controle;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Assunto;
import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Autor;
import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Noticia;
import br.edu.utfpr.td.tsi.agencia.noticias.persistencia.AssuntoDAO;
import br.edu.utfpr.td.tsi.agencia.noticias.persistencia.AutorDAO;
import br.edu.utfpr.td.tsi.agencia.noticias.persistencia.NoticiaDAO;

@Controller
public class NoticiaController {

    @Autowired
    private NoticiaDAO noticiaDAO;

    @Autowired
    private AutorDAO autorDAO;

    @Autowired
    private AssuntoDAO assuntoDAO;

    @GetMapping(value = "/cadastrarNoticia")
    public String exibirPaginaCadastrarNoticia(Model model) {
        model.addAttribute("autores", autorDAO.listarTodos());
        model.addAttribute("assuntos", assuntoDAO.listarTodos());
        return "cadastrarNoticia";
    }

    @PostMapping(value = "/cadastrarNoticia")
    public String cadastrarDocumento(Noticia noticia) {
        noticia.setDataCriacao(LocalDate.now());
        noticiaDAO.cadastrar(noticia);
        return "redirect:/listarNoticias"; 
    }

    @GetMapping(value = "/listarNoticias")
    public String exibirPaginaListarNoticias(Model model) {
        List<Noticia> noticias = noticiaDAO.listarTodas();
        
        // Populando dados completos dos relacionamentos para a view
        for (Noticia noticia : noticias) {
            if (noticia.getAutor() != null && noticia.getAutor().getId() != null) {
                noticia.setAutor(autorDAO.procurar(noticia.getAutor().getId()));
            }
            if (noticia.getAssunto() != null && noticia.getAssunto().getId() != null) {
                noticia.setAssunto(assuntoDAO.procurar(noticia.getAssunto().getId()));
            }
        }
        
        model.addAttribute("noticias", noticias);
        return "listarNoticias";
    }

    @GetMapping(value = "/editarNoticia")
    public String mostrarpaginaEditaNoticia(@RequestParam String idNoticia, Model model) {
        Noticia noticia = noticiaDAO.consultar(idNoticia);
        
        // Prevenção contra NullPointerException na view
        if (noticia.getAutor() == null) noticia.setAutor(new Autor());
        if (noticia.getAssunto() == null) noticia.setAssunto(new Assunto());
        
        model.addAttribute("noticia", noticia);
        model.addAttribute("autores", autorDAO.listarTodos());
        model.addAttribute("assuntos", assuntoDAO.listarTodos());
        
        return "editarNoticia";
    }

    @PostMapping(value = "/editarNoticia")
    public String editaNoticia(@RequestParam String idNoticia, Noticia noticia) {
        noticiaDAO.atualizar(idNoticia, noticia);
        return "redirect:/listarNoticias";
    }

    @GetMapping(value = "/removerNoticia")
    public String removerDocumentos(@RequestParam String idNoticia) {
        System.out.println("Removendo noticia de id " + idNoticia);
        noticiaDAO.remover(idNoticia);
        return "redirect:/listarNoticias"; 
    }
}