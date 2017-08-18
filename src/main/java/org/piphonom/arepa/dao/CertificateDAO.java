package org.piphonom.arepa.dao;

import org.piphonom.arepa.dao.dataset.Certificate;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by piphonom
 */
@Transactional
public interface CertificateDAO extends CrudRepository<Certificate, Integer> {

}
