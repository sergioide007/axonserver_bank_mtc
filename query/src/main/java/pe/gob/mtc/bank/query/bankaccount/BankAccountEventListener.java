package pe.gob.mtc.bank.query.bankaccount;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import pe.gob.mtc.bank.api.bankaccount.BankAccountCreatedEvent;
import pe.gob.mtc.bank.api.bankaccount.MoneyAddedEvent;
import pe.gob.mtc.bank.api.bankaccount.MoneySubtractedEvent;

@Component
public class BankAccountEventListener {

    private BankAccountRepository repository;
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public BankAccountEventListener(BankAccountRepository repository, SimpMessageSendingOperations messagingTemplate) {
        this.repository = repository;
        this.messagingTemplate = messagingTemplate;
    }

    @EventHandler
    public void on(BankAccountCreatedEvent event) {
        repository.save(new BankAccountEntry(event.getId(), 0, event.getOverdraftLimit()));

        broadcastUpdates();
    }

    @EventHandler
    public void on(MoneyAddedEvent event) {
        BankAccountEntry bankAccountEntry = repository.findOneByAxonBankAccountId(event.getBankAccountId());
        bankAccountEntry.setBalance(bankAccountEntry.getBalance() + event.getAmount());

        repository.save(bankAccountEntry);

        broadcastUpdates();
    }

    @EventHandler
    public void on(MoneySubtractedEvent event) {
        BankAccountEntry bankAccountEntry = repository.findOneByAxonBankAccountId(event.getBankAccountId());
        bankAccountEntry.setBalance(bankAccountEntry.getBalance() - event.getAmount());

        repository.save(bankAccountEntry);

        broadcastUpdates();
    }

    private void broadcastUpdates() {
        Iterable<BankAccountEntry> bankAccountEntries = repository.findAll();
        messagingTemplate.convertAndSend("/topic/bank-accounts.updates", bankAccountEntries);
    }

}
