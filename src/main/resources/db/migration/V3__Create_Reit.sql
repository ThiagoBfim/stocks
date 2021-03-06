CREATE TABLE TB_REIT
(
    ID_REIT                  BIGSERIAL  NOT NULL,
    COD_REIT                 varchar(6) NOT NULL,
    NU_DIVIDEND_YIELD        NUMERIC,
    NU_MARKET_VALUE_BILLION  NUMERIC,
    NU_COMPANY_VALUE_BILLION NUMERIC,
    NU_STAR                  NUMERIC,
    DT_LAST_UPDATE           timestamp DEFAULT now(),
    CONSTRAINT PK_REIT
        PRIMARY KEY (ID_REIT)
);

ALTER TABLE TB_REIT
    ADD CONSTRAINT UK_COD_REIT UNIQUE (COD_REIT);

COMMENT ON TABLE TB_REIT
    IS 'Table with info for REITs';

COMMENT ON COLUMN TB_REIT.NU_STAR
    IS 'Ranges from 1 to 10 with 10 being excellent';
