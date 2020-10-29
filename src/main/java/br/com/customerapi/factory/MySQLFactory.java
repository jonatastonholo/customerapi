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
    private static MySQLFactory thisObject;

    private static Jdbi jdbi;

    private final String USER_NAME = "root";
    private final String PASSWORD = "123456";
    private final String DATABASE_NAME = "CUSTOMER_API";
    private final String SERVER_URL = "http://127.0.0.1";


    private MySQLFactory() throws ClassNotFoundException {
        MysqlDataSource mysqlDS = new MysqlDataSource();
        mysqlDS.setURL("jdbc:mysql://127.0.0.1:3306/CUSTOMER_API");
        mysqlDS.setUser(USER_NAME);
        mysqlDS.setPassword(PASSWORD);
        this.jdbi = jdbi.create(mysqlDS);
        jdbi.installPlugin(new SqlObjectPlugin());
        thisObject = this;
    }

    public static void initialize() {
        try {
            log.info("Initializing MySQL connection...");
            new MySQLFactory();
            log.info("OK");
        }
        catch (Exception e) {
            log.error("Error while trying connect to MySQL server\n" + e.getMessage());
        }

    }

//    public static Long nextId() {
//        return dbi.withHandle(new HandleCallback<Long>() {
//            public Long withHandle(Handle handle) throws Exception {
//                Map<String, Object> rs = handle.createQuery(
//                        "SELECT idseq.nextval AS id FROM dual").first();
//                return (Long) rs.get("id");
//            }
//        });
//    }

    public static Jdbi jdbi() {
        return thisObject.jdbi;
    }

}
