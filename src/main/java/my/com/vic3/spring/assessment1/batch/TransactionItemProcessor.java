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
package my.com.vic3.spring.assessment1.batch;

import lombok.extern.slf4j.Slf4j;
import my.com.vic3.spring.assessment1.model.Transaction;
import org.springframework.batch.item.ItemProcessor;

/**
 *
 * @author Fauzan
 */
@Slf4j
public class TransactionItemProcessor implements ItemProcessor<Transaction, Transaction>{
    
    @Override
    public Transaction process(Transaction item) throws Exception {
              
        final String accountNumber = item.getAccountNumber();
        final String trxAmount = item.getTrxAmount();
        final String descriptionName = item.getDescription();
        final String trxDate = item.getTrxDate();
        final String trxTime = item.getTrxTime();
        final String customerId = item.getCustomerId();
        
        final Transaction processedItem = new Transaction(accountNumber, trxAmount, descriptionName, trxDate, trxTime, customerId);
        
        log.info("Converting (" + item + ") into (" + processedItem + ")");
        
        return processedItem;
    }
}
