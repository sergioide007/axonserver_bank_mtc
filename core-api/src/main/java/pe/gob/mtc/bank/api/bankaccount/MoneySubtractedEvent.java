package pe.gob.mtc.bank.api.bankaccount;

import lombok.Value;
import lombok.experimental.NonFinal;

@Value
@NonFinal
public abstract class MoneySubtractedEvent {

    private String bankAccountId;
    private long amount;
}
