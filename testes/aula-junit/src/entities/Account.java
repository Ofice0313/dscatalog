package entities;


import java.util.Objects;

public class Account {

    public static double DEPOSIT_FEE_PERCENTAGE = 0.02;

    private Long id;
    private Double balance;

    public Account() {

    }

    public Account(Long id, Double balance) {
        this.id = id;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        if(amount > 0) {
            amount -= amount * DEPOSIT_FEE_PERCENTAGE;
            balance += amount;
        }
    }

    public void withdraw(double amount) {
        if(amount > balance) {
            throw new IllegalArgumentException();
        }
        balance -= amount;
    }

    public double fullWithdraw() {
        double aux = balance;
        balance = 0.0;
        return aux;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                '}';
    }
}
