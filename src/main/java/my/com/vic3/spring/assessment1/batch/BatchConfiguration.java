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

import javax.sql.DataSource;
import my.com.vic3.spring.assessment1.model.Transaction;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 *
 * @author Fauzan
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Value("${file.input}")
    private String fileInput;
    
    @Bean
    public FlatFileItemReader reader() {
        
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter("|");
        tokenizer.setNames(new String[]{"accountNumber", "trxAmount", "description","trxDate", "trxTime", "customerId"});
        
        DefaultLineMapper lineMapper = new DefaultLineMapper();
        lineMapper.setLineTokenizer(tokenizer);
        
        return new FlatFileItemReaderBuilder().name("transactionItemReader")
          .resource(new ClassPathResource(fileInput))
          .lineTokenizer(tokenizer)
          .linesToSkip(1)
          .fieldSetMapper(new BeanWrapperFieldSetMapper() {{
              setTargetType(Transaction.class);
          }})
          .build();
    }
    
    @Bean
    public JdbcBatchItemWriter writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder()
          .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
          .sql("INSERT INTO transaction (account_number, trx_amount, description, trx_date, trx_time, customer_id) VALUES (:accountNumber, :trxAmount, :description, :trxDate, :trxTime, :customerId)")
          .dataSource(dataSource)
          .build();
    }
    
    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob")
          .incrementer(new RunIdIncrementer())
          .listener(listener)
          .flow(step1)                
          .end()
          .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter writer) {
        return stepBuilderFactory.get("step1")
          .<Transaction, Transaction> chunk(10)
          .reader(reader())
          .processor(processor())
          .writer(writer)
          .build();
    }

    @Bean
    public TransactionItemProcessor processor() {
        return new TransactionItemProcessor();
    }
    
}
