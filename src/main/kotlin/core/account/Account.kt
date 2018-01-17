package core.account

import com.google.common.base.MoreObjects
import com.google.common.base.Preconditions.checkArgument
import java.math.BigDecimal

/**
 * Represents an account with entries.
 */
class Account {
    private val entries = ArrayList<AccountingEntry>()

    val accountDetails: AccountDetails

    /**
     * Returns the debit/credit balance with consideration of the increase side
     * @return Balance
     */
    val balance: BigDecimal
        get() {
            val signum = if (accountDetails.increaseSide == AccountSide.DEBIT)
                BigDecimal.ONE
            else
                BigDecimal.ONE.negate()
            return rawBalance.multiply(signum)
        }

    /**
     * Returns the debit/credit balance without consideration of the increase side
     * @return Balance
     */
    val rawBalance: BigDecimal
        get() {
            return entries
                .map { e -> if (e.accountSide == AccountSide.DEBIT) e.amount else e.amount.negate() }
                .fold(BigDecimal.ZERO) { acc, e -> acc + e }
        }

    constructor(accountDetails: AccountDetails) {
        this.accountDetails = checkNotNull(accountDetails)
    }

    constructor(accountNumber: String, name: String, increaseSide: AccountSide) {
        this.accountDetails = AccountDetailsImpl(accountNumber, name, increaseSide)
    }

    /**
     * Adds an entry to the account.
     * @param entry A debit or credit entry
     */
    fun addEntry(entry: AccountingEntry) {
        checkNotNull(entry)
        checkArgument(entry.accountNumber.equals(accountDetails.accountNumber))
        entries.add(entry)
    }

    override fun toString(): String {
        return MoreObjects.toStringHelper(this)
            .add("entries", entries)
            .add("accountDetails", accountDetails)
            .toString()
    }
}
