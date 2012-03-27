package com.logictreeit.supply.listeners;

import com.logictree.supply.models.Order;

public interface OrderEditListener {
	public void onOrderEdit(Order order, String quantity);
}
