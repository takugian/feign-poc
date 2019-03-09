package tg.com.feignpoc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tg.com.feignpoc.feignclient.IPokeApiClient;
import tg.com.feignpoc.model.Pokemon;
import tg.com.feignpoc.model.PokemonList;

@Service
public class PokemonService implements IPokemonService {

	@Autowired
	private IPokeApiClient pokeApiClient;

	@Override
	public Pokemon obterPeloId(Integer id) {
		return this.pokeApiClient.obterPeloId(id);
	}

	@Override
	public PokemonList listar() {
		return this.pokeApiClient.listar();
	}

}
