package core.chartofaccounts

import com.google.common.base.MoreObjects
import com.google.common.base.Preconditions.checkArgument
import core.account.AccountDetails

/**
 * Represents an immutable collection of available accounts.
 */
class ChartOfAccounts(accountDetails: Set<AccountDetails>) {
    private val accountNumberToAccountDetails: MutableMap<String, AccountDetails>

    init {
        kotlin.checkNotNull(accountDetails)
        checkArgument(!accountDetails.isEmpty())
        this.accountNumberToAccountDetails = HashMap()
        accountDetails.forEach { ad -> this.accountNumberToAccountDetails[ad.accountNumber] = ad }
    }

    fun getAccountNumberToAccountDetails(): Map<String, AccountDetails> {
        return HashMap(accountNumberToAccountDetails)
    }

    override fun toString(): String {
        return MoreObjects.toStringHelper(this)
            .add("accounts", accountNumberToAccountDetails.values)
            .toString()
    }
}
