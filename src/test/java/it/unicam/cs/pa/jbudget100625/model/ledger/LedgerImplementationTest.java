package it.unicam.cs.pa.jbudget100625.model.ledger;

import it.unicam.cs.pa.jbudget100625.model.account.Account;
import it.unicam.cs.pa.jbudget100625.model.account.AccountImplementation;
import it.unicam.cs.pa.jbudget100625.model.account.AccountType;
import it.unicam.cs.pa.jbudget100625.model.movement.Movement;
import it.unicam.cs.pa.jbudget100625.model.scheduledTransaction.ScheduledTransaction;
import it.unicam.cs.pa.jbudget100625.model.scheduledTransaction.ScheduledTransactionImplementation;
import it.unicam.cs.pa.jbudget100625.model.tag.Tag;
import it.unicam.cs.pa.jbudget100625.model.tag.TagImplementation;
import it.unicam.cs.pa.jbudget100625.model.transaction.Transaction;
import it.unicam.cs.pa.jbudget100625.model.transaction.TransactionImplementation;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Alex Citeroni
 */
public class LedgerImplementationTest {
    private final List<Account> accounts = new ArrayList<>();
    private final List<Transaction> transactions = new ArrayList<>();
    private final List<Tag> tags = new ArrayList<>();
    private final List<ScheduledTransaction> scheduledTransactions = new ArrayList<>();
    private final Tag tag = new TagImplementation(1, "name", "description");
    private final Ledger ledger = new LedgerImplementation(accounts, transactions, tags, scheduledTransactions);
    private final Account account = new AccountImplementation(1, AccountType.ASSETS, "Nome", "Descrizione", 20.0);
    private LocalDate date;

    @Test
    public void getAccounts() {
        assertEquals(accounts, ledger.getAccounts());
    }

    @Test
    public void addTransaction() {
        List<Movement> movements = new ArrayList<>();
        Transaction transaction = new TransactionImplementation(1, date, new ArrayList<>(), movements);
        ledger.addTransaction(transaction);
        assertEquals(transactions, ledger.getTransactions());
    }

    @Test
    public void removeTransaction() {
        List<Movement> movements = new ArrayList<>();
        Transaction transaction = new TransactionImplementation(1, date, new ArrayList<>(), movements);
        ledger.addTransaction(transaction);
        ledger.removeTransaction(transaction);
        assertEquals(transactions, ledger.getTransactions());
    }

    @Test
    public void getTransactions() {
        assertEquals(transactions, ledger.getTransactions());
    }

    @Test
    public void testGetTransactions() {
        assertEquals(transactions, ledger.getTransactions(predicate -> predicate.getTotalAmount() == 0));
    }

    @Test
    public void getTags() {
        assertEquals(tags, ledger.getTags());
    }

    @Test
    public void addAccount() {
        ledger.addAccount(account);
        assertEquals(accounts, ledger.getAccounts());
    }

    @Test
    public void addTag() {
        ledger.addTag(tag);
        assertEquals(tags, ledger.getTags());
    }

    @Test
    public void removeTag() {

        ledger.addTag(tag);
        ledger.removeTag(tag);
        assertEquals(tags, ledger.getTags());
    }

    @Test
    public void getScheduledTransactions() {
        assertEquals(scheduledTransactions, ledger.getScheduledTransaction());
    }

    @Test
    public void addScheduledTransaction() {
        List<ScheduledTransaction> scheduledTransactionList = new ArrayList<>();
        ScheduledTransaction scheduledTransaction = new ScheduledTransactionImplementation(1, "Piano di ammortamento", transactions);
        scheduledTransactionList.add(scheduledTransaction);
        ledger.addScheduledTransaction(scheduledTransaction);
        assertEquals(scheduledTransactionList, scheduledTransactions);
    }

    @Test
    public void removeScheduledTransaction() {
        List<ScheduledTransaction> scheduledTransactionList = new ArrayList<>();
        ScheduledTransaction scheduledTransaction = new ScheduledTransactionImplementation(1, "Piano di ammortamento", transactions);
        scheduledTransactionList.add(scheduledTransaction);
        ledger.addScheduledTransaction(scheduledTransaction);
        ledger.removeScheduledTransaction(scheduledTransaction);
        scheduledTransactionList.remove(scheduledTransaction);
        assertEquals(scheduledTransactionList, scheduledTransactions);
    }

    @Test
    public void schedule() {
        ledger.schedule(date);
        assertEquals(1, ledger.getScheduledTransaction().size());
    }
}
