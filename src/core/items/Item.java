package core.items;

import core.Entity;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

public abstract class Item implements Serializable, Entity {
    @Serial
    private static final long serialVersionUID = 1L;

    public abstract BigDecimal getPrice();

    public abstract String getSearchString();
}
