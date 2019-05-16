package com.waes.diff.domain.service;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonDiff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Objects;

@Component
public class JsonHelper {

    private final ObjectMapper objectMapper;

    @Autowired
    public JsonHelper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    JsonNode fromBase64(final String content) {
        try {
            return objectMapper.readTree(Base64.getDecoder().decode(content));
        } catch (Exception ex) {
            throw new IllegalArgumentException("Invalid content");
        }
    }

    String compare(final JsonNode left, final JsonNode right) {
        try {
            final ComparisonResult result = new ComparisonResult(
                    Objects.equals(left, right),
                    (left.size() == right.size())
            );

            if (!result.isEquals() && result.isEqualsInSize()) {
                final JsonNode diff = JsonDiff.asJson(left, right);
                result.setDiff(diff);
            }

            return objectMapper.writeValueAsString(result);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class ComparisonResult {

    private boolean equals;

    private boolean equalsInSize;

    private Object diff = new ArrayList<>();

    ComparisonResult(boolean equals, boolean equalsInSize) {
        this.equals = equals;
        this.equalsInSize = equalsInSize;
    }

    boolean isEquals() {
        return equals;
    }

    boolean isEqualsInSize() {
        return equalsInSize;
    }

    void setDiff(Object diff) {
        this.diff = diff;
    }
}
