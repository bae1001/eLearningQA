package es.ubu.lsi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

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
	private static boolean isSessionExpired;

	private SessionService() {
		cookie_mannager = new CookieManager();
		cookie_mannager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
		CookieHandler.setDefault(cookie_mannager);
		client = new OkHttpClient.Builder().cookieJar(new JavaNetCookieJar(cookie_mannager))
				.readTimeout(Duration.ofMinutes(5))
				.build();
	}

	public static SessionService getInstance(String username, String password, String host) throws IOException {
		if (SessionService.service == null) {
			SessionService.service = new SessionService();
			SessionService.setSSKeyString(username, password, host);
			SessionService.setSessionExpired(false);
			return SessionService.service;
		}
		return service;
	}

	public String getSSKey(String host) {
		if (checkSSKey(host)) {
			return sessionKey;
		}
		return null;
	}

	public Response getResponse(Request request) throws IOException {
		return client.newCall(request).execute();
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
		try (Response responseForLoginPetitionForm = client.newCall(request).execute()) {
			String loginFormUrl = responseForLoginPetitionForm.request()
					.url()
					.toString();
			Document loginDoc = Jsoup.parse(responseForLoginPetitionForm.body()
					.byteStream(), null, loginUrl);
			Element e = loginDoc.selectFirst("input[name=logintoken]");
			String logintoken = (e == null) ? "" : e.attr("value");

			RequestBody formBody = new FormBody.Builder().add("username", username)
					.add("password", password)
					.add("logintoken", logintoken)
					.build();

			try (Response loginResponse = client.newCall(new Request.Builder().url(loginFormUrl).post(formBody).build())
					.execute()) {
				return loginResponse.body().string();

			}
		}
	}

	private boolean checkSSKey(String host) {
		String testUrl = host + "/lib/ajax/service.php?sesskey=" + sessionKey + "&info=core_user_set_user_preferences";
		String jsonString = "[{\"index\":0,\"methodname\":\"core_user_set_user_preferences\",\"args\":{\"preferences\":[{\"name\":\"drawer-open-index\",\"value\":false,\"userid\":0}]}}]";
		RequestBody requestBody = RequestBody.create(jsonString, MediaType.parse("application/json"));
		Request request = new Request.Builder()
				.url(testUrl)
				.post(requestBody)
				.build();
		try (Response response = client.newCall(request).execute()) {
			JsonArray jsonTest = JsonParser.parseString(response.body().string()).getAsJsonArray();
			return "false".equals(jsonTest.get(0).getAsJsonObject().get("error").getAsString());
		} catch (IllegalStateException e) {
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean isSessionExpired() {
		return isSessionExpired;
	}

	public static void setSessionExpired(boolean isSessionExpired) {
		SessionService.isSessionExpired = isSessionExpired;
	}
}