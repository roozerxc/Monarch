package constants;

import java.awt.*;

public interface Settings {

    int TIME_OUT_MINUTES = 10;
    long FISHERY_MAX = 9_999_999_999_999_999L;
    int FISHERY_GEAR_MAX = 999_999;
    int FISHERY_SHARES_MAX = 99_999_999;
    int FISHERY_SHARES_FEES = 4;
    int COOLDOWN_TIME_SEC = 30;
    int COOLDOWN_MAX_ALLOWED = 5;
    long[] PATREON_ROLE_IDS = { };
    Color PREMIUM_COLOR = new Color(0, 0, 255);
    int FISHERY_DESPAWN_MINUTES = 1;
    int FISHERY_POWERUP_TIMEOUT_MINUTES = 5;

    String[] NSFW_FILTERS = {
            // The MOST obvious should be added in the server config, look at
            // the commit history if you do not want to see cursed shit.

            // By default this array is empty for Monarch...
    };

    String[] NSFW_STRICT_FILTERS = {
            // Ditto with NSFW_FILTERS.
    };

}
