package com.epam.esm.dao.parametersources;

import com.epam.esm.dao.constant.TableNames;
import com.epam.esm.dao.entity.Tag;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

public class TagParameterSource implements SqlParameterSource {
    Map<String, Object> parameters;

    public TagParameterSource(Tag tag) {
        parameters = new HashMap<>();
        if (nonNull(tag)) {
            load(tag);
        }
    }

    private void load(Tag tag) {
        parameters.put(TableNames.Tag.NAME, tag.getName());
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
