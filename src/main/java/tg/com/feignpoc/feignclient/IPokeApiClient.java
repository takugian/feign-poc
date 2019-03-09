package tg.com.feignpoc.feignclient;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tg.com.feignpoc.model.Pokemon;
import tg.com.feignpoc.model.PokemonList;

@Qualifier("feignClient")
@RequestMapping(path = "https://pokeapi.co/api/v2")
public interface IPokeApiClient {

	@RequestMapping(method = RequestMethod.POST, path = "/pokemon", headers = { "cdUsuario=UZ79054",
			"cdEstacao=UEIPA1000366" })
	Pokemon inserir(@RequestBody Pokemon pokemon);

	@RequestMapping(method = RequestMethod.PUT, path = "/pokemon/{id}", headers = { "cdUsuario=UZ79054",
			"cdEstacao=UEIPA1000366" })
	Pokemon atualizar(@PathVariable("id") Integer id, @RequestBody Pokemon pokemon);

	@RequestMapping(method = RequestMethod.DELETE, path = "/pokemon/{id}", headers = { "cdUsuario=UZ79054",
			"cdEstacao=UEIPA1000366" })
	void excluir(@PathVariable("id") Integer id);

	@RequestMapping(method = RequestMethod.GET, path = "/pokemon/{id}", headers = { "cdUsuario=UZ79054",
			"cdEstacao=UEIPA1000366" })
	Pokemon obterPeloId(@PathVariable("id") Integer id);

	@RequestMapping(method = RequestMethod.GET, path = "/pokemon", params = { "offset=10", "limit=10" }, headers = {
			"cdUsuario=UZ79054", "cdEstacao=UEIPA1000366" })
	PokemonList listar();

}
