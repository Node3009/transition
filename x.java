package androidx.transition;

import android.os.Build;
import android.view.ViewGroup;

class x {
    static w a(ViewGroup viewGroup) {
        if (Build.VERSION.SDK_INT >= 18) {
            return new v(viewGroup);
        }
        return u.a(viewGroup);
    }

    static void a(ViewGroup viewGroup, boolean z) {
        if (Build.VERSION.SDK_INT >= 18) {
            z.a(viewGroup, z);
        } else {
            y.a(viewGroup, z);
        }
    }
}
