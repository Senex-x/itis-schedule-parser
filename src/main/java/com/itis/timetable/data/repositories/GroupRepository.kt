package com.itis.timetable.data.repositories

import com.itis.timetable.data.models.group.Group
import lombok.NoArgsConstructor
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
open class GroupRepository : BaseRepository<Group, Long> {
    @Autowired
    private lateinit var sessionFactory: SessionFactory

    override fun save(item: Group) {
        val session = sessionFactory.currentSession
        session.save(item)
    }

    override fun get(primaryKey: Long): Group? {
        val session = sessionFactory.currentSession
        return session.get(Group::class.java, primaryKey)
    }

    override fun getAll(): List<Group> {
        TODO("Not yet implemented")
    }

    override fun delete(id: Long) {
        TODO("Not yet implemented")
    }
}
/*
@Repository
@Transactional
class BankAccountDAO {

    fun listBankAccountInfo(): List<BankAccountInfo> {
        val sql = ("Select new " + BankAccountInfo::class.java.getName() //
                + "(e.id,e.fullName,e.balance) " //
                + " from " + BankAccount::class.java.getName() + " e ")
        val session: Session = sessionFactory!!.currentSession
        val query: Query<BankAccountInfo> = session.createQuery(sql, BankAccountInfo::class.java)
        return query.getResultList()
    }

    // MANDATORY: Transaction must be created before.
    @Transactional(propagation = Propagation.MANDATORY)
    @Throws(BankTransactionException::class)
    fun addAmount(id: Long, amount: Double) {
        val account: BankAccount = findById(id) ?: throw BankTransactionException("Account not found $id")
        val newBalance: Double = account.getBalance() + amount
        if (account.getBalance() + amount < 0) {
            throw BankTransactionException(
                "The money in the account '" + id + "' is not enough (" + account.getBalance() + ")"
            )
        }
        account.setBalance(newBalance)
    }

    // Do not catch BankTransactionException in this method.
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = BankTransactionException::class)
    @Throws(
        BankTransactionException::class
    )
    fun sendMoney(fromAccountId: Long, toAccountId: Long, amount: Double) {
        addAmount(toAccountId, amount)
        addAmount(fromAccountId, -amount)
    }
}*/