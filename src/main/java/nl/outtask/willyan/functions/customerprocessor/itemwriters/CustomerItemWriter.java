package nl.outtask.willyan.functions.customerprocessor.itemwriters;

import nl.outtask.willyan.functions.customerprocessor.domain.model.Customer;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import java.util.Optional;

public class CustomerItemWriter implements ItemWriter<Customer> {

    @Override
    public void write(Chunk<? extends Customer> chunk) throws Exception {
        System.out.println("HIER");
        Optional<String> selene = chunk.getItems().stream()
                .filter(c -> c.getName().equalsIgnoreCase("Selene"))
                .map(Customer::getDateOfBirth)
                .findFirst();
        selene.ifPresent(System.out::println);
    }
}
