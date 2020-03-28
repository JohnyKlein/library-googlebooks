package com.library.app.model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Book {
    private String id;
    private String title;
    private List<String> authors;
    private String publisher;
    private String publishedDate;
    private String description;
    private String thumbnail;

    public Book(String id, String title, List<String> authors, String publisher, String publishedDate, String description,
                String thumbnail) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public String getId() {
        return id;
    }

    public String getTitle() { return title; }

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

        if (items != null) {
            items.forEach(item -> {
                try {
                    String id = (String) item.get("id");
                    HashMap<String, Object> volume = (HashMap<String, Object>) item.get("volumeInfo");
                    String title = (String) volume.get("title");
                    List<String> authors = (List<String>) volume.get("author");
                    String publisher = (String) volume.get("publisher");
                    String publishedDate = (String) volume.get("publishedDate");
                    String description = (String) volume.get("description");
                    String thumbnail = getThumbnail((HashMap<String, Object>) volume.get("imageLinks"));

                    books.add(new Book(id, title, authors, publisher, publishedDate, description, thumbnail));
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            });
        }

        return books;
    }

    private static String getThumbnail(HashMap<String, Object> volumeImage) {
        if (volumeImage != null) {
            if (volumeImage.get("thumbnail") != null) {
                return (String) volumeImage.get("thumbnail");
            }
        }

        return "";
    }
}
