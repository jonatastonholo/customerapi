package br.com.customerapi.factory;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jônatas Ribeiro Tonholo
 */
public class MySQLFactory extends DatabaseFactory {
    final static Logger log = LoggerFactory.getLogger(MySQLFactory.class);

    private static final String USER_NAME = "root";
    private static final String PASSWORD = "123456";
    private static final String DATABASE_NAME = "CUSTOMER_API";
    private static final String SERVER_URL = "127.0.0.1";
    private static final String SERVER_PORT = "3306";

    public MySQLFactory() {
        try {
            log.info("Initializing MySQL connection...");
            MysqlDataSource mysqlDS = new MysqlDataSource();
            mysqlDS.setURL("jdbc:mysql://" + SERVER_URL + ":" + SERVER_PORT + "/" + DATABASE_NAME);
            mysqlDS.setUser(USER_NAME);
            mysqlDS.setPassword(PASSWORD);
            createJdbi(mysqlDS);
            log.info("OK");
        }
        catch (Exception e) {
            log.error("Error while trying connect to MySQL server\n");
            log.debug(e.getMessage());
        }
    }
}
