package sample;

public class User {
    private int user_id;
    private String userName;
    private String email;

    public User() {
    }

    public User(int user_id, String userName, String email) {
        this.user_id = user_id;
        this.userName = userName;
        this.email = email;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
