package pe.gob.mtc.bank.api.bankaccount;

import lombok.Value;
import lombok.experimental.NonFinal;

@Value
@NonFinal
public abstract class MoneyAddedEvent {

    private String bankAccountId;
    private long amount;
}
