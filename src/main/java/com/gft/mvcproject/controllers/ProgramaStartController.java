package com.gft.mvcproject.controllers;

import com.gft.mvcproject.entities.ProgramaStart;
import com.gft.mvcproject.services.ProgramaStartService;
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
@RequestMapping("/turma")
public class ProgramaStartController {

    @Autowired
    private ProgramaStartService programaStartService;

    private ModelAndView definePageType(ProgramaStart programaStart, ModelAndView mv, boolean isNewProgramaStart, boolean isUnique, boolean hasError, boolean isSaving) {
        if (isNewProgramaStart) {
            mv.addObject("page", " - Cadastro");
            mv.addObject("button", "Cadastrar");
        } else {
            mv.addObject("page", " - Edição");
            mv.addObject("button", "Salvar");
        }

        if (!isUnique && isNewProgramaStart) {
            mv.addObject("programaStart", programaStart);
            mv.addObject("error", "Turma já existe!");
            return mv;
        }

        if (!isSaving) {
            return mv;
        }

        if (!hasError) {
            if (isNewProgramaStart) {
                programaStartService.saveProgramaStart(programaStart);
                mv.addObject("programaStart", new ProgramaStart());
                mv.addObject("message", "Turma cadastrada com sucesso!");
            } else {
                mv.addObject("programaStart", programaStartService.saveProgramaStart(programaStart));
                mv.addObject("message", "Turma salva com sucesso!");
            }
        } else {
            mv.addObject("programaStart", programaStart);
        }

        return mv;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView newProgramaStart() {
        ModelAndView mv = new ModelAndView("programaStart/form.html");
        mv.addObject("programaStart", new ProgramaStart());
        mv.addObject("page", " - Cadastro");
        mv.addObject("button", "Cadastrar");
        return mv;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ModelAndView saveProgramaStart(@Valid ProgramaStart programaStart, BindingResult bindingResult) {
        ModelAndView mv = new ModelAndView("programaStart/form.html");

        boolean newProgramaStart = true;

        if (programaStart.getId() != null) {
            newProgramaStart = false;
        }

        System.out.println(programaStart.getTermino());

        ModelAndView newMv = definePageType(
                programaStart,
                mv,
                newProgramaStart,
                programaStartService.isUnique(programaStart.getTurma()),
                bindingResult.hasErrors(),
                true
        );

        return newMv;
    }

    @RequestMapping("/edit")
    public ModelAndView editProgramaStart(@RequestParam Long id) {
        ModelAndView mv = new ModelAndView("programaStart/form.html");
        ModelAndView newMv = definePageType(null, mv,false, true, false, false);

        try {
            ProgramaStart programaStart = programaStartService.getProgramaStart(id);
            newMv.addObject("programaStart", programaStart);
        } catch (Exception e) {
            newMv.addObject("programaStart", new ProgramaStart());
            newMv.addObject("error", e.getMessage());
        }

        return newMv;
    }

    @RequestMapping
    public ModelAndView listProgramaStart() {
        ModelAndView mv = new ModelAndView("programaStart/list.html");

        mv.addObject("turmas", programaStartService.listProgramaStart());

        return mv;
    }

    @RequestMapping("/delete")
    public ModelAndView deleteProgramaStart(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/programaStart");

        try {
            programaStartService.deleteProgramaStart(id);
            redirectAttributes.addFlashAttribute("message", "Turma deletada!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao tentar excluir turma!");
        }

        return mv;
    }
}
