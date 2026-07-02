package org.example.dashboard;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class DashboardController {
    @GetMapping("/dashboard")
    public String dashboard(
            HttpSession session,
            @CookieValue(value="lastVisit", required=false) String lastVisit,
            @CookieValue(value = "theme", defaultValue = "light") String theme,
            HttpServletResponse response,
            Model model
    ) {
        String username = (String) session.getAttribute("username");

        if (username == null) {
            return "redirect:/login";
        }

        model.addAttribute("username", username);
        model.addAttribute("theme", theme);

        if (lastVisit != null) {
            LocalDateTime ldt = LocalDateTime.parse(lastVisit, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            model.addAttribute("lastVisit", ldt);
        }

        Cookie cookie = new Cookie("lastVisit", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        cookie.setMaxAge(7*24*60*60);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);

        return "dashboard";
    }

    @GetMapping("/theme")
    public String theme(@RequestParam String mode, HttpServletResponse response, Model model) {
        Cookie cookie = new Cookie("theme", mode);
        cookie.setMaxAge(7*24*60*60);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);
        return "redirect:/dashboard";
    }

}
