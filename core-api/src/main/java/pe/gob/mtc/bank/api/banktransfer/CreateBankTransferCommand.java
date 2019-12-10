package pe.gob.mtc.bank.api.banktransfer;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Value
public class CreateBankTransferCommand {

    @TargetAggregateIdentifier
    private String bankTransferId;
    private String sourceBankAccountId;
    private String destinationBankAccountId;
    private long amount;
}