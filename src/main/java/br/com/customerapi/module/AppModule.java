package br.com.customerapi.module;

import br.com.customerapi.dao.CustomerDao;
import br.com.customerapi.factory.DatabaseFactory;
import br.com.customerapi.factory.MySQLFactory;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

/**
 * App module to configure Google Guice Dependencies Injection
 *
 * @author Jônatas Ribeiro Tonholo
 */
public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DatabaseFactory.class).to(MySQLFactory.class).in(Scopes.SINGLETON);
        bind(CustomerDao.class).in(Scopes.SINGLETON);
    }
}
