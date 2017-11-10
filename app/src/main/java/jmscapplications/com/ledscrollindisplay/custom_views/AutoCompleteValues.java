package jmscapplications.com.ledscrollindisplay.custom_views;

import java.util.ArrayList;

public class AutoCompleteValues {
    ArrayList<String> values = new ArrayList();

    public void add(String value) {
        this.values.add(value);
        if (value.length() > 10) {
            this.values.remove(0);
        }
    }

    public String[] getValues() {
        return (String[]) this.values.toArray(new String[this.values.size()]);
    }
}
