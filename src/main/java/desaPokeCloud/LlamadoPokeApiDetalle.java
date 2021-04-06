package desaPokeCloud;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import desaPokeCloud.utils.Pokemon;
import desaPokeCloud.utils.Utils;

@WebServlet(
	    name = "pokemonesDet",
	    urlPatterns = {"/pokeApiDetalle"}
	)
public class LlamadoPokeApiDetalle extends HttpServlet  
{
	private static final long serialVersionUID = 1L;

	@Override
	  public void doGet(HttpServletRequest request, HttpServletResponse response) 
	      throws IOException {

		String id = request.getParameter("id");
	    response.setContentType("text/plain");
	    response.setCharacterEncoding("UTF-8");

	    response.getWriter().print(buscarDatos(id));

	  }
	  
	  private Pokemon searchEvolution(JsonNode node, Pokemon pokemon)
	  {
	        JsonNode speciesNode = node.path("species"); 
	        JsonNode nameNode = speciesNode.path("name"); 
	        pokemon.setEvolution(nameNode.asText());
	        JsonNode typesNode = node.path("evolves_to");
	        Iterator<JsonNode> types = typesNode.elements();
	        if (types.hasNext())
	        {
	        	pokemon = searchEvolution(types.next(), pokemon);
	        }
	        return pokemon;
	  }

	  private Pokemon searchDescription(JsonNode rootNode, Pokemon pokemon)
	  {
	        JsonNode descriptionsNode = rootNode.path("descriptions");
	        Iterator<JsonNode> descriptions = descriptionsNode.elements();
	        while(descriptions.hasNext())
	        {
	        	JsonNode description = descriptions.next();
		        JsonNode descriptionNode = description.path("description");
		        JsonNode languageNode = description.path("language");
		        JsonNode languageNameNode = languageNode.path("name");
		        if ("en".equals(languageNameNode.asText()))
		        {
		        	pokemon.setDescription(descriptionNode.asText());
		        }
	        }
	        return pokemon;
	  }

	public String buscarDatos(String id)
	{
		Pokemon pokemon = Utils.buscarDatosBasicos(id, new Pokemon());
		String salida = Utils.llamadoRest("https://pokeapi.co/api/v2/characteristic/" + pokemon.getId() + "/");
		pokemon = searchDescription(Utils.leerJSon(salida), pokemon);

		salida = Utils.llamadoRest("https://pokeapi.co/api/v2/evolution-chain/" + pokemon.getId() + "/");
		JsonNode rootNode1 = Utils.leerJSon(salida);
		JsonNode chainNode = rootNode1.path("chain");
		pokemon = searchEvolution(chainNode, pokemon);

		String json = "";
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try 
		{
			json = ow.writeValueAsString(pokemon);
		}
		catch (JsonProcessingException e) 
		{
			pokemon.setName("tu pokemon no esta disponible");
			try 
			{
				return ow.writeValueAsString(pokemon);
			}
			catch (JsonProcessingException ex) 
			{
				return "";
			}
		}
		return json;
	}
}
