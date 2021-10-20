package androidx.transition;

import android.graphics.Matrix;
import android.util.Log;
import android.view.View;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class af extends ae {
    private static Method a;
    private static boolean b;
    private static Method c;
    private static boolean d;

    af() {
    }

    @Override // androidx.transition.ah
    public void a(View view, Matrix matrix) {
        a();
        Method method = a;
        if (method != null) {
            try {
                method.invoke(view, matrix);
            } catch (IllegalAccessException unused) {
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e.getCause());
            }
        }
    }

    @Override // androidx.transition.ah
    public void b(View view, Matrix matrix) {
        b();
        Method method = c;
        if (method != null) {
            try {
                method.invoke(view, matrix);
            } catch (IllegalAccessException unused) {
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e.getCause());
            }
        }
    }

    private void a() {
        if (!b) {
            try {
                a = View.class.getDeclaredMethod("transformMatrixToGlobal", Matrix.class);
                a.setAccessible(true);
            } catch (NoSuchMethodException e) {
                Log.i("ViewUtilsApi21", "Failed to retrieve transformMatrixToGlobal method", e);
            }
            b = true;
        }
    }

    private void b() {
        if (!d) {
            try {
                c = View.class.getDeclaredMethod("transformMatrixToLocal", Matrix.class);
                c.setAccessible(true);
            } catch (NoSuchMethodException e) {
                Log.i("ViewUtilsApi21", "Failed to retrieve transformMatrixToLocal method", e);
            }
            d = true;
        }
    }
}
