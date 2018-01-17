package core

import com.google.common.base.MoreObjects
import com.google.common.collect.Sets
import core.account.Account
import core.account.AccountDetails
import core.account.AccountingEntry
import core.chartofaccounts.ChartOfAccounts
import core.transaction.AccountingTransaction
import core.transaction.AccountingTransactionBuilder
import java.math.BigDecimal
import java.util.HashMap

import com.google.common.base.Preconditions.checkArgument
import java.util.function.Consumer

/**
 * Represents a set of accounts and their transactions.
 */
class Ledger(private val coa: ChartOfAccounts) {
    private val accountNumberToAccount = HashMap<String, Account>()

    private val journal = Journal()

    init {
        // Create coa accounts
        coa.getAccountNumberToAccountDetails().values.forEach(Consumer<AccountDetails> { this.addAccount(it) })
    }

    constructor(journal: Journal, coa: ChartOfAccounts) : this(coa) {
        // Add transactions
        journal.getTransactions().forEach(Consumer<AccountingTransaction> { this.commitTransaction(it) })
    }

    fun createTransaction(info: Map<String, String>?): AccountingTransactionBuilder {
        return AccountingTransactionBuilder.create(info)
    }

    fun commitTransaction(transaction: AccountingTransaction) {
        // Add entries to accounts
        transaction.entries.forEach(Consumer<AccountingEntry> { this.addAccountEntry(it) })
        journal.addTransaction(transaction)
    }

    fun computeTrialBalance(): TrialBalanceResult {
        return TrialBalanceResult(Sets.newHashSet(accountNumberToAccount.values))
    }

    fun getAccountBalance(accountNumber: String): BigDecimal? {
        return accountNumberToAccount[accountNumber]?.balance
    }

    private fun addAccount(accountDetails: AccountDetails) {
        val newAccountNumber = accountDetails.accountNumber
        val accountNumberNotInUse = !accountNumberToAccount.containsKey(newAccountNumber)
        checkArgument(
            accountNumberNotInUse,
            "An account with the account number %s exists already in the ledger", newAccountNumber
        )
        accountNumberToAccount[accountDetails.accountNumber] = Account(accountDetails)
    }

    private fun addAccountEntry(entry: AccountingEntry) {
        accountNumberToAccount[entry.accountNumber]?.addEntry(entry)
    }

    override fun toString(): String {
        return MoreObjects.toStringHelper(this)
            .add("accountNumberToAccountMap", accountNumberToAccount)
            .add("journal", journal)
            .add("chartOfAccounts", coa)
            .toString()
    }
}
