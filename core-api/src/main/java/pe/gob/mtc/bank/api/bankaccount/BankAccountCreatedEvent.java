package pe.gob.mtc.bank.api.bankaccount;

import lombok.Value;

@Value
public class BankAccountCreatedEvent {

    private String id;
    private long overdraftLimit;

}
