package com.library.app;

import com.library.app.request.DoRequest;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Controller
public class LibraryController {

    @GetMapping(value="/")
    public String login() {
        return "login";
    }

    @GetMapping("/books")
    public String listBooks(@RequestParam(name="nameSearch") String nameSearch, Model model) {
        String googleAddress = "https://www.googleapis.com/books/v1/volumes?q=" + nameSearch;
        JSONObject response = getResponse();
        List<String> titles = getTitles(response);
        model.addAttribute("nameSearch", nameSearch);
        model.addAttribute("titles", titles);
//        model.addAttribute("googleAPI", response);

        return "books";
    }

    private List<String> getTitles(JSONObject response) {
        List<String> titles = new ArrayList<>();
        List<HashMap<String, Object>> items = (List<HashMap<String, Object>>) response.toMap().get("items");

        items.forEach(item -> {
            HashMap<String, Object> volume = (HashMap<String, Object>) item.get("volumeInfo");
            String title = (String) volume.get("title");
            titles.add(title);
        });

        return titles;
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
