package androidx.transition;

import android.view.View;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class s {
    public final Map<String, Object> a = new HashMap();
    public View b;
    final ArrayList<Transition> c = new ArrayList<>();

    public boolean equals(Object obj) {
        if (!(obj instanceof s)) {
            return false;
        }
        s sVar = (s) obj;
        return this.b == sVar.b && this.a.equals(sVar.a);
    }

    public int hashCode() {
        return (this.b.hashCode() * 31) + this.a.hashCode();
    }

    public String toString() {
        String str = (("TransitionValues@" + Integer.toHexString(hashCode()) + ":\n") + "    view = " + this.b + "\n") + "    values:";
        for (String str2 : this.a.keySet()) {
            str = str + "    " + str2 + ": " + this.a.get(str2) + "\n";
        }
        return str;
    }
}
