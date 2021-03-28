package com.crudUser.CadastroDeUsuarios.service.builders;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public abstract class BaseParamsBuilder<T extends BaseParamsBuilder> {
    Map<String, Object> params = new HashMap<>();

    public T addParamIfNotNull(String key, Object value) {
        Optional.ofNullable(value).ifPresent(v -> params.put(key, v));
        return (T) this;
    }

    public T addListParamIfNotNull(String key, List value) {
        if(Objects.nonNull(value) && !value.isEmpty()){
            params.put(key, value);
        }
        return (T) this;
    }
}

