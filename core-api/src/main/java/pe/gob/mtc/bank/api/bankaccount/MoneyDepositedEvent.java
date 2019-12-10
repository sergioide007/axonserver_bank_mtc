package pe.gob.mtc.bank.api.bankaccount;

public class MoneyDepositedEvent extends MoneyAddedEvent {

    public MoneyDepositedEvent(String id, long amountOfMoneyDeposited) {
        super(id, amountOfMoneyDeposited);
    }
}
