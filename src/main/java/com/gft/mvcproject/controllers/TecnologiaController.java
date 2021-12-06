package com.gft.mvcproject.controllers;

import com.gft.mvcproject.entities.Tecnologia;
import com.gft.mvcproject.repositories.TecnologiaRepository;
import com.gft.mvcproject.services.TecnologiaService;
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
@RequestMapping("/tecnologia")
public class TecnologiaController {

    @Autowired
    private TecnologiaService tecnologiaService;

    private ModelAndView definePageType(Tecnologia tecnologia, ModelAndView mv, boolean isNewTecnologia, boolean isUnique, boolean hasError, boolean isSaving) {
        if (isNewTecnologia) {
            mv.addObject("page", " - Cadastro");
            mv.addObject("button", "Cadastrar");
        } else {
            mv.addObject("page", " - Edição");
            mv.addObject("button", "Salvar");
        }

        if (!isUnique && isNewTecnologia) {
            mv.addObject("tecnologia", tecnologia);
            mv.addObject("error", "Tecnologia já existe!");
            return mv;
        }

        if (!isSaving) {
            return mv;
        }

        if (!hasError) {
            if (isNewTecnologia) {
                tecnologiaService.saveTecnologia(tecnologia);
                mv.addObject("tecnologia", new Tecnologia());
                mv.addObject("message", "Tecnologia cadastrada com sucesso!");
            } else {
                mv.addObject("tecnologia", tecnologiaService.saveTecnologia(tecnologia));
                mv.addObject("message", "Tecnologia salva com sucesso!");
            }
        } else {
            mv.addObject("tecnologia", tecnologia);
        }

        return mv;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView newTecnologia() {
        ModelAndView mv = new ModelAndView("tecnologia/form.html");
        mv.addObject("tecnologia", new Tecnologia());
        mv.addObject("page", " - Cadastro");
        mv.addObject("button", "Cadastrar");
        return mv;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ModelAndView saveTecnologia(@Valid Tecnologia tecnologia, BindingResult bindingResult) {
        ModelAndView mv = new ModelAndView("tecnologia/form.html");

        boolean newTecnologia = true;

        if (tecnologia.getId() != null) {
            newTecnologia = false;
        }

        ModelAndView newMv = definePageType(
                tecnologia,
                mv,
                newTecnologia,
                tecnologiaService.isUnique(tecnologia.getNome()),
                bindingResult.hasErrors(),
                true
        );

        return newMv;
    }

    @RequestMapping("/edit")
    public ModelAndView editTecnologia(@RequestParam Integer id) {
        ModelAndView mv = new ModelAndView("tecnologia/form.html");
        ModelAndView newMv = definePageType(null, mv,false, true, false, false);

        try {
            Tecnologia tecnologia = tecnologiaService.getTecnologia(id);
            newMv.addObject("tecnologia", tecnologia);
        } catch (Exception e) {
            newMv.addObject("tecnologia", new Tecnologia());
            newMv.addObject("error", e.getMessage());
        }

        return newMv;
    }

    @RequestMapping
    public ModelAndView listTecnologia() {
        ModelAndView mv = new ModelAndView("tecnologia/list.html");

        mv.addObject("tecnologias", tecnologiaService.listTecnologias());

        return mv;
    }

    @RequestMapping("/delete")
    public ModelAndView deleteTecnologia(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/tecnologia");

        try {
            tecnologiaService.deleteTecnologia(id);
            redirectAttributes.addFlashAttribute("message", "Tecnologia deletada!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao tentar excluir tecnologia!");
        }

        return mv;
    }
}
