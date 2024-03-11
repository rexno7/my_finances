package mywebsite.aoc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/aoc", "/aoc/"})
public class AoCController {
	
	@Autowired
	private Day01 day01;
	
	@GetMapping("/1")
	public void day01() {
		day01.execute();
	}
}
