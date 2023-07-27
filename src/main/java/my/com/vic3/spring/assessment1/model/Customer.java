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
package my.com.vic3.spring.assessment1.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Fauzan
 */
@Entity
@Table(name = "customer")
public class Customer implements Serializable{
    
    private static final long serialVersionUID = 1L;    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_id")
    private Long customerId;
    @OneToMany
    @JoinTable(
            name = "customer_account", 
            joinColumns = @JoinColumn(
                    name = "customer_id", referencedColumnName = "customer_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "account_number", referencedColumnName = "account_number"
            ))
    private List<Account> accounts;

    public Customer() {
    }

    public Customer(Long customerId) {
        this.customerId = customerId;
    }

    public Customer(Long customerId, List<Account> accounts) {
        this.customerId = customerId;
        this.accounts = accounts;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
    
}
