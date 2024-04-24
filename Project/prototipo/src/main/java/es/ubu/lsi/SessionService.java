package es.ubu.lsi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import okhttp3.*;
import java.time.Duration;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SessionService {
	private static SessionService service;
	private static OkHttpClient client;
	private static String sessionKey;
	private static CookieManager cookie_mannager;

	private SessionService() {
		cookie_mannager = new CookieManager();
		cookie_mannager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
		CookieHandler.setDefault(cookie_mannager);
		client = new OkHttpClient.Builder().cookieJar(new JavaNetCookieJar(cookie_mannager))
				.readTimeout(Duration.ofMinutes(5))
				.build();
	}

	public static SessionService getInstance() {
		if (SessionService.service == null) {
			return new SessionService();
		}
		return service;
	}

	public String getSSkey(String username, String password, String host) throws IOException {
		if (SessionService.sessionKey == null && checkSSKey(SessionService.sessionKey, host)) {
			SessionService.setSSKeyString(username, password, host);
			return SessionService.sessionKey;
		}
		return SessionService.sessionKey;
	}

	private static void setSSKeyString(String username, String password, String host) throws IOException {
		String ssKeyHtml = webLoging(username, password, host);
		Pattern pattern = Pattern.compile("sesskey=(\\w+)");
		Matcher m = pattern.matcher(ssKeyHtml);
		if (m.find()) {
			SessionService.sessionKey = m.group(1);
		}
	}

	private static String webLoging(String username, String password, String host) throws IOException {
		String loginUrl = host + "/login/index.php";
		Request request = new Request.Builder()
				.url(loginUrl)
				.build();
		try (Response response = client.newCall(request).execute()) {
			String loginFormUrl = response.request()
					.url()
					.toString();
			Document loginDoc = Jsoup.parse(response.body()
					.byteStream(), null, loginUrl);
			Element e = loginDoc.selectFirst("input[name=logintoken]");
			String logintoken = (e == null) ? "" : e.attr("value");

			RequestBody formBody = new FormBody.Builder().add("username", username)
					.add("password", password)
					.add("logintoken", logintoken)
					.build();

			try (Response response2 = client.newCall(new Request.Builder().url(loginFormUrl).post(formBody).build())
					.execute()) {
				return response2.body().string();

			}
		}
	}

	private boolean checkSSKey(String sskey, String host) throws IOException {
		String testUrl = host + "/lib/ajax/service.php?sesskey=" + sskey + "&info=core_message_get_conversations";
		Request request = new Request.Builder()
				.url(testUrl)
				.build();
		try (Response response = client.newCall(request).execute()) {
			return response.isSuccessful();
		}
	}

}
