package pe.gob.mtc.bank.api.banktransfer;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Value
public class MarkBankTransferFailedCommand {

    @TargetAggregateIdentifier
    private String bankTransferId;
}
