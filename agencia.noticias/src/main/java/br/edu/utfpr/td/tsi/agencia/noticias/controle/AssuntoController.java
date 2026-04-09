package br.edu.utfpr.td.tsi.agencia.noticias.controle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Assunto;
import br.edu.utfpr.td.tsi.agencia.noticias.persistencia.AssuntoDAO;

@Controller
public class AssuntoController {

    @Autowired
    private AssuntoDAO assuntoDAO;

    @GetMapping("/cadastrarAssunto")
    public String mostrarPaginaCadastroAssunto() {
        return "cadastrarAssunto";
    }

    @PostMapping("/cadastrarAssunto")
    public String cadastrarAssunto(Assunto assunto) {
        assuntoDAO.gravar(assunto);
        return "redirect:/listarAssunto";
    }

    @GetMapping("/listarAssunto")
    public String listarAssuntos(Model model) {
        List<Assunto> assuntos = assuntoDAO.listarTodos();
        model.addAttribute("assuntos", assuntos);
        return "listarAssunto";
    }

    @GetMapping("/editarAssunto")
    public String mostrarPaginaEditarAssunto(@RequestParam String idAssunto, Model model) {
        Assunto assunto = assuntoDAO.procurar(idAssunto);
        if (assunto == null) {          
            return "redirect:/listarAssunto";
        }
        model.addAttribute("assunto", assunto);
        return "editarAssunto";
    }


    @PostMapping("/editarAssunto")
    public String editarAssunto(@RequestParam String idAssunto, Assunto assunto) {
        assuntoDAO.remover(idAssunto);
        assunto.setId(idAssunto);
        assuntoDAO.gravar(assunto);
        return "redirect:/listarAssunto";
    }

    @GetMapping("/removerAssunto")
    public String removerAssunto(@RequestParam String idAssunto) {
        System.out.println("Removendo assunto de id " + idAssunto);
        assuntoDAO.remover(idAssunto);
        return "redirect:/listarAssunto";
    }
}
