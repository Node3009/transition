package androidx.transition;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewOverlay;

/* access modifiers changed from: package-private */
public class ab implements ac {
    private final ViewOverlay a;

    ab(View view) {
        this.a = view.getOverlay();
    }

    @Override // androidx.transition.ac
    public void a(Drawable drawable) {
        this.a.add(drawable);
    }

    @Override // androidx.transition.ac
    public void b(Drawable drawable) {
        this.a.remove(drawable);
    }
}
