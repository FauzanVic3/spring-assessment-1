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
package my.com.vic3.spring.assessment1.service;

import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import my.com.vic3.spring.assessment1.helper.JSONUtil;
import my.com.vic3.spring.assessment1.model.Transaction;
import my.com.vic3.spring.assessment1.repository.TransactionRepository;
import org.hibernate.LockMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Fauzan
 */
@Slf4j
@Service
public class TransactionService {
    
    @Autowired
    TransactionRepository transactionRepository;
    
    public Page<Transaction> getAll(Pageable pageable){
        
        return transactionRepository.findAll(pageable);
    }
    
    public Optional<List<Transaction>> getByCustomerId(Pageable pageable, Long customerId){
        return transactionRepository.findByCustomerId(pageable, customerId);
    }
    
    public Optional<List<Transaction>> getByAccountNumber(Pageable pageable, Long accountNumber){
        return transactionRepository.findByAccountNumber(pageable, accountNumber);
    }
    
    public Optional<List<Transaction>> getByDescription(Pageable pageable, String description){
        return transactionRepository.findByDescription(pageable, description);
    }
    
    public Optional<List<Transaction>> getByFilter(Pageable pageable, Long customerId, Long accountNumber, String description){
        
        return transactionRepository.findByFilter(
                customerId != null ? String.valueOf(customerId) : "", 
                accountNumber != null ? String.valueOf(accountNumber) : "", 
                description,
                pageable);
    }
    
    public Optional<Transaction> createTransaction(Transaction transaction){
                
        if(transaction != null){
            return Optional.ofNullable(transactionRepository.save(transaction));
        }else{
            return null;
        }
    }
    
    public Optional<Transaction> updateTransaction(Transaction transaction){
        
        Long l = transaction.getId();
        
        log.info("Update transaction id : " + l);
        
//        Transaction t = transactionRepository.getReferenceById(l.intValue());
        Optional<Transaction> t1 = transactionRepository.findById(l);
        
        if(t1.isPresent()){
            Transaction t = t1.get();
            t.setAccountNumber(JSONUtil.nullOrString(transaction.getAccountNumber(), t.getAccountNumber()));
            t.setCustomerId(JSONUtil.nullOrString(transaction.getCustomerId(), t.getCustomerId()));
            t.setDescription(JSONUtil.nullOrString(transaction.getDescription(), t.getDescription()));
            t.setTrxAmount(JSONUtil.nullOrString(transaction.getTrxAmount(), t.getTrxAmount()));
            t.setTrxDate(JSONUtil.nullOrString(transaction.getTrxDate(), t.getTrxDate()));
            t.setTrxTime(JSONUtil.nullOrString(transaction.getTrxTime(), t.getTrxTime()));

            return Optional.ofNullable(transactionRepository.save(t));
        }else{
            return null;
        }
    }
    
    public String deleteTransaction(String id){
        
        Long l = Long.getLong(id);
        
        log.info("Delete transaction id : " + l);
        
        Optional<Transaction> t1 = transactionRepository.findById(l);
        
        if(t1.isPresent()){
            Transaction t = t1.get();
            
            transactionRepository.delete(t);
            
            return "Delete success";
        }else{
            return "Delete failed. Record with that ID is not found";
        }
    }
}
