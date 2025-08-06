package product.star.account.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InMemoryAccountService implements AccountService {

    private final PhoneToAccountResolver phoneToAccountResolver;
    private final AccountDao accountDao;

    @Autowired
    public InMemoryAccountService(PhoneToAccountResolver phoneToAccountResolver, AccountDao accountDao) {
        this.phoneToAccountResolver = phoneToAccountResolver;
        this.accountDao = accountDao;
    }

    @Override
    public void transfer(long fromId, long toId, long amount) {
        var accountFrom = accountDao.getAccount(fromId);
        var accountTo = accountDao.getAccount(toId);
        if (accountFrom.getAmount() < amount) {
            throw new IllegalArgumentException("Not enough money on account: " + fromId);
        }
        accountDao.setAmount(fromId, accountFrom.getAmount() - amount);
        accountDao.setAmount(toId, accountTo.getAmount() + amount);
    }

    @Override
    public void transferByPhoneNumber(long fromId, String phoneNumber, long amount) {
        var to = phoneToAccountResolver.findAccountByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new IllegalArgumentException("Phone number not found: " + phoneNumber));
        transfer(fromId, to.getId(), amount);
    }
}
