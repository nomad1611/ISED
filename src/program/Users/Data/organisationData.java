package program.Users.Data;

public class organisationData {
    private  final String docName;
    private final String time;
    private final Integer index;

    public organisationData(String docName, String time, Integer index) {
        this.docName = docName;
        this.time = time;
        this.index = index;
    }

    public String getDocName() {
        return docName;
    }
    public String getTime() {
        return time;
    }
    public Integer getIndex() {
        return index;
    }
}
