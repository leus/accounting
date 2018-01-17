package core.account

import com.google.common.base.MoreObjects
import com.google.common.base.Preconditions.checkArgument
import com.google.common.base.Preconditions.checkState
import core.transaction.AccountingTransaction
import java.math.BigDecimal

/**
 * Represents an Accounting Entry.
 * The transaction reference is set automatically when an AccountingEntry is passed to the transaction constructor.
 * Once the transaction is set, it can't be changed.
 */
class AccountingEntry(amount: BigDecimal, accountNumber: String, accountSide: AccountSide) {

    val amount: BigDecimal

    val accountSide: AccountSide

    val accountNumber: String


    // Indicates if the transaction was set
    private var freeze = false

    init {
        this.amount = checkNotNull(amount)
        this.accountNumber = checkNotNull(accountNumber)
        this.accountSide = checkNotNull(accountSide)
        checkArgument(amount.signum() == 1, "Accounting entries can't have a negative amount")
        checkArgument(!accountNumber.isEmpty())
    }

    /**
     * Gets the associated transaction.
     * Throws a NullPointerException if no transaction is associated.
     * @return Associated transaction
     */
    var transaction: AccountingTransaction
        get() {
            checkNotNull(transaction) { "Getter returning null. You have to set a transaction." }
            return transaction
        }
        /**
         * This setter is required to enable circular references between entries and transactions.
         * @param transaction The transaction belonging to this entry
         */
        set(transaction: AccountingTransaction) {
            checkState(!freeze, "An AccountingEntry's transaction can only be set once")
            checkNotNull(transaction)
            freeze = true
        }

    override fun toString(): String {
        return MoreObjects.toStringHelper(this)
            .add("amount", amount.toString())
            .addValue(accountSide)
            .toString()
    }
}
