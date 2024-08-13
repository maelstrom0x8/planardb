package io.godelhaze.plank;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.apache.commons.pool2.PooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Plank Client
 *
 * @author Emmanuel Godwin
 * @see ConnectionFactory
 * @see Connection
 */
public class Plank implements KeyValueStore {

    private static final Logger LOG = LoggerFactory.getLogger(Plank.class);

    private final ConnectionFactory connectionFactory;

    public Plank() {
        connectionFactory = new ConnectionFactory();
    }


    public String sendQuery(String query) throws Exception {
        PooledObject<Connection> cpo = null;
        try {
            cpo = connectionFactory.makeObject();
            cpo.getObject().connect(cpo.getObject().getAddress());
            PrintWriter out = new PrintWriter(cpo.getObject().getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(cpo.getObject().getInputStream()));

            out.println(query);
            return in.readLine();
        } finally {
            connectionFactory.destroyObject(cpo);
        }
    }

    @Override
    public void put(String key, String value) {
        String request = "pset " + key + " " + value;
        try {
            sendQuery(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String get(String key) {
        String request = "pget " + key;
        try {
            return sendQuery(request);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void remove(String key) {}

    @Override
    public void clear() {}
}
