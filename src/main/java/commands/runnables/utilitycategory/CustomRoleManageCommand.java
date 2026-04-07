package commands.runnables.utilitycategory;

import commands.CommandEvent;
import commands.listeners.CommandProperties;
import commands.runnables.ComponentMenuAbstract;
import constants.Emojis;
import core.ExceptionLogger;
import core.TextManager;
import core.atomicassets.AtomicRole;
import core.utils.ComponentsUtil;
import modules.CustomRoles;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.components.buttons.Button;
import net.dv8tion.jda.api.components.container.ContainerChildComponent;
import net.dv8tion.jda.api.components.section.Section;
import net.dv8tion.jda.api.components.textdisplay.TextDisplay;
import net.dv8tion.jda.api.entities.Member;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@CommandProperties(
        trigger = "customrolemanage",
        botGuildPermissions = Permission.MANAGE_ROLES,
        emoji = "\uD83D\uDD16",
        executableWithoutArgs = true
)
public class CustomRoleManageCommand extends ComponentMenuAbstract {

    private AtomicRole atomicRole;

    public CustomRoleManageCommand(Locale locale, String prefix) {
        super(locale, prefix);
    }

    @Override
    public boolean onTrigger(@NotNull CommandEvent event, @NotNull String args) {
        CustomRoles.cleanUp(getGuildEntity(), event.getGuild());
        atomicRole = new AtomicRole(event.getGuild().getIdLong(), getGuildEntity().getCustomRoles().get(event.getUser().getIdLong()));
        if (atomicRole.get().isEmpty()) {
            TextDisplay content = TextDisplay.of(getString("error_no_role"));
            drawMessageNew(ComponentsUtil.createCommandComponentTreeError(this, content))
                    .exceptionally(ExceptionLogger.get());
            return false;
        }

        registerListeners(event.getMember());
        return true;
    }

    @Draw(state = STATE_ROOT)
    public List<ContainerChildComponent> drawRoot(Member member) {
        setDescription(getString("description"));
        ArrayList<ContainerChildComponent> components = new ArrayList<>();

        Button nameButton = buttonSecondary(Emojis.MENU_EDIT, e -> {
            return true;
        });
        Section nameSection = Section.of(nameButton, TextDisplay.of(getString("root_name", atomicRole.getName(getLocale()))));
        components.add(nameSection);

        String colorText = atomicRole.get()
                .map(role -> role.getColors().getPrimary())
                .map(color -> String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()))
                .orElse(TextManager.getString(getLocale(), TextManager.GENERAL, "notset"));
        Button colorButton = buttonSecondary(Emojis.MENU_EDIT, e -> {
            return true;
        });
        Section colorSection = Section.of(colorButton, TextDisplay.of(getString("root_color", colorText)));
        components.add(colorSection);

        return components;
    }

}
