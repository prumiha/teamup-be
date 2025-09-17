CREATE TABLE IF NOT EXISTS user_stats
(
    id                      BIGINT       NOT NULL PRIMARY KEY,
    activities_locked       INT          NOT NULL,
    activities_late_unlocks INT          NOT NULL,
    activities_attended     INT          NOT NULL,
    activities_no_shows     INT          NOT NULL,
    activities_organized    INT          NOT NULL,
    activities_cancelled    INT          NOT NULL,
    rating                  DOUBLE       NULL,
    CONSTRAINT fk_user_stats_user
    FOREIGN KEY (id) REFERENCES users (id)
    ON DELETE CASCADE
);