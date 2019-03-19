package ee.tarakas.core.bankaccount;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BankAccountRepository extends MongoRepository<BankAccount, String> {

    List<BankAccount> findAllByUserId(String userId);

    Optional<BankAccount> findByUserIdAndType(String userId, BankAccountType type);

}
