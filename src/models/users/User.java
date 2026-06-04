package models.users;

public abstract class User {
    protected String username;
    protected String password;
    protected String email;
    protected int balance;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.balance = 0;
    }

    public abstract String getRole();

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public int getBalance() { return balance; }
    public void setBalance(int balance) { this.balance = balance; }

    public void addBalance(int amount) {
        this.balance += amount;
    }

    public boolean deductBalance(int amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
            return true;
        }
        return false;
    }
}
