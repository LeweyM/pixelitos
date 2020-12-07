package pixel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {

  public static List<Change> listOfChanges(Change ...e) {
    final ArrayList<Change> changes = new ArrayList<>();
    changes.addAll(Arrays.asList(e));
    return changes;
  }

}
