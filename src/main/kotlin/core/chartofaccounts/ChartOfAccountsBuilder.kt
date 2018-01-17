package core.chartofaccounts

import core.account.AccountDetails
import core.account.AccountDetailsImpl
import core.account.AccountSide

import java.util.HashSet

class ChartOfAccountsBuilder private constructor() {
    private val accountDetails = HashSet<AccountDetails>()

    fun addAccount(accountNumber: String, name: String, increaseSide: AccountSide): ChartOfAccountsBuilder {
        val accountDetails = AccountDetailsImpl(accountNumber, name, increaseSide)
        this.accountDetails.add(accountDetails)
        return this
    }

    fun build(): ChartOfAccounts {
        return ChartOfAccounts(accountDetails)
    }

    companion object {

        @JvmStatic fun create(): ChartOfAccountsBuilder {
            return ChartOfAccountsBuilder()
        }
    }
}
