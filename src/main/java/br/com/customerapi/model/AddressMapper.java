package br.com.customerapi.model;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Jônatas Ribeiro Tonholo
 */
public class AddressMapper implements RowMapper<Address> {
    @Override
    public Address map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Address(
                rs.getLong("addressId"),
                rs.getLong("addr_customerId"),
                rs.getString("state"),
                rs.getString("city"),
                rs.getString("neighborhood"),
                rs.getString("zipCode"),
                rs.getString("street"),
                rs.getInt("number"),
                rs.getString("additionalInformation"),
                rs.getBoolean("main")
        );
    }
}
