package ee.tarakas.core.bankaccount;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BankAccountRepository extends MongoRepository<BankAccount, String> {

    List<BankAccount> findByUserId(String userId);
}
