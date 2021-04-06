package desaPokeCloud;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import desaPokeCloud.utils.Pokemon;
import desaPokeCloud.utils.Utils;

@WebServlet(
	    name = "pokemones",
	    urlPatterns = {"/pokeApi"}
	)
public class LlamadoPokeApi extends HttpServlet  
{
	private static final long serialVersionUID = 1L;

	@Override
	  public void doGet(HttpServletRequest request, HttpServletResponse response) 
	      throws IOException {
		String id = request.getParameter("id");
//		System.out.println("El id es: " + id);
	    response.setContentType("text/plain");
	    response.setCharacterEncoding("UTF-8");

	    response.getWriter().print(buscarDatos(id));

	  }

	public String buscarDatos(String id)
	{
		Pokemon pokemon = Utils.buscarDatosBasicos(id, new Pokemon());
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = "";
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
