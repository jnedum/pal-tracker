package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

/**
 * Created by joenedumgottil on 7/19/17.
 */
@RestController
@RequestMapping("/timeEntries")
public class TimeEntriesController {

    private TimeEntryRepository repo;
    private final CounterService counter;
    private final GaugeService gauge;

    public TimeEntriesController(TimeEntryRepository repo, CounterService counter, GaugeService gauge) {
        this.repo = repo;
        this.counter = counter;
        this.gauge = gauge;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntry) {
        TimeEntry created = repo.create(timeEntry);
        counter.increment("TimeEntry.created");
        gauge.submit("timeEntries.count", repo.list().size());

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity read(@PathVariable long id) {
        TimeEntry entry = repo.get(id);
        if (entry != null) {
            counter.increment("TimeEntry.read");
            return new ResponseEntity<>(entry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        counter.increment("TimeEntry.listed");
        return new ResponseEntity<>(repo.list(), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody TimeEntry entry) {
        TimeEntry updatedEntry = repo.update(id, entry);

        if (updatedEntry != null) {
            counter.increment("TimeEntry.updated");
            return new ResponseEntity<>(updatedEntry, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable Long id) {
        repo.delete(id);

        counter.increment("TimeEntry.deleted");
        gauge.submit("timeEntries.count", repo.list().size());

        return new ResponseEntity<TimeEntry>(HttpStatus.NO_CONTENT);
    }

}
