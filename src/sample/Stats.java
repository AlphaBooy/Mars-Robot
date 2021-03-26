package sample;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;

public class Stats {
    StringProperty name = new SimpleStringProperty();
    public static Callback<Stats, Observable[]> extractor() {
        return param -> new Observable[]{ param.name };
    }

    @Override
    public String toString() {
        return String.format("%s", name.get());
    }
}
