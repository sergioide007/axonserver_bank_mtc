package pe.gob.mtc.bank.query.bankaccount;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BankAccountEntry {

    @Id
    @GeneratedValue
    private long id;
    private String axonBankAccountId;
    private long balance;
    private long overdraftLimit;

    @SuppressWarnings("unused")
    public BankAccountEntry() {
    }

    public BankAccountEntry(String axonBankAccountId, long balance, long overdraftLimit) {
        this.axonBankAccountId = axonBankAccountId;
        this.balance = balance;
        this.overdraftLimit = overdraftLimit;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAxonBankAccountId() {
        return axonBankAccountId;
    }

    public void setAxonBankAccountId(String axonBankAccountId) {
        this.axonBankAccountId = axonBankAccountId;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public long getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(long overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }
}