package androidx.transition;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.util.Property;
import android.view.View;
import androidx.core.f.r;
import java.lang.reflect.Field;

/* access modifiers changed from: package-private */
public class ad {
    static final Property<View, Float> a = new Property<View, Float>(Float.class, "translationAlpha") {
        /* class androidx.transition.ad.AnonymousClass1 */

        /* renamed from: a */
        public Float get(View view) {
            return Float.valueOf(ad.c(view));
        }

        /* renamed from: a */
        public void set(View view, Float f) {
            ad.a(view, f.floatValue());
        }
    };
    static final Property<View, Rect> b = new Property<View, Rect>(Rect.class, "clipBounds") {
        /* class androidx.transition.ad.AnonymousClass2 */

        /* renamed from: a */
        public Rect get(View view) {
            return r.z(view);
        }

        /* renamed from: a */
        public void set(View view, Rect rect) {
            r.a(view, rect);
        }
    };
    private static final ah c;
    private static Field d;
    private static boolean e;

    static {
        if (Build.VERSION.SDK_INT >= 22) {
            c = new ag();
        } else if (Build.VERSION.SDK_INT >= 21) {
            c = new af();
        } else if (Build.VERSION.SDK_INT >= 19) {
            c = new ae();
        } else {
            c = new ah();
        }
    }

    static ac a(View view) {
        if (Build.VERSION.SDK_INT >= 18) {
            return new ab(view);
        }
        return aa.d(view);
    }

    static al b(View view) {
        if (Build.VERSION.SDK_INT >= 18) {
            return new ak(view);
        }
        return new aj(view.getWindowToken());
    }

    static void a(View view, float f) {
        c.a(view, f);
    }

    static float c(View view) {
        return c.a(view);
    }

    static void d(View view) {
        c.b(view);
    }

    static void e(View view) {
        c.c(view);
    }

    static void a(View view, int i) {
        a();
        Field field = d;
        if (field != null) {
            try {
                d.setInt(view, i | (field.getInt(view) & -13));
            } catch (IllegalAccessException unused) {
            }
        }
    }

    static void a(View view, Matrix matrix) {
        c.a(view, matrix);
    }

    static void b(View view, Matrix matrix) {
        c.b(view, matrix);
    }

    static void a(View view, int i, int i2, int i3, int i4) {
        c.a(view, i, i2, i3, i4);
    }

    private static void a() {
        if (!e) {
            try {
                d = View.class.getDeclaredField("mViewFlags");
                d.setAccessible(true);
            } catch (NoSuchFieldException unused) {
                Log.i("ViewUtils", "fetchViewFlagsField: ");
            }
            e = true;
        }
    }
}
