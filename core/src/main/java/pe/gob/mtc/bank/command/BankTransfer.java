package pe.gob.mtc.bank.command;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.spring.stereotype.Aggregate;

import pe.gob.mtc.bank.api.banktransfer.BankTransferCompletedEvent;
import pe.gob.mtc.bank.api.banktransfer.BankTransferCreatedEvent;
import pe.gob.mtc.bank.api.banktransfer.BankTransferFailedEvent;
import pe.gob.mtc.bank.api.banktransfer.CreateBankTransferCommand;
import pe.gob.mtc.bank.api.banktransfer.MarkBankTransferCompletedCommand;
import pe.gob.mtc.bank.api.banktransfer.MarkBankTransferFailedCommand;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
public class BankTransfer {

    @AggregateIdentifier
    private String BankTransferId;
    private String sourceBankAccountId;
    private String destinationBankAccountId;
    private long amount;
    private Status status;

    @SuppressWarnings("unused")
    protected BankTransfer() {
    }

    @CommandHandler
    public BankTransfer(CreateBankTransferCommand command) {
        apply(new BankTransferCreatedEvent(command.getBankTransferId(),
                                           command.getSourceBankAccountId(),
                                           command.getDestinationBankAccountId(),
                                           command.getAmount()));
    }

    @CommandHandler
    public void handle(MarkBankTransferCompletedCommand command) {
        apply(new BankTransferCompletedEvent(command.getBankTransferId()));
    }

    @CommandHandler
    public void handle(MarkBankTransferFailedCommand command) {
        apply(new BankTransferFailedEvent(command.getBankTransferId()));
    }

    @EventHandler
    public void on(BankTransferCreatedEvent event) throws Exception {
        this.BankTransferId = event.getBankTransferId();
        this.sourceBankAccountId = event.getSourceBankAccountId();
        this.destinationBankAccountId = event.getDestinationBankAccountId();
        this.amount = event.getAmount();
        this.status = Status.STARTED;
    }

    @EventHandler
    public void on(BankTransferCompletedEvent event) {
        this.status = Status.COMPLETED;
    }

    @EventHandler
    public void on(BankTransferFailedEvent event) {
        this.status = Status.FAILED;
    }

    private enum Status {
        STARTED,
        FAILED,
        COMPLETED
    }
}