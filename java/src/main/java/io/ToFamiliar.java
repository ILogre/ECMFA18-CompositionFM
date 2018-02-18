package io;

import criteria.CriteriaDescriptor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class ToFamiliar {


    private String productKey;
    private Map<String, String> products;

    public ToFamiliar(Set<Map<String, String>> raw, String productKey, Set<CriteriaDescriptor> conf) {
        this.productKey = productKey;
        this.products = new HashMap<>();
        initialize(raw, conf);
    }

    public  Map<String, String> getProducts() { return products; }

    private void initialize(Set<Map<String, String>> raw, Set<CriteriaDescriptor> conf) {
        for(Map<String, String> product: raw) {
            loadProduct(product, conf);
        }
    }

    private void loadProduct(Map<String, String> data, Set<CriteriaDescriptor> conf) {
        String productName = "prod_" + data.get(productKey);
        StringBuffer buff = new StringBuffer();
        buff.append("FM(");
        extractClassifiers(conf, buff);
        for(CriteriaDescriptor cd: conf) {
            Optional<String> extracted = cd.getValue(data);
            extracted.ifPresent(v ->
                    buff.append(cd.getKey()).append(": ").append(v).append("; ")
            );
        }
        buff.append(")");
        products.put(productName, buff.toString());
    }

    private void extractClassifiers(Set<CriteriaDescriptor> conf, StringBuffer buff) {
        buff.append("CompositionOperator: ");
        for(CriteriaDescriptor cd: conf) {
            buff.append(cd.getKey()).append(" ");
        }
        buff.append("; ");
    }

}
