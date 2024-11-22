package org.example.harrypotter.services;

import org.example.harrypotter.entities.House;
import org.example.harrypotter.entities.Student;
import org.example.harrypotter.repositories.HouseRepository;

import java.util.List;
import java.util.stream.Collectors;

public class HouseServiceImplementation implements HouseService{
    private HouseRepository houseRepository;

    public HouseServiceImplementation(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    @Override
    public List<House> getHouses() {
        return houseRepository.getHouses();
    }

    @Override
    public House getHouseByName(String name) {
        return houseRepository.getHouse(name);
    }

    @Override
    public void addHouse(House house){
        houseRepository.addHouse(house);
    }

    @Override
    public  void updateHouse(String name, House house){
        houseRepository.updateHouse(name,house);
    }
    @Override
    public void deleteHouse(String name){
        houseRepository.deleteHouse(name);
    }

}
