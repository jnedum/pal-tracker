package io.pivotal.pal.tracker;

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

    public TimeEntriesController(TimeEntryRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntry) {
        TimeEntry created = repo.create(timeEntry);

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity read(@PathVariable long id) {
        TimeEntry entry = repo.get(id);
        if (entry != null)
            return new ResponseEntity<>(entry, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        return new ResponseEntity<>(repo.list(), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody TimeEntry entry) {
        TimeEntry updatedEntry = repo.update(id, entry);

        if (updatedEntry != null)
            return new ResponseEntity<>(updatedEntry, HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable Long id) {
        repo.delete(id);

        return new ResponseEntity<TimeEntry>(HttpStatus.NO_CONTENT);
    }

}
