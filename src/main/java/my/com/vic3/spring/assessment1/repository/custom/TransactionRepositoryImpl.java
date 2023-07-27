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
package my.com.vic3.spring.assessment1.repository.custom;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import my.com.vic3.spring.assessment1.model.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 *
 * @author Fauzan
 */
@Slf4j
@Repository
@Transactional(readOnly = true)
public class TransactionRepositoryImpl implements TransactionRepositoryCustom{

    @PersistenceContext
    EntityManager entityManager;
    
    @Override
    public Optional<List<Transaction>> findByFilter(String customerId, String accountNumber, String description, Pageable pageable) {
        
        List<Transaction> transactions = null;
                
        String sql = "SELECT * FROM transaction t WHERE t.id is not null ";

        if(StringUtils.hasText(customerId)){
            sql += "AND t.customer_id = :customerId ";
        }
        if(StringUtils.hasText(accountNumber)){
            sql += "AND t.account_number = :accountNumber ";
        }
        if(StringUtils.hasText(description)){
            sql += "AND t.description = :description ";
        }
        
        try{
            Query q = entityManager.createNativeQuery(sql, Transaction.class);
            
            if(StringUtils.hasText(customerId)){
                q.setParameter("customerId", customerId);            
            }
            if(StringUtils.hasText(accountNumber)){
                q.setParameter("accountNumber", accountNumber);
            }
            if(StringUtils.hasText(description)){
                q.setParameter("description", description);
            }
            
            q.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
            q.setMaxResults(pageable.getPageSize());
            
            transactions = q.getResultList();
            
        }catch(Exception e){
            log.error("Error query ", e);
        }
        
        return Optional.ofNullable(transactions);
    }
    
}
