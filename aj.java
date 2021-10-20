package androidx.transition;

import android.os.IBinder;

/* access modifiers changed from: package-private */
public class aj implements al {
    private final IBinder a;

    aj(IBinder iBinder) {
        this.a = iBinder;
    }

    public boolean equals(Object obj) {
        return (obj instanceof aj) && ((aj) obj).a.equals(this.a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }
}
