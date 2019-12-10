package pe.gob.mtc.bank.command;

import org.axonframework.test.saga.FixtureConfiguration;
import org.axonframework.test.saga.SagaTestFixture;
import org.junit.*;

import pe.gob.mtc.bank.api.bankaccount.CreditDestinationBankAccountCommand;
import pe.gob.mtc.bank.api.bankaccount.DebitSourceBankAccountCommand;
import pe.gob.mtc.bank.api.bankaccount.DestinationBankAccountCreditedEvent;
import pe.gob.mtc.bank.api.bankaccount.DestinationBankAccountNotFoundEvent;
import pe.gob.mtc.bank.api.bankaccount.ReturnMoneyOfFailedBankTransferCommand;
import pe.gob.mtc.bank.api.bankaccount.SourceBankAccountDebitRejectedEvent;
import pe.gob.mtc.bank.api.bankaccount.SourceBankAccountDebitedEvent;
import pe.gob.mtc.bank.api.bankaccount.SourceBankAccountNotFoundEvent;
import pe.gob.mtc.bank.api.banktransfer.BankTransferCreatedEvent;
import pe.gob.mtc.bank.api.banktransfer.MarkBankTransferCompletedCommand;
import pe.gob.mtc.bank.api.banktransfer.MarkBankTransferFailedCommand;
import pe.gob.mtc.bank.command.BankTransferManagementSaga;

public class BankTransferManagementSagaTest {

    private FixtureConfiguration testFixture;

    @Before
    public void setUp() throws Exception {
        testFixture = new SagaTestFixture<>(BankTransferManagementSaga.class);
    }

    @Test
    public void testBankTransferCreated() throws Exception {
        String bankTransferId = "bankTransferId";
        String sourceBankAccountId = "sourceBankAccountId";
        String destinationBankAccountId = "destinationBankAccountId";
        long amountOfMoneyToTransfer = 40;

        testFixture.givenNoPriorActivity()
                   .whenAggregate(bankTransferId).publishes(new BankTransferCreatedEvent(bankTransferId,
                                                                                         sourceBankAccountId,
                                                                                         destinationBankAccountId,
                                                                                         amountOfMoneyToTransfer))
                   .expectActiveSagas(1)
                   .expectDispatchedCommands(new DebitSourceBankAccountCommand(sourceBankAccountId,
                                                                               bankTransferId,
                                                                               amountOfMoneyToTransfer));
    }

    @Test
    public void testSourceBankAccountNotFound() throws Exception {
        String bankTransferId = "bankTransferId";
        String sourceBankAccountId = "sourceBankAccountId";
        String destinationBankAccountId = "destinationBankAccountId";
        long amountOfMoneyToTransfer = 40;

        testFixture.givenAggregate(bankTransferId).published(new BankTransferCreatedEvent(bankTransferId,
                                                                                          sourceBankAccountId,
                                                                                          destinationBankAccountId,
                                                                                          amountOfMoneyToTransfer))
                   .whenPublishingA(new SourceBankAccountNotFoundEvent(bankTransferId))
                   .expectActiveSagas(0)
                   .expectDispatchedCommands(new MarkBankTransferFailedCommand(bankTransferId));
    }

    @Test
    public void testSourceBankAccountDebitRejected() throws Exception {
        String bankTransferId = "bankTransferId";
        String sourceBankAccountId = "sourceBankAccountId";
        String destinationBankAccountId = "destinationBankAccountId";
        long amountOfMoneyToTransfer = 40;

        testFixture.givenAggregate(bankTransferId).published(new BankTransferCreatedEvent(bankTransferId,
                                                                                          sourceBankAccountId,
                                                                                          destinationBankAccountId,
                                                                                          amountOfMoneyToTransfer))
                   .whenAggregate(sourceBankAccountId)
                   .publishes(new SourceBankAccountDebitRejectedEvent(bankTransferId))
                   .expectActiveSagas(0)
                   .expectDispatchedCommands(new MarkBankTransferFailedCommand(bankTransferId));
    }

    @Test
    public void testSourceBankAccountDebited() throws Exception {
        String bankTransferId = "bankTransferId";
        String sourceBankAccountId = "sourceBankAccountId";
        String destinationBankAccountId = "destinationBankAccountId";
        long amountOfMoneyToTransfer = 40;

        testFixture.givenAggregate(bankTransferId).published(new BankTransferCreatedEvent(bankTransferId,
                                                                                          sourceBankAccountId,
                                                                                          destinationBankAccountId,
                                                                                          amountOfMoneyToTransfer))
                   .whenAggregate(sourceBankAccountId).publishes(new SourceBankAccountDebitedEvent(sourceBankAccountId,
                                                                                                   amountOfMoneyToTransfer,
                                                                                                   bankTransferId))
                   .expectActiveSagas(1)
                   .expectDispatchedCommands(new CreditDestinationBankAccountCommand(destinationBankAccountId,
                                                                                     bankTransferId,
                                                                                     amountOfMoneyToTransfer));
    }

    @Test
    public void testDestinationBankAccountNotFound() throws Exception {
        String bankTransferId = "bankTransferId";
        String sourceBankAccountId = "sourceBankAccountId";
        String destinationBankAccountId = "destinationBankAccountId";
        long amountOfMoneyToTransfer = 40;

        testFixture.givenAggregate(bankTransferId).published(new BankTransferCreatedEvent(bankTransferId,
                                                                                          sourceBankAccountId,
                                                                                          destinationBankAccountId,
                                                                                          amountOfMoneyToTransfer))
                   .andThenAggregate(sourceBankAccountId).published(new SourceBankAccountDebitedEvent(
                sourceBankAccountId, amountOfMoneyToTransfer, bankTransferId))
                   .whenPublishingA(new DestinationBankAccountNotFoundEvent(bankTransferId))
                   .expectActiveSagas(0)
                   .expectDispatchedCommands(new ReturnMoneyOfFailedBankTransferCommand(sourceBankAccountId,
                                                                                        amountOfMoneyToTransfer),
                                             new MarkBankTransferFailedCommand(bankTransferId));
    }

    @Test
    public void testDestinationBankAccountCredited() throws Exception {
        String bankTransferId = "bankTransferId";
        String sourceBankAccountId = "sourceBankAccountId";
        String destinationBankAccountId = "destinationBankAccountId";
        long amountOfMoneyToTransfer = 40;

        testFixture.givenAggregate(bankTransferId).published(new BankTransferCreatedEvent(bankTransferId,
                                                                                          sourceBankAccountId,
                                                                                          destinationBankAccountId,
                                                                                          amountOfMoneyToTransfer))
                   .andThenAggregate(sourceBankAccountId).published(new SourceBankAccountDebitedEvent(
                sourceBankAccountId,
                amountOfMoneyToTransfer,
                bankTransferId))
                   .whenAggregate(destinationBankAccountId).publishes(new DestinationBankAccountCreditedEvent(
                destinationBankAccountId,
                amountOfMoneyToTransfer,
                bankTransferId))
                   .expectActiveSagas(0)
                   .expectDispatchedCommands(new MarkBankTransferCompletedCommand(bankTransferId));
    }
}
