package fr.minint.sief.web.rest;

import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

/**
 * REST controller for managing application appointment.
 */
@RestController
@RequestMapping("/api")
public class RdvResource {

    private final Logger log = LoggerFactory.getLogger(RdvResource.class);

    private static final String[] HOUR_VALUES = {
    	"09:00", "09:15", "09:30", "09:45",
    	"10:00", "10:15", "10:30", "10:45",
    	"11:00", "11:15", "11:30", "11:45",
    	"14:00", "14:15", "14:30", "14:45",
    	"15:00", "15:15", "15:30", "15:45",
    	"16:00", "16:15", "16:30", "16:45",
    	"17:00", "17:15", "17:30", "17:45",
    	};
    
    /**
     * GET  /rdv -> get all time slot
     */
    @RequestMapping(value = "/rdv",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SortedMap<DateTime, Set<String>>> getTimeSlots(@RequestParam(value = "email") String email) {
        log.debug("REST request to get rdv time slots with mail {}", email);
        SortedMap<DateTime, Set<String>> timeSlots = new TreeMap<DateTime, Set<String>>();
        
        long beginTime = new DateTime().getMillis();
        long endTime = new DateTime().plusMonths(2).getMillis();
        
        for (int i=0; i<15; i++) {
        	DateTime day = new DateTime(getRandomTimeBetweenTwoDates(beginTime, endTime));
        	Set<String> hours = new TreeSet<String>();
        	for(int j=0; j<6; j++) {
        		hours.add(HOUR_VALUES[(int)(Math.random() * (HOUR_VALUES.length -1))]);
        	}
        	timeSlots.put(day, hours);
        }
        
        return new ResponseEntity<>(timeSlots, HttpStatus.OK);
    }
    
    private DateTime getRandomTimeBetweenTwoDates (long beginTime, long endTime) {
        long diff = endTime - beginTime + 1;
        long time = beginTime + (long) (Math.random() * diff);
        DateTime d = new DateTime(time);
        return new DateTime(d.getYear(), d.getMonthOfYear(), d.getDayOfMonth(), 0, 0);
    }
    
}
