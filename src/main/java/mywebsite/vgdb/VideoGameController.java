package mywebsite.vgdb;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping({ "/videogames", "/videogames/" })
public class VideoGameController {

  @Autowired
  private VideoGameRepository videoGameRepo;

  @GetMapping
  public String getVGDB(Model model) {
    model.addAttribute("videoGames", videoGameRepo.findAll());
    return "videogames";
  }

  @PostMapping()
  public VideoGame createVideoGame(@RequestBody VideoGame videogame) {
    return videoGameRepo.save(videogame);
  }

  @GetMapping("/new")
  public String showAddGameForm(VideoGame videoGame) {
    return "new-videogame";
  }

  @PostMapping("/new")
  public String addVideoGame(@Valid VideoGame videoGame, BindingResult result, Model model) {
    if (result.hasErrors()) {
      return "new-videogame";
    }

    videoGameRepo.save(videoGame);
    return "redirect:/videogames";
  }

//	@PutMapping("/{id}")
//	public ResponseEntity<VideoGame> editVideoGame(@PathVariable Long id, 
//			@RequestBody VideoGame videoGame) {
//		Optional<VideoGame> optionalVideoGame = videoGameRepo.findById(id);
//		if (!optionalVideoGame.isPresent()) {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//		
//		VideoGame vg = optionalVideoGame.get();
//		vg.setName(videoGame.getName());
//		vg.setPlatform(videoGame.getPlatform());
//		
//		VideoGame updatedVideoGame = videoGameRepo.save(vg);
//		return ResponseEntity.ok(updatedVideoGame);
//	}

  @GetMapping("/{id}/edit")
  public String showEditVideoGame(@PathVariable Long id, Model model) {
    Optional<VideoGame> optionalVideoGame = videoGameRepo.findById(id);
    if (!optionalVideoGame.isPresent()) {
      return "redirect:/videogames";
    }

    model.addAttribute("videoGame", optionalVideoGame.get());
    return "update-videogame";
  }

  @PostMapping("/{id}")
  public String updateVideoGame(@PathVariable Long id, @Valid VideoGame videoGame,
      BindingResult result, Model model) {
    if (result.hasErrors()) {
      return "update-videogame";
    }

    videoGameRepo.save(videoGame);
    return "redirect:/videogames";
  }

  @GetMapping("/{id}/delete")
  public String deleteVideoGame(@PathVariable Long id) {
    Optional<VideoGame> optionalVideoGame = videoGameRepo.findById(id);
    if (!optionalVideoGame.isPresent()) {
      return "redirect:/videogames";
    }

    videoGameRepo.delete(optionalVideoGame.get());
    return "redirect:/videogames";
  }
}
