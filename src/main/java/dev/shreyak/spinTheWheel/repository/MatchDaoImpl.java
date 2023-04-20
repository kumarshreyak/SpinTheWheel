package dev.shreyak.spinTheWheel.repository;

import dev.shreyak.spinTheWheel.model.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MatchDaoImpl implements MatchDao {

    PerformanceRepository performanceRepository;
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<Match> findByInfo_Venue(String stadium) {
        return performanceRepository.findByInfo_Venue(stadium);
    }

    @Override
    public List<Match> findByBatsmanAndBowler(String batsman, String bowler) {

        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.andOperator(
                Criteria.where("info.registry.people." + batsman).exists(true),
                Criteria.where("info.registry.people." + bowler).exists(true)
        );
        query.addCriteria(criteria);

        return mongoTemplate.find(query, Match.class, "Matches");
    }

    @Override
    public List<Match> findByPlayerInIPL(String player, String afterDate) {
        Query query = new Query();
        query.addCriteria(Criteria.where("info.registry.people." + player).exists(true)
                .and("info.dates").gte(afterDate));
        return mongoTemplate.find(query, Match.class, "IPL2023");
    }


    @Override
    public List<Match> findByPlayerInT20(String player, String afterDate) {
        Query query = new Query();
        query.addCriteria(Criteria.where("info.registry.people." + player).exists(true)
                .and("info.dates").gt(afterDate));
        return mongoTemplate.find(query, Match.class, "T20_2021");
    }

    @Override
    public List<Match> findByPlayerAndVenueInIPL(String player, String venue, String afterDate) {
        Query query = new Query();
        query.addCriteria(Criteria.where("info.registry.people." + player).exists(true)
                .and("info.dates").gt(afterDate).and("info.venue").is(venue));
        return mongoTemplate.find(query, Match.class, "IPL2023");
    }

    @Override
    public List<Match> findByPlayerAndVenueInT20(String player, String venue, String afterDate) {
        Query query = new Query();
        query.addCriteria(Criteria.where("info.registry.people." + player).exists(true)
                .and("info.dates").gt(afterDate).and("info.venue").is(venue));
        return mongoTemplate.find(query, Match.class, "T20_2021");
    }

    @Override
    public List<Match> findMatchesByBatsmanAndBowlerAndVenue(String batsman, String bowler, String venue) {
        Query query = new Query();
        query.addCriteria(Criteria.where("info.registry.people." + batsman).exists(true)
                .and("info.registry.people." + bowler).exists(true)
                .and("info.venue").is(venue));
        return mongoTemplate.find(query, Match.class, "Matches");

    }
}
