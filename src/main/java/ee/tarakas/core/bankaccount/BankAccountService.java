package ee.tarakas.core.bankaccount;

import ee.tarakas.core.user.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BankAccountService {

	private static final String DUMMY_ACCOUNT_NUMBER = "EE001001001001";
	private static final String DUMMY_TOKEN = "token";

	private final BankAccountRepository bankAccountRepository;

	BankAccountService(BankAccountRepository bankAccountRepository) {
		this.bankAccountRepository = bankAccountRepository;
	}

	BigDecimal getUserTotalBalance(String userId) {
		return bankAccountRepository.findAllByUserId(userId).stream()
				.map(BankAccount::getAmount)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	/**
	 *
	 * @param userId user ID
	 * @param amount amount to add or subtract from balance
	 * @return new balance
	 */
	BigDecimal insertOrUpdateBalance(String userId, BigDecimal amount) {
		BankAccount bankAccount = bankAccountRepository.findByUserIdAndType(userId, BankAccountType.DEMO)
				.orElseThrow(() -> new IllegalArgumentException("User has no bank account"));

		bankAccount.updateAmountBy(amount);
		return bankAccountRepository.save(bankAccount).getAmount();
	}

	public void createDemoBankAccount(User user) {
		// create demo account for new user
		BankAccount bankAccount = BankAccount.builder()
				.userId(user.getId())
				.bank(Bank.LHV)
				.accountNumber(DUMMY_ACCOUNT_NUMBER)
				.token(DUMMY_TOKEN)
				.type(BankAccountType.DEMO)
				.build();
		bankAccountRepository.save(bankAccount);
	}

}
