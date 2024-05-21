package program.Users.Data;

import java.sql.Timestamp;

public class outcomData {
    private final Integer id;
    private final String name;
    private final String executor;
    private final java.sql.Timestamp created; // Use Timestamp for TIMESTAMP columns
    private final String message;



    public outcomData(int id, String name, String executor, java.sql.Timestamp created, String message) {

        this.id = id;
        this.name = name;
        this.executor = executor;
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


    public String getExecutor() {
        return executor;
    }
}

