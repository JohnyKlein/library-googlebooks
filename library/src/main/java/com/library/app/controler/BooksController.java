package com.library.app.controler;

import com.library.app.model.Book;
import com.library.app.utils.Auth;
import com.library.app.utils.Request;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@Controller
public class BooksController {
    private static final String URL_BASE = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String URL_BASE_DELIMITADOR = "https://www.googleapis.com/books/v1/volumes?maxResults=10";

    @Autowired
    private Environment environment;

    @RequestMapping(value={"/", "/books"}, method=RequestMethod.GET)
    public String list(@RequestHeader(value = "Authorization-token", required = false) String idToken,
                       Model model) {
        if (Auth.isValidToken(idToken)) {
            model.addAttribute("serverAddress", getHostAndPort());
            return "books";
        }

        return "login";
    }

    @GetMapping("/books-by-index")
    @CrossOrigin
    @ResponseBody
    public List<Book> listByIndex(@RequestParam(name="name") String name,
                                  @RequestParam(name="index") String index) {
        String fullAddress = URL_BASE_DELIMITADOR + "&startIndex=" + index + "&q=";
        JSONObject response = getResponse(fullAddress, name);
        List<Book> books = Book.getBooksByResponse(response);

        return books;
    }

    @GetMapping("/books-total")
    @CrossOrigin
    @ResponseBody
    private Integer getTotalSearch(@RequestParam(name="name") String name) {
        String fullAddress = URL_BASE;
        JSONObject response = getResponse(fullAddress, name);
        Integer totalItems = (Integer) response.toMap().get("totalItems");

        return totalItems;
    }

    private String getHostAndPort() {
        String address = environment.getProperty("server.address");
        String port = environment.getProperty("server.port");

        return address + ":" + port;
    }

    private JSONObject getResponse(String url, String wordSearched) {
        return Request.getRequest(url, wordSearched);
    }

}