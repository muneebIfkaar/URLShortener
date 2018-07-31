package com.urls.model;

import java.util.function.Supplier;

import org.slf4j.LoggerFactory;

import com.urls.model.beans.StrategyResponseBean;
import com.urls.model.strategy.StrategyContext;

import ch.qos.logback.classic.Logger;

public class ControllerTaskContext implements Supplier<StrategyResponseBean> {

	private final static Logger log = (Logger) LoggerFactory.getLogger(ControllerTaskContext.class);

	private final StrategyContext context;
	private final Object message;

	/**
	 * This constructor must be used as supplier in CompletionStage.
	 * 
	 * @param context:
	 *            ControllerTaskContext
	 * @param message:
	 *            Object: this cannot be null otherwise IllegalArgumentException
	 *            will be thrown while processing the value
	 */

	public ControllerTaskContext(StrategyContext context, Object message) {

		this.context = context;
		this.message = message;
	}

	public StrategyResponseBean get() {

		/*if (message == null) {
			log.warn("Invalid State. Message Object is null. ");
			throw new IllegalArgumentException("Message Parameter Is null");
		}*/

		log.info("Message received. Processing on Message. ");

		StrategyResponseBean response = context.execute(message);

		log.debug("Sending response back ");
		return response;
	}
}
