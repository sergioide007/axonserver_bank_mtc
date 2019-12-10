package pe.gob.mtc.bank.api.bankaccount;

import lombok.Value;

@Value
public class DestinationBankAccountNotFoundEvent {

    private String bankTransferId;
}
