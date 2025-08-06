package product.star.account.manager;

import org.springframework.context.support.GenericGroovyApplicationContext;

public class AccountManagerMainGroovy {
    public static void main(String[] args) {
        try (GenericGroovyApplicationContext context = new GenericGroovyApplicationContext("AccountManagerConfig.groovy")) {

            PhoneToAccountResolver phoneToAccountResolver = context.getBean(PhoneToAccountResolver.class);
            AccountService accountService = context.getBean(AccountService.class);
            AccountDao accountDao = context.getBean(AccountDao.class);

            Account account1 = new Account(1, 1000L);
            Account account2 = new Account(2, 2000L);

            accountDao.addAccount(account1);
            accountDao.addAccount(account2);

            String phoneNumber = "12345";
            phoneToAccountResolver.addMapping(phoneNumber, account2);

            System.out.println(account1);
            System.out.println(account2);

            accountService.transferByPhoneNumber(account1.getId(), phoneNumber, 500L);
            System.out.println("After transferByPhoneNumber:");
            System.out.println(account1);
            System.out.println(account2);

            accountService.transfer(account1.getId(), account2.getId(), 250L);
            System.out.println("After direct transfer:");
            System.out.println(account1);
            System.out.println(account2);
        }
    }
}
