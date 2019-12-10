package pe.gob.mtc.bank.api.banktransfer;

import lombok.Value;

@Value
public class BankTransferCreatedEvent {

    private String BankTransferId;
    private String sourceBankAccountId;
    private String destinationBankAccountId;
    private long amount;
}
