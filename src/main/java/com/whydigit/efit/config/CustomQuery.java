package com.whydigit.efit.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class CustomQuery {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void initialize() {
        try {
            executeQueries();
        } catch (Exception e) {
            throw new RuntimeException("Error initializing database", e);
        }
    }

    private void executeQueries() {
	
	
	jdbcTemplate.execute("CREATE OR REPLACE VIEW avalkitqty AS SELECT a.stockbranch AS stockbranch, a.kitcode AS kitcode, (CASE WHEN (FLOOR(MIN(a.avalqty)) IS NULL) THEN 0 ELSE FLOOR(MIN(a.avalqty)) END) AS avalqty FROM (SELECT c.stockbranch AS stockbranch, a.kitcode AS kitcode, b.asset AS asset, b.quantity AS quantity, SUM(c.skuqty) AS `SUM(skuqty)`, (SUM(c.skuqty) / b.quantity) AS avalqty FROM (kit a JOIN kit2 b) JOIN stockdetails c ON a.kitid = b.kitid AND b.asset = c.sku AND c.status='S' GROUP BY a.kitcode, c.stockbranch, b.asset, b.quantity) a GROUP BY a.kitcode, a.stockbranch");

    // Add your new SQL query
    jdbcTemplate.execute("CREATE OR REPLACE VIEW rp_dev.availableasset AS SELECT stockdetails.stockbranch AS stockbranch, stockdetails.sku AS sku, stockdetails.skucode AS skucode, SUM(stockdetails.skuqty) AS `sum(skuqty)`, stockdetails.orgid AS orgid, category FROM stockdetails WHERE stockdetails.status = 'S' GROUP BY stockdetails.stockbranch, stockdetails.sku, stockdetails.skucode, category, stockdetails.orgid HAVING SUM(stockdetails.skuqty) > 0");
    }
}

