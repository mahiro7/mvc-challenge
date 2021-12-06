package com.gft.mvcproject.services;

import com.gft.mvcproject.entities.Daily;
import com.gft.mvcproject.entities.Starter;
import com.gft.mvcproject.repositories.DailyRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DailyService {
    @Autowired
    private DailyRepository dailyRepository;

    public Daily saveDaily(Daily daily) {
        return dailyRepository.save(daily);
    }

    public List<Daily> listDailies() {
        return dailyRepository.findAll();
    }

    public Daily getDaily(Long id) throws NotFoundException {
        Optional<Daily> daily = dailyRepository.findById(id);

        if (daily.isEmpty()) {
            throw new NotFoundException("Daily não encontrada!");
        }

        return daily.get();
    }

    public void deleteDaily(Long id) {
        dailyRepository.deleteById(id);
    }

    public boolean isUnique(Starter starter, Date data) {
        return dailyRepository.findByStarterNDia(starter, data) == null;
    }
}
