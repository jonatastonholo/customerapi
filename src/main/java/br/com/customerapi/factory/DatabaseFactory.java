package br.com.customerapi.factory;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

/**
 * @author Jônatas Ribeiro Tonholo
 */
public class DatabaseFactory {
    private Jdbi jdbi;

    public Jdbi jdbi() {
        return jdbi;
    }

    protected void createJdbi(MysqlDataSource mysqlDS) {
        this.jdbi = Jdbi.create(mysqlDS);
        jdbi.installPlugin(new SqlObjectPlugin());
    }

}
