package io.pivotal.pal.tracker;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by joenedumgottil on 7/19/17.
 */
public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    public HashMap<Long, TimeEntry> timeEntries = new HashMap<>();

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        timeEntry.setId(timeEntries.size() + 1);
        timeEntries.put(timeEntry.getId(), timeEntry);
        return timeEntry;
    }

    @Override
    public TimeEntry get(Long id) {
        if (timeEntries.containsKey(id))
            return timeEntries.get(id);

        return null;
    }

    @Override
    public List<TimeEntry> list() {
        return new ArrayList<TimeEntry>(timeEntries.values());
    }

    @Override
    public TimeEntry update(Long id, TimeEntry timeEntry) {
        if (!timeEntries.containsKey(id)) return null;

        timeEntry.setId(id);
        timeEntries.put(id, timeEntry);

        return timeEntry;
    }

    @Override
    public void delete(Long id) {
        timeEntries.remove(id);
    }
}
