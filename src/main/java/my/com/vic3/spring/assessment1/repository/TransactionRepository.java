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
package my.com.vic3.spring.assessment1.repository;

import java.util.List;
import java.util.Optional;
import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import my.com.vic3.spring.assessment1.model.Transaction;
import my.com.vic3.spring.assessment1.repository.custom.TransactionRepositoryCustom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Fauzan
 */
public interface TransactionRepository extends JpaRepository<Transaction, Integer>, TransactionRepositoryCustom{
    
    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "3000")})
    @Transactional
    Optional<Transaction> findById(Long id);
    
    Optional<List<Transaction>> findByCustomerId(Pageable pageable, Long customerId);
    
    Optional<List<Transaction>> findByDescription(Pageable pageable, String description);
    
    Optional<List<Transaction>> findByAccountNumber(Pageable pageable, Long accountNumber);
                
}
