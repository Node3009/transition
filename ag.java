package androidx.transition;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ag extends af {
    private static Method a;
    private static boolean b;

    ag() {
    }

    @Override // androidx.transition.ah
    public void a(View view, int i, int i2, int i3, int i4) {
        a();
        Method method = a;
        if (method != null) {
            try {
                method.invoke(view, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4));
            } catch (IllegalAccessException unused) {
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e.getCause());
            }
        }
    }

    @SuppressLint({"PrivateApi"})
    private void a() {
        if (!b) {
            try {
                a = View.class.getDeclaredMethod("setLeftTopRightBottom", Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE);
                a.setAccessible(true);
            } catch (NoSuchMethodException e) {
                Log.i("ViewUtilsApi22", "Failed to retrieve setLeftTopRightBottom method", e);
            }
            b = true;
        }
    }
}
