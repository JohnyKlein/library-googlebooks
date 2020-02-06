package com.library.app.model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Book {
    private String title;
    private List<String> authors;
    private String publisher;
    private String publishedDate;
    private String description;
    private String thumbnail;

    public Book(String title, List<String> authors, String publisher, String publishedDate, String description,
                String thumbnail) {
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public static List<Book> getBooksByResponse(JSONObject response) {
        List<Book> books = new ArrayList<>();
        List<HashMap<String, Object>> items = (List<HashMap<String, Object>>) response.toMap().get("items");

        items.forEach(item -> {
            HashMap<String, Object> volume = (HashMap<String, Object>) item.get("volumeInfo");
            String title = (String) volume.get("title");
            List<String> authors = (List<String>) volume.get("author");
            String publisher = (String) volume.get("publisher");
            String publishedDate = (String) volume.get("publishedDate");
            String description = (String) volume.get("description");
            String thumbnail = (String) ((HashMap<String, Object>) volume.get("imageLinks")).get("thumbnail");
            books.add(new Book(title, authors, publisher, publishedDate, description, thumbnail));
        });

        return books;
    }
}
