package androidx.transition;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/* access modifiers changed from: package-private */
public class u extends aa implements w {
    u(Context context, ViewGroup viewGroup, View view) {
        super(context, viewGroup, view);
    }

    static u a(ViewGroup viewGroup) {
        return (u) aa.d(viewGroup);
    }

    @Override // androidx.transition.w
    public void a(View view) {
        this.a.a(view);
    }

    @Override // androidx.transition.w
    public void b(View view) {
        this.a.b(view);
    }
}
