package other.client;

public class clientData {
    private final Integer id;
    private final String name;
    private final String position;
    private   Integer salary;

    public clientData( Integer id,String name, String position, Integer salary) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.salary = salary;

    }
    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;

    }
    public String getPosition() {
        return position;
    }
    public Integer getSalary() {
        return salary;
    }


}
