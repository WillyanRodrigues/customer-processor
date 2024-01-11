package nl.outtask.willyan.functions.customerprocessor.config;

import nl.outtask.willyan.functions.customerprocessor.domain.model.Customer;
import nl.outtask.willyan.functions.customerprocessor.domain.model.mapper.CustomerRowMapper;
import nl.outtask.willyan.functions.customerprocessor.itemwriters.CustomerItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlServerPagingQueryProvider;
import org.springframework.batch.item.support.ListItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.util.Map;

@Configuration
@EnableBatchProcessing
public class JobConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public Step readAndPrintStep(JobRepository jobRepository,
                                 PlatformTransactionManager transactionManager) {
        return new StepBuilder("readAndPrintStep", jobRepository)
                .<Customer, Customer>chunk(10,transactionManager)
                .reader(itemReader())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public ItemReader<Customer> itemReader() {
        System.out.println("Executing");
        SqlServerPagingQueryProvider queryProvider = new SqlServerPagingQueryProvider();
        queryProvider.setSelectClause("*");
        queryProvider.setFromClause("customer");
        queryProvider.setSortKeys(Map.of("customer.id", Order.ASCENDING));

        return new JdbcPagingItemReaderBuilder<Customer>()
                .dataSource(dataSource)
                .saveState(false)
                .queryProvider(queryProvider)
                .sortKeys(queryProvider.getSortKeys())
                .pageSize(10)
                .rowMapper(new CustomerRowMapper())
                .build();
    }

    @Bean
    public ItemWriter<Customer> itemWriter() {
        return new CustomerItemWriter();
    }
}
