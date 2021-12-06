package com.gft.mvcproject.controllers;

import com.gft.mvcproject.entities.Daily;
import com.gft.mvcproject.entities.Grupo;
import com.gft.mvcproject.entities.Starter;
import com.gft.mvcproject.services.DailyService;
import com.gft.mvcproject.services.StarterService;
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
@RequestMapping("/daily")
public class DailyController {
    @Autowired
    private DailyService dailyService;
    @Autowired
    private StarterService starterService;

    private ModelAndView definePageType(Daily daily, ModelAndView mv, boolean isNewDaily, boolean isUnique, boolean hasError, boolean isSaving) {
        starterService.listStarters();

        if (isNewDaily) {
            mv.addObject("page", " - Cadastro");
            mv.addObject("button", "Cadastrar");
        } else {
            mv.addObject("page", " - Edição");
            mv.addObject("button", "Salvar");
        }

        if (!isUnique) {
            mv.addObject("daily", daily);
            mv.addObject("error", "Daily já existe!");
            return mv;
        }

        if (!isSaving) {
            return mv;
        }

        if (!hasError) {
            if (isNewDaily) {
                dailyService.saveDaily(daily);
                mv.addObject("daily", new Daily());
                mv.addObject("message", "Daily cadastrada com sucesso!");
            } else {
                mv.addObject("daily", dailyService.saveDaily(daily));
                mv.addObject("message", "Daily salva com sucesso!");
            }
        } else {
            mv.addObject("daily", daily);
        }

        return mv;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView newDaily() {
        ModelAndView mv = new ModelAndView("daily/form.html");
        mv.addObject("starters", starterService.listStarters());
        mv.addObject("daily", new Daily());
        mv.addObject("page", " - Cadastro");
        mv.addObject("button", "Cadastrar");
        return mv;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ModelAndView saveDaily(@Valid Daily daily, BindingResult bindingResult) {
        ModelAndView mv = new ModelAndView("daily/form.html");

        boolean newDaily = true;

        if (daily.getId() != null) {
            newDaily = false;
        }

        ModelAndView newMv = definePageType(
                daily,
                mv,
                newDaily,
                dailyService.isUnique(daily.getStarter(), daily.getData()),
                bindingResult.hasErrors(),
                true
        );

        return newMv;
    }

    @RequestMapping("/edit")
    public ModelAndView editDaily(@RequestParam Long id) {
        ModelAndView mv = new ModelAndView("daily/form.html");
        ModelAndView newMv = definePageType(null, mv,false, true, false, false);

        try {
            Daily daily = dailyService.getDaily(id);
            newMv.addObject("daily", daily);
        } catch (Exception e) {
            newMv.addObject("daily", new Daily());
            newMv.addObject("error", e.getMessage());
        }

        return newMv;
    }

    @RequestMapping
    public ModelAndView listDaily() {
        ModelAndView mv = new ModelAndView("daily/list.html");

        mv.addObject("dailies", dailyService.listDailies());

        return mv;
    }

    @RequestMapping("/delete")
    public ModelAndView deleteDaily(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/daily");

        try {
            dailyService.deleteDaily(id);
            redirectAttributes.addFlashAttribute("message", "Daily deletada!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao tentar excluir daily!");
        }

        return mv;
    }

    @RequestMapping("/feito")
    public ModelAndView getFeito(@RequestParam Long id) throws NotFoundException {
        ModelAndView mv = new ModelAndView("daily/feito.html");

        Daily daily = dailyService.getDaily(id);
        mv.addObject("daily", daily);

        return mv;
    }

    @RequestMapping("/fazendo")
    public ModelAndView getFazendo(@RequestParam Long id) throws NotFoundException {
        ModelAndView mv = new ModelAndView("daily/fazendo.html");

        Daily daily = dailyService.getDaily(id);
        mv.addObject("daily", daily);

        return mv;
    }
}
