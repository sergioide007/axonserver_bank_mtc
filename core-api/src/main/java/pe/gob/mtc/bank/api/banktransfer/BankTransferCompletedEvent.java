package pe.gob.mtc.bank.api.banktransfer;

import lombok.Value;

@Value
public class BankTransferCompletedEvent {

    private String bankTransferId;
}
