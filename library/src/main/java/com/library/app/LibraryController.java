package com.library.app;

import com.library.app.model.Book;
import com.library.app.request.DoRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class LibraryController {

    private static final String URL_BASE = "https://www.googleapis.com/books/v1/volumes?maxResults=10&q=";

    @Autowired
    private Environment environment;

    @GetMapping(value="/")
    public String login() {
        return "login";
    }

    @GetMapping("/books")
    public String listBooksByName(@RequestParam(name="name") String name, Model model) {
        String fullAddress = URL_BASE + name;
        JSONObject response = getResponse(fullAddress);
        List<Book> books = Book.getBooksByResponse(response);
        model.addAttribute("name", name);
        model.addAttribute("books", books);
        model.addAttribute("serverAddress", getHostAndPort());

        return "books";
    }

    @GetMapping("/books-by-index")
    @CrossOrigin
    @ResponseBody
    public List<Book> listBooksByNameAndIndex(@RequestParam(name="name") String name,
                                              @RequestParam(name="index") String index) {
        String fullAddress = URL_BASE + name + "&startIndex=" + index;
        JSONObject response = getResponse(fullAddress);
        List<Book> books = Book.getBooksByResponse(response);

        return books;
    }

    private String getHostAndPort() {
        String address = environment.getProperty("server.address");
        String port = environment.getProperty("server.port");

        return address + ":" + port;
    }

    private JSONObject getResponse(String fullAddress) {
        return DoRequest.getRequest(fullAddress);
    }

}
