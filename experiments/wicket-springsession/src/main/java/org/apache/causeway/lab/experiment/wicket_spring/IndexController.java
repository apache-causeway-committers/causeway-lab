package org.apache.causeway.lab.experiment.wicket_spring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class IndexController {

    @GetMapping("/home")
    public String index() {
        return """
            <!DOCTYPE html>
            <html>
                <body>
                    <h1>Hello World!</h1>
                    <a href="/logout">Sign Out</a>
                </body>
            </html>""";
    }

    @GetMapping("/login")
    public String login() {
        return "Auto Login";
    }

    @GetMapping("/logout")
    public String test() {
        return "Logout!";
    }



}
