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
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * @author Fauzan
 */
@Slf4j
@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
          this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
            if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
                log.info("!!! JOB FINISHED! Time to verify the results");

                jdbcTemplate.query("SELECT account_number, customer_id, description, trx_amount, trx_date, trx_time from transaction",
                    (rs, row) -> new Transaction(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6)
                        )
            ).forEach(rawItem -> log.info("Found <{{}}> in the database.", rawItem));
        }
    }

    @Override
    public void beforeJob(JobExecution je) {
        
    }
}