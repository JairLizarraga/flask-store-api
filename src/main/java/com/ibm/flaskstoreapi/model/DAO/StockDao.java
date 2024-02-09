package com.ibm.flaskstoreapi.model.DAO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockDao {

	private Integer storeId;
	private Integer productId;
	private Integer quantity;
	
}
