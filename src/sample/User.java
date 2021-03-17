package sample;

public class User {
    private int user_id;
    private String user_name;
    private String user_password;
    private int user_mobile;
    private String user_email;
    private String user_address;

    public User(int user_id, String user_name, String user_password, int user_mobile, String user_email, String user_address) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_password = user_password;
        this.user_mobile = user_mobile;
        this.user_email = user_email;
        this.user_address = user_address;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public int getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(int user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }
}
