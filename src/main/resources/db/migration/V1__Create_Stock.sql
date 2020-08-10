
CREATE TABLE TB_STOCK (
  ID_STOCK      BIGSERIAL NOT NULL,
  COD_STOCK     varchar(6) NOT NULL,
  NU_DIVIDEND_YIELD  NUMERIC ,
  NU_MARKET_VALUE_BILLION  NUMERIC,
  NU_COMPANY_VALUE_BILLION  NUMERIC,
  NU_STAR  NUMERIC,
  CONSTRAINT PK_STOCK
    PRIMARY KEY (ID_STOCK)
);

ALTER TABLE TB_STOCK
 ADD  CONSTRAINT UK_COD_STOCK UNIQUE (COD_STOCK);

COMMENT ON TABLE TB_STOCK
  IS 'Table with info for stocks';

COMMENT ON COLUMN TB_STOCK.NU_STAR
  IS 'Ranges from 1 to 10 with 10 being excellent';
--
-- Inserindo dados na tabela "pais"
--

INSERT INTO TB_STOCK (ID_STOCK,
COD_STOCK,
NU_DIVIDEND_YIELD,
NU_MARKET_VALUE_BILLION,
NU_COMPANY_VALUE_BILLION,
NU_STAR) VALUES
(1,'EGIE3', 5.1, 37.728, 49.918, 9),
(2,'ITSA4', 8.5, 85.874, 86.891, 9),
(3,'BIDI11', 0.4, NULL, 15.081, 7.5);
