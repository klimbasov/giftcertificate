package com.epam.esm.dao.util.databasegenerator;

//import org.hibernate.boot.MetadataSources;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import org.hibernate.boot.spi.MetadataImplementor;
//import org.hibernate.tool.hbm2ddl.SchemaExport;
//
//import java.util.HashMap;
//import java.util.Map;

public class HibernateMySQLDatabaseGenerator {
//    public void generate(Class<?>[] entities){
//        Map<String, String> settings = new HashMap<>();
//        settings.put("connection.driver_class", "com.mysql.jdbc.Driver");
//        settings.put("dialect", "org.hibernate.dialect.MySQL57InnoDBDialect");
//        settings.put("hibernate.connection.url", "jdbc:mysql://localhost/testdb?useSSL=false");
//        settings.put("hibernate.connection.username", "root");
//        settings.put("hibernate.connection.password", "");
//        settings.put("hibernate.hbm2ddl.auto", "create");
//        settings.put("show_sql", "true");
//
//        MetadataSources metadata = new MetadataSources(
//                new StandardServiceRegistryBuilder()
//                        .applySettings(settings)
//                        .build());
//        for(Class<?> entity : entities){
//            metadata.addAnnotatedClass(entity);
//        }
//
//        SchemaExport schemaExport = new SchemaExport(
//                (MetadataImplementor) metadata.buildMetadata()
//        );
//        schemaExport.setHaltOnError(true);
//        schemaExport.setFormat(true);
//        schemaExport.setDelimiter(";");
//        schemaExport.setOutputFile("db-schema.sql");
//        schemaExport.execute(true, true, false, true);
//    }
}
