package io.lana.simplespring.lib.controller;

public interface Identified<T> {
    String getId();

    void setId(T id);
}
