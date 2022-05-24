package com.epam.esm.dao.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ScriptRunner implements AutoCloseable{
    final Connection connection;
    final ResourceLoader resourceLoader;

    public ScriptRunner(DataSource dataSource, ResourceLoader loader) throws SQLException {
        connection = dataSource.getConnection();
        resourceLoader = loader;
    }

    public void run(String path){
        Resource script = resourceLoader.getResource(path);
        ScriptUtils.executeSqlScript(connection, script);
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
