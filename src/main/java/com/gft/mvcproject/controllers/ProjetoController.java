package com.gft.mvcproject.controllers;

import com.gft.mvcproject.entities.Projeto;
import com.gft.mvcproject.services.ProjetoService;
import com.gft.mvcproject.services.StarterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/projeto")
public class ProjetoController {
    @Autowired
    private ProjetoService projetoService;
    @Autowired
    private StarterService starterService;

    private ModelAndView definePageType(Projeto projeto, ModelAndView mv, boolean isNewProjeto, boolean isUnique, boolean hasError, boolean isSaving) {
        mv.addObject("starters", starterService);

        if (isNewProjeto) {
            mv.addObject("page", " - Cadastro");
            mv.addObject("button", "Cadastrar");
        } else {
            mv.addObject("page", " - Edição");
            mv.addObject("button", "Salvar");
        }

        if (!isUnique && isNewProjeto) {
            mv.addObject("projeto", projeto);
            mv.addObject("error", "Projeto já existe!");
            return mv;
        }

        if (!isSaving) {
            return mv;
        }

        if (!hasError) {
            if (isNewProjeto) {
                projetoService.saveProjeto(projeto);
                mv.addObject("projeto", new Projeto());
                mv.addObject("message", "Projeto cadastrado com sucesso!");
            } else {
                mv.addObject("projeto", projetoService.saveProjeto(projeto));
                mv.addObject("message", "Projeto salvo com sucesso!");
            }
        } else {
            mv.addObject("projeto", projeto);
        }

        return mv;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView newProjeto() {
        ModelAndView mv = new ModelAndView("projeto/form.html");
        mv.addObject("starters", starterService.listStarters());
        mv.addObject("projeto", new Projeto());
        mv.addObject("page", " - Cadastro");
        mv.addObject("button", "Cadastrar");
        return mv;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ModelAndView saveProjeto(@Valid Projeto projeto, BindingResult bindingResult) {
        ModelAndView mv = new ModelAndView("projeto/form.html");

        boolean newProjeto = true;

        if (projeto.getId() != null) {
            newProjeto = false;
        }

        ModelAndView newMv = definePageType(
                projeto,
                mv,
                newProjeto,
                projetoService.isUnique(projeto.getStarter(), projeto.getEtapa()),
                bindingResult.hasErrors(),
                true
        );

        return newMv;
    }

    @RequestMapping("/edit")
    public ModelAndView editProjeto(@RequestParam Long id) {
        ModelAndView mv = new ModelAndView("projeto/form.html");
        ModelAndView newMv = definePageType(null, mv,false, true, false, false);

        try {
            Projeto projeto = projetoService.getProjeto(id);
            newMv.addObject("projeto", projeto);
        } catch (Exception e) {
            newMv.addObject("projeto", new Projeto());
            newMv.addObject("error", e.getMessage());
        }

        return newMv;
    }

    @RequestMapping
    public ModelAndView listProjeto() {
        ModelAndView mv = new ModelAndView("projeto/list.html");

        mv.addObject("projetos", projetoService.listProjetos());

        return mv;
    }

    @RequestMapping("/delete")
    public ModelAndView deleteProjeto(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/projeto");

        try {
            projetoService.deleteProjeto(id);
            redirectAttributes.addFlashAttribute("message", "Projeto deletado!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao tentar excluir projeto!");
        }

        return mv;
    }
}
