package com.gft.mvcproject.controllers;

import com.gft.mvcproject.entities.User;
import com.gft.mvcproject.services.CargoService;
import com.gft.mvcproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CargoService cargoService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private ModelAndView definePageType(User user, ModelAndView mv, boolean isNewUser, boolean isUnique, boolean hasError, boolean isSaving) {
        mv.addObject("cargos", cargoService.listCargos());

        if (isNewUser) {
            mv.addObject("page", " - Cadastro");
            mv.addObject("button", "Cadastrar");
        } else {
            mv.addObject("page", " - Edição");
            mv.addObject("button", "Salvar");
        }

        if (!isUnique && isNewUser) {
            mv.addObject("user", user);
            mv.addObject("error", "Usuário já existe!");
            return mv;
        }

        if (!isSaving) {
            return mv;
        }

        if (!hasError) {
            if (isNewUser) {
                userService.saveUser(user);
                mv.addObject("user", new User());
                mv.addObject("message", "Usuário cadastrado com sucesso!");
            } else {
                user.setPassword("");
                mv.addObject("user", userService.saveUser(user));
                mv.addObject("message", "Usuário salvo com sucesso!");
            }
        } else {
            mv.addObject("user", user);
        }

        return mv;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView newUser() {
        ModelAndView mv = new ModelAndView("user/form.html");
        mv.addObject("user", new User());
        mv.addObject("page", " - Cadastro");
        mv.addObject("button", "Cadastrar");
        mv.addObject("cargos", cargoService.listCargos());
        return mv;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ModelAndView saveUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView mv = new ModelAndView("user/form.html");

        boolean newUser = true;

        if (user.getId() != null) {
            newUser = false;
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        ModelAndView newmv = definePageType(
                user,
                mv,
                newUser,
                !userService.usernameExists(user.getUsername()),
                bindingResult.hasErrors(),
                true
        );

        return newmv;
    }

    @RequestMapping("/edit")
    public ModelAndView editUser(@RequestParam Long id) {
        ModelAndView mv = new ModelAndView("user/form.html");
        ModelAndView newmv = definePageType(null, mv,false, true, false, false);

        try {
            User user = userService.getUser(id);
            newmv.addObject("user", user);
        } catch (Exception e) {
            newmv.addObject("user", new User());
            newmv.addObject("error", e.getMessage());
        }

        return newmv;
    }

    @RequestMapping
    public ModelAndView listUser(@RequestParam String cargo) {
        ModelAndView mv = new ModelAndView("user/list.html");

        mv.addObject("cargo", cargo);

        if (cargo.trim().equals("Administradores")) {
            mv.addObject("users", userService.listAdm());
        } else if(cargo.trim().equals("Scrum Masters")) {
            mv.addObject("users", userService.listSm());
        }

        return mv;
    }

    @RequestMapping("/delete")
    public ModelAndView deleteUser(@RequestParam Long id, @RequestParam String cargo, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/user?cargo="+cargo);

        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("message", "Usuário deletado!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao tentar excluir usuário!");
        }

        return mv;
    }
}
