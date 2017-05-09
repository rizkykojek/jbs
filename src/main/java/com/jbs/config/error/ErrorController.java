package com.jbs.config.error;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by rizkykojek on 5/9/17.
 */
@Controller
public class ErrorController {

    @RequestMapping(value = "errors", method = RequestMethod.GET)
    public String renderErrorPage(final Model model, HttpServletRequest httpRequest) {

        String errorMsg = "";
        int httpErrorCode = getErrorCode(httpRequest);

        switch (httpErrorCode) {
            case 400: {
                errorMsg = "Http Error Code: 400. Bad Request. Please Contact Administrator Support";
                break;
            }
            case 401: {
                errorMsg = "Http Error Code: 401. Unauthorized. Please Contact Administrator Support";
                break;
            }
            case 404: {
                errorMsg = "Http Error Code: 404. Resource not found. Please Contact Administrator Support";
                break;
            }
            case 500: {
                errorMsg = "Http Error Code: 500. Internal Server Error. Please Contact Administrator Support";
                break;
            }
        }
        model.addAttribute("errorMsg", errorMsg);
        return "error_page";
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
                .getAttribute("javax.servlet.error.status_code");
    }
}
