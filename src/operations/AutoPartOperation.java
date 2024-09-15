package operations;

import core.items.AutoPart;
import interfaces.AutoPartInterface;
import java.util.ArrayList;

public class AutoPartOperation implements AutoPartInterface {
    private final ArrayList<AutoPart> autoPartList;

    public AutoPartOperation() {
        this.autoPartList = new ArrayList<>();
    }

    // set autoPartList from reading objects from file
    public void setAutoPartList(ArrayList<Object> autoPartList) {
        for (Object o : autoPartList) {
            if (o instanceof AutoPart){
                this.autoPartList.add((AutoPart) o);
            }
        }
    }

    // add auto part to autoPartList
    @Override
    public void addAutoPart(AutoPart autoPart) {
        int lastID = 0;
        for (AutoPart a : autoPartList) {
            int id = (int) Integer.parseInt(a.getPartID().replace("p-", ""));
            if (id > lastID) {
                lastID = id;
            }
        }
        lastID = lastID + 1;
        autoPart.setPartID("p-" + lastID);
        autoPartList.add(autoPart);
        System.out.println("Auto Part added: " + autoPart.getSearchString());
    }

    // get all auto parts from autoPartList
    @Override
    public ArrayList<AutoPart> getAllAutoParts() {
        return autoPartList;
    }

    // update an auto part in autoPartList
    @Override
    public void updateAutoPart(AutoPart updatedPart) {
        for (int i = 0; i < autoPartList.size(); i++) {
            if (autoPartList.get(i).getPartID().equals(updatedPart.getPartID())) {
                autoPartList.set(i, updatedPart);
                System.out.println("Part updated: " + updatedPart.getSearchString());
                return;
            }
        }
        System.out.println("Part not found: " + updatedPart.getSearchString());
    }

    // remove an auto part from autoPartList
    @Override
    public void removeAutoPart(AutoPart part) {
        for (int i = 0; i < autoPartList.size(); i++) {
            if (autoPartList.get(i).getPartID().equals(part.getPartID())) {
                autoPartList.remove(i);
                System.out.println("Part removed: " + part.getSearchString());
                return;
            }
        }
        System.out.println("Part not found: " + part.getSearchString());
    }
}
