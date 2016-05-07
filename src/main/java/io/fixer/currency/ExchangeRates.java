package io.fixer.currency;

import java.util.Map;

import java.time.LocalDate;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Class for wrapping currency exchange rates.
 */
public class ExchangeRates {

	private String base;
	private LocalDate date;
	private Map<String, Double> rates;

	public ExchangeRates() {}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Map<String, Double> getRates() {
		return rates;
	}

	public void setRates(Map<String, Double> rates) {
		this.rates = rates;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public static void main(String[] args) {
		ExchangeRates	er = new ExchangeRates();
		System.out.println(er);
	}

}
