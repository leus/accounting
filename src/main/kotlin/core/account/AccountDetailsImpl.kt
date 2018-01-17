package core.account

import com.google.common.base.MoreObjects
import com.google.common.base.Preconditions.checkArgument


/**
 * Represents an immutable account description.
 */
class AccountDetailsImpl(accountNumber: String, name: String, increaseSide: AccountSide) : AccountDetails {
    override val increaseSide: AccountSide

    override val accountNumber: String

    private var name: String

    init {
        this.accountNumber = checkNotNull(accountNumber)
        this.increaseSide = checkNotNull(increaseSide)
        this.name = checkNotNull(name)
        checkArgument(!accountNumber.isEmpty())
        checkArgument(!name.isEmpty())
    }

    override fun toString(): String {
        return MoreObjects.toStringHelper(this)
            .add("accountNumber", accountNumber)
            .add("name", name)
            .add("increaseSide", increaseSide)
            .toString()
    }
}

