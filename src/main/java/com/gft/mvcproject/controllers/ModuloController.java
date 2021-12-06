package com.gft.mvcproject.controllers;

import com.gft.mvcproject.entities.Modulo;
import com.gft.mvcproject.services.*;
import com.gft.mvcproject.services.ModuloService;
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
@RequestMapping("/modulo")
public class ModuloController {
    @Autowired
    private ModuloService moduloService;
    @Autowired
    private DailyService dailyService;
    @Autowired
    private ProjetoService projetoService;

    private ModelAndView definePageType(Modulo modulo, ModelAndView mv, boolean isNewModulo, boolean isUnique, boolean hasError, boolean isSaving) {
        mv.addObject("dailies", dailyService.listDailies());
        mv.addObject("projetos", projetoService.listProjetos());
        if (isNewModulo) {
            mv.addObject("page", " - Cadastro");
            mv.addObject("button", "Cadastrar");
        } else {
            mv.addObject("page", " - Edição");
            mv.addObject("button", "Salvar");
        }

        if (!isUnique && isNewModulo) {
            mv.addObject("modulo", modulo);
            mv.addObject("error", "Modulo já existe!");
            return mv;
        }

        if (!isSaving) {
            return mv;
        }

        if (!hasError) {
            if (isNewModulo) {
                moduloService.saveModulo(modulo);
                mv.addObject("modulo", new Modulo());
                mv.addObject("message", "Modulo cadastrado com sucesso!");
            } else {
                mv.addObject("modulo", moduloService.saveModulo(modulo));
                mv.addObject("message", "Modulo salvo com sucesso!");
            }
        } else {
            mv.addObject("modulo", modulo);
        }

        return mv;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView newModulo() {
        ModelAndView mv = new ModelAndView("modulo/form.html");
        mv.addObject("dailies", dailyService.listDailies());
        mv.addObject("projetos", projetoService.listProjetos());
        mv.addObject("modulo", new Modulo());
        mv.addObject("page", " - Cadastro");
        mv.addObject("button", "Cadastrar");
        return mv;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ModelAndView saveModulo(@Valid Modulo modulo, BindingResult bindingResult) {
        ModelAndView mv = new ModelAndView("modulo/form.html");

        boolean newModulo = true;

        if (modulo.getId() != null) {
            newModulo = false;
        }

        ModelAndView newMv = definePageType(
                modulo,
                mv,
                newModulo,
                moduloService.isUnique(modulo.getNome()),
                bindingResult.hasErrors(),
                true
        );

        return newMv;
    }

    @RequestMapping("/edit")
    public ModelAndView editModulo(@RequestParam Integer id) {
        ModelAndView mv = new ModelAndView("modulo/form.html");
        ModelAndView newMv = definePageType(null, mv,false, true, false, false);

        try {
            Modulo modulo = moduloService.getModulo(id);
            newMv.addObject("modulo", modulo);
        } catch (Exception e) {
            newMv.addObject("modulo", new Modulo());
            newMv.addObject("error", e.getMessage());
        }

        return newMv;
    }

    @RequestMapping
    public ModelAndView listModulo() {
        ModelAndView mv = new ModelAndView("modulo/list.html");

        mv.addObject("modulos", moduloService.listDailies());

        return mv;
    }

    @RequestMapping("/delete")
    public ModelAndView deleteModulo(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/modulo");

        try {
            moduloService.deleteModulo(id);
            redirectAttributes.addFlashAttribute("message", "Modulo deletado!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao tentar excluir modulo!");
        }

        return mv;
    }
}
