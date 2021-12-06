package com.gft.mvcproject.controllers;

import com.gft.mvcproject.entities.Starter;
import com.gft.mvcproject.entities.User;
import com.gft.mvcproject.services.StarterService;
import com.gft.mvcproject.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/starter")
public class StarterController {
    @Autowired
    private StarterService starterService;

    private ModelAndView definePageType(Starter starter, ModelAndView mv, boolean isNewStarter, boolean isUnique, boolean hasError, boolean isSaving) {
        if (isNewStarter) {
            mv.addObject("page", " - Cadastro");
            mv.addObject("button", "Cadastrar");
        } else {
            mv.addObject("page", " - Edição");
            mv.addObject("button", "Salvar");
        }

        if (!isUnique && isNewStarter) {
            mv.addObject("starter", starter);
            mv.addObject("usernameError", "Starter já existe!");
            return mv;
        }

        if (!isSaving) {
            return mv;
        }

        if (!hasError) {
            if (isNewStarter) {
                starterService.saveStarter(starter);
                mv.addObject("starter", new Starter());
                mv.addObject("message", "Usuário cadastrado com sucesso!");
            } else {
                mv.addObject("starter", starterService.saveStarter(starter));
                mv.addObject("message", "Usuário salvo com sucesso!");
            }
        } else {
            mv.addObject("starter", starter);
        }

        return mv;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView newStarter() {
        ModelAndView mv = new ModelAndView("starter/form.html");
        mv.addObject("starter", new Starter());
        mv.addObject("page", " - Cadastro");
        mv.addObject("button", "Cadastrar");
        return mv;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ModelAndView saveStarter(@Valid Starter starter, BindingResult bindingResult, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        ModelAndView mv = new ModelAndView("starter/form.html");

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        starter.setPhotos(fileName);

        if (!bindingResult.hasErrors()) {
            Starter savedUser = starterService.saveStarter(starter);

            String uploadDir = "user-photos/" + savedUser.getId();

            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }

        boolean newStarter = true;

        if (starter.getId() != null) {
            newStarter = false;
        }

        ModelAndView newMv = definePageType(
                starter,
                mv,
                newStarter,
                starterService.isUnique(starter.getQuatroLetras()),
                bindingResult.hasErrors(),
                true
        );

        return newMv;
    }

    @RequestMapping("/edit")
    public ModelAndView editStarter(@RequestParam Long id) {
        ModelAndView mv = new ModelAndView("starter/form.html");
        ModelAndView newMv = definePageType(null, mv,false, true, false, false);

        try {
            Starter starter = starterService.getStarter(id);
            newMv.addObject("starter", starter);
        } catch (Exception e) {
            newMv.addObject("starter", new Starter());
            newMv.addObject("error", e.getMessage());
        }

        return newMv;
    }

    @RequestMapping
    public ModelAndView listStarter() {
        ModelAndView mv = new ModelAndView("starter/list.html");

        mv.addObject("starters", starterService.listStarters());

        return mv;
    }

    @RequestMapping("/delete")
    public ModelAndView deleteStarter(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/starter");

        try {
            starterService.deleteStarter(id);
            redirectAttributes.addFlashAttribute("message", "Starter deletado!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao tentar excluir starter!");
        }

        return mv;
    }
}
