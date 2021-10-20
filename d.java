package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.f.r;

public class d extends ai {
    public d(int i) {
        a(i);
    }

    public d() {
    }

    @Override // androidx.transition.Transition, androidx.transition.ai
    public void captureStartValues(s sVar) {
        super.captureStartValues(sVar);
        sVar.a.put("android:fade:transitionAlpha", Float.valueOf(ad.c(sVar.b)));
    }

    private Animator a(final View view, float f, float f2) {
        if (f == f2) {
            return null;
        }
        ad.a(view, f);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, ad.a, f2);
        ofFloat.addListener(new a(view));
        addListener(new m() {
            /* class androidx.transition.d.AnonymousClass1 */

            @Override // androidx.transition.m, androidx.transition.Transition.d
            public void b(Transition transition) {
                ad.a(view, 1.0f);
                ad.e(view);
                transition.removeListener(this);
            }
        });
        return ofFloat;
    }

    @Override // androidx.transition.ai
    public Animator a(ViewGroup viewGroup, View view, s sVar, s sVar2) {
        float f = 0.0f;
        float a2 = a(sVar, 0.0f);
        if (a2 != 1.0f) {
            f = a2;
        }
        return a(view, f, 1.0f);
    }

    @Override // androidx.transition.ai
    public Animator b(ViewGroup viewGroup, View view, s sVar, s sVar2) {
        ad.d(view);
        return a(view, a(sVar, 1.0f), 0.0f);
    }

    private static float a(s sVar, float f) {
        Float f2;
        return (sVar == null || (f2 = (Float) sVar.a.get("android:fade:transitionAlpha")) == null) ? f : f2.floatValue();
    }

    /* access modifiers changed from: private */
    public static class a extends AnimatorListenerAdapter {
        private final View a;
        private boolean b = false;

        a(View view) {
            this.a = view;
        }

        public void onAnimationStart(Animator animator) {
            if (r.r(this.a) && this.a.getLayerType() == 0) {
                this.b = true;
                this.a.setLayerType(2, null);
            }
        }

        public void onAnimationEnd(Animator animator) {
            ad.a(this.a, 1.0f);
            if (this.b) {
                this.a.setLayerType(0, null);
            }
        }
    }
}
