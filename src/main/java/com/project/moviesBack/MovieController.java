package com.project.moviesBack;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class MovieController {
	@CrossOrigin(origins = "http://localhost:8200")
	@GetMapping("/movie")
	public String searchMovie(@RequestParam(name="name", required=true) String name, Model model) {
		try {
			WebClient webClient = WebClient.create("http://www.omdbapi.com/?apikey=fe9044ff&t="+name);
			Mono<String> response = webClient.get().retrieve().bodyToMono(String.class);
			
			response.subscribe(res -> model.addAttribute("movieName", res));
			
			response.block();			
			
			return "movies";
			
		}catch(Exception e) {
			return "error";
		}
	}
}
