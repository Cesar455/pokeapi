package desaPokeCloud.utils;

import java.util.ArrayList;
import java.util.Collection;

public class Pokemon 
{
	private String name = "";
	private String id = "";
	private Collection<NameField> types = new ArrayList<NameField>();
	private String weight = "";
	private Collection<NameField> abilities = new ArrayList<NameField>();
	private String description  = ""; 
	private Collection<NameField> evolution = new ArrayList<NameField>();
	private String image = "";
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Collection<NameField> getTypes() {
		return types;
	}
	public void setTypes(String type) {
		this.types.add(new NameField(type));
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public Collection<NameField> getAbilities() {
		return abilities;
	}
	public void setAbilities(String abilities) {
		this.abilities.add(new NameField(abilities));
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Collection<NameField> getEvolution() {
		return evolution;
	}
	public void setEvolution(String evolution) {
		this.evolution.add(new NameField(evolution));
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
}
