package ch.baramex.trackersmod.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class VersionChecker implements Runnable {
	private static boolean isLatestVersion = false;
	private static String latestVersion = "";
	
	@Override
	public void run() {
		try {
			URL url = new URL("https://api.github.com/repos/baramex/trackers-mod-mc/releases/latest");
	
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	
			connection.setRequestProperty("accept", "application/json");
			
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
	
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				
				JsonObject obj = new JsonParser().parse(response.toString()).getAsJsonObject();
				
				latestVersion = obj.get("tag_name").getAsString();
	            
	            if(obj.get("tag_name").getAsString().equals("v" + Reference.VERSION)) {
	            	isLatestVersion = true;
	            }
	            else {
	            	isLatestVersion = false;
	            }
			}
		}
		 catch (IOException e) {
				e.printStackTrace();
		}
	}
	
	public boolean isLatestVersion() {
		return isLatestVersion;
	}
	
	public String getLatestVersion() {
		return latestVersion;
	}
}
