package io.pivotal.pal.trackertest;

import io.pivotal.pal.tracker.InMemoryTimeEntryRepository;
import io.pivotal.pal.tracker.TimeEntry;
import org.junit.Test;

import java.sql.Time;
import java.util.List;
import static java.util.Arrays.asList;

import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by joenedumgottil on 7/19/17.
 */
public class InMemoryTimeEntryRepositoryTest {
    @Test
    public void create() {
        InMemoryTimeEntryRepository repo = new InMemoryTimeEntryRepository();
        TimeEntry createdTimeEntry = repo.create(
                new TimeEntry(123,456,"today",8));

        TimeEntry expected = new TimeEntry(1L,123,456,"today",8);
        assertEquals(expected, createdTimeEntry);

//        TimeEntry readEntry = repo.get(createdTimeEntry.getId());
//        assertEquals(readEntry,expected);
    }

    @Test
    public void get() {
        InMemoryTimeEntryRepository repo = new InMemoryTimeEntryRepository();
        repo.create(new TimeEntry(123,456,"today",8));

        TimeEntry expected = new TimeEntry(1L,123,456,"today",8);
        TimeEntry readEntry = repo.get(1L);
        assertEquals(expected, readEntry);
    }

    @Test
    public void list() {
        InMemoryTimeEntryRepository repo = new InMemoryTimeEntryRepository();
        repo.create(new TimeEntry(123, 456, "today", 8));
        repo.create(new TimeEntry(789, 654, "yesterday", 4));

        List<TimeEntry> expected = asList(
                new TimeEntry(1L, 123, 456, "today", 8),
                new TimeEntry(2L, 789, 654, "yesterday", 4)
        );

        assertEquals(expected, repo.list());
    }

    @Test
    public void update() {
        InMemoryTimeEntryRepository repo = new InMemoryTimeEntryRepository();
        TimeEntry created = repo.create(new TimeEntry(123, 456, "today", 8));

        TimeEntry updatedEntry = repo.update(
                created.getId(),
                new TimeEntry(321,654,"tomorrow",5)
        );

        TimeEntry expected = new TimeEntry(created.getId(), 321, 654, "tomorrow", 5);
        assertEquals(expected, updatedEntry);
        assertEquals(expected, repo.get(updatedEntry.getId()));
    }

    @Test
    public void delete() {
        InMemoryTimeEntryRepository repo = new InMemoryTimeEntryRepository();
        TimeEntry created = repo.create(new TimeEntry(123, 456, "today", 8));

        repo.delete(created.getId());

        assertThat(repo.list()).isEmpty();
    }
}
