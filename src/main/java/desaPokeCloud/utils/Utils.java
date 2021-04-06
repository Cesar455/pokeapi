package desaPokeCloud.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils 
{
	public static Collection<String> leerArrayNodo(JsonNode node, String arrField, String parentField, String field)
	{
		Collection<String> list = new ArrayList<String>();
        JsonNode typesNode = node.path(arrField);
        Iterator<JsonNode> types = typesNode.elements();
        while(types.hasNext())
        {
        	JsonNode type = types.next();
	        JsonNode typeNode = type.path(parentField);
	        JsonNode typeNameNode = typeNode.path(field);
	        list.add(typeNameNode.asText());
        }
        return list;
	}

	public static String leerNodoToString(JsonNode node, String field)
	{
        JsonNode nameNode = node.path(field); 
        return nameNode.asText();
	}

	public static JsonNode leerJSon(String json)
	{
	      //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        //read JSON like DOM Parser
        try 
        {
			return objectMapper.readTree(json);
		} catch (IOException e) {
			return null;
		}
	}
	
	public static Pokemon buscarDatosBasicos(String id, Pokemon pokemon)
	{
		String salida = Utils.llamadoRest("https://pokeapi.co/api/v2/pokemon/" + id + "/");
        
		JsonNode rootNode = Utils.leerJSon(salida);
        pokemon.setName(Utils.leerNodoToString(rootNode, "name"));
        pokemon.setId(Utils.leerNodoToString(rootNode, "id"));
        
        JsonNode spritesNode = rootNode.path("sprites"); 
        pokemon.setImage(Utils.leerNodoToString(spritesNode, "front_default"));
 
        Collection<String> types = Utils.leerArrayNodo(rootNode, "types", "type", "name");
        for (Iterator<String> iter = types.iterator(); iter.hasNext();)
        {
        	pokemon.setTypes(iter.next());
        }

        pokemon.setWeight(Utils.leerNodoToString(rootNode, "weight"));

        Collection<String> abilities = Utils.leerArrayNodo(rootNode, "abilities", "ability", "name");
        for (Iterator<String> iter = abilities.iterator(); iter.hasNext();)
        {
        	pokemon.setAbilities(iter.next());
        }
        return pokemon;
	}
	
	public static String llamadoRest(String path)
	{
		String salida = "";
		try
		{
	        URL url = new URL(path);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Content-Type", "application/json");
	        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
			
	        if (conn.getResponseCode() != 200) {
	            throw new RuntimeException("Failed : HTTP error code : "
	                    + conn.getResponseCode());
	        }

	        BufferedReader br = new BufferedReader(new InputStreamReader(
	            (conn.getInputStream())));

	        String output;
	        while ((output = br.readLine()) != null) {
	            salida = salida + output;
	        }
	        
	        conn.disconnect();
		}
		catch(ProtocolException pex)
		{
			return "Tu pokemon juega a las escondidas";
		}
		catch(MalformedURLException pex)
		{
			return "Tu pokemon juega a las escondidas";
		}
		catch(IOException pex)
		{
			return "Tu pokemon anda mañoso";
		}
		return salida;
	}
}
