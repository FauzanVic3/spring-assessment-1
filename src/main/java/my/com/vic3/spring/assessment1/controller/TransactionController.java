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
package my.com.vic3.spring.assessment1.controller;

import java.util.List;
import javax.validation.Valid;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import my.com.vic3.common.lib.handler.exception.BadRequestException;
import my.com.vic3.common.lib.handler.exception.NotFoundException;
import my.com.vic3.spring.assessment1.model.Transaction;
import my.com.vic3.spring.assessment1.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Fauzan
 */
@RestController
@RequestMapping("/api")
public class TransactionController {
    
    @Autowired
    TransactionService transactionService;
    
    @GetMapping("/v1/transactions")
    public Page<Transaction> getAll(
            @QueryParam("page") int page, 
            @QueryParam("size") int size){
        
        Pageable pageable = PageRequest.of(page, size);            
        return transactionService
                .getAll(pageable);
    }
    
    @GetMapping("/v1/transaction")
    public List<Transaction> getByFilter(
            @QueryParam("page") int page, 
            @QueryParam("size") int size, 
            @Nullable @QueryParam("customer-id") Long customerId, 
            @Nullable @QueryParam("account-number") Long accountNumber,
            @Nullable @QueryParam("description") String description){
                
        Pageable pageable = PageRequest.of(page, size);   
                
        return transactionService
                .getByFilter(pageable, customerId, accountNumber, description)
                .orElseThrow(
                        () -> new NotFoundException("Record not found")
                );                
    }
    
    @PostMapping("/v1/transaction")
    public Transaction create(@RequestBody @Valid Transaction transaction){
        return transactionService
                .createTransaction(transaction)
                .orElseThrow(
                        () -> new BadRequestException("Unable to add transaction")
                );
    }
    
    @PutMapping("/v1/transaction")
    public Transaction update(@RequestBody @Valid Transaction transaction){
        
        if(transaction == null) throw new BadRequestException("Empty request to update");
        if(transaction.getId() == null) throw new BadRequestException("ID for transaction is empty");
        
        return transactionService
                .updateTransaction(transaction)
                .orElseThrow(
                        () -> new BadRequestException("Unable to update transaction")
                );
    }
    
    @DeleteMapping("/v1/transaction")
    public String delete(@PathParam("id") String id){
        
        if(id == null) throw new BadRequestException("ID to delete not provided");
        
        return transactionService
                .deleteTransaction(id);
                
    }
}
