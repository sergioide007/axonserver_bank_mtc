package pe.gob.mtc.bank.api.banktransfer;

import lombok.Value;

@Value
public class BankTransferFailedEvent {

    private String bankTransferId;
}
