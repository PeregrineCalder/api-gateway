package datasource.unpooled;

import datasource.DataSource;
import datasource.DataSourceFactory;
import datasource.DataSourceType;
import session.Configuration;

/**
 * @projectName: api-gateway
 * @package: datasource.unpooled
 * @className: UnpooledDataSourceFactory
 * @author: Peregrine Calder
 * @version: 1.0
 */

public class UnpooledDataSourceFactory implements DataSourceFactory {
    protected UnpooledDataSource dataSource;

    public UnpooledDataSourceFactory() {
        this.dataSource = new UnpooledDataSource();
    }

    @Override
    public void setProperties(Configuration configuration, String uri) {
        this.dataSource.setConfiguration(configuration);
        this.dataSource.setDataSourceType(DataSourceType.Dubbo);
        this.dataSource.setHttpStatement(configuration.getHttpStatement(uri));
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }
}
