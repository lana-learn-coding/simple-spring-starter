package io.lana.simplespring.app;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/hello")
public class HelloController {
    @GetMapping
    public ModelAndView index() {
        return new ModelAndView("index", "message", new Message());
    }

    @PostMapping
    public String index(@Valid @ModelAttribute final Message message, final BindingResult result, final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "index";
        }

        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/hello/detail";
    }

    @GetMapping("detail")
    public String detail() {
        return "detail";
    }
}
