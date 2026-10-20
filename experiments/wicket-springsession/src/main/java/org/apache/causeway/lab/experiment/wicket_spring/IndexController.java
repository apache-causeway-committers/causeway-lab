package org.apache.causeway.lab.experiment.wicket_spring;

import org.jspecify.annotations.Nullable;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class IndexController {

    @GetMapping("/home")
    String index() {
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
    String login(final @Nullable CsrfToken csrfToken) {
        return """
            <!DOCTYPE html>
            <html>
                <body>
                    <h1>Login</h1>
                    <p>Access to this App</p>
                    <form action="/authenticate" method="post">
                        <p><input type="text" name="username" autocomplete="username" placeholder="User" required autofocus value="sven" /></p>
                        <p><input type="password" name="password" autocomplete="current-password" placeholder="Password" required /></p>
                        <p><input type="checkbox" name="rememberMe" />&nbsp;Remember Me</p>
                        %s
                        <p><input type="submit" value="Login"/></p>
                    </form>
                </body>
            </html>"""
            .formatted(csrfToken!=null
                ? """
                    <p><input type="hidden" name="%s" value="%s"></p>"""
                    .formatted(csrfToken.getParameterName(), csrfToken.getToken())
                : "");
    }

}
