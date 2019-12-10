package pe.gob.mtc.bank.query.banktransfer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BankTransferEntry {

    @Id
    @GeneratedValue
    private long id;
    private String axonBankTransferId;
    private String sourceBankAccountId;
    private String destinationBankAccountId;
    private long amount;
    private Status status;

    @SuppressWarnings("unused")
    public BankTransferEntry() {
    }

    public BankTransferEntry(String axonBankTransferId, String sourceBankAccountId, String destinationBankAccountId, long amount) {
        this.axonBankTransferId = axonBankTransferId;
        this.sourceBankAccountId = sourceBankAccountId;
        this.destinationBankAccountId = destinationBankAccountId;
        this.amount = amount;
        this.status = Status.STARTED;
    }

    public String getAxonBankTransferId() {
        return axonBankTransferId;
    }

    public void setAxonBankTransferId(String axonBankTransferId) {
        this.axonBankTransferId = axonBankTransferId;
    }

    public String getSourceBankAccountId() {
        return sourceBankAccountId;
    }

    public void setSourceBankAccountId(String sourceBankAccountId) {
        this.sourceBankAccountId = sourceBankAccountId;
    }

    public String getDestinationBankAccountId() {
        return destinationBankAccountId;
    }

    public void setDestinationBankAccountId(String destinationBankAccountId) {
        this.destinationBankAccountId = destinationBankAccountId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        STARTED,
        FAILED,
        COMPLETED
    }
}
