package core

import com.google.common.base.MoreObjects
import core.transaction.AccountingTransaction

import java.util.ArrayList

/**
 * Represents a collection of transactions.
 */
class Journal {
    private val transactions = ArrayList<AccountingTransaction>()

    fun addTransaction(transaction: AccountingTransaction) {
        kotlin.checkNotNull(transaction)
        transactions.add(transaction)
    }

    fun getTransactions(): List<AccountingTransaction> {
        return ArrayList(transactions)
    }

    override fun toString(): String {
        return MoreObjects.toStringHelper(this)
            .add("transactions", transactions)
            .toString()
    }
}
