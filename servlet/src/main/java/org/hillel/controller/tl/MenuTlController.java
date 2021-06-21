package org.hillel.controller.tl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuTlController {

    @GetMapping("/menu")
    public String menuPage(){
        return "menu_view";
    }


}
