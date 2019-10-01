package com.example.RestAPI;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/apiStatus")
@CrossOrigin(origins= {"http://localhost:3000","http://localhost:3001","http://localhost:8080"})
public class ControllerApiStatus {
    private restRepositoryAPIFailure apiFailureRepository;
    public ControllerApiStatus(restRepositoryAPIFailure apiFailureRepository) {
        this.apiFailureRepository = apiFailureRepository;
    }
    @GetMapping
    public List<ApiStatus> getList() {
        return (List<ApiStatus>) apiFailureRepository.findAll(sortByIdAsc());
    }
    private Sort sortByIdAsc() {
        return new Sort(Sort.Direction.DESC, "id");
    }
    @PostMapping
    public ApiStatus addUser(@RequestBody ApiStatus apistatus) {
        return apiFailureRepository.save(apistatus);
    }
    @DeleteMapping(value = "/{id}")
    public void delUser(@PathVariable("id") Integer id) {
    	apiFailureRepository.deleteById(id);
    }
}

