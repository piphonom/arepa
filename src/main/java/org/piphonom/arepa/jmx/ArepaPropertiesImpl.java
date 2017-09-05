package org.piphonom.arepa.jmx;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by piphonom
 */
@Component
@ManagedResource(objectName = ArepaProperties.MBEAN_NAME)
public class ArepaPropertiesImpl implements ArepaProperties {
    private String rootCaStoreLocation;
    private String rootCaAlias;
    private String rootCaStorePassword;
    private String rootCaKeyPassword;
    private int groupCaDaysValidity;
    private int groupCaKeySize;

    public ArepaPropertiesImpl() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            rootCaStoreLocation = properties.getProperty("org.piphonom.arepa.rootca.location");
            rootCaAlias = properties.getProperty("org.piphonom.arepa.rootca.alias");
            rootCaStorePassword = properties.getProperty("org.piphonom.arepa.rootca.storepass");
            rootCaKeyPassword = properties.getProperty("org.piphonom.arepa.rootca.keypass");
            groupCaDaysValidity = Integer.valueOf(properties.getProperty("org.piphonom.arepa.group.ca.daysValidity"));
            groupCaKeySize = Integer.valueOf(properties.getProperty("org.piphonom.arepa.group.ca.keySize"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @ManagedAttribute(description="Get Location of Root CA store")
    public String getRootCaStoreLocation() {
        return rootCaStoreLocation;
    }

    @Override
    @ManagedAttribute(description="Set Location of Root CA store")
    public void setRootCaStoreLocation(String rootCaStoreLocation) {
        this.rootCaStoreLocation = rootCaStoreLocation;
    }

    @Override
    @ManagedAttribute(description="Get Alias of Root CA in the store")
    public String getRootCaAlias() {
        return rootCaAlias;
    }

    @Override
    @ManagedAttribute(description="Set Alias of Root CA in the store")
    public void setRootCaAlias(String rootCaAlias) {
        this.rootCaAlias = rootCaAlias;
    }

    @Override
    @ManagedAttribute(description="Get Root CA store password")
    public String getRootCaStorePassword() {
        return rootCaStorePassword;
    }

    @Override
    @ManagedAttribute(description="Set Root CA store password")
    public void setRootCaStorePassword(String rootCaStorePassword) {
        this.rootCaStorePassword = rootCaStorePassword;
    }

    @Override
    @ManagedAttribute(description="Get Root CA key password")
    public String getRootCaKeyPassword() {
        return rootCaKeyPassword;
    }

    @Override
    @ManagedAttribute(description="Set Root CA key password")
    public void setRootCaKeyPassword(String rootCaKeyPassword) {
        this.rootCaKeyPassword = rootCaKeyPassword;
    }

    @Override
    @ManagedAttribute(description="Get Group CA days validity")
    public int getGroupCaDaysValidity() {
        return groupCaDaysValidity;
    }

    @Override
    @ManagedAttribute(description="Set Group CA days validity")
    public void setGroupCaDaysValidity(int groupCaDaysValidity) {
        this.groupCaDaysValidity = groupCaDaysValidity;
    }

    @Override
    @ManagedAttribute(description="Get Group CA key size")
    public int getGroupCaKeySize() {
        return groupCaKeySize;
    }

    @Override
    @ManagedAttribute(description="Set Group CA key size")
    public void setGroupCaKeySize(int groupCaKeySize) {
        this.groupCaKeySize = groupCaKeySize;
    }

    @ManagedOperation(description="Update properties through the app")
    public void updateProperties() {
        synchronized(this) {
            notifyAll();
        }
    }
}
