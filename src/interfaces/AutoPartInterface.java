package interfaces;

import core.items.AutoPart;

import java.util.ArrayList;

public interface AutoPartInterface {
    void setAutoPartList(ArrayList<Object> autoPartListList);

    // Create a new auto part
    void addAutoPart(AutoPart part);

    // Retrieve all auto parts
    ArrayList<AutoPart> getAllAutoParts();

    // Update part details
    void updateAutoPart(AutoPart part);

    // Remove a part (Soft delete)
    void removeAutoPart(AutoPart part);
}
