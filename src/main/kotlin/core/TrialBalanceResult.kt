package core

import com.google.common.base.MoreObjects
import core.account.Account
import core.account.AccountDetails

import java.math.BigDecimal
import java.time.Instant
import java.util.TreeMap

import com.google.common.base.Preconditions.checkArgument

/**
 * Describes a ledger's accounts with their corresponding balances at a specific moment in time.
 */
class TrialBalanceResult(accounts: Set<Account>) {
    private val accountDetailsToBalance =
        TreeMap<AccountDetails, BigDecimal> { o1, o2 -> o1.accountNumber.compareTo(o2.accountNumber) }
    private val creationTimestamp: Long
    private val isBalanced: Boolean

    init {
        kotlin.checkNotNull(accounts)
        checkArgument(!accounts.isEmpty())
        accounts.forEach { a -> accountDetailsToBalance[a.accountDetails] = a.balance }
        creationTimestamp = Instant.now().toEpochMilli()
        val balance = BigDecimal.ZERO
        accounts.forEach { a -> balance.add(a.balance) }
        isBalanced = balance == BigDecimal.ZERO
    }

    override fun toString(): String {
        return MoreObjects.toStringHelper(this)
            .add("isBalanced", isBalanced)
            .add("accountDetailsToBalance", accountDetailsToBalance)
            .add("creationTimestamp", Instant.ofEpochMilli(creationTimestamp).toString())
            .toString()
    }
}
