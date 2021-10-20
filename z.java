package androidx.transition;

import android.util.Log;
import android.view.ViewGroup;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* access modifiers changed from: package-private */
public class z {
    private static Method a;
    private static boolean b;

    static void a(ViewGroup viewGroup, boolean z) {
        a();
        Method method = a;
        if (method != null) {
            try {
                method.invoke(viewGroup, Boolean.valueOf(z));
            } catch (IllegalAccessException e) {
                Log.i("ViewUtilsApi18", "Failed to invoke suppressLayout method", e);
            } catch (InvocationTargetException e2) {
                Log.i("ViewUtilsApi18", "Error invoking suppressLayout method", e2);
            }
        }
    }

    private static void a() {
        if (!b) {
            try {
                a = ViewGroup.class.getDeclaredMethod("suppressLayout", Boolean.TYPE);
                a.setAccessible(true);
            } catch (NoSuchMethodException e) {
                Log.i("ViewUtilsApi18", "Failed to retrieve suppressLayout method", e);
            }
            b = true;
        }
    }
}
