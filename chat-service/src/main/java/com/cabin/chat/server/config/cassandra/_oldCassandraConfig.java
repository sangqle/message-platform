package com.cabin.chat.server.config.cassandra;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractReactiveCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableReactiveCassandraRepositories(basePackageClasses = {_oldCassandraConfig.class})
public class _oldCassandraConfig extends AbstractReactiveCassandraConfiguration {

    @Value("${spring.data.cassandra.keyspace-name}")
    String keySpace;

    @Value("${spring.data.cassandra.contact-points}")
    String contactPoints;

    @Value("${spring.data.cassandra.local-datacenter}")
    String datacenter;

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {

        CreateKeyspaceSpecification specification = CreateKeyspaceSpecification.createKeyspace(keySpace)
            .ifNotExists()
            .with(KeyspaceOption.DURABLE_WRITES, true)
            .withSimpleReplication(3);

        return Arrays.asList(specification);
    }

    @Override
    protected String getKeyspaceName() {
        return keySpace;
    }

    @Override
    protected String getContactPoints() {
        return contactPoints;
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    protected String getLocalDataCenter() {
        return datacenter;
    }

    public static void main(String[] args) {
    }
}
