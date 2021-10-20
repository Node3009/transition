package androidx.transition;

import android.view.View;
import android.view.WindowId;

/* access modifiers changed from: package-private */
public class ak implements al {
    private final WindowId a;

    ak(View view) {
        this.a = view.getWindowId();
    }

    public boolean equals(Object obj) {
        return (obj instanceof ak) && ((ak) obj).a.equals(this.a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }
}
