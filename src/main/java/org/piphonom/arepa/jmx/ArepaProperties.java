package org.piphonom.arepa.jmx;

/**
 * Created by piphonom
 */
public interface ArepaProperties {
    public final String MBEAN_NAME = "org.piphonom.arepa:name=ArepaProperties";

    public String getRootCaStoreLocation();
    public void setRootCaStoreLocation(String caStoreLocation);
    public String getRootCaAlias();
    public void setRootCaAlias(String caAlias);
    public String getRootCaStorePassword();
    public void setRootCaStorePassword(String caStorePassword);
    public String getRootCaKeyPassword();
    public void setRootCaKeyPassword(String caKeyPassword);
    public int getGroupCaDaysValidity();
    public void setGroupCaDaysValidity(int groupCaDaysValidity);
    public int getGroupCaKeySize();
    public void setGroupCaKeySize(int groupCaKeySize);
}
