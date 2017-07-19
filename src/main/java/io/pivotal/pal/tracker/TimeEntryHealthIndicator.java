package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * Created by joenedumgottil on 7/19/17.
 */
@Component
public class TimeEntryHealthIndicator implements HealthIndicator {

    private static final int MAX_ENTRIES = 5;
    private final TimeEntryRepository repo;

    public TimeEntryHealthIndicator(TimeEntryRepository repo) {
        this.repo = repo;
    }

    @Override
    public Health health() {
        Health.Builder builder = new Health.Builder();

        if (repo.list().size() < MAX_ENTRIES)
            builder.up();
        else
            builder.down();

        return builder.build();
    }
}
