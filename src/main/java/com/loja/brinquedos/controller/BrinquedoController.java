package com.loja.brinquedos.controller;

import com.loja.brinquedos.model.Brinquedo;
import com.loja.brinquedos.repository.BrinquedoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BrinquedoController {

    @Autowired
    private BrinquedoRepository brinquedoRepository;

    // Método para listar todos os brinquedos
    @GetMapping("/listar")
    public String listarBrinquedos(Model model) {
        model.addAttribute("brinquedos", brinquedoRepository.findAll());
        return "listar";
    }

    // Método para exibir o formulário de adição de brinquedo
    @GetMapping("/adicionar")
    public String formularioBrinquedo(Model model) {
        model.addAttribute("brinquedo", new Brinquedo());
        return "formulario";
    }

    // Método para salvar um novo brinquedo ou editar um existente
    @PostMapping("/salvar")
    public String salvarBrinquedo(@ModelAttribute Brinquedo brinquedo) {
        brinquedoRepository.save(brinquedo); // Salva o brinquedo (novo ou editado)
        return "redirect:/listar"; // Redireciona para a lista de brinquedos após salvar
    }

    // Método para editar um brinquedo existente
    @GetMapping("/editar/{id}")
    public String editarBrinquedo(@PathVariable Long id, Model model) {
        Brinquedo brinquedo = brinquedoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("brinquedo", brinquedo);
        return "formulario";
    }

    // Método para excluir um brinquedo
    @PostMapping("/excluir/{id}")
    public String excluirBrinquedo(@PathVariable Long id) {
        Brinquedo brinquedo = brinquedoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        brinquedoRepository.delete(brinquedo);
        return "redirect:/listar"; // Redireciona para a lista de brinquedos após excluir
    }

    // Método para exibir a descrição de um brinquedo
    @GetMapping("/descricao/{id}")
    public String exibirDescricao(@PathVariable Long id, Model model) {
        Brinquedo brinquedo = brinquedoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("brinquedo", brinquedo);
        return "descricao"; // Redireciona para a página de descrição do brinquedo
    }
}
