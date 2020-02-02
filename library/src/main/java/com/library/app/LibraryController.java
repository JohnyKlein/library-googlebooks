package com.library.app;

import com.library.app.request.DoRequest;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LibraryController {

    @GetMapping(value="/")
    public String test() {
        return "login";
    }

    @GetMapping("/books")
    public String listBooks(@RequestParam(name="nameSearch") String nameSearch, Model model) {
        String googleAddress = "https://www.googleapis.com/books/v1/volumes?q=" + nameSearch;
        model.addAttribute("nameSearch", nameSearch);
        model.addAttribute("googleAPI", getResponse());

        return "books";
    }

    private JSONObject getResponse() {
        return DoRequest.getRequest("https://www.googleapis.com/books/v1/volumes?q=harry");
    }

//    @GetMapping("/books")
//    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
//        model.addAttribute("name", name);
//        return "greeting";
//    }

}
