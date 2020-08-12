package com.money.stocks.service.reit;

import com.money.stocks.domain.Reit;
import com.money.stocks.domain.enuns.TypeReitSearch;

public interface ReitSearch {

    TypeReitSearch getType();

    Reit getReit(String reitCod);

}
