package pe.gob.mtc.bank.query.bankaccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccountEntry, String> {

    Iterable<BankAccountEntry> findAllByOrderByIdAsc();
    BankAccountEntry findOneByAxonBankAccountId(String axonBankTransferId);
}
