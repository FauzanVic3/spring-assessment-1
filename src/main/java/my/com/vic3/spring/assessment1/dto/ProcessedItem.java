/*
 * Copyright (C) 2023 Fauzan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package my.com.vic3.spring.assessment1.dto;

import my.com.vic3.spring.assessment1.model.Account;
import my.com.vic3.spring.assessment1.model.Customer;
import my.com.vic3.spring.assessment1.model.Description;
import my.com.vic3.spring.assessment1.model.Transaction;

/**
 *
 * @author Fauzan
 */
public class ProcessedItem {
        
    private Account account;
    private Customer customer;
    private Description description;
    private Transaction transaction;

    public ProcessedItem() {
    }

    public ProcessedItem(Account account, Customer customer, Description description, Transaction transaction) {
        this.account = account;
        this.customer = customer;
        this.description = description;
        this.transaction = transaction;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
    
    
}
