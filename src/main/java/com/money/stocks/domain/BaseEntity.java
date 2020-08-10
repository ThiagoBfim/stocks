package com.money.stocks.domain;

import java.io.Serializable;

public interface BaseEntity<T extends Serializable> extends Serializable {

   T getId();

}
