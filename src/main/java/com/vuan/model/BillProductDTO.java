package com.vuan.model;

import lombok.Data;

@Data
// sp trong bill
public class BillProductDTO {
	private int id;
	private long unitPrice;
	private int quantity;
	private BillDTO billDTO;
	private ProductDTO productDTO;

}
