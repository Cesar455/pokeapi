poke_container = document.getElementById('poke_container');
pokemons_number = 11;

function generarPokedex()
{
	for (i = 1; i < pokemons_number; i++) 
	{
		getPokemon(i);
	}
};

function getPokemon(id)
{
	$.ajax({
	    url : '/pokeApi',
	    data : { id : id },
	    type : 'GET',
	    dataType : 'json',
	    success : function(json) 
	    {
			createPokemonCard(json);
	    },
	    error : function(xhr, status) 
	    {
	        alert('Disculpe, existió un problema');
	    },
	});
}

function createPokemonCard(json)
{
	pokemonEl = document.createElement('div');
	pokemonEl.classList.add('pokemon');
	typeTmp = "";
	for(i=0;i < json.types.length;i++)
	{
		typeTmp = typeTmp + json.types[i].name;
		if ((i+1) < json.types.length)
		{
			typeTmp = typeTmp + ", ";
		}
	}
	abilityTmp = "";
	for(i=0;i < json.abilities.length;i++)
	{
		abilityTmp = abilityTmp + json.abilities[i].name;
		if ((i+1) < json.abilities.length)
		{
			abilityTmp = abilityTmp + ", ";
		}
	}
	pokeInnerHTML = '<div class=\"info\">';
	pokeInnerHTML = pokeInnerHTML + '	<div class=\"img-container\"><a href=\"javascript:searchDetail(' + json.id + ')\">';
	pokeInnerHTML = pokeInnerHTML + '		<img src=\"' + json.image + '\" alt=\"' + json.name + '\" /></a>';
	pokeInnerHTML = pokeInnerHTML + '	</div>';
	pokeInnerHTML = pokeInnerHTML + '	<h3 class=\"name\">' + json.name + '</h3>';
	pokeInnerHTML = pokeInnerHTML + '	<small class=\"type\">weight: <span>' + json.weight + '<br/></span></small>';
	pokeInnerHTML = pokeInnerHTML + '	<small class=\"type\">type: <span>' + typeTmp + '<br/></span></small>';
	pokeInnerHTML = pokeInnerHTML + '	<small class=\"type\">abilities: <span>' + abilityTmp + '<br/></span></small>';
	pokeInnerHTML = pokeInnerHTML + '</div>';
	pokemonEl.innerHTML = pokeInnerHTML;
	poke_container.appendChild(pokemonEl);
}
function searchDetail(id)
{
	modal.style.display = "block";
	poke_detail = document.getElementById("poke_detail");
	poke_detail.innerHTML = 'Cargando...';
	$.ajax({
	    url : '/pokeApiDetalle',
	    data : { id : id },
	    type : 'GET',
	    dataType : 'json',
	    success : function(json) 
	    {
			createPokemonDetail(json);
	    },
	    error : function(xhr, status) 
	    {
	        alert('Disculpe, existió un problema');
	    },
	});
}

function createPokemonDetail(json)
{
	pokemonEl = document.createElement('div');
	typeTmp = "";
	for(i=0;i < json.types.length;i++)
	{
		typeTmp = typeTmp + json.types[i].name;
		if ((i+1) < json.types.length)
		{
			typeTmp = typeTmp + ", ";
		}
	}
	abilityTmp = "";
	for(i=0;i < json.abilities.length;i++)
	{
		abilityTmp = abilityTmp + json.abilities[i].name;
		if ((i+1) < json.abilities.length)
		{
			abilityTmp = abilityTmp + ", ";
		}
	}
	evolution = "";
	for(i=0;i < json.evolution.length;i++)
	{
		evolution = evolution + json.evolution[i].name;
		if ((i+1) < json.evolution.length)
		{
			evolution = evolution + ", ";
		}
	}
	pokeInnerHTML = '<div class=\"info\">';
	pokeInnerHTML = pokeInnerHTML + '	<div class=\"img-container\" align=\"center\">';
	pokeInnerHTML = pokeInnerHTML + '		<img src=\"' + json.image + '\" alt=\"' + json.name + '\" />';
	pokeInnerHTML = pokeInnerHTML + '	</div>';
	pokeInnerHTML = pokeInnerHTML + '	<h3 class=\"name\">' + json.name + '</h3>';
	pokeInnerHTML = pokeInnerHTML + '	<small class=\"type\">weight: <span>' + json.weight + '<br/></span></small>';
	pokeInnerHTML = pokeInnerHTML + '	<small class=\"type\">type: <span>' + typeTmp + '<br/></span></small>';
	pokeInnerHTML = pokeInnerHTML + '	<small class=\"type\">abilities: <span>' + abilityTmp + '<br/></span></small>';
	pokeInnerHTML = pokeInnerHTML + '	<small class=\"type\">description: <span>' + json.description + '<br/></span></small>';
	pokeInnerHTML = pokeInnerHTML + '	<small class=\"type\">Evolutions: <span>' + evolution + '<br/></span></small>';
	pokeInnerHTML = pokeInnerHTML + '</div>';
	pokemonEl.innerHTML = pokeInnerHTML;
	poke_detail.innerHTML = '';
	poke_detail.appendChild(pokemonEl);
}

generarPokedex();