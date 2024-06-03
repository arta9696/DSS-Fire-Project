package app;

import app.DataObjectModels.ProtectionObject;
import app.Database.RDFCon;
import org.apache.commons.io.FileUtils;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnection;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Main {
    public static String AppName = "Fire System DSS";
    public static String DBConnectionString = "mongodb://devroot:devroot@localhost:27017";
    public static String DatabaseName = "FireSystemDSS";
    public static String Fuseki = "http://localhost:3030/FireDSS";
    public static String KnowBaseLocation = "D:\\ProjectFire.ttl";

    public static void main(String[] args) {
        //new DBCon().createUser("test", "123", "Client");
        //SwingUtilities.invokeLater(() -> new Auth().setVisible(true));

        String fuseki = System.getProperty("user.home") +"/Downloads/apache-jena-fuseki-4.2.0";
        KnowBaseLocation = fuseki + "/ProjectFire.ttl";
        try {
            String[] cmd1 = {
                    "python",
                    fuseki + "/script1.py",
                    fuseki,
            };
            Runtime.getRuntime().exec(cmd1).waitFor();
            String[] cmd2 = {
                    "python",
                    fuseki + "/script2.py",
                    fuseki,
            };
            Runtime.getRuntime().exec(cmd2);
            Thread.sleep(5000);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new Client("Client").setVisible(true));
    }
}