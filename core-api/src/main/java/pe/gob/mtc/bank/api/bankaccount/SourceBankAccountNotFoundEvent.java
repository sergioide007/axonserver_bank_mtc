package pe.gob.mtc.bank.api.bankaccount;

import lombok.Value;

@Value
public class SourceBankAccountNotFoundEvent {

    private String bankTransferId;
}
