package cn.hrfax.open.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class IndexController {

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

}
