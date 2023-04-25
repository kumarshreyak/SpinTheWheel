package dev.shreyak.spinTheWheel.service;

import dev.shreyak.spinTheWheel.model.Delivery;
import dev.shreyak.spinTheWheel.model.Inning;
import dev.shreyak.spinTheWheel.model.Match;
import dev.shreyak.spinTheWheel.repository.MatchDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static dev.shreyak.spinTheWheel.model.PlayerPerformanceRequest.TYPE_IPL;
import static dev.shreyak.spinTheWheel.model.PlayerPerformanceRequest.TYPE_T20;

@Slf4j
@Service
public class PerformanceServiceImpl {

    @Autowired
    private MatchDao matchDao;



}

