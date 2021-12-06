package com.gft.mvcproject.controllers;

import com.gft.mvcproject.entities.Grupo;
import com.gft.mvcproject.services.GrupoService;
import com.gft.mvcproject.services.StarterService;
import com.gft.mvcproject.services.TecnologiaService;
import com.gft.mvcproject.services.UserService;
import javassist.NotFoundException;
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
@RequestMapping("/grupo")
public class GrupoController {
    @Autowired
    private GrupoService grupoService;
    @Autowired
    private TecnologiaService tecnologiaService;
    @Autowired
    private UserService userService;
    @Autowired
    private StarterService starterService;

    private ModelAndView definePageType(Grupo grupo, ModelAndView mv, boolean isNewGrupo, boolean isUnique, boolean hasError, boolean isSaving) {
        mv.addObject("starters", starterService.listStarters());
        mv.addObject("scrumMasters", userService.listSm());
        mv.addObject("tecnologias", tecnologiaService.listTecnologias());

        if (isNewGrupo) {
            mv.addObject("page", " - Cadastro");
            mv.addObject("button", "Cadastrar");
        } else {
            mv.addObject("page", " - Edição");
            mv.addObject("button", "Salvar");
        }

        if (!isUnique && isNewGrupo) {
            mv.addObject("grupo", grupo);
            mv.addObject("usernameError", "Grupo com este Scrum Master já existe!");
            return mv;
        }

        if (!isSaving) {
            return mv;
        }

        if (!hasError) {
            if (isNewGrupo) {
                grupoService.saveGrupo(grupo);
                mv.addObject("grupo", new Grupo());
                mv.addObject("message", "Grupo cadastrado com sucesso!");
            } else {
                mv.addObject("grupo", grupoService.saveGrupo(grupo));
                mv.addObject("message", "Grupo salvo com sucesso!");
            }
        } else {
            mv.addObject("grupo", grupo);
        }

        return mv;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView newGrupo() {
        ModelAndView mv = new ModelAndView("grupo/form.html");
        mv.addObject("starters", starterService.listStarters());
        mv.addObject("scrumMasters", userService.listSm());
        mv.addObject("tecnologias", tecnologiaService.listTecnologias());
        mv.addObject("grupo", new Grupo());
        mv.addObject("page", " - Cadastro");
        mv.addObject("button", "Cadastrar");
        return mv;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ModelAndView saveGrupo(@Valid Grupo grupo, BindingResult bindingResult) {
        ModelAndView mv = new ModelAndView("grupo/form.html");

        boolean newGrupo = true;

        if (grupo.getId() != null) {
            newGrupo = false;
        }

        ModelAndView newMv = definePageType(
                grupo,
                mv,
                newGrupo,
                grupoService.isUnique(grupo.getScrumMaster()),
                bindingResult.hasErrors(),
                true
        );

        return newMv;
    }

    @RequestMapping("/edit")
    public ModelAndView editGrupo(@RequestParam Long id) {
        ModelAndView mv = new ModelAndView("grupo/form.html");
        ModelAndView newMv = definePageType(null, mv,false, true, false, false);

        try {
            Grupo grupo = grupoService.getGrupo(id);
            newMv.addObject("grupo", grupo);
        } catch (Exception e) {
            newMv.addObject("grupo", new Grupo());
            newMv.addObject("error", e.getMessage());
        }

        return newMv;
    }

    @RequestMapping
    public ModelAndView listGrupo() {
        ModelAndView mv = new ModelAndView("grupo/list.html");

        mv.addObject("grupos", grupoService.listGrupos());

        return mv;
    }

    @RequestMapping("/delete")
    public ModelAndView deleteGrupo(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/grupo");

        try {
            grupoService.deleteGrupo(id);
            redirectAttributes.addFlashAttribute("message", "Grupo deletado!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao tentar excluir grupo!");
        }

        return mv;
    }

    @RequestMapping("/starters")
    public ModelAndView listStarters(@RequestParam Long id) throws NotFoundException {
        ModelAndView mv = new ModelAndView("grupo/starters.html");

        Grupo grupo = grupoService.getGrupo(id);

        mv.addObject("starters", grupo.getStarters());

        return mv;
    }
}
