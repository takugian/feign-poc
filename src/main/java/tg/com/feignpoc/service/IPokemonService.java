package tg.com.feignpoc.service;

import org.springframework.stereotype.Service;

import tg.com.feignpoc.model.Pokemon;
import tg.com.feignpoc.model.PokemonList;

@Service
public interface IPokemonService {

	Pokemon obterPeloId(Integer id);

	PokemonList listar();

}
