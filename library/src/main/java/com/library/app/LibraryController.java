package com.library.app;

import com.library.app.model.Book;
import com.library.app.request.DoRequest;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class LibraryController {

    @GetMapping(value="/")
    public String login() {
        return "login";
    }

    @GetMapping("/books")
    public String listBooks(@RequestParam(name="nameSearch") String nameSearch, Model model) {
        String fullAddress = "https://www.googleapis.com/books/v1/volumes?maxResults=40&q=" + nameSearch;
        JSONObject response = getResponse(fullAddress);
        List<Book> books = Book.getBooksByResponse(response);
        model.addAttribute("nameSearch", nameSearch);
        model.addAttribute("books", books);

        return "books";
    }

    private JSONObject getResponse(String fullAddress) {
        return DoRequest.getRequest(fullAddress);
    }

}
