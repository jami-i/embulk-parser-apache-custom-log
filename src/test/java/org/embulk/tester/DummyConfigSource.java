package org.embulk.tester;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.embulk.config.ConfigSource;
import org.embulk.config.DataSource;

import java.util.List;
import java.util.Map;

public class DummyConfigSource implements ConfigSource {
    @Override
    public <T> T loadConfig(Class<T> taskType) {
        return null;
    }

    @Override
    public List<String> getAttributeNames() {
        return null;
    }

    @Override
    public Iterable<Map.Entry<String, JsonNode>> getAttributes() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public <E> E get(Class<E> type, String attrName) {
        return null;
    }

    @Override
    public <E> E get(Class<E> type, String attrName, E defaultValue) {
        return null;
    }

    @Override
    public ConfigSource getNested(String attrName) {
        return null;
    }

    @Override
    public ConfigSource getNestedOrSetEmpty(String attrName) {
        return null;
    }

    @Override
    public ConfigSource set(String attrName, Object v) {
        return null;
    }

    @Override
    public ConfigSource setNested(String attrName, DataSource v) {
        return null;
    }

    @Override
    public ConfigSource setAll(DataSource other) {
        return null;
    }

    @Override
    public ConfigSource remove(String attrName) {
        return null;
    }

    @Override
    public ConfigSource deepCopy() {
        return null;
    }

    @Override
    public ConfigSource merge(DataSource other) {
        return null;
    }

    @Override
    public ObjectNode getObjectNode() {
        return null;
    }
}
