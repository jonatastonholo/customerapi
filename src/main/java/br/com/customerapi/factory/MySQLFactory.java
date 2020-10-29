package br.com.customerapi.factory;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jônatas Ribeiro Tonholo
 */
public class MySQLFactory {
    final static Logger log = LoggerFactory.getLogger(MySQLFactory.class);
    private static Jdbi jdbi;
    private static MySQLFactory instance;

    private static final String USER_NAME = "root";
    private static final String PASSWORD = "123456";
    private static final String DATABASE_NAME = "CUSTOMER_API";
    private static final String SERVER_URL = "127.0.0.1";
    private static final String SERVER_PORT = "3306";

    private MySQLFactory() {
        MysqlDataSource mysqlDS = new MysqlDataSource();
        mysqlDS.setURL("jdbc:mysql://" + SERVER_URL + ":" + SERVER_PORT + "/" + DATABASE_NAME);
        mysqlDS.setUser(USER_NAME);
        mysqlDS.setPassword(PASSWORD);
        jdbi = Jdbi.create(mysqlDS);
        jdbi.installPlugin(new SqlObjectPlugin());
    }

    @SuppressWarnings("InstantiationOfUtilityClass") // FIXME
    public static void initialize() {
        try {
            if(instance == null) {
                log.info("Initializing MySQL connection...");
                instance = new MySQLFactory();
                log.info("OK");
            }
            else {
                log.info("MySQL connection is already initialized.");
            }
        }
        catch (Exception e) {
            log.error("Error while trying connect to MySQL server\n" + e.getMessage());
        }

    }

    public static Jdbi jdbi() {
        return jdbi;
    }

}
