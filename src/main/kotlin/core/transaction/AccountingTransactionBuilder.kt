package core.transaction

import core.account.AccountSide
import core.account.AccountingEntry
import java.math.BigDecimal
import java.time.Instant
import java.util.HashSet

class AccountingTransactionBuilder private constructor(private val info: Map<String, String>?) {
    private val entries = HashSet<AccountingEntry>()

    fun debit(amount: BigDecimal, accountNumber: String): AccountingTransactionBuilder {
        entries.add(AccountingEntry(amount, accountNumber, AccountSide.DEBIT))
        return this
    }

    fun credit(amount: BigDecimal, accountNumber: String): AccountingTransactionBuilder {
        entries.add(AccountingEntry(amount, accountNumber, AccountSide.CREDIT))
        return this
    }

    fun build(): AccountingTransaction {
        return AccountingTransaction(entries, info, Instant.now().toEpochMilli())
    }

    companion object {

        @JvmStatic fun create(info: Map<String, String>?): AccountingTransactionBuilder {
            return AccountingTransactionBuilder(info)
        }

        @JvmStatic fun create(): AccountingTransactionBuilder {
            return AccountingTransactionBuilder(null)
        }
    }
}
