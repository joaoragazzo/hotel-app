package unifal.hotel.api;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import unifal.hotel.services.NameService;

@RestController
@AllArgsConstructor
public class NameController {

    final private NameService nameService;

    @GetMapping("/nome")
    public String getName()
    {
        return nameService.getName();
    }

    @GetMapping("/example")
    public String examplePage()
    {
        return "This is an example of a page";
    }

}
