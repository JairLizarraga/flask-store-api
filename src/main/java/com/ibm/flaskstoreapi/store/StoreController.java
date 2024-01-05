package com.ibm.flaskstoreapi.store;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/store/")
public class StoreController {


	private final StoreRepository storeRepository;
    
    public StoreController(StoreRepository productRepository) {
        this.storeRepository = productRepository;
    }
	
	@PostMapping
	public Store addProduct(@RequestBody Store store) {
		return storeRepository.save(store);
	}
	
	@DeleteMapping("/{storeId}")
	public void deleteStore(@PathVariable int storeId) {
		storeRepository.deleteById(storeId);
	}
	
	@PutMapping("/{storeId}")
	public void updateStore(@PathVariable int storeId, @RequestBody Store store) {
		if(storeRepository.existsById(storeId)) {
			store.setStoreId(storeId);
			storeRepository.save(store);
		}
	}
	
	@GetMapping("/")
	public List<Store> getProducts(){
		return storeRepository.findAll();
	}
	
	@GetMapping("/{storeId}")
	public ResponseEntity<Store> getStoreById(@PathVariable int storeId) {
		Optional<Store> store = storeRepository.findById(storeId);
		return store.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
}
