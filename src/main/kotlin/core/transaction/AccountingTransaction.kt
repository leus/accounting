package core.transaction

import core.account.AccountSide
import core.account.AccountingEntry
import java.math.BigDecimal
import java.time.Instant
import java.util.*

import com.google.common.base.Preconditions.checkArgument
import com.google.common.base.Preconditions.checkNotNull

/**
 * Represents a group of related account entries.
 */
class AccountingTransaction @JvmOverloads constructor(
    entries: Set<AccountingEntry>,
    info: Map<String, String>? = null,
    private val bookingDateTimestamp: Long = Instant.now().toEpochMilli()
) {
    val entries: Set<AccountingEntry>

    private val info: Map<String, String>

    val isBalanced: Boolean
        get() {
            val debits = entries.map { e ->
                if (e.accountSide === AccountSide.DEBIT)
                    e.amount
                else
                    e.amount.negate()
            }.fold(BigDecimal.ZERO) { acc, e -> acc + e}
            return debits.compareTo(BigDecimal.ZERO) == 0
        }

    init {
        this.info = info ?: HashMap()
        this.entries = checkNotNull(entries)
        checkArgument(!entries.isEmpty())
        checkArgument(entries.size >= 2, "A transaction consists of at least two entries")
        checkArgument(isBalanced, "Transaction unbalanced")
        entries.forEach { e -> e.transaction = this }
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("Transaction ")
            .append(Instant.ofEpochMilli(bookingDateTimestamp).toString())
            .append("\n")
        entries.stream().forEach { e -> sb.append(e).append("\n") }
        return sb.toString()
    }
}
