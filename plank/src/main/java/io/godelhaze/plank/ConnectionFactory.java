package io.godelhaze.plank;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.io.IOException;

public class ConnectionFactory extends BasePooledObjectFactory<Connection> {
    @Override
    public Connection create() throws Exception {
        return new Connection();
    }

    @Override
    public PooledObject<Connection> wrap(Connection connection) {
        return new DefaultPooledObject<>(connection);
    }

    @Override
    public void passivateObject(PooledObject<Connection> connection) {
        try {
            connection.getObject().close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
