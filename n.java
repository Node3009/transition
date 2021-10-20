package androidx.transition;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import androidx.core.f.r;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

public class n {
    static ArrayList<ViewGroup> a = new ArrayList<>();
    private static Transition b = new b();
    private static ThreadLocal<WeakReference<androidx.a.a<ViewGroup, ArrayList<Transition>>>> c = new ThreadLocal<>();

    static androidx.a.a<ViewGroup, ArrayList<Transition>> a() {
        androidx.a.a<ViewGroup, ArrayList<Transition>> aVar;
        WeakReference<androidx.a.a<ViewGroup, ArrayList<Transition>>> weakReference = c.get();
        if (weakReference != null && (aVar = weakReference.get()) != null) {
            return aVar;
        }
        androidx.a.a<ViewGroup, ArrayList<Transition>> aVar2 = new androidx.a.a<>();
        c.set(new WeakReference<>(aVar2));
        return aVar2;
    }

    private static void b(ViewGroup viewGroup, Transition transition) {
        if (transition != null && viewGroup != null) {
            a aVar = new a(transition, viewGroup);
            viewGroup.addOnAttachStateChangeListener(aVar);
            viewGroup.getViewTreeObserver().addOnPreDrawListener(aVar);
        }
    }

    /* access modifiers changed from: private */
    public static class a implements View.OnAttachStateChangeListener, ViewTreeObserver.OnPreDrawListener {
        Transition a;
        ViewGroup b;

        public void onViewAttachedToWindow(View view) {
        }

        a(Transition transition, ViewGroup viewGroup) {
            this.a = transition;
            this.b = viewGroup;
        }

        private void a() {
            this.b.getViewTreeObserver().removeOnPreDrawListener(this);
            this.b.removeOnAttachStateChangeListener(this);
        }

        public void onViewDetachedFromWindow(View view) {
            a();
            n.a.remove(this.b);
            ArrayList<Transition> arrayList = n.a().get(this.b);
            if (arrayList != null && arrayList.size() > 0) {
                Iterator<Transition> it = arrayList.iterator();
                while (it.hasNext()) {
                    it.next().resume(this.b);
                }
            }
            this.a.clearValues(true);
        }

        public boolean onPreDraw() {
            a();
            if (!n.a.remove(this.b)) {
                return true;
            }
            final androidx.a.a<ViewGroup, ArrayList<Transition>> a2 = n.a();
            ArrayList<Transition> arrayList = a2.get(this.b);
            ArrayList arrayList2 = null;
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                a2.put(this.b, arrayList);
            } else if (arrayList.size() > 0) {
                arrayList2 = new ArrayList(arrayList);
            }
            arrayList.add(this.a);
            this.a.addListener(new m() {
                /* class androidx.transition.n.a.AnonymousClass1 */

                @Override // androidx.transition.m, androidx.transition.Transition.d
                public void b(Transition transition) {
                    ((ArrayList) a2.get(a.this.b)).remove(transition);
                }
            });
            this.a.captureValues(this.b, false);
            if (arrayList2 != null) {
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    ((Transition) it.next()).resume(this.b);
                }
            }
            this.a.playTransition(this.b);
            return true;
        }
    }

    private static void c(ViewGroup viewGroup, Transition transition) {
        ArrayList<Transition> arrayList = a().get(viewGroup);
        if (arrayList != null && arrayList.size() > 0) {
            Iterator<Transition> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().pause(viewGroup);
            }
        }
        if (transition != null) {
            transition.captureValues(viewGroup, true);
        }
        k a2 = k.a(viewGroup);
        if (a2 != null) {
            a2.a();
        }
    }

    public static void a(ViewGroup viewGroup, Transition transition) {
        if (!a.contains(viewGroup) && r.x(viewGroup)) {
            a.add(viewGroup);
            if (transition == null) {
                transition = b;
            }
            Transition clone = transition.clone();
            c(viewGroup, clone);
            k.a(viewGroup, null);
            b(viewGroup, clone);
        }
    }
}
