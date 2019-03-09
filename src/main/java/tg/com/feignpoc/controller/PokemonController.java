package tg.com.feignpoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tg.com.feignpoc.model.Pokemon;
import tg.com.feignpoc.model.PokemonList;
import tg.com.feignpoc.service.IPokemonService;

@RestController
@RequestMapping(path = "/pokemon")
public class PokemonController {

	private IPokemonService pokemonService;

	@Autowired
	public PokemonController(IPokemonService pokemonService) {
		this.pokemonService = pokemonService;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public Pokemon obterPeloId(@PathVariable("id") Integer id) {
		return pokemonService.obterPeloId(id);
	}

	@RequestMapping(method = RequestMethod.GET)
	public PokemonList listar() {
		return pokemonService.listar();
	}

}
