package androidx.transition;

import android.util.Log;
import android.view.View;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ae extends ah {
    private static Method a;
    private static boolean b;
    private static Method c;
    private static boolean d;

    @Override // androidx.transition.ah
    public void b(View view) {
    }

    @Override // androidx.transition.ah
    public void c(View view) {
    }

    ae() {
    }

    @Override // androidx.transition.ah
    public void a(View view, float f) {
        a();
        Method method = a;
        if (method != null) {
            try {
                method.invoke(view, Float.valueOf(f));
            } catch (IllegalAccessException unused) {
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e.getCause());
            }
        } else {
            view.setAlpha(f);
        }
    }

    @Override // androidx.transition.ah
    public float a(View view) {
        b();
        Method method = c;
        if (method != null) {
            try {
                return ((Float) method.invoke(view, new Object[0])).floatValue();
            } catch (IllegalAccessException unused) {
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e.getCause());
            }
        }
        return super.a(view);
    }

    private void a() {
        if (!b) {
            try {
                a = View.class.getDeclaredMethod("setTransitionAlpha", Float.TYPE);
                a.setAccessible(true);
            } catch (NoSuchMethodException e) {
                Log.i("ViewUtilsApi19", "Failed to retrieve setTransitionAlpha method", e);
            }
            b = true;
        }
    }

    private void b() {
        if (!d) {
            try {
                c = View.class.getDeclaredMethod("getTransitionAlpha", new Class[0]);
                c.setAccessible(true);
            } catch (NoSuchMethodException e) {
                Log.i("ViewUtilsApi19", "Failed to retrieve getTransitionAlpha method", e);
            }
            d = true;
        }
    }
}
