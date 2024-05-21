package program.Users.Data;

import java.sql.Timestamp;

public class organisationData {
    private  final String name;
    private final Timestamp created;
    private final Integer id;

    public organisationData(Integer id,String name, Timestamp created) {
        this.name = name;
        this.created = created;
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public Timestamp getCreated() {
        return created;
    }
    public Integer getId() {
        return id;
    }
}
