package pe.gob.mtc.bank.api.bankaccount;

public class MoneyWithdrawnEvent extends MoneySubtractedEvent {

    public MoneyWithdrawnEvent(String bankAccountId, long amountOfMoney) {
        super(bankAccountId, amountOfMoney);
    }
}