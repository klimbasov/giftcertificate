package com.epam.esm.dao.parametersources;

import com.epam.esm.dao.constant.TableNames;
import com.epam.esm.dao.entity.Certificate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

public class CertificateParameterSource implements SqlParameterSource {
    Map<String, Object> parameters;

    public CertificateParameterSource(Certificate certificate) {
        parameters = new HashMap<>();
        if (nonNull(certificate)) {
            load(certificate);
        }
    }

    private void load(Certificate certificate) {
        parameters.put(TableNames.Certificate.NAME, certificate.getName());
        parameters.put(TableNames.Certificate.DESCRIPTION, certificate.getDescription());
        parameters.put(TableNames.Certificate.PRICE, certificate.getPrice());
        parameters.put(TableNames.Certificate.CREATE_DATE, certificate.getCreateDate());
        parameters.put(TableNames.Certificate.LAST_UPDATE_DATE, certificate.getLastUpdateDate());
        parameters.put(TableNames.Certificate.DURATION, certificate.getDuration());
    }

    @Override
    public boolean hasValue(String paramName) {
        return parameters.containsValue(paramName);
    }

    @Override
    public Object getValue(String paramName) throws IllegalArgumentException {
        return parameters.get(paramName);
    }

    @Override
    public String[] getParameterNames() {
        return parameters.keySet().toArray(new String[0]);
    }
}
