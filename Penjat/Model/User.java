import java.io.Serializable;

public class User implements Serializable{
    private String name;
    private String user;
    private String password;
    private boolean admin;
    private int punts;

    public User(String name, String user, String password) {
        this.name = name;
        this.user = user;
        this.password = password;
        admin = false;
        punts = 0;
    }

    public User(String name, String user, String password, boolean admin) {
        this.name = name;
        this.user = user;
        this.password = password;
        this.admin = admin;
        punts = 0;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public void addPunts(int punts) {
        this.punts += punts;
    }

    public boolean isAdmin() {
        return admin;
    }

    public String getName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getName'");
    }
    
}
