package pe.gob.mtc.bank.api.bankaccount;

import lombok.Getter;

@Getter
public class SourceBankAccountDebitedEvent extends MoneySubtractedEvent {

    private String bankTransferId;

    public SourceBankAccountDebitedEvent(String id, long amountOfMoney, String bankTransferId) {
        super(id, amountOfMoney);

        this.bankTransferId = bankTransferId;
    }
}
