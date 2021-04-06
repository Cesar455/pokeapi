package desaPokeCloud.utils;

public class KeyDescription 
{
	String key;
	String description;

	public KeyDescription()
	{}
	
	public KeyDescription(String key, String description)
	{
		this.key = key;
		this.description = description;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
