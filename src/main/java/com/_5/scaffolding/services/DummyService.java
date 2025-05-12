package com._5.scaffolding.services;

import com._5.scaffolding.models.Dummy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DummyService {
    Dummy getDummy(Long id);
    List<Dummy> getAllDummys();
    Dummy createDummy(Dummy dummy);
    Dummy updateDummy(Dummy dummy);
    void deleteDummy(Dummy dummy);
}
