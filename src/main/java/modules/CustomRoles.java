package modules;

import mysql.hibernate.entity.guild.GuildEntity;
import net.dv8tion.jda.api.entities.Guild;

import java.util.ArrayList;
import java.util.Map;

public class CustomRoles {

    public static void cleanUp(GuildEntity guildEntity, Guild guild) {
        guildEntity.beginTransaction();
        Map<Long, Long> customRoles = guildEntity.getCustomRoles();
        for (long userId : new ArrayList<>(customRoles.keySet())) {
            long roleId = customRoles.get(userId);
            if (guild.getRoleById(roleId) == null) {
                customRoles.remove(userId);
            }
        }
        guildEntity.commitTransaction();
    }

}
