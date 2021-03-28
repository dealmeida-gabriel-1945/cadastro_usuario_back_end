package com.crudUser.CadastroDeUsuarios.service.builders;

import java.util.Base64;
import java.util.Map;
import java.util.Objects;

public class PdfParamsBuilder extends BaseParamsBuilder<PdfParamsBuilder> implements BuilderBase {

    @Override
    public Map<String, Object> build() {
        return params;
    }

    public PdfParamsBuilder addImage(String key, byte[] img) {
        if(Objects.nonNull(img) && img.length != 0) {
            params.put(key, String.join("", "data:", Base64.getEncoder().encodeToString(img)));
        }
        return this;
    }

}
