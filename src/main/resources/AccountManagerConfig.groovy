
import product.star.account.manager.*

beans {
    accountDao(InMemoryAccountDao)

    phoneToAccountResolver(InMemoryPhoneToAccountResolver)

    accountService(InMemoryAccountService,
            ref("phoneToAccountResolver"),
            ref("accountDao")
    )
}
