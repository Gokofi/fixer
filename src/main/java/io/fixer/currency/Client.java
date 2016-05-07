package io.fixer.currency;

import java.io.InputStream;
import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.URL;

import java.time.LocalDate;

import com.fatboyindustrial.gsonjavatime.Converters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.io.IOUtils;

/**
 * Class for obtaining currency exchange rates. The class uses the <a href="http://fixer.io/">Fixer.io</a> service.
 */
public class Client {

	/**
	 * URI of the currency exchange service.
	 */
	public static final String	SERVICE_URI = "http://api.fixer.io/";

	public Client() {}

	/**
	 * Obtains historical currency exchange rates for the given date against the currency specified.
	 * Both parameters may be {@code null}:
	 * <ul>
	 * <li>if the parameter {@code date} is <code>null</code>, the method returns the latest currency exchange rates,
	 * <li>If the parameter {@code currency} is <code>null</code>, the method returns currency exchange rates against the Euro.
	 * </ul>
	 *
	 * @param date a date, may be {@code null}
	 * @param currency a currency (e.g., {@code "CHF"}), may be {@code null}
	 * @return an object wrapping the information returned
	 * @throws IOException if any I/O error occurs
	 */
	public ExchangeRates getExchangeRates(LocalDate date, String currency) throws IOException {
		StringBuilder	sb = new StringBuilder().append(SERVICE_URI)
			.append(date != null ? date.toString() : "latest");
		if (currency != null)
			sb.append("?base=").append(currency);
		URL	url = new URL(sb.toString());
		HttpURLConnection	conn = (HttpURLConnection) url.openConnection();
		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new IOException(conn.getResponseMessage());
		}
		String	s = IOUtils.toString(conn.getInputStream(), "UTF-8");
		Gson    gson = Converters.registerLocalDate(new GsonBuilder()).create();
		return gson.fromJson(s, ExchangeRates.class);
	}

	/**
	 * Obtains historical currency exchange rates for the given date against the Euro.
	 * If the parameter {@code date} is <code>null</code>, the method returns the latest currency exchange rates,
	 *
	 * @param date a date, may be {@code null}
	 * @return an object wrapping the information returned
	 * @throws IOException if any I/O error occurs
	 */
	public ExchangeRates getExchangeRates(LocalDate date) throws IOException {
		return getExchangeRates(date, null);
	}

	/**
	 * Obtains the latest currency exchange rates against the currency specified.
	 * If the parameter {@code currency} is <code>null</code>, the method returns currency exchange rates against the Euro.
	 *
	 * @param currency a currency (e.g., {@code "CHF"}), may be {@code null}
	 * @return an object wrapping the information returned
	 * @throws IOException if any I/O error occurs
	 */
	public ExchangeRates getExchangeRates(String currency) throws IOException {
		return getExchangeRates(null, currency);
	}

	/**
	 * Obtains the latest currency exchange rates against the Euro.
	 *
	 * @return an object wrapping the information returned
	 * @throws IOException if any I/O error occurs
	 */
	public ExchangeRates getExchangeRates() throws IOException {
		return getExchangeRates(null, null);
	}

	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			System.err.printf("Usage: %s <date> <currency>\n", Client.class.getName());;
			System.exit(1);
		}
		try {
			ExchangeRates	er = new Client().getExchangeRates(LocalDate.parse(args[0]), args[1]);
			System.out.println(er);
			System.out.println(er.getRates().getClass());
		} catch(IOException e) {
			System.err.println(e.getMessage());
		}
	}

}
