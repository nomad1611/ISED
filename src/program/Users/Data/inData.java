package program.Users.Data;

import java.sql.Timestamp;

public class inData {
    private final Integer id;
    private final String name;
    private final String author;
    private final java.sql.Timestamp created; // Use Timestamp for TIMESTAMP columns
    private final String message;

    public inData(Integer id, String name, String author, java.sql.Timestamp created, String message) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.created = created;
        this.message = message;
    }
    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;

    }
    public Timestamp getCreated() {
        return created;
    }
    public String getMessage() {
        return message;
    }


    public String getAuthor() {
        return author;
    }
}
