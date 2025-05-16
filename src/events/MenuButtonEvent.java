package events;

import java.util.EventObject;

public class MenuButtonEvent extends EventObject {
    private final String panelType;

    public MenuButtonEvent(Object source, String panelType) {
        super(source);
        this.panelType = panelType;
    }

    public String getPanelType() {
        return panelType;
    }
}
