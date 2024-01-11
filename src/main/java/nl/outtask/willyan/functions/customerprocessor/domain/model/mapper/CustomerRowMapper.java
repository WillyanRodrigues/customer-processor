package nl.outtask.willyan.functions.customerprocessor.domain.model.mapper;

import nl.outtask.willyan.functions.customerprocessor.domain.model.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRowMapper implements RowMapper<Customer> {
    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getInt("id"));
        customer.setName(rs.getString("name"));
        customer.setDocumentNumber(rs.getString("documentNumber"));
        return customer;
    }
}
