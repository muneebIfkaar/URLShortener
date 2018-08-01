package com.url.model.strategy;

import com.url.model.beans.StrategyResponseBean;
import com.url.model.strategy.interfaces.IStrategy;

public class StrategyContext {
	
	private final IStrategy strategy;
	
	public StrategyContext(IStrategy strategy) {
		this.strategy = strategy;
	}
	
	public StrategyResponseBean execute(Object obj) {
		return strategy.execute(obj);
	}

}
