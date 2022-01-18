package ch.baramex.trackersmod.utils;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

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
		final CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpGet request = new HttpGet("https://api.github.com/repos/baramex/trackers-mod-mc/releases/latest");
		
		request.addHeader("Content-Type", "application/json");
		
		try (CloseableHttpResponse response = httpClient.execute(request)) {

            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();

            if (entity != null) {
                String result = EntityUtils.toString(entity);
                
                JsonObject obj = new JsonParser().parse(result).getAsJsonObject();
                
                latestVersion = obj.get("tag_name").getAsString();
                
                if(obj.get("tag_name").getAsString().equals("v" + Reference.VERSION)) {
                	isLatestVersion = true;
                }
                else {
                	isLatestVersion = false;
                }
            }

        } catch (Exception  e1) {
			e1.printStackTrace();
		}
		
		try {
			httpClient.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public boolean isLatestVersion() {
		return isLatestVersion;
	}
	
	public String getLatestVersion() {
		return latestVersion;
	}
}
