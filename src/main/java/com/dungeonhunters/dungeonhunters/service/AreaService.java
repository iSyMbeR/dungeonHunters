package com.dungeonhunters.dungeonhunters.service;

import com.dungeonhunters.dungeonhunters.Repository.AreaRepository;

import com.dungeonhunters.dungeonhunters.model.Area;
import com.dungeonhunters.dungeonhunters.model.Card;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AreaService {

    private final AreaRepository areaRepository;

    public void addArea(Area area){
        areaRepository.save(area);
    }

    public void deleteArea(Area area){
        areaRepository.delete(area);
    }

    public List<Area> getAllAreas() { return areaRepository.findAll(); }

    public Area getAreaById(Long id){return areaRepository.findById(id).orElseThrow();}

    public int getSize(){
        return (int) areaRepository.count();
    }
}
