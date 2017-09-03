package org.piphonom.arepa.model;


import org.piphonom.arepa.dao.CertificateDAO;
import org.piphonom.arepa.dao.DeviceDAO;
import org.piphonom.arepa.dao.dataset.Certificate;
import org.piphonom.arepa.dao.dataset.Device;
import org.piphonom.arepa.dao.dataset.DeviceGroup;
import org.piphonom.arepa.exceptions.DeviceExistsException;
import org.piphonom.arepa.exceptions.http.InternalServerErrorException;
import org.piphonom.arepa.exceptions.http.NotFoundException;
import org.piphonom.arepa.helpers.pki.CSRSigner;
import org.piphonom.arepa.service.DeviceService;
import org.piphonom.arepa.service.PubIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.sql.Timestamp;
import java.util.stream.Collectors;

/**
 * Created by piphonom
 */
@Service
public class DeviceServiceImpl implements DeviceService {

    /**
     * TODO: make MBean to control these values
     */
    private int DEFAULT_DEVICE_DAYS_VALIDITY = 365;

    @Autowired
    private DeviceDAO deviceDAO;

    @Autowired
    private CertificateDAO certificateDAO;

    @Autowired
    private PubIdGenerator pubIdGenerator;

    @Autowired
    private CSRSigner csrSigner;

    @Override
    public Device save(Device device) {
        return deviceDAO.save(device);
    }

    @Override
    public Device createDevice(DeviceGroup group, String name) throws DeviceExistsException {
        if (group.getDevices().stream().filter(device -> device.getName().equals(name)).collect(Collectors.toList()).size() != 0) {
            throw new DeviceExistsException();
        }
        Device device = new Device();
        device.setName(name);
        device.setCreationTime(new Timestamp(System.currentTimeMillis()));
        device.setPubId(pubIdGenerator.createNew());
        device.setState(Device.State.CREATED);
        device.setDeviceGroupRef(group);
        return device;
    }

    @Override
    public Device getDeviceByPublicId(String pubId) {
        Device device = deviceDAO.getByPubId(pubId);
        if (device == null) {
            throw new NotFoundException();
        }
        return device;
    }

    @Override
    public byte[] registerDevice(String pubId, byte[] csr) {
        Device device = getDeviceByPublicId(pubId);
        PrivateKey groupPrivKey;
        X509Certificate groupCertificate;
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(device.getDeviceGroupRef().getPrivateKeyCA());
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            groupPrivKey = keyFactory.generatePrivate(keySpec);

            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            InputStream in = new ByteArrayInputStream(device.getDeviceGroupRef().getCertificateCA());
            groupCertificate = (X509Certificate)certFactory.generateCertificate(in);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException | CertificateException e) {
            e.printStackTrace();
            throw new InternalServerErrorException("Group.CA.instantiation.failure");
        }

        long serialNumber = System.currentTimeMillis();
        csrSigner.setCSR(csr)
                 .setCaCertificate(groupCertificate)
                 .setCaKey(groupPrivKey)
                 .setDaysValidity(DEFAULT_DEVICE_DAYS_VALIDITY)
                 .setSerialNumber(serialNumber);

        if (!csrSigner.sign()) {
            throw new InternalServerErrorException("CSR.sign.failure");
        }

        Certificate certEntity = new Certificate();
        certEntity.setValue(csrSigner.getCertificate());
        certEntity.setDeviceGroupRef(device.getDeviceGroupRef());
        certEntity.setRevoked(false);
        certEntity.setSerialNumber(serialNumber);
        certificateDAO.save(certEntity);

        device.setCertificateRef(certEntity);
        deviceDAO.save(device);

        return csrSigner.getCertificate();
    }
}
