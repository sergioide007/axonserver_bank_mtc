package pe.gob.mtc.bank.query.banktransfer;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.gob.mtc.bank.api.banktransfer.BankTransferCompletedEvent;
import pe.gob.mtc.bank.api.banktransfer.BankTransferCreatedEvent;
import pe.gob.mtc.bank.api.banktransfer.BankTransferFailedEvent;

@Component
public class BankTransferEventListener {

    private BankTransferRepository repository;

    @Autowired
    public BankTransferEventListener(BankTransferRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(BankTransferCreatedEvent event) {
        repository.save(new BankTransferEntry(event.getBankTransferId(),
                                              event.getSourceBankAccountId(),
                                              event.getDestinationBankAccountId(),
                                              event.getAmount()));
    }

    @EventHandler
    public void on(BankTransferFailedEvent event) {
        BankTransferEntry bankTransferEntry = repository.findOneByAxonBankTransferId(event.getBankTransferId());
        bankTransferEntry.setStatus(BankTransferEntry.Status.FAILED);

        repository.save(bankTransferEntry);
    }

    @EventHandler
    public void on(BankTransferCompletedEvent event) {
        BankTransferEntry bankTransferEntry = repository.findOneByAxonBankTransferId(event.getBankTransferId());
        bankTransferEntry.setStatus(BankTransferEntry.Status.COMPLETED);

        repository.save(bankTransferEntry);
    }


}
