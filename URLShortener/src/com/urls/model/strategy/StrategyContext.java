package com.urls.model.strategy;

import com.urls.model.beans.StrategyResponseBean;
import com.urls.model.strategy.interfaces.IStrategy;

public class StrategyContext {
	
	private final IStrategy strategy;
	
	public StrategyContext(IStrategy strategy) {
		this.strategy = strategy;
	}
	
	public StrategyResponseBean execute(Object obj) {
		return strategy.execute(obj);
	}

}
