package com.mpl.soapconsumer.controller;

import com.mpl.soapconsumer.model.LoginModel;
import com.mpl.soapconsumer.org.sakaiproject.webservices.SakaiLogin;
import com.mpl.soapconsumer.org.sakaiproject.webservices.SakaiLoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class LoginController {

    /**
     * Controla el acceso a la pagina inicial
     * @param model modelo a enviar id y pw
     * @param bindingResult verifica que los modelos sean los solicitados
     * @param m es el modelo que controla la vista
     * @return devuelve la vista que se vera
     */
    @PostMapping("/access")
    public String login(@Valid @ModelAttribute("model") LoginModel model, BindingResult bindingResult, Model m) {
        //Verifica que el id y la contraseña no esten vacios
        if (model.getId().isEmpty() && model.getPw().isEmpty()) {
            m.addAttribute("model", model);
            m.addAttribute("hasErrorId", true);
            m.addAttribute("hasErrorPw", true);
            m.addAttribute("src", "http://mpl.com.co/site/en/img/core-img/logo_transp.png");
            return "login";
        } else {
            //Si el id esta vacio se envia el error
            if (model.getId().isEmpty()) {
                m.addAttribute("model", model);
                m.addAttribute("hasErrorId", true);
                m.addAttribute("hasErrorPw", false);
                m.addAttribute("src", "http://mpl.com.co/site/en/img/core-img/logo_transp.png");
                return "login";
            }
            //Si la contraseña esta vacia se envia el error
            if (model.getPw().isEmpty()) {
                m.addAttribute("model", model);
                m.addAttribute("hasErrorPw", true);
                m.addAttribute("hasErrorId", false);
                m.addAttribute("src", "http://mpl.com.co/site/en/img/core-img/logo_transp.png");
                return "login";
            }
        }
        //Checa que los modelos cumplan las condiciones
        if (bindingResult.hasErrors()) {
            m.addAttribute("model", model);
            m.addAttribute("hasErrorId", true);
            m.addAttribute("hasErrorPw", true);
            m.addAttribute("src", "http://mpl.com.co/site/en/img/core-img/logo_transp.png");
            return "login";
        } else {
            try {
                //Realiza la peticion al WSDL y verifica el acceso
                SakaiLoginService request = new SakaiLoginService();
                SakaiLogin mLogin = request.getSakaiLoginPort();
                String log = mLogin.login(model.getId(), model.getPw());
                m.addAttribute("success", log);
                return "index";
            } catch (Exception e) {
                //Si el acceso es erroneo entonces
                //muestra el error de acceso
                System.err.println(e.getMessage());
                m.addAttribute("model", model);
                m.addAttribute("error", true);
                m.addAttribute("src", "http://mpl.com.co/site/en/img/core-img/logo_transp.png");
                return "login";
            }
        }
    }

    /**
     * Es la carga inicial de la vista
     * @param model
     * @return
     */
    @GetMapping("/login")
    public String home(Model model) {
        model.addAttribute("id", "");
        model.addAttribute("hasErrorId", false);
        model.addAttribute("pw", "");
        model.addAttribute("hasErrorPw", false);
        model.addAttribute("error", false);
        model.addAttribute("model", new LoginModel());
        model.addAttribute("src", "http://mpl.com.co/site/en/img/core-img/logo_transp.png");
        return "login";
    }
}
