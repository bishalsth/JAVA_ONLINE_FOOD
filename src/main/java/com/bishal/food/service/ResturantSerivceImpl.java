package com.bishal.food.service;

import com.bishal.food.dto.ResturantDto;
import com.bishal.food.model.Address;
import com.bishal.food.model.Resturant;
import com.bishal.food.model.User;
import com.bishal.food.repo.AddresRepo;
import com.bishal.food.repo.ResturantRepo;
import com.bishal.food.repo.UserRepo;
import com.bishal.food.request.CreateResturantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResturantSerivceImpl implements ResturantService{

    @Autowired
    private ResturantRepo resturantRepo;

    @Autowired
    private AddresRepo addresRepo;

    @Autowired
    private UserRepo userRepo;
    @Override
    public Resturant createResturant(CreateResturantRequest req, User user) {

        Address address = addresRepo.save(req.getAddress());
        Resturant resturant = new Resturant();
        resturant.setAddress(address);
        resturant.setName(req.getName());
        resturant.setDescription(req.getDescription());
        resturant.setImages(req.getImages());
        resturant.setOwner(user);
        resturant.setCuisineType(req.getCuisineType());
        resturant.setRegistrationDate(LocalDateTime.now());
        resturant.setOpeningHours(req.getOpeningHours());
        resturant.setContactInformation(req.getContactInformation());
        return resturantRepo.save(resturant);
    }

    @Override
    public Resturant updateResturant(Long resturantId, CreateResturantRequest updateResturant) throws Exception {

        Resturant resturant = findResturantById(resturantId);
        if(resturant.getCuisineType() !=null){
            resturant.setCuisineType(updateResturant.getCuisineType());
        }
        if(resturant.getDescription() !=null){
            resturant.setDescription(updateResturant.getDescription());
        }

        if(resturant.getName() !=null){
            resturant.setName(updateResturant.getName());
        }
        return resturantRepo.save(resturant);
    }

    @Override
    public void deleteResturant(Long resturantId) throws Exception {

        Resturant resturant = findResturantById(resturantId);
        resturantRepo.delete(resturant);

    }

    @Override
    public List<Resturant> getAllResturant() {
//        List<Resturant> resturants = resturantRepo.findAll();
//        List<Resturant> collect = resturants.stream().map(resturant -> resturant).collect(Collectors.toList());


        return resturantRepo.findAll();
    }

    @Override
    public List<Resturant> searchResturant(String keyword) {
        return resturantRepo.findBySearchQuery(keyword);
    }

    @Override
    public Resturant findResturantById(Long id) throws Exception {
        Optional<Resturant> opt = resturantRepo.findById(id);
        if(opt.isEmpty()){
            throw  new Exception("resturant with id not found"+id);
        }
        return opt.get();
    }

    @Override
    public Resturant getResturantByUserId(Long userId) throws Exception {
        Resturant resturant = resturantRepo.findByOwnerId(userId);
        if(resturant ==null){
            throw new Exception("resturant wiht owner id not found"+userId);
        }
        return resturant;
    }

    @Override
    public ResturantDto addToFavorites(Long resturantId, User user) throws Exception {
        Resturant resturant = findResturantById(resturantId);

        ResturantDto dto = new ResturantDto();
        dto.setDescription(resturant.getDescription());
        dto.setTitle(resturant.getName());
        dto.setImages(resturant.getImages());
        dto.setId(resturantId);
//        if(user.getFavorites().contains(dto)){
//            user.getFavorites().remove(dto);
//        }else user.getFavorites().add(dto);
        boolean isFavorited = false;
        List<ResturantDto> favorites = user.getFavorites();
        for(ResturantDto favorite : favorites){
            if(favorite.getId().equals(resturantId)){
                isFavorited =true;
                break;
            }

        }
        if(isFavorited){
            favorites.removeIf(favorite ->favorite.getId().equals(resturantId));

        }else {
            favorites.add(dto);
        }
        userRepo.save(user);
        return dto;
    }

    @Override
    public Resturant updateResturantStatus(Long id) throws Exception {

        Resturant resturant = findResturantById(id);
        resturant.setOpen(!resturant.isOpen());
        return resturantRepo.save(resturant);
    }
}
